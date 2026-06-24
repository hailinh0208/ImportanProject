/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.important.notification.client.dto.v1_0;

import cms.important.notification.client.dto.v1_0.DisplayPage;
import cms.important.notification.client.function.UnsafeSupplier;
import cms.important.notification.client.serdes.v1_0.PrioritySerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author LinhNTH15
 * @generated
 */
@Generated("")
public class Priority implements Cloneable, Serializable {

	public static Priority toDTO(String json) {
		return PrioritySerDes.toDTO(json);
	}

	public DisplayPage[] getDisplayPages() {
		return displayPageSingle;
	}

	public void setDisplayPages(DisplayPage[] displayPageSingle) {
		this.displayPageSingle = displayPageSingle;
	}

	public void setDisplayPages(
		UnsafeSupplier<DisplayPage[], Exception> displayPageSingleUnsafeSupplier) {

		try {
			displayPageSingle = displayPageSingleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DisplayPage[] displayPageSingle;

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

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void setPriority(
		UnsafeSupplier<Integer, Exception> priorityUnsafeSupplier) {

		try {
			priority = priorityUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer priority;

	@Override
	public Priority clone() throws CloneNotSupportedException {
		return (Priority)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Priority)) {
			return false;
		}

		Priority priority = (Priority)object;

		return Objects.equals(toString(), priority.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return PrioritySerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1395950547