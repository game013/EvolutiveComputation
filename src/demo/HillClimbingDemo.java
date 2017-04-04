/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import optimization.distribution.Distribution;
import optimization.distribution.NormalDistribution;
import optimization.function.fitness.Function;
import optimization.function.fitness.RastriginFunction;
import optimization.function.space.HyperCube;
import optimization.function.space.Space;
import optimization.genetic.operator.GeneticMutator;
import optimization.genetic.operator.MutationByDistribution;
import optimization.genetic.operator.MutatorAdapter;
import optimization.genetic.operator.OneFifthRule;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.hillclimbing.HillClimbing;
import optimization.util.metric.CommonMetric;

/**
 * @author Oscar Garavito
 *
 */
public class HillClimbingDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int dimension = 10;
		int numberOfIterations = 100_000;
		// run(new ParetoDistribution(0.9, 0.00055), dimension,
		// numberOfIterations);
		// System.out.println("***************************************");
		run(new NormalDistribution(0.2), dimension, numberOfIterations);
		System.out.println("***************************************");
		MutatorAdapter<double[], Double> oneFifthAdapter = new OneFifthRule<>(100, 0.9);
		run(new NormalDistribution(0.2), dimension, numberOfIterations, Optional.of(oneFifthAdapter));
		// System.out.println("***************************************");
		// run(new UniformDistribution(), dimension, numberOfIterations);
		// System.out.println("***************************************");
		// run(new ExponentialDistribution(3), dimension, numberOfIterations);
	}

	/**
	 * @param distribution
	 * @param dimension
	 * @param numberOfIterations
	 */
	private static void run(Distribution distribution, int dimension, int numberOfIterations) {

		run(distribution, dimension, numberOfIterations, Optional.empty());
	}

	private static void run(Distribution distribution, int dimension, int numberOfIterations,
			Optional<MutatorAdapter<double[], Double>> adapter) {

		Space<double[]> space = getRastriginSpace(dimension);
		Function<double[], Double> rastrigin = new RastriginFunction(10);
		GeneticMutator<double[], Double> mutation = new MutationByDistribution(distribution);
		Search<double[], Double> hillDescent;
		if (adapter.isPresent()) {
			hillDescent = new HillClimbing<>(numberOfIterations, mutation, adapter.get());
		} else {
			hillDescent = new HillClimbing<>(numberOfIterations, mutation);
		}
		OptimizationProblem<double[], Double> problem = new OptimizationProblem<>(space, rastrigin,
				Comparator.reverseOrder());
		int numberOfExperiments = 30;
		double[] results = new double[numberOfExperiments];
		for (int i = 0; i < numberOfExperiments; i++) {
			results[i] = hillDescent.solve(problem).getFitnessValue();
		}
		System.out.println(String.format("Distribution: [%s]", distribution.getClass().getSimpleName()));
		if (adapter.isPresent()) {
			System.out.println(String.format("Using adapter: [%s]", adapter.get().getClass().getSimpleName()));
		} else {
			System.out.println("Using adapter: [None]");
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
