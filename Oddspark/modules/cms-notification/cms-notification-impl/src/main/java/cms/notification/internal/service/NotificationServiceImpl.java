/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.service;

import cms.notification.dto.v1_0.MessageInfo;
import cms.notification.dto.v1_0.RegularNotification;
import cms.notification.dto.v1_0.RegularNotificationResponse;
import cms.notification.internal.constants.NotificationConstants;
import cms.notification.internal.helper.ListTypeHelper;
import cms.notification.internal.helper.NotificationBuilder;
import cms.notification.internal.helper.NotificationEntryFilter;

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

@Component(service = NotificationService.class)
public class NotificationServiceImpl implements NotificationService {

	@Override
	public RegularNotificationResponse getRegularNotifications(int competition, int limit, long companyId, Locale locale, TimeZone timeZone) throws Exception {
		List<ListTypeEntry> competitionEntries = ListTypeHelper.loadEntries(
			NotificationConstants.ERC_LIST_COMPETITION_TYPE, companyId,
			_listTypeDefinitionLocalService, _listTypeEntryLocalService);

		ListTypeEntry competitionEntry = competitionEntries.get(competition - 1);
		String competitionKey = competitionEntry.getKey();

		Map<String, ListTypeEntry> categoryEntries = ListTypeHelper.loadEntryMap(
			NotificationConstants.ERC_LIST_NOTICE_CATEGORIES, companyId,
			_listTypeDefinitionLocalService, _listTypeEntryLocalService);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService
				.fetchObjectDefinitionByExternalReferenceCode(
					NotificationConstants.ERC_OBJECT_NOTICE_MASTER, companyId);

		if (objectDefinition == null) {
			return _emptyResponse();
		}

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis + timeZone.getOffset(nowMillis));

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
				limit
			).map(
				entry -> NotificationBuilder.build(
					entry, competition, competitionEntry, categoryEntries,
					locale, now)
			).collect(
				Collectors.toList()
			);

		RegularNotificationResponse response = new RegularNotificationResponse();

		response.setRegularNotifications(items.toArray(new RegularNotification[0]));
		response.setReturnedCount(items.size());
		response.setMessageInfo((MessageInfo)null);

		return response;
	}

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

	private RegularNotificationResponse _emptyResponse() {
		RegularNotificationResponse response = new RegularNotificationResponse();

		response.setRegularNotifications(new RegularNotification[0]);
		response.setReturnedCount(0);
		response.setMessageInfo((MessageInfo)null);

		return response;
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}