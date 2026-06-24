/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.important.notification.client.dto.v1_0;

import cms.important.notification.client.dto.v1_0.Item;
import cms.important.notification.client.dto.v1_0.MessageInfo;
import cms.important.notification.client.function.UnsafeSupplier;
import cms.important.notification.client.serdes.v1_0.ImportantNotificationResponseSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author LinhNTH15
 * @generated
 */
@Generated("")
public class ImportantNotificationResponse implements Cloneable, Serializable {

	public static ImportantNotificationResponse toDTO(String json) {
		return ImportantNotificationResponseSerDes.toDTO(json);
	}

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

	public void setItems(
		UnsafeSupplier<Item[], Exception> itemsUnsafeSupplier) {

		try {
			items = itemsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Item[] items;

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
	public ImportantNotificationResponse clone()
		throws CloneNotSupportedException {

		return (ImportantNotificationResponse)super.clone();
	}

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
		return ImportantNotificationResponseSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:661024773