/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import optimization.function.fitness.CoevolutionaryFunctionBuilder;
import optimization.function.fitness.Function;
import optimization.function.space.BitSetSpace;
import optimization.function.space.Space;
import optimization.genetic.operator.CustomGeneticOperator;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.operator.crossover.BitCrossover;
import optimization.genetic.operator.crossover.GeneticCrossover;
import optimization.genetic.operator.mutation.BitMutator;
import optimization.genetic.operator.mutation.GeneticMutator;
import optimization.genetic.replace.ElitistReplacement;
import optimization.genetic.replace.GeneticReplacement;
import optimization.genetic.select.GeneticParentSelector;
import optimization.genetic.select.GeneticSelector;
import optimization.problem.ClassificationProblem;
import optimization.search.genetic.CoevolutionaryGeneticAlgorithm;
import optimization.search.termination.ForLoopTerminationCondition;
import optimization.search.termination.TerminationCondition;
import optimization.util.type.DataType;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class CoevolutionaryDemo {

	private static final int NUM_ITERS = 10_000;

	private static final int POP_SIZE = 128;

	private static final int DATA_SIZE = 35;

	private static final int NUM_OF_CLASSES = 2;

	private static final int NUM_OF_FEATURES = 7;

	private List<DataType> dataToValidateType0;
	private List<DataType> dataToValidateType1;

	public static void main(String[] args) {

		new CoevolutionaryDemo().runAll(2);
	}

	private void runAll(int numOfExperiments) {

		for (int i = 1; i <= numOfExperiments; i++) {
			System.out.println("*********** Running experiment: " + i);
			run(i);
		}
	}

	/**
	 * 
	 */
	private void run(int experiment) {

		TerminationCondition<Population<BitSet, Long>> terminationCondition = new ForLoopTerminationCondition<>(
				NUM_ITERS);
		GeneticSelector<BitSet, Long> selector = new GeneticParentSelector<>();

		GeneticCrossover<BitSet, Long> crossover = new BitCrossover<>(0.8);
		GeneticMutator<BitSet, Long> mutator = new BitMutator<>();
		GeneticOperator<BitSet, Long> geneticOperator = new CustomGeneticOperator<>(crossover, mutator);
		GeneticReplacement<BitSet, Long> replacement = new ElitistReplacement<>(POP_SIZE, true);
		CoevolutionaryGeneticAlgorithm search = new CoevolutionaryGeneticAlgorithm(terminationCondition, selector,
				geneticOperator, replacement, POP_SIZE, DATA_SIZE);

		List<DataType> dataToValidate = loadDataFromFile();// loadRandomData();
		List<Function<BitSet, Long>> classificationFunctions = CoevolutionaryFunctionBuilder.build(NUM_OF_CLASSES,
				DATA_SIZE, dataToValidate);
		Space<BitSet> space = new BitSetSpace(DATA_SIZE + 7);
		ClassificationProblem<BitSet> problem = new ClassificationProblem<>(space, classificationFunctions,
				NUM_OF_CLASSES);
		List<Solution<BitSet, Long>> solution = search.solve(problem);
		System.out.println(solution);
		double result = verifySolution(solution);

		try {
			Files.write(Paths.get("/tmp/code-analyzer/results/result.dat"), Arrays.asList(String.valueOf(result)),
					StandardOpenOption.APPEND);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private double verifySolution(List<Solution<BitSet, Long>> solution) {

		List<DataType> dataToValidate = new ArrayList<>();
		dataToValidate.addAll(dataToValidateType0.subList(500, 700));
		dataToValidate.addAll(dataToValidateType1.subList(500, 700));
		List<Function<BitSet, Long>> evalFns = CoevolutionaryFunctionBuilder.build(NUM_OF_CLASSES, DATA_SIZE,
				dataToValidate);
		double avg = 0;
		for (int i = 0; i < solution.size(); i++) {
			Solution<BitSet, Long> sol = solution.get(i);
			Function<BitSet, Long> evalFn = evalFns.get(i);
			long result = evalFn.calculate(sol.getSolution());
			avg += result;
			System.out.println("Evaluating solution: " + sol.getSolution() + " obtained: " + result + "/400");
		}
		return avg / 800;
	}

	protected List<DataType> loadRandomData() {

		List<DataType> dataToValidate = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			double[] features = new double[NUM_OF_FEATURES];
			for (int j = 0; j < NUM_OF_FEATURES; j++) {
				features[j] = random.nextDouble();
			}
			dataToValidate.add(new DataType(features, random.nextInt(NUM_OF_CLASSES)));
		}
		return dataToValidate;
	}

	protected List<DataType> loadDataFromFile() {

		List<DataType> dataToValidate = new ArrayList<>();
		dataToValidateType0 = new ArrayList<>();
		dataToValidateType1 = new ArrayList<>();
		double[] maximums = new double[NUM_OF_FEATURES];
		try (Scanner scanner = new Scanner(new FileInputStream("/tmp/code-analyzer/metrics/metrics_file.dat"))) {
			for (int i = 0; i < 1400; i++) {
				String[] line = scanner.nextLine().split(" ");
				int type = Integer.parseInt(line[0]);
				double[] features = new double[NUM_OF_FEATURES];
				for (int j = 0; j < NUM_OF_FEATURES; j++) {
					features[j] = Double.parseDouble(line[j + 1]);
					maximums[j] = Math.max(maximums[j], features[j]);
				}
				if (type == 0) {
					dataToValidateType0.add(new DataType(features, type));
				} else {
					dataToValidateType1.add(new DataType(features, type));
				}
			}
			System.out.println("File loaded");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		normalize(dataToValidateType0, maximums);
		normalize(dataToValidateType1, maximums);
		dataToValidate.addAll(dataToValidateType0.subList(0, 500));
		dataToValidate.addAll(dataToValidateType1.subList(0, 500));
		System.out.println("Data to validate loaded");
		return dataToValidate;
	}

	private void normalize(List<DataType> dataToValidate, double[] maximums) {

		for (DataType dataType : dataToValidate) {
			for (int i = 0; i < NUM_OF_FEATURES; i++) {
				dataType.getFeatures()[i] /= maximums[i];
			}
		}
	}

}
