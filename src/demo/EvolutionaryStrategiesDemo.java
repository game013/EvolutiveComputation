/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.util.Comparator;

import optimization.distribution.Distribution;
import optimization.distribution.NormalDistribution;
import optimization.function.fitness.Function;
import optimization.function.fitness.GriewankFunction;
import optimization.function.space.Space;
import optimization.genetic.operator.mutation.GeneticMutator;
import optimization.genetic.operator.mutation.MutationByDistribution;
import optimization.genetic.operator.recombination.EvolutionaryRecombinator;
import optimization.genetic.operator.recombination.IntermediateRecombinator;
import optimization.genetic.parameter.ParameterGenerator;
import optimization.genetic.parameter.RandomSigmaGenerator;
import optimization.genetic.replace.ElitistReplacement;
import optimization.genetic.replace.GeneticReplacement;
import optimization.genetic.select.GeneticSelector;
import optimization.genetic.select.UniformGeneticSelector;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.genetic.EvolutionaryStrategies;
import optimization.search.termination.ForLoopTerminationCondition;
import optimization.search.termination.TerminationCondition;
import optimization.util.FunctionSpaces;
import optimization.util.metric.GenericMetric;
import optimization.util.metric.Tracer;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class EvolutionaryStrategiesDemo {

	private static final int DIMENSION = 100;

	private static final int POP_SIZE = 100;

	private static final int CHILD_SIZE = 200;

	private static final int MAX_ITERS = 100_000;

	private static final int PARENTS_SAMPLE_SIZE = 4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new EvolutionaryStrategiesDemo().run30();
	}

	private void run30() {

		Tracer.add(Solution.class);
		for (int i = 1; i <= 30; i++) {
			System.out.println("Run: " + i);
			Tracer.start();
			run();

			GenericMetric metric = new GenericMetric();
			metric.putDataOfBestInFile(i, "gw/evolutionary");
		}
	}

	/**
	 * Runs a test of evolutionary strategies.
	 */
	private void run() {

		TerminationCondition<Population<double[], Double>> terminationCondition = new ForLoopTerminationCondition<>(
				MAX_ITERS);
		ParameterGenerator parameterGenerator = new RandomSigmaGenerator(0.1, 0.4);
		Distribution distribution = new NormalDistribution(0.2);

		GeneticSelector<double[], Double> selector = new UniformGeneticSelector<>(PARENTS_SAMPLE_SIZE);
		// EvolutionaryRecombinator<double[], Double> recombinator = new DominantRecombinator<>();
		EvolutionaryRecombinator<double[], Double> recombinator = new IntermediateRecombinator<>();
		GeneticMutator<double[], Double> mutator = new MutationByDistribution<>(distribution);
		// GeneticReplacement<double[], Double> replacement = new GenerationalStableStateReplacement<>();
		GeneticReplacement<double[], Double> replacement = new ElitistReplacement<>(POP_SIZE, true, 0.02, 0.4);

		Space<double[]> space = FunctionSpaces.getGriewankSpace(DIMENSION);
		Function<double[], Double> rastrigin = new GriewankFunction();
//		Function<double[], Double> rastrigin = new RastriginFunction(DIMENSION);
//		Space<double[]> space = FunctionSpaces.getRastriginSpace(DIMENSION);
		OptimizationProblem<double[], Double> problem = new OptimizationProblem<>(space, rastrigin,
				Comparator.naturalOrder());

		Search<double[], Double> evolutiveSolver = new EvolutionaryStrategies<>(POP_SIZE, terminationCondition,
				parameterGenerator, CHILD_SIZE, selector, recombinator, mutator, replacement);
		Solution<double[], Double> solution = evolutiveSolver.solve(problem);

		System.out.println("Best: " + solution.getFitnessValue());
	}

}
