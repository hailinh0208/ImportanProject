package cms.notification.internal.exception;

import cms.notification.dto.v1_0.ErrorResponse;
import cms.notification.dto.v1_0.MessageInfo;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

public class NotificationNotFoundException extends WebApplicationException {

	public NotificationNotFoundException(
		String messageId, String messageBody) {

		super(_buildResponse(messageId, messageBody));
	}

	private static Response _buildResponse(
		String messageId, String messageBody) {

		MessageInfo messageInfo = new MessageInfo();

		messageInfo.setMessageId(messageId);
		messageInfo.setMessageBody(messageBody);

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setTraceId(UUID.randomUUID().toString());
		errorResponse.setMessageInfo(messageInfo);

		return Response.status(
			Response.Status.NOT_FOUND
		).entity(
			errorResponse.toString()
		).type(
			MediaType.APPLICATION_JSON
		).build();
	}

}
