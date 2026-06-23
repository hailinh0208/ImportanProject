/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.client.dto.v1_0;

import cms.notification.client.function.UnsafeSupplier;
import cms.notification.client.serdes.v1_0.MessageInfoSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author ADMIN
 * @generated
 */
@Generated("")
public class MessageInfo implements Cloneable, Serializable {

	public static MessageInfo toDTO(String json) {
		return MessageInfoSerDes.toDTO(json);
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public void setMessageBody(
		UnsafeSupplier<String, Exception> messageBodyUnsafeSupplier) {

		try {
			messageBody = messageBodyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String messageBody;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setMessageId(
		UnsafeSupplier<String, Exception> messageIdUnsafeSupplier) {

		try {
			messageId = messageIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String messageId;

	@Override
	public MessageInfo clone() throws CloneNotSupportedException {
		return (MessageInfo)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof MessageInfo)) {
			return false;
		}

		MessageInfo messageInfo = (MessageInfo)object;

		return Objects.equals(toString(), messageInfo.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return MessageInfoSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:916757592