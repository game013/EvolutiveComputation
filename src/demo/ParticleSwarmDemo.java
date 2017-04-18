/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.fitness.GriewankFunction;
import optimization.function.space.Space;
import optimization.genetic.operator.CustomGeneticOperator;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.operator.crossover.GeneticCrossover;
import optimization.genetic.replace.GenerationalReplacement;
import optimization.genetic.replace.GeneticReplacement;
import optimization.genetic.select.GeneticParentSelector;
import optimization.genetic.select.GeneticSelector;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.genetic.GeneticAlgorithm;
import optimization.util.FunctionSpaces;
import optimization.util.goal.Goal;
import optimization.util.metric.GenericMetric;
import optimization.util.metric.Tracer;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class ParticleSwarmDemo {
	
	private static final int DIMENSION = 100;

	private static final int POP_SIZE = 100;

	private static final int NUM_ITER = 100_000;

	private static final double FIXED_VELOCITY = 0.2;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		run();
	}

	private static void run() {

		Space<double[]> space = FunctionSpaces.getGriewankSpace(DIMENSION);
		Function<double[], Double> rastrigin = new GriewankFunction();
//		Function<double[], Double> rastrigin = new RastriginFunction(DIMENSION);
//		Space<double[]> space = FunctionSpaces.getRastriginSpace(DIMENSION);
		GeneticSelector<double[], Double> selector = new GeneticParentSelector<>();
		GeneticCrossover<double[], Double> crossover = getCrossover();
		GeneticOperator<double[], Double> geneticOperator = new CustomGeneticOperator<>(crossover);
		GeneticReplacement<double[], Double> replacement = new GenerationalReplacement<>();

		Search<double[], Double> geneticSolver = new GeneticAlgorithm<>(POP_SIZE, NUM_ITER, selector, geneticOperator,
				replacement);
		OptimizationProblem<double[], Double> problem = new OptimizationProblem<>(space, rastrigin,
				Comparator.naturalOrder());
		int numberOfExperiments = 30;
		Tracer.add(Solution.class);
		for (int i = 1; i <= numberOfExperiments; i++) {
			Tracer.start();
			System.out.println(String.format("Experiment: [%d]", i));
			geneticSolver.solve(problem).getFitnessValue();

			GenericMetric metric = new GenericMetric();
			metric.putDataOfBestInFile(i, "gw/particle_swarm");
		}
	}

	public static GeneticCrossover<double[], Double> getCrossover() {

		GeneticCrossover<double[], Double> crossover = (parents, fitnessFunction, mutator, space) -> {

			List<Solution<double[], Double>> newPop = new ArrayList<>();
			Solution<double[], Double> best = Goal.getBestFromPopulation(parents, Comparator.naturalOrder());
			for (Solution<double[], Double> child : parents) {
				if (!child.equals(best)) {
					newPop.add(new Solution<double[], Double>(recombine(best.getSolution(), child.getSolution()),
							fitnessFunction));
				} else {
					newPop.add(child);
				}
			}
			return newPop;
		};
		return crossover;
	}

	private static double[] recombine(double[] solution, double[] child) {

		double[] newSol = new double[solution.length];
		double normal = 0;
		for (int i = 0; i < solution.length; i++) {
			normal += Math.pow(solution[i] - child[i], 2);
		}
		normal = Math.sqrt(normal);
		for (int i = 0; i < solution.length; i++) {
			newSol[i] = child[i] + (solution[i] - child[i]) * FIXED_VELOCITY / (normal);
		}
		return newSol;
	}

}
