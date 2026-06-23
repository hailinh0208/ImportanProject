/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.client.dto.v1_0;

import cms.notification.client.dto.v1_0.MessageInfo;
import cms.notification.client.dto.v1_0.RegularNotification;
import cms.notification.client.function.UnsafeSupplier;
import cms.notification.client.serdes.v1_0.RegularNotificationResponseSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author ADMIN
 * @generated
 */
@Generated("")
public class RegularNotificationResponse implements Cloneable, Serializable {

	public static RegularNotificationResponse toDTO(String json) {
		return RegularNotificationResponseSerDes.toDTO(json);
	}

	public MessageInfo getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(MessageInfo messageInfo) {
		this.messageInfo = messageInfo;
	}

	public void setMessageInfo(
		UnsafeSupplier<MessageInfo, Exception> messageInfoUnsafeSupplier) {

		try {
			messageInfo = messageInfoUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected MessageInfo messageInfo;

	public RegularNotification[] getRegularNotifications() {
		return regularNotifications;
	}

	public void setRegularNotifications(
		RegularNotification[] regularNotifications) {

		this.regularNotifications = regularNotifications;
	}

	public void setRegularNotifications(
		UnsafeSupplier<RegularNotification[], Exception>
			regularNotificationsUnsafeSupplier) {

		try {
			regularNotifications = regularNotificationsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected RegularNotification[] regularNotifications;

	public Integer getReturnedCount() {
		return returnedCount;
	}

	public void setReturnedCount(Integer returnedCount) {
		this.returnedCount = returnedCount;
	}

	public void setReturnedCount(
		UnsafeSupplier<Integer, Exception> returnedCountUnsafeSupplier) {

		try {
			returnedCount = returnedCountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer returnedCount;

	@Override
	public RegularNotificationResponse clone()
		throws CloneNotSupportedException {

		return (RegularNotificationResponse)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RegularNotificationResponse)) {
			return false;
		}

		RegularNotificationResponse regularNotificationResponse =
			(RegularNotificationResponse)object;

		return Objects.equals(
			toString(), regularNotificationResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return RegularNotificationResponseSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1733332703