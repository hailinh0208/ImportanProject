/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.important.notification.client.serdes.v1_0;

import cms.important.notification.client.dto.v1_0.MessageInfo;
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
public class MessageInfoSerDes {

	public static MessageInfo toDTO(String json) {
		MessageInfoJSONParser messageInfoJSONParser =
			new MessageInfoJSONParser();

		return messageInfoJSONParser.parseToDTO(json);
	}

	public static MessageInfo[] toDTOs(String json) {
		MessageInfoJSONParser messageInfoJSONParser =
			new MessageInfoJSONParser();

		return messageInfoJSONParser.parseToDTOs(json);
	}

	public static String toJSON(MessageInfo messageInfo) {
		if (messageInfo == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (messageInfo.getMessageBody() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messageBody\": ");

			sb.append("\"");

			sb.append(_escape(messageInfo.getMessageBody()));

			sb.append("\"");
		}

		if (messageInfo.getMessageId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messageId\": ");

			sb.append("\"");

			sb.append(_escape(messageInfo.getMessageId()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		MessageInfoJSONParser messageInfoJSONParser =
			new MessageInfoJSONParser();

		return messageInfoJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(MessageInfo messageInfo) {
		if (messageInfo == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (messageInfo.getMessageBody() == null) {
			map.put("messageBody", null);
		}
		else {
			map.put(
				"messageBody", String.valueOf(messageInfo.getMessageBody()));
		}

		if (messageInfo.getMessageId() == null) {
			map.put("messageId", null);
		}
		else {
			map.put("messageId", String.valueOf(messageInfo.getMessageId()));
		}

		return map;
	}

	public static class MessageInfoJSONParser
		extends BaseJSONParser<MessageInfo> {

		@Override
		protected MessageInfo createDTO() {
			return new MessageInfo();
		}

		@Override
		protected MessageInfo[] createDTOArray(int size) {
			return new MessageInfo[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "messageBody")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "messageId")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			MessageInfo messageInfo, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "messageBody")) {
				if (jsonParserFieldValue != null) {
					messageInfo.setMessageBody((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "messageId")) {
				if (jsonParserFieldValue != null) {
					messageInfo.setMessageId((String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:1614666777