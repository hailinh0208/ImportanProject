/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.helper;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Utility methods for filtering ObjectEntry records for the regular
 * notification API.
 */
public final class NotificationEntryFilter {

	/**
	 * Returns true if the entry should be included in the regular notification
	 * response. An entry is included when all of the following hold:
	 * - importantNotice is false (important notices use a separate API)
	 * - publishStatus is true
	 * - displayDate is set and is not in the future
	 * - competitions field contains the requested competitionKey
	 */
	public static boolean isMatchingEntry(
		ObjectEntry entry, String competitionKey, Date now) {

		Map<String, Serializable> values = entry.getValues();

		if (Boolean.TRUE.equals(values.get("importantNotice"))) {
			return false;
		}

		if (!Boolean.TRUE.equals(values.get("publishStatus"))) {
			return false;
		}

		Date displayDate = entry.getDisplayDate();

		if ((displayDate == null) || displayDate.after(now)) {
			return false;
		}

		return containsKey(values.get("competitions"), competitionKey);
	}

	/**
	 * Returns true if the given multi-value field (stored as a JSON array or
	 * a comma-separated string) contains the targetKey.
	 */
	public static boolean containsKey(Object value, String targetKey) {
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

	private NotificationEntryFilter() {
	}

}
