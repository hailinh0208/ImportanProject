/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.important.notification.internal.resource.v1_0;

import cms.important.notification.dto.v1_0.ImportantNotificationResponse;
import cms.important.notification.internal.constants.ImportantNotificationConstants;
import cms.important.notification.internal.exception.ImportantNoticeBadRequestException;
import cms.important.notification.internal.service.ImportantNotificationService;
import cms.important.notification.resource.v1_0.NotificationResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author LinhNTH15
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/notification.properties",
	scope = ServiceScope.PROTOTYPE, service = NotificationResource.class
)
public class NotificationResourceImpl extends BaseNotificationResourceImpl {

	@Override
	public ImportantNotificationResponse getImportantNotification(
			Integer pagecode)
		throws Exception {

		if (pagecode == null) {
			throw new ImportantNoticeBadRequestException(
				ImportantNotificationConstants.ERROR_MISSING_PAGECODE,
				"画面コードを入力してください。");
		}

		return _importantNotificationService.getImportantNotifications(
			pagecode, contextCompany.getCompanyId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextCompany.getTimeZone());
	}

	@Reference
	private ImportantNotificationService _importantNotificationService;

}
// LIFERAY-REST-BUILDER-HASH:-1083434949