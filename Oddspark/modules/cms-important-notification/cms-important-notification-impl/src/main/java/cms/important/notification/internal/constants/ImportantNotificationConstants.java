package cms.important.notification.internal.constants;

public final class ImportantNotificationConstants {

	public static final String ERC_OBJECT_NOTICE_MASTER = "OP_OBJ_NOTICE_MASTER";
	public static final String ERC_OBJECT_IMPORTANT_NOTICE_PRIORITY =
		"OP_OBJ_IMPORTANT_NOTICE_PRIORITY";

	public static final String ERC_LIST_IMPORTANT_NOTICE_DISPLAY_PAGE =
		"OP_LIST_IMPORTANT_NOTICE_DISPLAY_PAGE";

	public static final String ERC_LIST_DISPLAY_BEFORE_AFTER_LOGIN =
		"OP_LIST_DISPLAY_BEFORE_AFTER_LOGIN";

	public static final String FK_NOTICE_MASTER_ID =
		"r_importantNoticePriority_c_NoticeMasterId";

	public static final int MIN_PAGECODE = 1;

	public static final int IMPORTANT_NOTICE_LIMIT = 3;

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String ERROR_MISSING_PAGECODE = "MDBCME0001";
	public static final String ERROR_INVALID_PAGECODE = "MDBCME0014";

	public static final String ERROR_SERVER_ERROR = "MDBCME0012";
	public static final String ERROR_SERVER_MESSAGE =
		"一時的にアクセスできない状態です。時間を置いて再度お試しください。";

	private ImportantNotificationConstants() {
	}

}
