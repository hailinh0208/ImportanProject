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
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ImportantNotificationBuilder {

	public static Item build(
		ObjectEntry entry, String pageKey, ListTypeEntry pageEntry,
		List<ObjectEntry> relatedPriorities, Locale locale) {

		Map<String, Serializable> values = entry.getValues();

		Item item = new Item();

		item.setId(entry.getObjectEntryId());
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

		// setDisplayPages stores into the displayPageMulti field
		item.setDisplayPages(_buildDisplayPage(pageKey, pageEntry, locale));
		item.setPriorities(
			_buildPriorities(relatedPriorities, pageKey, pageEntry, locale));

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

		// setDisplayPages stores into the displayPageSingle field
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
