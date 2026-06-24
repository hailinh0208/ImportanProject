package cms.important.notification.internal.service;

import cms.important.notification.dto.v1_0.ImportantNotificationResponse;

import java.util.Locale;
import java.util.TimeZone;

public interface ImportantNotificationService {

	ImportantNotificationResponse getImportantNotifications(
			int pagecode, long companyId, Locale locale, TimeZone timeZone)
		throws Exception;

}
