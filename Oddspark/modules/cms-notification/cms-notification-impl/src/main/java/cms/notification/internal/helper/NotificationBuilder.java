/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.helper;

import cms.notification.dto.v1_0.Category;
import cms.notification.dto.v1_0.Competition;
import cms.notification.dto.v1_0.RegularNotification;
import cms.notification.internal.constants.NotificationConstants;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Utility methods for building notification DTOs from raw ObjectEntry data.
 */
public final class NotificationBuilder {

	/**
	 * Builds a RegularNotification DTO from a single ObjectEntry.
	 * Sets isNew=true if displayDate is within the last 6 hours relative to now.
	 */
	public static RegularNotification build(ObjectEntry entry, int competitionCode, ListTypeEntry competitionEntry,
		Map<String, ListTypeEntry> categoryEntries, Locale locale, Date now) {

		Map<String, Serializable> values = entry.getValues();

		RegularNotification notification = new RegularNotification();

		notification.setId(entry.getObjectEntryId());
		notification.setNoticeTitle((String)values.get("noticeTitle"));

		Date displayDate = entry.getDisplayDate();

		if (displayDate != null) {
			notification.setDisplayDatetime(
				new SimpleDateFormat(NotificationConstants.DATE_FORMAT).format(
					displayDate));
			notification.setIsNew(
				(now.getTime() - displayDate.getTime()) <=
					NotificationConstants.SIX_HOURS_MS);
		}

		notification.setCategories(_buildCategories(values.get("categories"), categoryEntries, locale));

		Competition competition = new Competition();

		competition.setKey((long)competitionCode);
		competition.setName(competitionEntry.getName(locale));

		notification.setCompetitions(new Competition[] {competition});

		return notification;
	}

	/**
	 * Parses a multi-value field (JSON array or comma-separated string) into
	 * an array of Category DTOs, resolved via the categoryEntries map.
	 * Keys not found in the map are silently skipped.
	 */
	private static Category[] _buildCategories(Object value, Map<String, ListTypeEntry> categoryEntries, Locale locale) {

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

	private NotificationBuilder() {
	}

}
