/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.resource.v1_0;

import cms.notification.dto.v1_0.MessageInfo;
import cms.notification.dto.v1_0.RegularNotification;
import cms.notification.dto.v1_0.RegularNotificationResponse;
import cms.notification.internal.constants.NotificationConstants;
import cms.notification.internal.exception.NotificationBadRequestException;
import cms.notification.internal.helper.ListTypeHelper;
import cms.notification.internal.helper.NotificationBuilder;
import cms.notification.internal.helper.NotificationEntryFilter;
import cms.notification.resource.v1_0.NotificationResource;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author ADMIN
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/notification.properties",
	scope = ServiceScope.PROTOTYPE, service = NotificationResource.class
)
public class NotificationResourceImpl extends BaseNotificationResourceImpl {

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	/**
	 * Returns a paginated list of published regular notices filtered by
	 * competition type. Entries are sorted by displayDate descending, then
	 * by objectEntryId descending as a tiebreaker.
	 *
	 * @param competition 1-based index into the OP_LIST_COMPETITION_TYPE picklist
	 * @param limit       maximum number of results; defaults to 3 when null or <= 0
	 */
	@Override
	public RegularNotificationResponse getRegularNotification(Integer competition, Integer limit) throws Exception {
		// Validate: competition parameter must be present and within the allowed range
		if (competition == null) {
			throw new NotificationBadRequestException(
				NotificationConstants.ERROR_MISSING_COMPETITION,
				"競技タイプを入力してください。");
		}

		if (competition < NotificationConstants.MIN_COMPETITION ||
				competition > NotificationConstants.MAX_COMPETITION) {
			throw new NotificationBadRequestException(
					NotificationConstants.ERROR_INVALID_COMPETITION,
					"正しい競技タイプを入力してください。");
		}

		// Resolve company context and preferred locale from the incoming request
		long companyId = contextCompany.getCompanyId();
		Locale locale = contextAcceptLanguage.getPreferredLocale();

		// Load the competition picklist and resolve the entry matching the requested index.
		List<ListTypeEntry> competitionEntries = ListTypeHelper.loadEntries(
			NotificationConstants.ERC_LIST_COMPETITION_TYPE, companyId,
			_listTypeDefinitionLocalService, _listTypeEntryLocalService);

		ListTypeEntry competitionEntry = competitionEntries.get(competition - 1);
		String competitionKey = competitionEntry.getKey();

		// Load the category picklist as a key→entry map for O(1) lookup when building DTOs
		Map<String, ListTypeEntry> categoryEntries = ListTypeHelper.loadEntryMap(
			NotificationConstants.ERC_LIST_NOTICE_CATEGORIES, companyId,
			_listTypeDefinitionLocalService, _listTypeEntryLocalService);

		// Fetch the Object Definition; return empty response if it has not been deployed yet
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService
				.fetchObjectDefinitionByExternalReferenceCode(
					NotificationConstants.ERC_OBJECT_NOTICE_MASTER, companyId);

		if (objectDefinition == null) {
			return _emptyResponse();
		}

		// Apply default limit when the caller does not specify one
		int maxResults = (limit != null && limit > 0) ? limit : NotificationConstants.DEFAULT_LIMIT;

		// Adjust "now" to match the company timezone so displayDate comparisons
		// are consistent regardless of the JVM timezone (GMT) vs. the portal timezone.
		// Admin sets the correct timezone in Control Panel → Instance Settings → Localization.

		TimeZone companyTimeZone = contextCompany.getTimeZone();
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis + companyTimeZone.getOffset(nowMillis));

		// Fetch all entries, filter by business rules, sort, cap, and map to DTOs
		List<RegularNotification> items =
			_objectEntryLocalService.getObjectEntries(
				0, objectDefinition.getObjectDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS
			).stream(
			).filter(
				entry -> NotificationEntryFilter.isMatchingEntry(
					entry, competitionKey, now)
			).sorted(
				(a, b) -> _compareByDisplayDateThenId(a, b)
			).limit(
				maxResults
			).map(
				entry -> NotificationBuilder.build(
					entry, competition, competitionEntry, categoryEntries,
					locale, now)
			).collect(
				Collectors.toList()
			);

		// Build and return the final response
		RegularNotificationResponse response = new RegularNotificationResponse();

		response.setRegularNotifications(items.toArray(new RegularNotification[0]));
		response.setReturnedCount(items.size());
		response.setMessageInfo((MessageInfo)null);

		return response;
	}

	/**
	 * Sorts entries by displayDate descending, using objectEntryId descending
	 * as a stable tiebreaker when both dates are equal.
	 */
	private int _compareByDisplayDateThenId(ObjectEntry a, ObjectEntry b) {
		Date dateA = a.getDisplayDate();
		Date dateB = b.getDisplayDate();

		if ((dateA != null) && (dateB != null)) {
			int cmp = dateB.compareTo(dateA);

			if (cmp != 0) {
				return cmp;
			}
		}

		return Long.compare(b.getObjectEntryId(), a.getObjectEntryId());
	}

	/**
	 * Returns an empty response with zero results, used when the Object
	 * Definition does not exist in the current company.
	 */
	private RegularNotificationResponse _emptyResponse() {
		RegularNotificationResponse response = new RegularNotificationResponse();

		response.setRegularNotifications(new RegularNotification[0]);
		response.setReturnedCount(0);
		response.setMessageInfo((MessageInfo)null);

		return response;
	}
}
