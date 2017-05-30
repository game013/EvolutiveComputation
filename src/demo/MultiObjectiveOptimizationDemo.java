/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;

import optimization.distribution.Distribution;
import optimization.distribution.ParetoDistribution;
import optimization.function.DominanceComparator;
import optimization.function.fitness.Function;
import optimization.function.fitness.Mop4;
import optimization.function.space.Space;
import optimization.genetic.operator.CustomGeneticOperator;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.operator.crossover.GeneticCrossover;
import optimization.genetic.operator.crossover.MultidimensionalSpaceCrossover;
import optimization.genetic.operator.mutation.GeneticMutator;
import optimization.genetic.operator.mutation.MutationByDistribution;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.genetic.MultiObjectiveSearch;
import optimization.search.termination.ForLoopTerminationCondition;
import optimization.search.termination.TerminationCondition;
import optimization.util.FunctionSpaces;
import optimization.util.goal.Distance;
import optimization.util.goal.EuclideanDistance;
import optimization.util.metric.Tracer;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class MultiObjectiveOptimizationDemo {

	private static final int POP_SIZE = 1_000;

	private static final int DIMENSION = 3;

	private static final int NUM_ITER = 10_000;

	private double[][] realFrontier;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		new MultiObjectiveOptimizationDemo().runAll(30);
	}

	public MultiObjectiveOptimizationDemo() {
	}

	public void runAll(int numOfExperiments) {
		for (int i = 1; i <= numOfExperiments; i++) {
			System.out.println("*********** Running experiment: " + i);
			run(i);
		}
	}

	public void run(int experiment) {

		TerminationCondition<Population<double[], double[]>> terminationCondition = new ForLoopTerminationCondition<>(
				NUM_ITER);
		Distribution distribution = new ParetoDistribution(0.9, 0.055);

		Function<double[], double[]> fitnessFunction = new Mop4(DIMENSION);
		Space<double[]> space = FunctionSpaces.getRastriginSpace(DIMENSION);

		Comparator<double[]> goal = new DominanceComparator(new boolean[] { true, true });

		OptimizationProblem<double[], double[]> problem = new OptimizationProblem<>(space, fitnessFunction, goal);

		GeneticMutator<double[], double[]> mutator = new MutationByDistribution<>(distribution);
		GeneticCrossover<double[], double[]> crossover = new MultidimensionalSpaceCrossover<>(0.8);
		GeneticOperator<double[], double[]> operator = new CustomGeneticOperator<>(crossover, mutator);
		Distance<double[]> distance = new EuclideanDistance();

		Search<double[], double[]> search = new MultiObjectiveSearch<>(POP_SIZE, terminationCondition, operator,
				distance);

		Tracer.start();
		Tracer.add(Population.class);
		Solution<double[], double[]> solution = search.solve(problem);
		System.out.println("Best found solution: " + Arrays.toString(solution.getSolution()) + " -> "
				+ Arrays.toString(solution.getFitnessValue()));

		printPopulation(fitnessFunction, experiment);
	}

	@SuppressWarnings("unchecked")
	private void printPopulation(Function<double[], double[]> fitnessFunction, int experiment) {

		readRealFrontierFile();
		List<Object> populationList = Tracer.get(Population.class);
		System.out.println(String.format("Population in each iteration [%d]:", populationList.size()));
		int i = 0;
		double[] errors = new double[populationList.size()];
		for (Object populationObj : populationList) {
			Population<double[], double[]> population = (Population<double[], double[]>) populationObj;

			Set<double[]> distinctSolutions = new HashSet<>();
			population.getPopulation().stream().map(Solution::getFitnessValue).forEach(distinctSolutions::add);
			errors[i] = calculeError(distinctSolutions);
			++i;
			if (i % 1000 == 0) {
				System.out.println("Processed error on iteration: " + i);
			}
			if (i == populationList.size()) {
				saveBestOnFile(experiment, distinctSolutions);
			}
		}
		saveErrorsOnFile(experiment, errors);
	}

	private double calculeError(Set<double[]> distinctSolutions) {

		Distance<double[]> distance = new EuclideanDistance();
		double error = 0;
		for (double[] sol : distinctSolutions) {
			double minDistance = Double.MAX_VALUE;
			for (double[] real : this.realFrontier) {
				minDistance = Math.min(minDistance, distance.calcule(sol, real));
			}
			error += Math.pow(minDistance, 2);
		}
		return error / distinctSolutions.size();
	}

	private void readRealFrontierFile() {

		int rows = 122;
		double[][] realFrontier = new double[rows][2];
		try (Scanner scanner = new Scanner(new FileInputStream("resources/kursawe_frontier.dat"))) {
			for (int i = 0; i < rows; i++) {
				String[] line = scanner.nextLine().split(" ");
				realFrontier[i][0] = Double.parseDouble(line[0]);
				realFrontier[i][1] = Double.parseDouble(line[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.realFrontier = realFrontier;
		System.out.println("loaded real frontier file ...");
	}

	private void saveErrorsOnFile(int file, double[] errors) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("/tmp/mo/errors_%d.txt", file)))) {
			for (int i = 0; i < errors.length; i++) {
				bw.write(String.format("%d %f\n", i + 1, errors[i]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveBestOnFile(int file, Set<double[]> distinctSolutions) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(String.format("/tmp/mo/best_%d.txt", file)))) {
			StringJoiner joiner = new StringJoiner("\n");
			distinctSolutions.stream().map(this::doubleArrayToString).forEach(joiner::add);
			System.out.println("Best: " + joiner.toString());
			bw.write(joiner.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String doubleArrayToString(double[] array) {

		StringJoiner joiner = new StringJoiner(" ");
		for (double dat : array) {
			joiner.add(String.format("%f", dat));
		}
		return joiner.toString();
	}

}
