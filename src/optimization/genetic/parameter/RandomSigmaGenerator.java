/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.parameter;

import java.util.Random;

import optimization.util.type.SolutionParameter;
import optimization.util.type.constant.ParameterName;

/**
 * @author Oscar Garavito
 *
 */
public class RandomSigmaGenerator implements ParameterGenerator {

	private static final Random RANDOM = new Random();

	/**
	 * 
	 */
	private final double minimum;

	/**
	 * 
	 */
	private final double maximum;

	/**
	 * @param minimum
	 * @param maximum
	 */
	public RandomSigmaGenerator(double minimum, double maximum) {

		this.minimum = minimum;
		this.maximum = maximum;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.parameter.ParameterGenerator#generateParameter()
	 */
	@Override
	public SolutionParameter generateParameters() {

		SolutionParameter parameters = new SolutionParameter();
		parameters.set(ParameterName.SIGMA, this.minimum + (this.maximum - this.minimum) * RANDOM.nextDouble());
		return parameters;
	}

}
