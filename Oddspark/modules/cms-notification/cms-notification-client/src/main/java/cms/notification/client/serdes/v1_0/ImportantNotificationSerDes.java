/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.client.serdes.v1_0;

import cms.notification.client.dto.v1_0.DisplayPage;
import cms.notification.client.dto.v1_0.ImportantNotification;
import cms.notification.client.dto.v1_0.Priority;
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
public class ImportantNotificationSerDes {

	public static ImportantNotification toDTO(String json) {
		ImportantNotificationJSONParser importantNotificationJSONParser =
			new ImportantNotificationJSONParser();

		return importantNotificationJSONParser.parseToDTO(json);
	}

	public static ImportantNotification[] toDTOs(String json) {
		ImportantNotificationJSONParser importantNotificationJSONParser =
			new ImportantNotificationJSONParser();

		return importantNotificationJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ImportantNotification importantNotification) {
		if (importantNotification == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (importantNotification.getDisplayBeforeOrAfterLogin() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayBeforeOrAfterLogin\": ");

			sb.append("\"");

			sb.append(
				_escape(importantNotification.getDisplayBeforeOrAfterLogin()));

			sb.append("\"");
		}

		if (importantNotification.getDisplayPages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayPages\": ");

			sb.append("[");

			for (int i = 0; i < importantNotification.getDisplayPages().length;
				 i++) {

				sb.append(
					String.valueOf(importantNotification.getDisplayPages()[i]));

				if ((i + 1) < importantNotification.getDisplayPages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (importantNotification.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(importantNotification.getId());
		}

		if (importantNotification.getImportantNoticeEnd() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importantNoticeEnd\": ");

			sb.append("\"");

			sb.append(_escape(importantNotification.getImportantNoticeEnd()));

			sb.append("\"");
		}

		if (importantNotification.getImportantNoticeStart() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importantNoticeStart\": ");

			sb.append("\"");

			sb.append(_escape(importantNotification.getImportantNoticeStart()));

			sb.append("\"");
		}

		if (importantNotification.getNoticeContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"noticeContent\": ");

			sb.append("\"");

			sb.append(_escape(importantNotification.getNoticeContent()));

			sb.append("\"");
		}

		if (importantNotification.getNoticeTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"noticeTitle\": ");

			sb.append("\"");

			sb.append(_escape(importantNotification.getNoticeTitle()));

			sb.append("\"");
		}

		if (importantNotification.getPriorities() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priorities\": ");

			sb.append("[");

			for (int i = 0; i < importantNotification.getPriorities().length;
				 i++) {

				sb.append(
					String.valueOf(importantNotification.getPriorities()[i]));

				if ((i + 1) < importantNotification.getPriorities().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ImportantNotificationJSONParser importantNotificationJSONParser =
			new ImportantNotificationJSONParser();

		return importantNotificationJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ImportantNotification importantNotification) {

		if (importantNotification == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (importantNotification.getDisplayBeforeOrAfterLogin() == null) {
			map.put("displayBeforeOrAfterLogin", null);
		}
		else {
			map.put(
				"displayBeforeOrAfterLogin",
				String.valueOf(
					importantNotification.getDisplayBeforeOrAfterLogin()));
		}

		if (importantNotification.getDisplayPages() == null) {
			map.put("displayPages", null);
		}
		else {
			map.put(
				"displayPages",
				String.valueOf(importantNotification.getDisplayPages()));
		}

		if (importantNotification.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(importantNotification.getId()));
		}

		if (importantNotification.getImportantNoticeEnd() == null) {
			map.put("importantNoticeEnd", null);
		}
		else {
			map.put(
				"importantNoticeEnd",
				String.valueOf(importantNotification.getImportantNoticeEnd()));
		}

		if (importantNotification.getImportantNoticeStart() == null) {
			map.put("importantNoticeStart", null);
		}
		else {
			map.put(
				"importantNoticeStart",
				String.valueOf(
					importantNotification.getImportantNoticeStart()));
		}

		if (importantNotification.getNoticeContent() == null) {
			map.put("noticeContent", null);
		}
		else {
			map.put(
				"noticeContent",
				String.valueOf(importantNotification.getNoticeContent()));
		}

		if (importantNotification.getNoticeTitle() == null) {
			map.put("noticeTitle", null);
		}
		else {
			map.put(
				"noticeTitle",
				String.valueOf(importantNotification.getNoticeTitle()));
		}

		if (importantNotification.getPriorities() == null) {
			map.put("priorities", null);
		}
		else {
			map.put(
				"priorities",
				String.valueOf(importantNotification.getPriorities()));
		}

		return map;
	}

	public static class ImportantNotificationJSONParser
		extends BaseJSONParser<ImportantNotification> {

		@Override
		protected ImportantNotification createDTO() {
			return new ImportantNotification();
		}

		@Override
		protected ImportantNotification[] createDTOArray(int size) {
			return new ImportantNotification[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(
					jsonParserFieldName, "displayBeforeOrAfterLogin")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "displayPages")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "importantNoticeEnd")) {

				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "importantNoticeStart")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "noticeContent")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "noticeTitle")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "priorities")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			ImportantNotification importantNotification,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "displayBeforeOrAfterLogin")) {

				if (jsonParserFieldValue != null) {
					importantNotification.setDisplayBeforeOrAfterLogin(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayPages")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					DisplayPage[] displayPagesArray =
						new DisplayPage[jsonParserFieldValues.length];

					for (int i = 0; i < displayPagesArray.length; i++) {
						displayPagesArray[i] = DisplayPageSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					importantNotification.setDisplayPages(displayPagesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					importantNotification.setId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "importantNoticeEnd")) {

				if (jsonParserFieldValue != null) {
					importantNotification.setImportantNoticeEnd(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "importantNoticeStart")) {

				if (jsonParserFieldValue != null) {
					importantNotification.setImportantNoticeStart(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "noticeContent")) {
				if (jsonParserFieldValue != null) {
					importantNotification.setNoticeContent(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "noticeTitle")) {
				if (jsonParserFieldValue != null) {
					importantNotification.setNoticeTitle(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priorities")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					Priority[] prioritiesArray =
						new Priority[jsonParserFieldValues.length];

					for (int i = 0; i < prioritiesArray.length; i++) {
						prioritiesArray[i] = PrioritySerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					importantNotification.setPriorities(prioritiesArray);
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
// LIFERAY-REST-BUILDER-HASH:-1190302895