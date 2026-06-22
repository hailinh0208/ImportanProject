/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.exception;

import cms.notification.dto.v1_0.ErrorResponse;
import cms.notification.dto.v1_0.MessageInfo;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

/**
 * Thrown when a request contains invalid or missing parameters.
 * Serializes an ErrorResponse DTO as the JSON body so the structure stays
 * consistent with the rest of the API contract.
 */
public class NotificationBadRequestException extends WebApplicationException {

	public NotificationBadRequestException(
		String messageId, String messageBody) {

		super(_buildResponse(messageId, messageBody));
	}

	private static Response _buildResponse(String messageId, String messageBody) {
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setMessageId(messageId);
		messageInfo.setMessageBody(messageBody);

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTraceId(UUID.randomUUID().toString());
		errorResponse.setMessageInfo(messageInfo);

		return Response.status(
			Response.Status.BAD_REQUEST
		).entity(
			errorResponse.toString()
		).type(
			MediaType.APPLICATION_JSON
		).build();
	}

}
