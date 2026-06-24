package cms.important.notification.internal.helper;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

public final class NotificationEntryFilter {

	public static boolean containsKey(Object value, String targetKey) {
		if (value == null) {
			return false;
		}

		String raw = String.valueOf(value).trim();

		if (raw.isEmpty() || raw.equals("null")) {
			return false;
		}

		try {
			if (raw.startsWith("[")) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray(raw);

				for (int i = 0; i < jsonArray.length(); i++) {
					if (targetKey.equals(jsonArray.getString(i))) {
						return true;
					}
				}
			}
			else {
				for (String part : raw.split(",")) {
					if (targetKey.equals(part.trim())) {
						return true;
					}
				}
			}
		}
		catch (Exception e) {
			return raw.contains(targetKey);
		}

		return false;
	}

	private NotificationEntryFilter() {
	}

}
