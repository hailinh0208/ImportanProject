/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.important.notification.client.serdes.v1_0;

import cms.important.notification.client.dto.v1_0.DisplayPage;
import cms.important.notification.client.dto.v1_0.Item;
import cms.important.notification.client.dto.v1_0.Priority;
import cms.important.notification.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author LinhNTH15
 * @generated
 */
@Generated("")
public class ItemSerDes {

	public static Item toDTO(String json) {
		ItemJSONParser itemJSONParser = new ItemJSONParser();

		return itemJSONParser.parseToDTO(json);
	}

	public static Item[] toDTOs(String json) {
		ItemJSONParser itemJSONParser = new ItemJSONParser();

		return itemJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Item item) {
		if (item == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (item.getDisplayBeforeOrAfterLogin() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayBeforeOrAfterLogin\": ");

			sb.append("\"");

			sb.append(_escape(item.getDisplayBeforeOrAfterLogin()));

			sb.append("\"");
		}

		if (item.getDisplayPages() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayPageMulti\": ");

			sb.append("[");

			for (int i = 0; i < item.getDisplayPages().length; i++) {
				sb.append(String.valueOf(item.getDisplayPages()[i]));

				if ((i + 1) < item.getDisplayPages().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (item.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(item.getId());
		}

		if (item.getImportantNoticeEnd() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importantNoticeEnd\": ");

			sb.append("\"");

			sb.append(_escape(item.getImportantNoticeEnd()));

			sb.append("\"");
		}

		if (item.getImportantNoticeStart() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importantNoticeStart\": ");

			sb.append("\"");

			sb.append(_escape(item.getImportantNoticeStart()));

			sb.append("\"");
		}

		if (item.getNoticeContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"noticeContent\": ");

			sb.append("\"");

			sb.append(_escape(item.getNoticeContent()));

			sb.append("\"");
		}

		if (item.getNoticeTitle() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"noticeTitle\": ");

			sb.append("\"");

			sb.append(_escape(item.getNoticeTitle()));

			sb.append("\"");
		}

		if (item.getPriorities() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priorities\": ");

			sb.append("[");

			for (int i = 0; i < item.getPriorities().length; i++) {
				sb.append(String.valueOf(item.getPriorities()[i]));

				if ((i + 1) < item.getPriorities().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ItemJSONParser itemJSONParser = new ItemJSONParser();

		return itemJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Item item) {
		if (item == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (item.getDisplayBeforeOrAfterLogin() == null) {
			map.put("displayBeforeOrAfterLogin", null);
		}
		else {
			map.put(
				"displayBeforeOrAfterLogin",
				String.valueOf(item.getDisplayBeforeOrAfterLogin()));
		}

		if (item.getDisplayPages() == null) {
			map.put("displayPageMulti", null);
		}
		else {
			map.put("displayPageMulti", String.valueOf(item.getDisplayPages()));
		}

		if (item.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(item.getId()));
		}

		if (item.getImportantNoticeEnd() == null) {
			map.put("importantNoticeEnd", null);
		}
		else {
			map.put(
				"importantNoticeEnd",
				String.valueOf(item.getImportantNoticeEnd()));
		}

		if (item.getImportantNoticeStart() == null) {
			map.put("importantNoticeStart", null);
		}
		else {
			map.put(
				"importantNoticeStart",
				String.valueOf(item.getImportantNoticeStart()));
		}

		if (item.getNoticeContent() == null) {
			map.put("noticeContent", null);
		}
		else {
			map.put("noticeContent", String.valueOf(item.getNoticeContent()));
		}

		if (item.getNoticeTitle() == null) {
			map.put("noticeTitle", null);
		}
		else {
			map.put("noticeTitle", String.valueOf(item.getNoticeTitle()));
		}

		if (item.getPriorities() == null) {
			map.put("priorities", null);
		}
		else {
			map.put("priorities", String.valueOf(item.getPriorities()));
		}

		return map;
	}

	public static class ItemJSONParser extends BaseJSONParser<Item> {

		@Override
		protected Item createDTO() {
			return new Item();
		}

		@Override
		protected Item[] createDTOArray(int size) {
			return new Item[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(
					jsonParserFieldName, "displayBeforeOrAfterLogin")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "displayPageMulti")) {
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
			Item item, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(
					jsonParserFieldName, "displayBeforeOrAfterLogin")) {

				if (jsonParserFieldValue != null) {
					item.setDisplayBeforeOrAfterLogin(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "displayPageMulti")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					DisplayPage[] displayPageMultiArray =
						new DisplayPage[jsonParserFieldValues.length];

					for (int i = 0; i < displayPageMultiArray.length; i++) {
						displayPageMultiArray[i] = DisplayPageSerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					item.setDisplayPages(displayPageMultiArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					item.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "importantNoticeEnd")) {

				if (jsonParserFieldValue != null) {
					item.setImportantNoticeEnd((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "importantNoticeStart")) {

				if (jsonParserFieldValue != null) {
					item.setImportantNoticeStart((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "noticeContent")) {
				if (jsonParserFieldValue != null) {
					item.setNoticeContent((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "noticeTitle")) {
				if (jsonParserFieldValue != null) {
					item.setNoticeTitle((String)jsonParserFieldValue);
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

					item.setPriorities(prioritiesArray);
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
// LIFERAY-REST-BUILDER-HASH:590484885