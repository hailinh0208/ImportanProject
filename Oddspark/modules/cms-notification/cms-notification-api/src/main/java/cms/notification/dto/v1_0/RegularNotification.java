/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.dto.v1_0;

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
import jakarta.validation.constraints.Size;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author ADMIN
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "Regular notification item", value = "RegularNotification"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "RegularNotification")
public class RegularNotification implements Serializable {

	public static RegularNotification toDTO(String json) {
		return ObjectMapperUtil.readValue(RegularNotification.class, json);
	}

	public static RegularNotification unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			RegularNotification.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Category[] getCategories() {
		if (_categoriesSupplier != null) {
			categories = _categoriesSupplier.get();

			_categoriesSupplier = null;
		}

		return categories;
	}

	public void setCategories(Category[] categories) {
		this.categories = categories;

		_categoriesSupplier = null;
	}

	@JsonIgnore
	public void setCategories(
		UnsafeSupplier<Category[], Exception> categoriesUnsafeSupplier) {

		_categoriesSupplier = () -> {
			try {
				return categoriesUnsafeSupplier.get();
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
	protected Category[] categories;

	@JsonIgnore
	private Supplier<Category[]> _categoriesSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Competition[] getCompetitions() {
		if (_competitionsSupplier != null) {
			competitions = _competitionsSupplier.get();

			_competitionsSupplier = null;
		}

		return competitions;
	}

	public void setCompetitions(Competition[] competitions) {
		this.competitions = competitions;

		_competitionsSupplier = null;
	}

	@JsonIgnore
	public void setCompetitions(
		UnsafeSupplier<Competition[], Exception> competitionsUnsafeSupplier) {

		_competitionsSupplier = () -> {
			try {
				return competitionsUnsafeSupplier.get();
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
	protected Competition[] competitions;

	@JsonIgnore
	private Supplier<Competition[]> _competitionsSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Format: YYYY-MM-DD HH:MM:SS"
	)
	public String getDisplayDatetime() {
		if (_displayDatetimeSupplier != null) {
			displayDatetime = _displayDatetimeSupplier.get();

			_displayDatetimeSupplier = null;
		}

		return displayDatetime;
	}

	public void setDisplayDatetime(String displayDatetime) {
		this.displayDatetime = displayDatetime;

		_displayDatetimeSupplier = null;
	}

	@JsonIgnore
	public void setDisplayDatetime(
		UnsafeSupplier<String, Exception> displayDatetimeUnsafeSupplier) {

		_displayDatetimeSupplier = () -> {
			try {
				return displayDatetimeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "Format: YYYY-MM-DD HH:MM:SS")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String displayDatetime;

	@JsonIgnore
	private Supplier<String> _displayDatetimeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getId() {
		if (_idSupplier != null) {
			id = _idSupplier.get();

			_idSupplier = null;
		}

		return id;
	}

	public void setId(Long id) {
		this.id = id;

		_idSupplier = null;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		_idSupplier = () -> {
			try {
				return idUnsafeSupplier.get();
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
	protected Long id;

	@JsonIgnore
	private Supplier<Long> _idSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getIsNew() {
		if (_isNewSupplier != null) {
			isNew = _isNewSupplier.get();

			_isNewSupplier = null;
		}

		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;

		_isNewSupplier = null;
	}

	@JsonIgnore
	public void setIsNew(
		UnsafeSupplier<Boolean, Exception> isNewUnsafeSupplier) {

		_isNewSupplier = () -> {
			try {
				return isNewUnsafeSupplier.get();
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
	protected Boolean isNew;

	@JsonIgnore
	private Supplier<Boolean> _isNewSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Size(max = 128)
	public String getNoticeTitle() {
		if (_noticeTitleSupplier != null) {
			noticeTitle = _noticeTitleSupplier.get();

			_noticeTitleSupplier = null;
		}

		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;

		_noticeTitleSupplier = null;
	}

	@JsonIgnore
	public void setNoticeTitle(
		UnsafeSupplier<String, Exception> noticeTitleUnsafeSupplier) {

		_noticeTitleSupplier = () -> {
			try {
				return noticeTitleUnsafeSupplier.get();
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
	protected String noticeTitle;

	@JsonIgnore
	private Supplier<String> _noticeTitleSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RegularNotification)) {
			return false;
		}

		RegularNotification regularNotification = (RegularNotification)object;

		return Objects.equals(toString(), regularNotification.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Category[] categories = getCategories();

		if (categories != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categories\": ");

			sb.append("[");

			for (int i = 0; i < categories.length; i++) {
				sb.append(String.valueOf(categories[i]));

				if ((i + 1) < categories.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Competition[] competitions = getCompetitions();

		if (competitions != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"competitions\": ");

			sb.append("[");

			for (int i = 0; i < competitions.length; i++) {
				sb.append(String.valueOf(competitions[i]));

				if ((i + 1) < competitions.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		String displayDatetime = getDisplayDatetime();

		if (displayDatetime != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayDatetime\": ");

			sb.append("\"");

			sb.append(_escape(displayDatetime));

			sb.append("\"");
		}

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		Boolean isNew = getIsNew();

		if (isNew != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"isNew\": ");

			sb.append(isNew);
		}

		String noticeTitle = getNoticeTitle();

		if (noticeTitle != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"noticeTitle\": ");

			sb.append("\"");

			sb.append(_escape(noticeTitle));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "cms.notification.dto.v1_0.RegularNotification",
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
// LIFERAY-REST-BUILDER-HASH:-1430955009