/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.client.serdes.v1_0;

import cms.notification.client.dto.v1_0.DisplayPage;
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
public class PrioritySerDes {

	public static Priority toDTO(String json) {
		PriorityJSONParser priorityJSONParser = new PriorityJSONParser();

		return priorityJSONParser.parseToDTO(json);
	}

	public static Priority[] toDTOs(String json) {
		PriorityJSONParser priorityJSONParser = new PriorityJSONParser();

		return priorityJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Priority priority) {
		if (priority == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (priority.getDisplayPages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayPages\": ");

			sb.append("[");

			for (int i = 0; i < priority.getDisplayPages().length; i++) {
				sb.append(String.valueOf(priority.getDisplayPages()[i]));

				if ((i + 1) < priority.getDisplayPages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (priority.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(priority.getId());
		}

		if (priority.getPriority() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priority\": ");

			sb.append(priority.getPriority());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		PriorityJSONParser priorityJSONParser = new PriorityJSONParser();

		return priorityJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Priority priority) {
		if (priority == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (priority.getDisplayPages() == null) {
			map.put("displayPages", null);
		}
		else {
			map.put("displayPages", String.valueOf(priority.getDisplayPages()));
		}

		if (priority.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(priority.getId()));
		}

		if (priority.getPriority() == null) {
			map.put("priority", null);
		}
		else {
			map.put("priority", String.valueOf(priority.getPriority()));
		}

		return map;
	}

	public static class PriorityJSONParser extends BaseJSONParser<Priority> {

		@Override
		protected Priority createDTO() {
			return new Priority();
		}

		@Override
		protected Priority[] createDTOArray(int size) {
			return new Priority[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "displayPages")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			Priority priority, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "displayPages")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					DisplayPage[] displayPagesArray =
						new DisplayPage[jsonParserFieldValues.length];

					for (int i = 0; i < displayPagesArray.length; i++) {
						displayPagesArray[i] = DisplayPageSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					priority.setDisplayPages(displayPagesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					priority.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "priority")) {
				if (jsonParserFieldValue != null) {
					priority.setPriority(
						Integer.valueOf((String)jsonParserFieldValue));
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
// LIFERAY-REST-BUILDER-HASH:-962101976