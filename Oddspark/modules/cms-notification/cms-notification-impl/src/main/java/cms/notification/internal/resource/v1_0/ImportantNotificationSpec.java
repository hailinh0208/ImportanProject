package cms.notification.internal.resource.v1_0;

import cms.important.notification.dto.v1_0.ImportantNotificationResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

@Path("/v1.0")
public class ImportantNotificationSpec {

	@GET
	@io.swagger.v3.oas.annotations.Operation(
		description = "Get important notifications by page code"
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "pagecode"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(name = "Notification")
		}
	)
	@Path("/cms-important-notification")
	@Produces({"application/json", "application/xml"})
	public ImportantNotificationResponse getImportantNotification(
		@io.swagger.v3.oas.annotations.Parameter(hidden = true)
		@QueryParam("pagecode") Integer pagecode) {

		return null;
	}

}