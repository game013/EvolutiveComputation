/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type;

/**
 * @author Oscar Garavito
 *
 */
public class DataType {

	private final double[] features;

	private final int type;

	/**
	 * @param features
	 * @param type
	 */
	public DataType(double[] features, int type) {

		this.features = features;
		this.type = type;
	}

	/**
	 * @return the features
	 */
	public double[] getFeatures() {
		return features;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

}
