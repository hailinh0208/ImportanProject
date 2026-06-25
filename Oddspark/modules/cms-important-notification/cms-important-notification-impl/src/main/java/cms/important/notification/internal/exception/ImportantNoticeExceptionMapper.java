package cms.important.notification.internal.exception;

import cms.important.notification.dto.v1_0.ErrorResponse;
import cms.important.notification.dto.v1_0.MessageInfo;
import cms.important.notification.internal.constants.ImportantNotificationConstants;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLTransientConnectionException;

import java.util.UUID;

import org.osgi.service.component.annotations.Component;

/**
 * Maps unhandled exceptions to structured JSON error responses.
 *
 * - DB connection errors (SQLTransientConnectionException or any cause whose
 *   class name contains "JDBCConnectionException") → 503 Service Unavailable
 * - All other unexpected exceptions → 500 Internal Server Error
 * - WebApplicationException (e.g. our 400/404 responses) → passed through as-is
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=CmsImportantNotification)",
		"osgi.jaxrs.extension=true"
	},
	service = ExceptionMapper.class
)
@Provider
public class ImportantNoticeExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		if (exception instanceof WebApplicationException) {
			return ((WebApplicationException)exception).getResponse();
		}

		Response.Status status = _isDbConnectionError(exception)
			? Response.Status.SERVICE_UNAVAILABLE
			: Response.Status.INTERNAL_SERVER_ERROR;

		return Response.status(
			status
		).entity(
			_buildErrorBody()
		).type(
			MediaType.APPLICATION_JSON
		).build();
	}

	private static String _buildErrorBody() {
		MessageInfo messageInfo = new MessageInfo();

		messageInfo.setMessageId(ImportantNotificationConstants.ERROR_SERVER_ERROR);
		messageInfo.setMessageBody(
			ImportantNotificationConstants.ERROR_SERVER_MESSAGE);

		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setTraceId(UUID.randomUUID().toString());
		errorResponse.setMessageInfo(messageInfo);

		return errorResponse.toString();
	}

	private static boolean _isDbConnectionError(Throwable throwable) {
		Throwable cause = throwable;

		while (cause != null) {
			if (cause instanceof SQLTransientConnectionException) {
				return true;
			}

			String className = cause.getClass().getName();

			if (className.contains("JDBCConnectionException") ||
				className.contains("SQLNonTransientConnectionException")) {

				return true;
			}

			cause = cause.getCause();
		}

		return false;
	}

}