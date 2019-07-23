package cn.cloud.log.util;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {
	/**
	 * to json
	 *
	 * @author Administrator
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		GsonBuilder builder = new GsonBuilder().disableHtmlEscaping().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss");
		return builder.create().toJson(obj);
	}

	/**
	 * 方法注释
	 *
	 * @author Administrator
	 * @param obj
	 * @param excludeFields
	 * @return
	 */
	public static String toJson(Object obj, String... excludeFields) {
		GsonBuilder builder = new GsonBuilder().disableHtmlEscaping().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss");

		builder.setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				for (String field : excludeFields) {
					if (f.getName().matches(field)) {
						return true;
					}
				}

				return false;
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		});

		return builder.create().toJson(obj);
	}

	/**
	 * to pretty json
	 *
	 * @author Administrator
	 * @param obj
	 * @return
	 */
	public static String toPrettyJson(Object obj) {
		GsonBuilder builder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
				.serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss");
		return builder.create().toJson(obj);
	}

	/**
	 * json转对象
	 *
	 * @author Administrator
	 * @param json
	 * @param c
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> c) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
				.create();
		return gson.fromJson(json, c);
	}

	/**
	 * json数组转list
	 *
	 * @author Administrator
	 * @param json
	 * @param elementType
	 * @return
	 */
	public static <T> List<T> fromJsonArray(String json, Class<T[]> elementType) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		T[] array = gson.fromJson(json, elementType);
		return Arrays.asList(array);
	}

	/**
	 * simple json to map
	 *
	 * @author Administrator
	 * @param s
	 * @return
	 */
	public static Map<String, String> json2Map(String s) {
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(s);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		Set<Entry<String, JsonElement>> entrySet = jsonObject.entrySet();

		Map<String, String> map = new HashMap<String,String>();
		for (Entry<String, JsonElement> entry : entrySet) {
			JsonElement e = entry.getValue();
			if (!e.isJsonNull()) {
				String v = e.getAsString();
				map.put(entry.getKey(), v);
			}
		}
		return map;
	}

	/**
	 * json转对象
	 *
	 * @author Administrator
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String json, Type type) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		return gson.fromJson(json, type);
	}
}
