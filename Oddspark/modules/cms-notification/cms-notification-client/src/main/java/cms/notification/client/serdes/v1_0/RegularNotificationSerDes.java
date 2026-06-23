/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.client.serdes.v1_0;

import cms.notification.client.dto.v1_0.Category;
import cms.notification.client.dto.v1_0.Competition;
import cms.notification.client.dto.v1_0.RegularNotification;
import cms.notification.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author ADMIN
 * @generated
 */
@Generated("")
public class RegularNotificationSerDes {

	public static RegularNotification toDTO(String json) {
		RegularNotificationJSONParser regularNotificationJSONParser =
			new RegularNotificationJSONParser();

		return regularNotificationJSONParser.parseToDTO(json);
	}

	public static RegularNotification[] toDTOs(String json) {
		RegularNotificationJSONParser regularNotificationJSONParser =
			new RegularNotificationJSONParser();

		return regularNotificationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(RegularNotification regularNotification) {
		if (regularNotification == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (regularNotification.getCategories() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categories\": ");

			sb.append("[");

			for (int i = 0; i < regularNotification.getCategories().length;
				 i++) {

				sb.append(
					String.valueOf(regularNotification.getCategories()[i]));

				if ((i + 1) < regularNotification.getCategories().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (regularNotification.getCompetitions() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"competitions\": ");

			sb.append("[");

			for (int i = 0; i < regularNotification.getCompetitions().length;
				 i++) {

				sb.append(
					String.valueOf(regularNotification.getCompetitions()[i]));

				if ((i + 1) < regularNotification.getCompetitions().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (regularNotification.getDisplayDatetime() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDatetime\": ");

			sb.append("\"");

			sb.append(_escape(regularNotification.getDisplayDatetime()));

			sb.append("\"");
		}

		if (regularNotification.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(regularNotification.getId());
		}

		if (regularNotification.getIsNew() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"isNew\": ");

			sb.append(regularNotification.getIsNew());
		}

		if (regularNotification.getNoticeTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"noticeTitle\": ");

			sb.append("\"");

			sb.append(_escape(regularNotification.getNoticeTitle()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RegularNotificationJSONParser regularNotificationJSONParser =
			new RegularNotificationJSONParser();

		return regularNotificationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		RegularNotification regularNotification) {

		if (regularNotification == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (regularNotification.getCategories() == null) {
			map.put("categories", null);
		}
		else {
			map.put(
				"categories",
				String.valueOf(regularNotification.getCategories()));
		}

		if (regularNotification.getCompetitions() == null) {
			map.put("competitions", null);
		}
		else {
			map.put(
				"competitions",
				String.valueOf(regularNotification.getCompetitions()));
		}

		if (regularNotification.getDisplayDatetime() == null) {
			map.put("displayDatetime", null);
		}
		else {
			map.put(
				"displayDatetime",
				String.valueOf(regularNotification.getDisplayDatetime()));
		}

		if (regularNotification.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(regularNotification.getId()));
		}

		if (regularNotification.getIsNew() == null) {
			map.put("isNew", null);
		}
		else {
			map.put("isNew", String.valueOf(regularNotification.getIsNew()));
		}

		if (regularNotification.getNoticeTitle() == null) {
			map.put("noticeTitle", null);
		}
		else {
			map.put(
				"noticeTitle",
				String.valueOf(regularNotification.getNoticeTitle()));
		}

		return map;
	}

	public static class RegularNotificationJSONParser
		extends BaseJSONParser<RegularNotification> {

		@Override
		protected RegularNotification createDTO() {
			return new RegularNotification();
		}

		@Override
		protected RegularNotification[] createDTOArray(int size) {
			return new RegularNotification[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "categories")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "competitions")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "displayDatetime")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "isNew")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "noticeTitle")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			RegularNotification regularNotification, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "categories")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					Category[] categoriesArray =
						new Category[jsonParserFieldValues.length];

					for (int i = 0; i < categoriesArray.length; i++) {
						categoriesArray[i] = CategorySerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					regularNotification.setCategories(categoriesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "competitions")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					Competition[] competitionsArray =
						new Competition[jsonParserFieldValues.length];

					for (int i = 0; i < competitionsArray.length; i++) {
						competitionsArray[i] = CompetitionSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					regularNotification.setCompetitions(competitionsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayDatetime")) {
				if (jsonParserFieldValue != null) {
					regularNotification.setDisplayDatetime(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					regularNotification.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "isNew")) {
				if (jsonParserFieldValue != null) {
					regularNotification.setIsNew((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "noticeTitle")) {
				if (jsonParserFieldValue != null) {
					regularNotification.setNoticeTitle(
						(String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1151875924