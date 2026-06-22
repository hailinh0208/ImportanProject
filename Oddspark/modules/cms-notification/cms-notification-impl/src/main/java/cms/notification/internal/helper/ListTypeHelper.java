/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package cms.notification.internal.helper;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility methods for loading Liferay Picklist (ListTypeDefinition) entries.
 */
public final class ListTypeHelper {

	/**
	 * Loads all entries for the picklist identified by the given ERC.
	 * Returns an empty list if the definition does not exist.
	 */
	public static List<ListTypeEntry> loadEntries(
		String definitionErc, long companyId,
		ListTypeDefinitionLocalService definitionService,
		ListTypeEntryLocalService entryService) {

		ListTypeDefinition definition = definitionService.fetchListTypeDefinitionByExternalReferenceCode(definitionErc, companyId);

		if (definition == null) { return Collections.emptyList(); }

		return entryService.getListTypeEntries(definition.getListTypeDefinitionId());
	}

	/**
	 * Loads all entries for a picklist and returns them as a map keyed by
	 * each entry's key value, for O(1) lookup by key.
	 */
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
