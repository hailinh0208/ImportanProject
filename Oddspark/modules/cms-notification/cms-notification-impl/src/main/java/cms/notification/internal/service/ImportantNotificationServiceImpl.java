package cms.notification.internal.service;

import cms.notification.dto.v1_0.ImportantNotification;
import cms.notification.dto.v1_0.ImportantNotificationResponse;
import cms.notification.dto.v1_0.MessageInfo;
import cms.notification.internal.constants.NotificationConstants;
import cms.notification.internal.exception.NotificationBadRequestException;
import cms.notification.internal.exception.NotificationNotFoundException;
import cms.notification.internal.helper.ImportantNotificationBuilder;
import cms.notification.internal.helper.ListTypeHelper;
import cms.notification.internal.helper.NotificationEntryFilter;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
			NotificationConstants.ERC_LIST_IMPORTANT_NOTICE_DISPLAY_PAGE,
			companyId, _listTypeDefinitionLocalService,
			_listTypeEntryLocalService);

		if ((pagecode < NotificationConstants.MIN_PAGECODE) ||
			(pagecode > displayPageEntries.size())) {

			throw new NotificationNotFoundException(
				NotificationConstants.ERROR_INVALID_PAGECODE,
				"正しい画面コードを入力してください。");
		}

		ListTypeEntry pageEntry = displayPageEntries.get(pagecode - 1);
		String pageKey = pageEntry.getKey();

		// 2. Get object definitions
		ObjectDefinition noticeMasterDef =
			_objectDefinitionLocalService
				.fetchObjectDefinitionByExternalReferenceCode(
					NotificationConstants.ERC_OBJECT_NOTICE_MASTER, companyId);

		ObjectDefinition priorityDef =
			_objectDefinitionLocalService
				.fetchObjectDefinitionByExternalReferenceCode(
					NotificationConstants.ERC_OBJECT_IMPORTANT_NOTICE_PRIORITY,
					companyId);

		if (noticeMasterDef == null) {
			return _emptyResponse();
		}

		// 3. Fetch all NoticeMaster entries
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis + timeZone.getOffset(nowMillis));

		List<ObjectEntry> filteredNotices =
			_objectEntryLocalService.getObjectEntries(
				0, noticeMasterDef.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS
			).stream(
			).filter(
				entry -> _isImportantNoticeMatch(entry, pageKey, now)
			).collect(
				Collectors.toList()
			);

		if (filteredNotices.isEmpty()) {
			return _emptyResponse();
		}

		// 4. Build priority map: noticeMasterId → list of priority entries
		Map<Long, List<ObjectEntry>> priorityMap = Collections.emptyMap();

		if (priorityDef != null) {
			priorityMap = _buildPriorityMap(
				noticeMasterDef, priorityDef, companyId);
		}

		// 5. Sort by priority (ASC, nulls last), then by importantNoticeStart
		//    DESC (for notices with null priority — closest start date to now)
		Map<Long, List<ObjectEntry>> finalPriorityMap = priorityMap;

		List<ObjectEntry> sorted = filteredNotices.stream(
		).sorted(
			Comparator.comparing(
				(ObjectEntry e) -> _getPriorityForPage(
					e, pageKey, finalPriorityMap),
				Comparator.nullsLast(Comparator.naturalOrder())
			).thenComparing(
				e -> _getImportantNoticeStart(e),
				Comparator.nullsLast(Comparator.reverseOrder())
			)
		).collect(
			Collectors.toList()
		);

		// 6. Deduplicate by notice ID (keep first = highest priority)
		Map<Long, ObjectEntry> seen = new LinkedHashMap<>();

		for (ObjectEntry entry : sorted) {
			seen.putIfAbsent(entry.getObjectEntryId(), entry);
		}

		// 7. Take top 3 and build response
		List<ImportantNotification> notifications = seen.values(
		).stream(
		).limit(
			NotificationConstants.IMPORTANT_NOTICE_LIMIT
		).map(
			entry -> ImportantNotificationBuilder.build(
				entry, pageKey, pageEntry,
				finalPriorityMap.getOrDefault(
					entry.getObjectEntryId(), Collections.emptyList()),
				locale)
		).collect(
			Collectors.toList()
		);

		ImportantNotificationResponse response =
			new ImportantNotificationResponse();

		response.setImportantNotifications(
			notifications.toArray(new ImportantNotification[0]));
		response.setReturnedCount(notifications.size());
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

	/**
	 * Builds a map of noticeMasterId → list of ImportantNoticePriority entries.
	 * Discovers the relationship FK field name dynamically via
	 * ObjectRelationshipLocalService.
	 */
	private Map<Long, List<ObjectEntry>> _buildPriorityMap(
			ObjectDefinition noticeMasterDef, ObjectDefinition priorityDef,
			long companyId)
		throws Exception {

		String fkFieldName = _findFkFieldName(
			noticeMasterDef, priorityDef);

		if (fkFieldName == null) {
			return Collections.emptyMap();
		}

		List<ObjectEntry> allPriorities =
			_objectEntryLocalService.getObjectEntries(
				0, priorityDef.getObjectDefinitionId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Map<Long, List<ObjectEntry>> map = new HashMap<>();

		for (ObjectEntry priority : allPriorities) {
			Serializable fkValue = priority.getValues().get(fkFieldName);

			if (fkValue instanceof Number) {
				long noticeMasterId = ((Number)fkValue).longValue();

				map.computeIfAbsent(
					noticeMasterId, k -> new ArrayList<>()
				).add(
					priority
				);
			}
		}

		return map;
	}

	/**
	 * Finds the FK field name on ImportantNoticePriority that points to
	 * NoticeMaster via the ObjectRelationship.
	 * Pattern: r_[relationshipName]_c_[noticeMasterObjectName]Id
	 */
	private String _findFkFieldName(
		ObjectDefinition noticeMasterDef, ObjectDefinition priorityDef) {

		try {
			List<ObjectRelationship> relationships =
				_objectRelationshipLocalService.getObjectRelationships(
					noticeMasterDef.getObjectDefinitionId());

			for (ObjectRelationship rel : relationships) {
				if (rel.getObjectDefinitionId2() ==
						priorityDef.getObjectDefinitionId()) {

					return "r_" + rel.getName() + "_c_" +
						noticeMasterDef.getName() + "Id";
				}
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	private Integer _getPriorityForPage(
		ObjectEntry entry, String pageKey,
		Map<Long, List<ObjectEntry>> priorityMap) {

		List<ObjectEntry> priorities = priorityMap.getOrDefault(
			entry.getObjectEntryId(), Collections.emptyList());

		return priorities.stream(
		).filter(
			p -> pageKey.equals(
				String.valueOf(p.getValues().get("displayPageSingle")))
		).map(
			p -> {
				Serializable val = p.getValues().get("priority");

				return (val instanceof Number)
					? ((Number)val).intValue() : null;
			}
		).filter(
			v -> v != null
		).findFirst(
		).orElse(
			null
		);
	}

	private Date _getImportantNoticeStart(ObjectEntry entry) {
		Object startObj = entry.getValues().get("importantNoticeStart");

		if (startObj instanceof Date) {
			return (Date)startObj;
		}

		return null;
	}

	private ImportantNotificationResponse _emptyResponse() {
		ImportantNotificationResponse response =
			new ImportantNotificationResponse();

		response.setImportantNotifications(new ImportantNotification[0]);
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

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}
