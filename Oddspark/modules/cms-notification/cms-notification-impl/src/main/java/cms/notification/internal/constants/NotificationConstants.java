/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.constants;

/**
 * Central place for all constants used across the cms-notification module.
 */
public final class NotificationConstants {

	// External reference codes for Object Definitions
	public static final String ERC_OBJECT_NOTICE_MASTER = "OP_OBJ_NOTICE_MASTER";

	// External reference codes for Picklists
	public static final String ERC_LIST_COMPETITION_TYPE = "OP_LIST_COMPETITION_TYPE";
	public static final String ERC_LIST_NOTICE_CATEGORIES = "OP_LIST_NOTICE_CATEGORIES";

	// Validation boundaries for the competition parameter
	public static final int MIN_COMPETITION = 1;
	public static final int MAX_COMPETITION = 5;

	// Default number of results when no limit is specified
	public static final int DEFAULT_LIMIT = 3;

	// Threshold in milliseconds for marking a notice as "new" (6 hours)
	public static final long SIX_HOURS_MS = 6L * 60 * 60 * 1000;

	// Date format used when serializing displayDatetime in the response
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// Error codes returned in bad-request responses
	public static final String ERROR_MISSING_COMPETITION = "MDBCME0001";
	public static final String ERROR_INVALID_COMPETITION = "MDBCME0014";

	// Error code for server-side failures (500 / 503)
	public static final String ERROR_SERVER_ERROR = "MDBCME0012";
	public static final String ERROR_SERVER_MESSAGE = "一時的にアクセスできない状態です。時間を置いて再度お試しください。";

	private NotificationConstants() {
	}
}