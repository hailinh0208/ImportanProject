/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.important.notification.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author LinhNTH15
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Response wrapper for important notifications",
	value = "ImportantNotificationResponse"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ImportantNotificationResponse")
public class ImportantNotificationResponse implements Serializable {

	public static ImportantNotificationResponse toDTO(String json) {
		return ObjectMapperUtil.readValue(
			ImportantNotificationResponse.class, json);
	}

	public static ImportantNotificationResponse unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			ImportantNotificationResponse.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Item[] getItems() {
		if (_itemsSupplier != null) {
			items = _itemsSupplier.get();

			_itemsSupplier = null;
		}

		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;

		_itemsSupplier = null;
	}

	@JsonIgnore
	public void setItems(
		UnsafeSupplier<Item[], Exception> itemsUnsafeSupplier) {

		_itemsSupplier = () -> {
			try {
				return itemsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Item[] items;

	@JsonIgnore
	private Supplier<Item[]> _itemsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public MessageInfo getMessageInfo() {
		if (_messageInfoSupplier != null) {
			messageInfo = _messageInfoSupplier.get();

			_messageInfoSupplier = null;
		}

		return messageInfo;
	}

	public void setMessageInfo(MessageInfo messageInfo) {
		this.messageInfo = messageInfo;

		_messageInfoSupplier = null;
	}

	@JsonIgnore
	public void setMessageInfo(
		UnsafeSupplier<MessageInfo, Exception> messageInfoUnsafeSupplier) {

		_messageInfoSupplier = () -> {
			try {
				return messageInfoUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected MessageInfo messageInfo;

	@JsonIgnore
	private Supplier<MessageInfo> _messageInfoSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Integer getReturnedCount() {
		if (_returnedCountSupplier != null) {
			returnedCount = _returnedCountSupplier.get();

			_returnedCountSupplier = null;
		}

		return returnedCount;
	}

	public void setReturnedCount(Integer returnedCount) {
		this.returnedCount = returnedCount;

		_returnedCountSupplier = null;
	}

	@JsonIgnore
	public void setReturnedCount(
		UnsafeSupplier<Integer, Exception> returnedCountUnsafeSupplier) {

		_returnedCountSupplier = () -> {
			try {
				return returnedCountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Integer returnedCount;

	@JsonIgnore
	private Supplier<Integer> _returnedCountSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ImportantNotificationResponse)) {
			return false;
		}

		ImportantNotificationResponse importantNotificationResponse =
			(ImportantNotificationResponse)object;

		return Objects.equals(
			toString(), importantNotificationResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Item[] items = getItems();

		if (items != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"items\": ");

			sb.append("[");

			for (int i = 0; i < items.length; i++) {
				sb.append(String.valueOf(items[i]));

				if ((i + 1) < items.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		MessageInfo messageInfo = getMessageInfo();

		if (messageInfo != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"messageInfo\": ");

			sb.append(String.valueOf(messageInfo));
		}

		Integer returnedCount = getReturnedCount();

		if (returnedCount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"returnedCount\": ");

			sb.append(returnedCount);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "cms.important.notification.dto.v1_0.ImportantNotificationResponse",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}
// LIFERAY-REST-BUILDER-HASH:-123888106