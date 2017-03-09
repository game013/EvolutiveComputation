/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package hilldescent;

import hilldescent.distribution.Distribution;
import hilldescent.distribution.ExponentialDistribution;
import hilldescent.distribution.NormalDistribution;
import hilldescent.distribution.ParetoDistribution;
import hilldescent.distribution.UniformDistribution;
import hilldescent.function.Function;
import hilldescent.function.FunctionHelper;
import hilldescent.function.RastriginFunction;
import hilldescent.metric.CommonMetric;

/**
 *
 * @author Oscar.Garavito
 */
public class HillDescent {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		int numberOfVars = 10;
		int numberOfRuns = 100_000;
		Function rastrigin = new RastriginFunction(10.0);
		HillDescent hillDescent = new HillDescent();
		hillDescent.execute(numberOfVars, numberOfRuns, rastrigin, new NormalDistribution(0.2));
		// hillDescent.execute(numberOfVars, numberOfRuns, rastrigin, new
		// UniformDistribution());
		// hillDescent.execute(numberOfVars, numberOfRuns, rastrigin, new
		// ParetoDistribution(1.1, 0.00055));
		// hillDescent.execute(numberOfVars, numberOfRuns, rastrigin, new
		// ExponentialDistribution(3));
		run(new ParetoDistribution(0.9, 0.00055), numberOfVars, numberOfRuns, rastrigin);
		System.out.println("***************************************");
		run(new NormalDistribution(0.2), numberOfVars, numberOfRuns, rastrigin);
		System.out.println("***************************************");
		run(new UniformDistribution(), numberOfVars, numberOfRuns, rastrigin);
		System.out.println("***************************************");
		run(new ExponentialDistribution(3), numberOfVars, numberOfRuns, rastrigin);
	}

	private static void run(Distribution distribution, int numberOfVars, int numberOfRuns, Function function) {

		HillDescent hillDescent = new HillDescent();
		int numberOfExperiments = 30;
		double[] results = new double[numberOfExperiments];
		for (int i = 0; i < numberOfExperiments; i++) {
			results[i] = hillDescent.execute(numberOfVars, numberOfRuns, function, distribution);
		}
		System.out.println(String.format("Distribution: [%s]", distribution.getClass().getSimpleName()));
		System.out.println(new CommonMetric(results));
	}

	/**
	 * @param numberOfVars
	 * @param numberOfRuns
	 * @param function
	 * @param dist
	 */
	public double execute(int numberOfVars, int numberOfRuns, Function function, Distribution dist) {

		// System.out.println("******************************************************");
		// System.out.println(String.format("Trying with distribution [%s]",
		// dist.getClass().getSimpleName()));
		double[] points = FunctionHelper.getStartRandomPoint(numberOfVars, function);
		double currentResult = Double.MAX_VALUE;
		for (int i = 0; i < numberOfRuns; i++) {
			double[] nextPoints = FunctionHelper.addDelta(points, dist, function);
			double result = function.calculate(nextPoints);
			if (result <= currentResult) {
				points = nextPoints;
				currentResult = result;
				// System.out.println(String.format("Optimal found: [%f]",
				// result));
			}
		}
		// System.out.println(String.format("Found points for [%d] vars and [%d]
		// runs", numberOfVars, numberOfRuns));
		// for (double point : points) {
		// System.out.print(String.format("%f ", point));
		// }
		// System.out.println("\nResult: " + currentResult);
		return currentResult;
	}

}
