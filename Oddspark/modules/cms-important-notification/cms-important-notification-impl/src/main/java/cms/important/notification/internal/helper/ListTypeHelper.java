package cms.important.notification.internal.helper;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ListTypeHelper {

	public static List<ListTypeEntry> loadEntries(
		String definitionErc, long companyId,
		ListTypeDefinitionLocalService definitionService,
		ListTypeEntryLocalService entryService) {

		ListTypeDefinition definition =
			definitionService.fetchListTypeDefinitionByExternalReferenceCode(
				definitionErc, companyId);

		if (definition == null) {
			return Collections.emptyList();
		}

		return entryService.getListTypeEntries(
			definition.getListTypeDefinitionId());
	}

	public static Map<String, ListTypeEntry> loadEntryMap(
		String definitionErc, long companyId,
		ListTypeDefinitionLocalService definitionService,
		ListTypeEntryLocalService entryService) {

		return loadEntries(
			definitionErc, companyId, definitionService, entryService
		).stream(
		).collect(
			Collectors.toMap(ListTypeEntry::getKey, Function.identity())
		);
	}

	private ListTypeHelper() {
	}

}
