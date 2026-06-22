/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.resource.v1_0;

import cms.notification.dto.v1_0.Category;
import cms.notification.dto.v1_0.Competition;
import cms.notification.dto.v1_0.MessageInfo;
import cms.notification.dto.v1_0.RegularNotification;
import cms.notification.dto.v1_0.RegularNotificationResponse;
import cms.notification.resource.v1_0.NotificationResource;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
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

	@Override
	public RegularNotificationResponse getRegularNotification(
			Integer competition, Integer limit)
		throws Exception {

		if (competition == null) {
			_throwBadRequest("MDBCME0001", "競技タイプを入力してください。");
		}

		long companyId = contextCompany.getCompanyId();
		Locale locale = contextAcceptLanguage.getPreferredLocale();

		// Load competition entries in order — param 1 = 1st entry, 2 = 2nd, etc.
		List<ListTypeEntry> competitionEntryList = _loadListTypeEntries(
			"OP_LIST_COMPETITION_TYPE", companyId);

		if (competitionEntryList.isEmpty() ||
			competition < 1 ||
			competition > 5) {

			_throwBadRequest("MDBCME0014", "正しい競技タイプを入力してください。");
		}

		ListTypeEntry competitionEntry = competitionEntryList.get(
			competition - 1);
		String competitionKey = competitionEntry.getKey();

		Map<String, ListTypeEntry> categoryEntries = _loadListTypeEntryMap(
			"OP_LIST_NOTICE_CATEGORIES", companyId);

		int maxResults = (limit != null && limit > 0) ? limit : 3;

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService
				.fetchObjectDefinitionByExternalReferenceCode(
					"OP_OBJ_NOTICE_MASTER", companyId);

		if (objectDefinition == null) {
			return _emptyResponse();
		}

		List<ObjectEntry> allEntries =
			_objectEntryLocalService.getObjectEntries(
				0, objectDefinition.getObjectDefinitionId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Date now = new Date();

		List<ObjectEntry> filtered = allEntries.stream(
		).filter(
			entry -> _isMatchingEntry(entry, competitionKey, now)
		).sorted(
			(a, b) -> {
				Date dateA = a.getDisplayDate();
				Date dateB = b.getDisplayDate();

				if (dateA != null && dateB != null) {
					int cmp = dateB.compareTo(dateA);

					if (cmp != 0) {
						return cmp;
					}
				}

				return Long.compare(
					b.getObjectEntryId(), a.getObjectEntryId());
			}
		).limit(
			maxResults
		).collect(
			Collectors.toList()
		);

		List<RegularNotification> items = filtered.stream(
		).map(
			entry -> _buildNotification(
				entry, competition, competitionEntry, categoryEntries, locale,
				now)
		).collect(
			Collectors.toList()
		);

		RegularNotificationResponse response = new RegularNotificationResponse();

		response.setRegularNotifications(
			items.toArray(new RegularNotification[0]));
		response.setReturnedCount(items.size());
		response.setMessageInfo((MessageInfo)null);

		return response;
	}

	private Category[] _buildCategories(
		Object value, Map<String, ListTypeEntry> categoryEntries, Locale locale) {

		if (value == null) {
			return new Category[0];
		}

		String raw = String.valueOf(value).trim();

		if (raw.isEmpty() || raw.equals("null")) {
			return new Category[0];
		}

		List<String> keys = new ArrayList<>();

		try {
			if (raw.startsWith("[")) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray(raw);

				for (int i = 0; i < jsonArray.length(); i++) {
					keys.add(jsonArray.getString(i));
				}
			}
			else {
				for (String part : raw.split(",")) {
					keys.add(part.trim());
				}
			}
		}
		catch (Exception e) {
			keys.add(raw);
		}

		return keys.stream(
		).filter(
			categoryEntries::containsKey
		).map(
			k -> {
				ListTypeEntry entry = categoryEntries.get(k);

				Category cat = new Category();

				cat.setKey(Long.parseLong(entry.getKey()));
				cat.setName(entry.getName(locale));

				return cat;
			}
		).toArray(
			Category[]::new
		);
	}

	private RegularNotification _buildNotification(
		ObjectEntry entry, int competitionCode,
		ListTypeEntry competitionEntry,
		Map<String, ListTypeEntry> categoryEntries, Locale locale, Date now) {

		Map<String, Serializable> values = entry.getValues();

		RegularNotification notification = new RegularNotification();

		notification.setId(entry.getObjectEntryId());
		notification.setNoticeTitle((String)values.get("noticeTitle"));

		Date displayDate = entry.getDisplayDate();

		if (displayDate != null) {
			notification.setDisplayDatetime(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(displayDate));
			notification.setIsNew(
				(now.getTime() - displayDate.getTime()) <= _SIX_HOURS_MS);
		}

		notification.setCategories(
			_buildCategories(values.get("categories"), categoryEntries, locale));

		Competition competition = new Competition();

		competition.setKey((long)competitionCode);
		competition.setName(competitionEntry.getName(locale));

		notification.setCompetitions(new Competition[] {competition});

		return notification;
	}

	private boolean _containsKey(Object value, String targetKey) {
		if (value == null) {
			return false;
		}

		String raw = String.valueOf(value).trim();

		if (raw.isEmpty() || raw.equals("null")) {
			return false;
		}

		try {
			if (raw.startsWith("[")) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray(raw);

				for (int i = 0; i < jsonArray.length(); i++) {
					if (targetKey.equals(jsonArray.getString(i))) {
						return true;
					}
				}
			}
			else {
				for (String part : raw.split(",")) {
					if (targetKey.equals(part.trim())) {
						return true;
					}
				}
			}
		}
		catch (Exception e) {
			return raw.contains(targetKey);
		}

		return false;
	}

	private RegularNotificationResponse _emptyResponse() {
		RegularNotificationResponse response = new RegularNotificationResponse();

		response.setRegularNotifications(new RegularNotification[0]);
		response.setReturnedCount(0);
		response.setMessageInfo((MessageInfo)null);

		return response;
	}

	private boolean _isMatchingEntry(
		ObjectEntry entry, String competitionKey, Date now) {

		Map<String, Serializable> values = entry.getValues();

		Boolean importantNotice = (Boolean)values.get("importantNotice");

		if (Boolean.TRUE.equals(importantNotice)) {
			return false;
		}

		Boolean publishStatus = (Boolean)values.get("publishStatus");

		if (!Boolean.TRUE.equals(publishStatus)) {
			return false;
		}

		Date displayDate = entry.getDisplayDate();

		if ((displayDate == null) || displayDate.after(now)) {
			return false;
		}

		return _containsKey(values.get("competitions"), competitionKey);
	}

	private List<ListTypeEntry> _loadListTypeEntries(
		String definitionErc, long companyId) {

		ListTypeDefinition definition =
			_listTypeDefinitionLocalService
				.fetchListTypeDefinitionByExternalReferenceCode(
					definitionErc, companyId);

		if (definition == null) {
			return Collections.emptyList();
		}

		return _listTypeEntryLocalService.getListTypeEntries(
			definition.getListTypeDefinitionId());
	}

	private Map<String, ListTypeEntry> _loadListTypeEntryMap(
		String definitionErc, long companyId) {

		return _loadListTypeEntries(
			definitionErc, companyId
		).stream(
		).collect(
			Collectors.toMap(ListTypeEntry::getKey, Function.identity())
		);
	}

	private void _throwBadRequest(String messageId, String messageBody) {
		String body = JSONUtil.put(
			"traceId", UUID.randomUUID().toString()
		).put(
			"messageInfo", JSONUtil.put(
				"messageId", messageId
			).put(
				"messageBody", messageBody
			)
		).toString();

		throw new WebApplicationException(
			Response.status(
				Response.Status.BAD_REQUEST
			).entity(
				body
			).type(
				MediaType.APPLICATION_JSON
			).build());
	}

	private static final long _SIX_HOURS_MS = 6L * 60 * 60 * 1000;

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}
