/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.service;

import cms.notification.dto.v1_0.RegularNotificationResponse;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Business logic layer for regular notification queries.
 * Resource classes validate input and delegate to this service.
 */
public interface NotificationService {

	RegularNotificationResponse getRegularNotifications(
			int competition, int limit, long companyId, Locale locale,
			TimeZone timeZone)
		throws Exception;

}