/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.util.Arrays;
import java.util.Comparator;

import optimization.function.fitness.Function;
import optimization.function.fitness.GriewankFunction;
import optimization.function.space.HyperCube;
import optimization.function.space.Space;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.genetic.DifferentialEvolution;
import optimization.search.termination.ForLoopTerminationCondition;
import optimization.util.FunctionSpaces;
import optimization.util.metric.GenericMetric;
import optimization.util.metric.Tracer;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class DifferentialEvolutionDemo {

	private static final int POP_SIZE = 100;
	
	private static final int DIMENSION = 100;
	
	private static final int NUM_ITER = 100_000;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new DifferentialEvolutionDemo().run();
	}

	private void run() {

		Space<double[]> space = FunctionSpaces.getGriewankSpace(DIMENSION);
		Function<double[], Double> rastrigin = new GriewankFunction();
//		Function<double[], Double> rastrigin = new RastriginFunction(DIMENSION);
//		Space<double[]> space = FunctionSpaces.getRastriginSpace(DIMENSION);
		Search<double[], Double> differentialEvolution = new DifferentialEvolution(POP_SIZE,
				new ForLoopTerminationCondition<>(NUM_ITER), 0.3, 0.5);
		OptimizationProblem<double[], Double> problem = new OptimizationProblem<>(space, rastrigin,
				Comparator.naturalOrder());
		int numberOfExperiments = 30;
		Tracer.add(Solution.class);
		for (int i = 1; i <= numberOfExperiments; i++) {
			Tracer.start();
			System.out.println(String.format("Experiment: [%d]", i));
			differentialEvolution.solve(problem).getFitnessValue();

			GenericMetric metric = new GenericMetric();
			metric.putDataOfBestInFile(i, "gw/differential_evolution");
		}
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
