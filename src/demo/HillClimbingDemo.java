/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.util.Arrays;

import optimization.distribution.Distribution;
import optimization.distribution.ExponentialDistribution;
import optimization.distribution.NormalDistribution;
import optimization.distribution.ParetoDistribution;
import optimization.distribution.UniformDistribution;
import optimization.function.fitness.Function;
import optimization.function.fitness.RastriginFunction;
import optimization.function.space.HyperCube;
import optimization.function.space.Space;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.hillclimbing.HillClimbing;
import optimization.search.hillclimbing.Mutation;
import optimization.search.hillclimbing.MutationByDistribution;
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
		run(new ParetoDistribution(0.9, 0.00055), dimension, numberOfIterations);
		System.out.println("***************************************");
		run(new NormalDistribution(0.2), dimension, numberOfIterations);
		System.out.println("***************************************");
		run(new UniformDistribution(), dimension, numberOfIterations);
		System.out.println("***************************************");
		run(new ExponentialDistribution(3), dimension, numberOfIterations);
	}

	private static void run(Distribution distribution, int dimension, int numberOfIterations) {

		Space<double[]> space = getRastriginSpace(dimension);
		Function<double[], Double> rastrigin = new RastriginFunction(10);
		Mutation<double[]> mutation = new MutationByDistribution(distribution);
		Search<double[], Double> hillDescent = new HillClimbing<>(numberOfIterations, mutation);
		OptimizationProblem<double[], Double> problem = new OptimizationProblem<>(space, rastrigin);
		int numberOfExperiments = 30;
		double[] results = new double[numberOfExperiments];
		for (int i = 0; i < numberOfExperiments; i++) {
			results[i] = hillDescent.solve(problem).getFitnessValue();
		}
		System.out.println(String.format("Distribution: [%s]", distribution.getClass().getSimpleName()));
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
