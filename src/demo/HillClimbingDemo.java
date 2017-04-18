/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.util.Comparator;
import java.util.Optional;

import optimization.distribution.Distribution;
import optimization.distribution.NormalDistribution;
import optimization.function.fitness.Function;
import optimization.function.fitness.GriewankFunction;
import optimization.function.space.Space;
import optimization.genetic.operator.mutation.GeneticMutator;
import optimization.genetic.operator.mutation.MutationByDistribution;
import optimization.genetic.operator.mutation.MutatorAdapter;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.hillclimbing.HillClimbing;
import optimization.util.FunctionSpaces;
import optimization.util.metric.GenericMetric;
import optimization.util.metric.Tracer;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class HillClimbingDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int dimension = 100;
		int numberOfIterations = 10_000_000;
		// run(new ParetoDistribution(0.9, 0.00055), dimension,
		// numberOfIterations);
		// System.out.println("***************************************");
		run(new NormalDistribution(0.2), dimension, numberOfIterations);
		System.out.println("***************************************");
		// MutatorAdapter<double[], Double> oneFifthAdapter = new
		// OneFifthRule<>(100, 0.9);
		// run(new NormalDistribution(0.2), dimension, numberOfIterations,
		// Optional.of(oneFifthAdapter));
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

		Space<double[]> space = FunctionSpaces.getGriewankSpace(dimension);
//		Space<double[]> space = FunctionSpaces.getRastriginSpace(dimension);
		// Function<double[], Double> rastrigin = new RastriginFunction(dimension);
		Function<double[], Double> rastrigin = new GriewankFunction();
		GeneticMutator<double[], Double> mutation = new MutationByDistribution(distribution);
		Search<double[], Double> hillDescent;
		if (adapter.isPresent()) {
			hillDescent = new HillClimbing<>(numberOfIterations, mutation, adapter.get());
		} else {
			hillDescent = new HillClimbing<>(numberOfIterations, mutation);
		}
		OptimizationProblem<double[], Double> problem = new OptimizationProblem<>(space, rastrigin,
				Comparator.naturalOrder());
		int numberOfExperiments = 30;
		Tracer.add(Solution.class);
		for (int i = 1; i <= numberOfExperiments; i++) {
			Tracer.start();
			System.out.println(String.format("Experiment: [%d]", i));
			hillDescent.solve(problem).getFitnessValue();

			GenericMetric metric = new GenericMetric();
			metric.putDataOfBestInFile(i, "gw/hill_climbing");
		}
	}

}
