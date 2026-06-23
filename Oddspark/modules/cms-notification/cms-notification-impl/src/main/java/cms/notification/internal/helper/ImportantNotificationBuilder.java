package cms.notification.internal.helper;

import cms.notification.dto.v1_0.DisplayPage;
import cms.notification.dto.v1_0.ImportantNotification;
import cms.notification.dto.v1_0.Priority;
import cms.notification.internal.constants.NotificationConstants;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.object.model.ObjectEntry;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ImportantNotificationBuilder {

	public static ImportantNotification build(
		ObjectEntry entry, String pageKey, ListTypeEntry pageEntry,
		List<ObjectEntry> relatedPriorities, Locale locale) {

		Map<String, Serializable> values = entry.getValues();

		ImportantNotification notification = new ImportantNotification();

		notification.setId(entry.getObjectEntryId());
		notification.setNoticeTitle((String)values.get("noticeTitle"));
		notification.setNoticeContent((String)values.get("noticeContent"));
		notification.setImportantNoticeStart(
			_formatDate(values.get("importantNoticeStart")));
		notification.setImportantNoticeEnd(
			_formatDate(values.get("importantNoticeEnd")));

		Object displayBeforeOrAfterLogin = values.get(
			"displayBeforeOrAfterLogin");

		if (displayBeforeOrAfterLogin != null) {
			notification.setDisplayBeforeOrAfterLogin(
				String.valueOf(displayBeforeOrAfterLogin));
		}

		notification.setDisplayPages(
			_buildDisplayPage(pageKey, pageEntry, locale));
		notification.setPriorities(
			_buildPriorities(relatedPriorities, pageKey, pageEntry, locale));

		return notification;
	}

	private static DisplayPage[] _buildDisplayPage(
		String pageKey, ListTypeEntry pageEntry, Locale locale) {

		DisplayPage page = new DisplayPage();

		try {
			page.setKey(Integer.parseInt(pageKey));
		}
		catch (NumberFormatException e) {
			page.setKey(0);
		}

		page.setName(pageEntry.getName(locale));

		return new DisplayPage[] {page};
	}

	private static Priority[] _buildPriorities(
		List<ObjectEntry> relatedPriorities, String pageKey,
		ListTypeEntry pageEntry, Locale locale) {

		return relatedPriorities.stream(
		).filter(
			p -> pageKey.equals(
				String.valueOf(p.getValues().get("displayPageSingle")))
		).map(
			p -> _buildPriority(p, pageKey, pageEntry, locale)
		).toArray(
			Priority[]::new
		);
	}

	private static Priority _buildPriority(
		ObjectEntry priorityEntry, String pageKey, ListTypeEntry pageEntry,
		Locale locale) {

		Priority priority = new Priority();

		priority.setId(priorityEntry.getObjectEntryId());

		Map<String, Serializable> values = priorityEntry.getValues();

		Serializable priorityValue = values.get("priority");

		if (priorityValue instanceof Number) {
			priority.setPriority(((Number)priorityValue).intValue());
		}

		priority.setDisplayPages(
			_buildDisplayPage(pageKey, pageEntry, locale));

		return priority;
	}

	private static String _formatDate(Object value) {
		if (value instanceof Date) {
			return new SimpleDateFormat(
				NotificationConstants.DATE_FORMAT
			).format(
				(Date)value
			);
		}

		return null;
	}

	private ImportantNotificationBuilder() {
	}

}
