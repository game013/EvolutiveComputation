/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.metric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Oscar Garavito
 *
 */
public class Tracer {

	/**
	 * 
	 */
	private static final Map<Class<?>, List<Object>> TRACE = new HashMap<>();

	/**
	 * 
	 */
	private static boolean ON = false;

	/**
	 * @param clazz
	 */
	public static void add(Class<?> clazz) {

		TRACE.put(clazz, new ArrayList<>());
	}

	/**
	 * 
	 */
	public static void start() {

		for (Entry<Class<?>, List<Object>> entry : TRACE.entrySet()) {
			entry.getValue().clear();
		}
		ON = true;
	}

	/**
	 * 
	 */
	public static void stop() {

		ON = false;
	}

	/**
	 * 
	 */
	public static void clear() {

		TRACE.clear();
	}

	/**
	 * @param clazz
	 * @param data
	 */
	public static void trace(Class<?> clazz, Object data) {

		if (ON && TRACE.containsKey(clazz)) {
			TRACE.get(clazz).add(data);
		}
	}

	public static boolean isActive(Class<?> clazz) {

		return ON && TRACE.containsKey(clazz);
	}

	/**
	 * @param clazz
	 * @return
	 */
	public static List<Object> get(Class<?> clazz) {

		return TRACE.getOrDefault(clazz, Collections.emptyList());
	}

}
