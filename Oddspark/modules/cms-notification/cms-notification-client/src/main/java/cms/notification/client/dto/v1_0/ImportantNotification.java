/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.client.dto.v1_0;

import cms.notification.client.dto.v1_0.DisplayPage;
import cms.notification.client.dto.v1_0.Priority;
import cms.notification.client.function.UnsafeSupplier;
import cms.notification.client.serdes.v1_0.ImportantNotificationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author ADMIN
 * @generated
 */
@Generated("")
public class ImportantNotification implements Cloneable, Serializable {

	public static ImportantNotification toDTO(String json) {
		return ImportantNotificationSerDes.toDTO(json);
	}

	public String getDisplayBeforeOrAfterLogin() {
		return displayBeforeOrAfterLogin;
	}

	public void setDisplayBeforeOrAfterLogin(String displayBeforeOrAfterLogin) {
		this.displayBeforeOrAfterLogin = displayBeforeOrAfterLogin;
	}

	public void setDisplayBeforeOrAfterLogin(
		UnsafeSupplier<String, Exception>
			displayBeforeOrAfterLoginUnsafeSupplier) {

		try {
			displayBeforeOrAfterLogin =
				displayBeforeOrAfterLoginUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String displayBeforeOrAfterLogin;

	public DisplayPage[] getDisplayPages() {
		return displayPages;
	}

	public void setDisplayPages(DisplayPage[] displayPages) {
		this.displayPages = displayPages;
	}

	public void setDisplayPages(
		UnsafeSupplier<DisplayPage[], Exception> displayPagesUnsafeSupplier) {

		try {
			displayPages = displayPagesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DisplayPage[] displayPages;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long id;

	public String getImportantNoticeEnd() {
		return importantNoticeEnd;
	}

	public void setImportantNoticeEnd(String importantNoticeEnd) {
		this.importantNoticeEnd = importantNoticeEnd;
	}

	public void setImportantNoticeEnd(
		UnsafeSupplier<String, Exception> importantNoticeEndUnsafeSupplier) {

		try {
			importantNoticeEnd = importantNoticeEndUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String importantNoticeEnd;

	public String getImportantNoticeStart() {
		return importantNoticeStart;
	}

	public void setImportantNoticeStart(String importantNoticeStart) {
		this.importantNoticeStart = importantNoticeStart;
	}

	public void setImportantNoticeStart(
		UnsafeSupplier<String, Exception> importantNoticeStartUnsafeSupplier) {

		try {
			importantNoticeStart = importantNoticeStartUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String importantNoticeStart;

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public void setNoticeContent(
		UnsafeSupplier<String, Exception> noticeContentUnsafeSupplier) {

		try {
			noticeContent = noticeContentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String noticeContent;

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public void setNoticeTitle(
		UnsafeSupplier<String, Exception> noticeTitleUnsafeSupplier) {

		try {
			noticeTitle = noticeTitleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String noticeTitle;

	public Priority[] getPriorities() {
		return priorities;
	}

	public void setPriorities(Priority[] priorities) {
		this.priorities = priorities;
	}

	public void setPriorities(
		UnsafeSupplier<Priority[], Exception> prioritiesUnsafeSupplier) {

		try {
			priorities = prioritiesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Priority[] priorities;

	@Override
	public ImportantNotification clone() throws CloneNotSupportedException {
		return (ImportantNotification)super.clone();
	}

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
		return ImportantNotificationSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:138710951