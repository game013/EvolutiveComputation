/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.util.Arrays;
import java.util.Comparator;

import optimization.function.fitness.Function;
import optimization.function.fitness.RastriginFunction;
import optimization.function.space.HyperCube;
import optimization.function.space.Space;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.genetic.DifferentialEvolution;
import optimization.search.termination.ForLoopTerminationCondition;
import optimization.util.metric.CommonMetric;

/**
 * @author Oscar Garavito
 *
 */
public class DifferentialEvolutionDemo {

	private static final int POP_SIZE = 100;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new DifferentialEvolutionDemo().run();
	}

	private void run() {

		int dimension = 10;
		int numberOfIterations = 10_000;

		Space<double[]> space = getRastriginSpace(dimension);
		Function<double[], Double> rastrigin = new RastriginFunction(10);
		Search<double[], Double> differentialEvolution = new DifferentialEvolution(POP_SIZE,
				new ForLoopTerminationCondition<>(numberOfIterations), 0.6, 1.7);
		OptimizationProblem<double[], Double> problem = new OptimizationProblem<>(space, rastrigin,
				Comparator.naturalOrder());
		int numberOfExperiments = 3;
		double[] results = new double[numberOfExperiments];
		for (int i = 0; i < numberOfExperiments; i++) {
			System.out.println(String.format("Experiment: [%d]", i));
			results[i] = differentialEvolution.solve(problem).getFitnessValue();
			System.out.println(results[i]);
		}
		System.out.println(new CommonMetric(results));
	}

	/**
	 * @param dimension
	 * @return
	 */
	public static Space<double[]> getRastriginSpace(int dimension) {

		double[] lowerBound = new double[dimension];
		Arrays.fill(lowerBound, -5.12);
		double[] upperBound = new double[dimension];
		Arrays.fill(upperBound, 5.12);
		return new HyperCube(lowerBound, upperBound);
	}

}
