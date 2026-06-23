/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.resource.v1_0;

import cms.notification.dto.v1_0.ImportantNotificationResponse;
import cms.notification.dto.v1_0.RegularNotificationResponse;
import cms.notification.internal.constants.NotificationConstants;
import cms.notification.internal.exception.NotificationBadRequestException;
import cms.notification.internal.service.ImportantNotificationService;
import cms.notification.internal.service.NotificationService;
import cms.notification.resource.v1_0.NotificationResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author ADMIN
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/notification.properties",
	scope = ServiceScope.PROTOTYPE, service = NotificationResource.class
)
public class NotificationResourceImpl extends BaseNotificationResourceImpl {

	@Override
	public RegularNotificationResponse getRegularNotification(
			Integer competition, Integer limit)
		throws Exception {

		if (competition == null) {
			throw new NotificationBadRequestException(
				NotificationConstants.ERROR_MISSING_COMPETITION,
				"競技タイプを入力してください。");
		}

		if (competition < NotificationConstants.MIN_COMPETITION ||
			competition > NotificationConstants.MAX_COMPETITION) {

			throw new NotificationBadRequestException(
				NotificationConstants.ERROR_INVALID_COMPETITION,
				"正しい競技タイプを入力してください。");
		}

		int maxResults =
			(limit != null && limit > 0)
				? limit
				: NotificationConstants.DEFAULT_LIMIT;

		return _notificationService.getRegularNotifications(
			competition, maxResults, contextCompany.getCompanyId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextCompany.getTimeZone());
	}

	@Override
	public ImportantNotificationResponse getImportantNotification(
			Integer pagecode)
		throws Exception {

		if (pagecode == null) {
			throw new NotificationBadRequestException(
				NotificationConstants.ERROR_MISSING_PAGECODE,
				"画面コードを入力してください。");
		}

		return _importantNotificationService.getImportantNotifications(
			pagecode, contextCompany.getCompanyId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextCompany.getTimeZone());
	}

	@Reference
	private ImportantNotificationService _importantNotificationService;

	@Reference
	private NotificationService _notificationService;

}