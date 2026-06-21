/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.client.dto.v1_0;

import cms.notification.client.function.UnsafeSupplier;
import cms.notification.client.serdes.v1_0.CompetitionSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author ADMIN
 * @generated
 */
@Generated("")
public class Competition implements Cloneable, Serializable {

	public static Competition toDTO(String json) {
		return CompetitionSerDes.toDTO(json);
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public void setKey(UnsafeSupplier<Long, Exception> keyUnsafeSupplier) {
		try {
			key = keyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long key;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	@Override
	public Competition clone() throws CloneNotSupportedException {
		return (Competition)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Competition)) {
			return false;
		}

		Competition competition = (Competition)object;

		return Objects.equals(toString(), competition.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CompetitionSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1130279765