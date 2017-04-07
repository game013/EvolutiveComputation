/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type;

import java.util.HashMap;
import java.util.Map;

import optimization.util.type.constant.ParameterName;

/**
 * @author Oscar Garavito
 *
 */
public class SolutionParameter {

	/**
	 * Map of parameters.
	 */
	private final Map<ParameterName, Object> parameterMap;

	/**
	 * 
	 */
	public SolutionParameter() {

		this.parameterMap = new HashMap<>();
	}

	/**
	 * @param key
	 * @return
	 */
	public Object get(String key) {

		return this.parameterMap.get(key);
	}

	/**
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <T> T get(String key, Class<T> clazz) {

		return clazz.cast(this.parameterMap.get(key));
	}

	/**
	 * @param key
	 * @param parameter
	 */
	public void set(ParameterName key, Object parameter) {

		this.parameterMap.put(key, parameter);
	}

}
