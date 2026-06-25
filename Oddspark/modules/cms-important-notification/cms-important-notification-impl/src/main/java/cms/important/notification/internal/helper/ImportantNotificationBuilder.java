package cms.important.notification.internal.helper;

import cms.important.notification.dto.v1_0.DisplayPage;
import cms.important.notification.dto.v1_0.Item;
import cms.important.notification.dto.v1_0.Priority;
import cms.important.notification.internal.constants.ImportantNotificationConstants;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.object.model.ObjectEntry;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public final class ImportantNotificationBuilder {

	public static Item build(
		ObjectEntry noticeEntry, String pageKey, ListTypeEntry pageEntry,
		ObjectEntry priorityEntry, Locale locale) {

		Map<String, Serializable> values = noticeEntry.getValues();

		Item item = new Item();

		item.setId(noticeEntry.getObjectEntryId());
		item.setNoticeTitle((String)values.get("noticeTitle"));
		item.setNoticeContent((String)values.get("noticeContent"));
		item.setImportantNoticeStart(
			_formatDate(values.get("importantNoticeStart")));
		item.setImportantNoticeEnd(
			_formatDate(values.get("importantNoticeEnd")));

		Object displayBeforeOrAfterLogin = values.get("displayBeforeOrAfterLogin");

		if (displayBeforeOrAfterLogin != null) {
			item.setDisplayBeforeOrAfterLogin(
				String.valueOf(displayBeforeOrAfterLogin));
		}

		item.setDisplayPages(_buildDisplayPage(pageKey, pageEntry, locale));
		item.setPriorities(
			new Priority[] {_buildPriority(priorityEntry, pageKey, pageEntry, locale)});

		return item;
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

	private static Priority _buildPriority(
		ObjectEntry priorityEntry, String pageKey, ListTypeEntry pageEntry,
		Locale locale) {

		Priority priority = new Priority();

		priority.setId(priorityEntry.getObjectEntryId());

		Map<String, Serializable> values = priorityEntry.getValues();

		Serializable priorityValue = values.get("priority");

		if (priorityValue instanceof Number) {
			int intVal = ((Number)priorityValue).intValue();

			priority.setPriority(intVal > 0 ? intVal : null);
		}

		priority.setDisplayPages(_buildDisplayPage(pageKey, pageEntry, locale));

		return priority;
	}

	private static String _formatDate(Object value) {
		if (value instanceof Date) {
			return new SimpleDateFormat(
				ImportantNotificationConstants.DATE_FORMAT
			).format(
				(Date)value
			);
		}

		return null;
	}

	private ImportantNotificationBuilder() {
	}

}