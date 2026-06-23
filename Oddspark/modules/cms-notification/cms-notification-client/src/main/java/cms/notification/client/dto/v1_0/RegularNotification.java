/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.client.dto.v1_0;

import cms.notification.client.dto.v1_0.Category;
import cms.notification.client.dto.v1_0.Competition;
import cms.notification.client.function.UnsafeSupplier;
import cms.notification.client.serdes.v1_0.RegularNotificationSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author ADMIN
 * @generated
 */
@Generated("")
public class RegularNotification implements Cloneable, Serializable {

	public static RegularNotification toDTO(String json) {
		return RegularNotificationSerDes.toDTO(json);
	}

	public Category[] getCategories() {
		return categories;
	}

	public void setCategories(Category[] categories) {
		this.categories = categories;
	}

	public void setCategories(
		UnsafeSupplier<Category[], Exception> categoriesUnsafeSupplier) {

		try {
			categories = categoriesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Category[] categories;

	public Competition[] getCompetitions() {
		return competitions;
	}

	public void setCompetitions(Competition[] competitions) {
		this.competitions = competitions;
	}

	public void setCompetitions(
		UnsafeSupplier<Competition[], Exception> competitionsUnsafeSupplier) {

		try {
			competitions = competitionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Competition[] competitions;

	public String getDisplayDatetime() {
		return displayDatetime;
	}

	public void setDisplayDatetime(String displayDatetime) {
		this.displayDatetime = displayDatetime;
	}

	public void setDisplayDatetime(
		UnsafeSupplier<String, Exception> displayDatetimeUnsafeSupplier) {

		try {
			displayDatetime = displayDatetimeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String displayDatetime;

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

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public void setIsNew(
		UnsafeSupplier<Boolean, Exception> isNewUnsafeSupplier) {

		try {
			isNew = isNewUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean isNew;

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

	@Override
	public RegularNotification clone() throws CloneNotSupportedException {
		return (RegularNotification)super.clone();
	}

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
		return RegularNotificationSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:456808099