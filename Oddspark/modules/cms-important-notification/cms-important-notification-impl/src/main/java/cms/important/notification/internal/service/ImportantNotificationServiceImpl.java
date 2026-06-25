package cms.important.notification.internal.service;

import cms.important.notification.dto.v1_0.ImportantNotificationResponse;
import cms.important.notification.dto.v1_0.Item;
import cms.important.notification.dto.v1_0.MessageInfo;
import cms.important.notification.internal.constants.ImportantNotificationConstants;
import cms.important.notification.internal.exception.ImportantNoticeNotFoundException;
import cms.important.notification.internal.helper.ImportantNotificationBuilder;
import cms.important.notification.internal.helper.ListTypeHelper;
import cms.important.notification.internal.helper.NotificationEntryFilter;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ImportantNotificationService.class)
public class ImportantNotificationServiceImpl
	implements ImportantNotificationService {

	@Override
	public ImportantNotificationResponse getImportantNotifications(
			int pagecode, long companyId, Locale locale, TimeZone timeZone)
		throws Exception {

		// 1. Load and validate display page list entries
		List<ListTypeEntry> displayPageEntries = ListTypeHelper.loadEntries(
			ImportantNotificationConstants.ERC_LIST_IMPORTANT_NOTICE_DISPLAY_PAGE,
			companyId, _listTypeDefinitionLocalService,
			_listTypeEntryLocalService);

		if ((pagecode < ImportantNotificationConstants.MIN_PAGECODE) ||
			(pagecode > displayPageEntries.size())) {

			throw new ImportantNoticeNotFoundException(
				ImportantNotificationConstants.ERROR_INVALID_PAGECODE,
				"正しい画面コードを入力してください。");
		}

		ListTypeEntry pageEntry = displayPageEntries.get(pagecode - 1);
		String pageKey = pageEntry.getKey();

		List<ListTypeEntry> loginDisplayEntries = ListTypeHelper.loadEntries(
			ImportantNotificationConstants.ERC_LIST_DISPLAY_BEFORE_AFTER_LOGIN,
			companyId, _listTypeDefinitionLocalService,
			_listTypeEntryLocalService);

		// 2. Get priority object definition
		ObjectDefinition priorityDef =
			_objectDefinitionLocalService
				.fetchObjectDefinitionByExternalReferenceCode(
					ImportantNotificationConstants
						.ERC_OBJECT_IMPORTANT_NOTICE_PRIORITY,
					companyId);

		if (priorityDef == null) {
			return _emptyResponse();
		}

		// 3. Fetch all priority entries and filter by displayPageSingle = pageKey
		List<ObjectEntry> allPriorities =
			_objectEntryLocalService.getObjectEntries(
				0, priorityDef.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		List<ObjectEntry> matchingPriorities = allPriorities.stream(
		).filter(
			p -> pageKey.equals(
				_toStringValue(p.getValues().get("displayPageSingle")))
		).collect(
			Collectors.toList()
		);

		if (matchingPriorities.isEmpty()) {
			return _emptyResponse();
		}

		// 4. Current date/time
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis + timeZone.getOffset(nowMillis));

		// 5. For each priority entry, fetch notice_master and apply all filters:
		//    - publishStatus = true
		//    - importantNotice = true
		//    - date range valid
		//    - displayPageMulti contains pageKey (double validation)
		//    Deduplicate by noticeMasterId (keep first = highest priority entry)
		Map<Long, ObjectEntry[]> seen = new LinkedHashMap<>();

		for (ObjectEntry priorityEntry : matchingPriorities) {
			Serializable fkValue = priorityEntry.getValues().get(
				ImportantNotificationConstants.FK_NOTICE_MASTER_ID);

			if (!(fkValue instanceof Number)) {
				continue;
			}

			long noticeMasterId = ((Number)fkValue).longValue();

			if (seen.containsKey(noticeMasterId)) {
				continue;
			}

			ObjectEntry notice = _objectEntryLocalService.fetchObjectEntry(
				noticeMasterId);

			if ((notice == null) ||
				!_isImportantNoticeMatch(notice, pageKey, now)) {

				continue;
			}

			seen.put(noticeMasterId, new ObjectEntry[] {notice, priorityEntry});
		}

		if (seen.isEmpty()) {
			return _emptyResponse();
		}

		// 7. Sort: priority ASC (0 = NULL = last), then importantNoticeStart DESC
		List<ObjectEntry[]> sorted = new ArrayList<>(seen.values());

		sorted.sort(
			Comparator.comparing(
				(ObjectEntry[] pair) -> _getEffectivePriority(pair[1]),
				Comparator.nullsLast(Comparator.naturalOrder())
			).thenComparing(
				pair -> _getImportantNoticeStart(pair[0]),
				Comparator.nullsLast(Comparator.reverseOrder())
			)
		);

		// 8. Take top 3 and build response
		List<Item> items = sorted.stream(
		).limit(
			ImportantNotificationConstants.IMPORTANT_NOTICE_LIMIT
		).map(
			pair -> ImportantNotificationBuilder.build(
				pair[0], pageKey, pageEntry, pair[1],
				loginDisplayEntries, locale)
		).collect(
			Collectors.toList()
		);

		ImportantNotificationResponse response =
			new ImportantNotificationResponse();

		response.setItems(items.toArray(new Item[0]));
		response.setReturnedCount(items.size());
		response.setMessageInfo((MessageInfo)null);

		return response;
	}

	private boolean _isImportantNoticeMatch(
		ObjectEntry entry, String pageKey, Date now) {

		Map<String, Serializable> values = entry.getValues();

		if (!Boolean.TRUE.equals(values.get("importantNotice"))) {
			return false;
		}

		if (!Boolean.TRUE.equals(values.get("publishStatus"))) {
			return false;
		}

		Object startObj = values.get("importantNoticeStart");

		if (!(startObj instanceof Date)) {
			return false;
		}

		Date start = (Date)startObj;

		if (start.after(now)) {
			return false;
		}

		Object endObj = values.get("importantNoticeEnd");

		if (endObj instanceof Date) {
			Date end = (Date)endObj;

			if (end.before(now)) {
				return false;
			}
		}

		return NotificationEntryFilter.containsKey(
			values.get("displayPageMulti"), pageKey);
	}

	private Integer _getEffectivePriority(ObjectEntry priorityEntry) {
		Serializable val = priorityEntry.getValues().get("priority");

		if (val instanceof Number) {
			int intVal = ((Number)val).intValue();

			// 0 stored as NULL equivalent → sort last
			return intVal > 0 ? intVal : null;
		}

		return null;
	}

	private Date _getImportantNoticeStart(ObjectEntry entry) {
		Object startObj = entry.getValues().get("importantNoticeStart");

		if (startObj instanceof Date) {
			return (Date)startObj;
		}

		return null;
	}

	private String _toStringValue(Object value) {
		if (value == null) {
			return null;
		}

		return String.valueOf(value).trim();
	}

	private ImportantNotificationResponse _emptyResponse() {
		ImportantNotificationResponse response =
			new ImportantNotificationResponse();

		response.setItems(new Item[0]);
		response.setReturnedCount(0);
		response.setMessageInfo((MessageInfo)null);

		return response;
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}