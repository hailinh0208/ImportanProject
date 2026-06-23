/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.client.serdes.v1_0;

import cms.notification.client.dto.v1_0.RegularNotification;
import cms.notification.client.dto.v1_0.RegularNotificationResponse;
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
public class RegularNotificationResponseSerDes {

	public static RegularNotificationResponse toDTO(String json) {
		RegularNotificationResponseJSONParser
			regularNotificationResponseJSONParser =
				new RegularNotificationResponseJSONParser();

		return regularNotificationResponseJSONParser.parseToDTO(json);
	}

	public static RegularNotificationResponse[] toDTOs(String json) {
		RegularNotificationResponseJSONParser
			regularNotificationResponseJSONParser =
				new RegularNotificationResponseJSONParser();

		return regularNotificationResponseJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		RegularNotificationResponse regularNotificationResponse) {

		if (regularNotificationResponse == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (regularNotificationResponse.getMessageInfo() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messageInfo\": ");

			sb.append(
				String.valueOf(regularNotificationResponse.getMessageInfo()));
		}

		if (regularNotificationResponse.getRegularNotifications() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"regularNotifications\": ");

			sb.append("[");

			for (int i = 0;
				 i <
					 regularNotificationResponse.
						 getRegularNotifications().length;
				 i++) {

				sb.append(
					String.valueOf(
						regularNotificationResponse.getRegularNotifications()
							[i]));

				if ((i + 1) < regularNotificationResponse.
						getRegularNotifications().length) {

					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (regularNotificationResponse.getReturnedCount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"returnedCount\": ");

			sb.append(regularNotificationResponse.getReturnedCount());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		RegularNotificationResponseJSONParser
			regularNotificationResponseJSONParser =
				new RegularNotificationResponseJSONParser();

		return regularNotificationResponseJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		RegularNotificationResponse regularNotificationResponse) {

		if (regularNotificationResponse == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (regularNotificationResponse.getMessageInfo() == null) {
			map.put("messageInfo", null);
		}
		else {
			map.put(
				"messageInfo",
				String.valueOf(regularNotificationResponse.getMessageInfo()));
		}

		if (regularNotificationResponse.getRegularNotifications() == null) {
			map.put("regularNotifications", null);
		}
		else {
			map.put(
				"regularNotifications",
				String.valueOf(
					regularNotificationResponse.getRegularNotifications()));
		}

		if (regularNotificationResponse.getReturnedCount() == null) {
			map.put("returnedCount", null);
		}
		else {
			map.put(
				"returnedCount",
				String.valueOf(regularNotificationResponse.getReturnedCount()));
		}

		return map;
	}

	public static class RegularNotificationResponseJSONParser
		extends BaseJSONParser<RegularNotificationResponse> {

		@Override
		protected RegularNotificationResponse createDTO() {
			return new RegularNotificationResponse();
		}

		@Override
		protected RegularNotificationResponse[] createDTOArray(int size) {
			return new RegularNotificationResponse[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "messageInfo")) {
				return false;
			}
			else if (Objects.equals(
						jsonParserFieldName, "regularNotifications")) {

				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "returnedCount")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			RegularNotificationResponse regularNotificationResponse,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "messageInfo")) {
				if (jsonParserFieldValue != null) {
					regularNotificationResponse.setMessageInfo(
						MessageInfoSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "regularNotifications")) {

				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					RegularNotification[] regularNotificationsArray =
						new RegularNotification[jsonParserFieldValues.length];

					for (int i = 0; i < regularNotificationsArray.length; i++) {
						regularNotificationsArray[i] =
							RegularNotificationSerDes.toDTO(
								(String)jsonParserFieldValues[i]);
					}

					regularNotificationResponse.setRegularNotifications(
						regularNotificationsArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "returnedCount")) {
				if (jsonParserFieldValue != null) {
					regularNotificationResponse.setReturnedCount(
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
// LIFERAY-REST-BUILDER-HASH:-1801312313