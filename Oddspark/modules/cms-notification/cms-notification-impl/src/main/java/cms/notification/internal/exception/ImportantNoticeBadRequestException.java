package cms.notification.internal.exception;

import cms.notification.dto.v1_0.ErrorResponse;
import cms.notification.dto.v1_0.MessageInfo;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ImportantNoticeBadRequestException extends WebApplicationException {

	public ImportantNoticeBadRequestException(
		String messageId, String messageBody) {

		super(_buildResponse(messageId, messageBody));
	}

	private static Response _buildResponse(
		String messageId, String messageBody) {

		MessageInfo messageInfo = new MessageInfo();

		messageInfo.setMessageId(messageId);
		messageInfo.setMessageBody(messageBody);

		ErrorResponse errorResponse = new ErrorResponse();

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
