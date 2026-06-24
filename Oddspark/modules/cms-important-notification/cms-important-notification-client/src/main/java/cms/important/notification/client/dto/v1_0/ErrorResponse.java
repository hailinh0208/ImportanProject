/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.important.notification.client.dto.v1_0;

import cms.important.notification.client.dto.v1_0.MessageInfo;
import cms.important.notification.client.function.UnsafeSupplier;
import cms.important.notification.client.serdes.v1_0.ErrorResponseSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author LinhNTH15
 * @generated
 */
@Generated("")
public class ErrorResponse implements Cloneable, Serializable {

	public static ErrorResponse toDTO(String json) {
		return ErrorResponseSerDes.toDTO(json);
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

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public void setTraceId(
		UnsafeSupplier<String, Exception> traceIdUnsafeSupplier) {

		try {
			traceId = traceIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String traceId;

	@Override
	public ErrorResponse clone() throws CloneNotSupportedException {
		return (ErrorResponse)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ErrorResponse)) {
			return false;
		}

		ErrorResponse errorResponse = (ErrorResponse)object;

		return Objects.equals(toString(), errorResponse.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ErrorResponseSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:484098396