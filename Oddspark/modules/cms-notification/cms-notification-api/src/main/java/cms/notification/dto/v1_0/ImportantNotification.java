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
	description = "Important notification item", value = "ImportantNotification"
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ImportantNotification")
public class ImportantNotification implements Serializable {

	public static ImportantNotification toDTO(String json) {
		return ObjectMapperUtil.readValue(ImportantNotification.class, json);
	}

	public static ImportantNotification unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			ImportantNotification.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	@Size(max = 32)
	public String getDisplayBeforeOrAfterLogin() {
		if (_displayBeforeOrAfterLoginSupplier != null) {
			displayBeforeOrAfterLogin =
				_displayBeforeOrAfterLoginSupplier.get();

			_displayBeforeOrAfterLoginSupplier = null;
		}

		return displayBeforeOrAfterLogin;
	}

	public void setDisplayBeforeOrAfterLogin(String displayBeforeOrAfterLogin) {
		this.displayBeforeOrAfterLogin = displayBeforeOrAfterLogin;

		_displayBeforeOrAfterLoginSupplier = null;
	}

	@JsonIgnore
	public void setDisplayBeforeOrAfterLogin(
		UnsafeSupplier<String, Exception>
			displayBeforeOrAfterLoginUnsafeSupplier) {

		_displayBeforeOrAfterLoginSupplier = () -> {
			try {
				return displayBeforeOrAfterLoginUnsafeSupplier.get();
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
	protected String displayBeforeOrAfterLogin;

	@JsonIgnore
	private Supplier<String> _displayBeforeOrAfterLoginSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public DisplayPage[] getDisplayPages() {
		if (_displayPagesSupplier != null) {
			displayPages = _displayPagesSupplier.get();

			_displayPagesSupplier = null;
		}

		return displayPages;
	}

	public void setDisplayPages(DisplayPage[] displayPages) {
		this.displayPages = displayPages;

		_displayPagesSupplier = null;
	}

	@JsonIgnore
	public void setDisplayPages(
		UnsafeSupplier<DisplayPage[], Exception> displayPagesUnsafeSupplier) {

		_displayPagesSupplier = () -> {
			try {
				return displayPagesUnsafeSupplier.get();
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
	protected DisplayPage[] displayPages;

	@JsonIgnore
	private Supplier<DisplayPage[]> _displayPagesSupplier;

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

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Format: YYYY-MM-DD HH:MM:SS"
	)
	public String getImportantNoticeEnd() {
		if (_importantNoticeEndSupplier != null) {
			importantNoticeEnd = _importantNoticeEndSupplier.get();

			_importantNoticeEndSupplier = null;
		}

		return importantNoticeEnd;
	}

	public void setImportantNoticeEnd(String importantNoticeEnd) {
		this.importantNoticeEnd = importantNoticeEnd;

		_importantNoticeEndSupplier = null;
	}

	@JsonIgnore
	public void setImportantNoticeEnd(
		UnsafeSupplier<String, Exception> importantNoticeEndUnsafeSupplier) {

		_importantNoticeEndSupplier = () -> {
			try {
				return importantNoticeEndUnsafeSupplier.get();
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
	protected String importantNoticeEnd;

	@JsonIgnore
	private Supplier<String> _importantNoticeEndSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Format: YYYY-MM-DD HH:MM:SS"
	)
	public String getImportantNoticeStart() {
		if (_importantNoticeStartSupplier != null) {
			importantNoticeStart = _importantNoticeStartSupplier.get();

			_importantNoticeStartSupplier = null;
		}

		return importantNoticeStart;
	}

	public void setImportantNoticeStart(String importantNoticeStart) {
		this.importantNoticeStart = importantNoticeStart;

		_importantNoticeStartSupplier = null;
	}

	@JsonIgnore
	public void setImportantNoticeStart(
		UnsafeSupplier<String, Exception> importantNoticeStartUnsafeSupplier) {

		_importantNoticeStartSupplier = () -> {
			try {
				return importantNoticeStartUnsafeSupplier.get();
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
	protected String importantNoticeStart;

	@JsonIgnore
	private Supplier<String> _importantNoticeStartSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Size(max = 255)
	public String getNoticeContent() {
		if (_noticeContentSupplier != null) {
			noticeContent = _noticeContentSupplier.get();

			_noticeContentSupplier = null;
		}

		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;

		_noticeContentSupplier = null;
	}

	@JsonIgnore
	public void setNoticeContent(
		UnsafeSupplier<String, Exception> noticeContentUnsafeSupplier) {

		_noticeContentSupplier = () -> {
			try {
				return noticeContentUnsafeSupplier.get();
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
	protected String noticeContent;

	@JsonIgnore
	private Supplier<String> _noticeContentSupplier;

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

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Priority[] getPriorities() {
		if (_prioritiesSupplier != null) {
			priorities = _prioritiesSupplier.get();

			_prioritiesSupplier = null;
		}

		return priorities;
	}

	public void setPriorities(Priority[] priorities) {
		this.priorities = priorities;

		_prioritiesSupplier = null;
	}

	@JsonIgnore
	public void setPriorities(
		UnsafeSupplier<Priority[], Exception> prioritiesUnsafeSupplier) {

		_prioritiesSupplier = () -> {
			try {
				return prioritiesUnsafeSupplier.get();
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
	protected Priority[] priorities;

	@JsonIgnore
	private Supplier<Priority[]> _prioritiesSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ImportantNotification)) {
			return false;
		}

		ImportantNotification importantNotification =
			(ImportantNotification)object;

		return Objects.equals(toString(), importantNotification.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String displayBeforeOrAfterLogin = getDisplayBeforeOrAfterLogin();

		if (displayBeforeOrAfterLogin != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayBeforeOrAfterLogin\": ");

			sb.append("\"");

			sb.append(_escape(displayBeforeOrAfterLogin));

			sb.append("\"");
		}

		DisplayPage[] displayPages = getDisplayPages();

		if (displayPages != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"displayPages\": ");

			sb.append("[");

			for (int i = 0; i < displayPages.length; i++) {
				sb.append(String.valueOf(displayPages[i]));

				if ((i + 1) < displayPages.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		Long id = getId();

		if (id != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(id);
		}

		String importantNoticeEnd = getImportantNoticeEnd();

		if (importantNoticeEnd != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importantNoticeEnd\": ");

			sb.append("\"");

			sb.append(_escape(importantNoticeEnd));

			sb.append("\"");
		}

		String importantNoticeStart = getImportantNoticeStart();

		if (importantNoticeStart != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"importantNoticeStart\": ");

			sb.append("\"");

			sb.append(_escape(importantNoticeStart));

			sb.append("\"");
		}

		String noticeContent = getNoticeContent();

		if (noticeContent != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"noticeContent\": ");

			sb.append("\"");

			sb.append(_escape(noticeContent));

			sb.append("\"");
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

		Priority[] priorities = getPriorities();

		if (priorities != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"priorities\": ");

			sb.append("[");

			for (int i = 0; i < priorities.length; i++) {
				sb.append(String.valueOf(priorities[i]));

				if ((i + 1) < priorities.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "cms.notification.dto.v1_0.ImportantNotification",
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
// LIFERAY-REST-BUILDER-HASH:1880875930