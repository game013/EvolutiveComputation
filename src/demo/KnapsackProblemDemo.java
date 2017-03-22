/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Properties;

import optimization.function.fitness.Function;
import optimization.function.fitness.KnapsackFitness;
import optimization.function.space.BitSetSpace;
import optimization.function.space.Space;
import optimization.genetic.operator.BinaryCrossover;
import optimization.genetic.operator.BitMutator;
import optimization.genetic.operator.CustomGeneticOperator;
import optimization.genetic.operator.GeneticCrossover;
import optimization.genetic.operator.GeneticMutator;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.replace.GenerationalReplacement;
import optimization.genetic.replace.GenerationalStableStateReplacement;
import optimization.genetic.replace.GeneticReplacement;
import optimization.genetic.select.ElitistSelector;
import optimization.genetic.select.GeneticSelector;
import optimization.genetic.select.RouletteGeneticSelector;
import optimization.genetic.select.TournamentGeneticSelector;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.genetic.GeneticAlgorithm;
import optimization.util.metric.KnapsackMetric;
import optimization.util.metric.Tracer;
import optimization.util.type.KnapsackElement;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * Demo of the knapsack. URL to get data sets:
 * https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html
 * 
 * @author Oscar Garavito
 *
 */
public class KnapsackProblemDemo {

	private static final int POP_SIZE = 100;

	private static final int NUM_ITER = 1_000;

	private double capacity;

	private Double[] weights;

	private Double[] values;

	private Boolean[] optimal;

	public static void main(String... args) {

		KnapsackProblemDemo demo = new KnapsackProblemDemo("resources/testSmall.properties");
		// demo.runGenerational();
		// demo.runTournamentGenerationalStableState();
		// demo.runElitistGenerational();
		demo.runElitistFlipMutationGenerationalStableState();
	}

	/**
	 * @param fileName
	 */
	public KnapsackProblemDemo(String fileName) {

		parseFileProperties(fileName);
	}

	/**
	 * Runs knapsack problem demo.
	 */
	public void runGenerational() {

		System.out.println("Runing pure generational demo.");
		Function<BitSet, Double> knapsackFitnessFunction = new KnapsackFitness(capacity, parseElements());
		Space<BitSet> space = new BitSetSpace(weights.length);

		GeneticSelector<BitSet, Double> rouletteSelector = new RouletteGeneticSelector<>(POP_SIZE);
		GeneticCrossover<BitSet, Double> crossover = new BinaryCrossover<>(0.9);
		GeneticOperator<BitSet, Double> geneticOperator = new CustomGeneticOperator<>(crossover);
		GeneticReplacement<BitSet, Double> replacement = new GenerationalReplacement<>();
		Tracer.add(Population.class);
		Tracer.start();

		Search<BitSet, Double> search = new GeneticAlgorithm<>(POP_SIZE, NUM_ITER, rouletteSelector, geneticOperator,
				replacement);
		OptimizationProblem<BitSet, Double> problem = new OptimizationProblem<>(space, knapsackFitnessFunction);
		Solution<BitSet, Double> foundSolution = search.solve(problem);

		System.out.println(String.format("Best found solution: %f, bitset: %s",
				calculateSolution(foundSolution.getSolution()), foundSolution.getSolution()));

		BitSet optimalBitSet = parseOptimalBitSet();
		System.out.println(
				String.format("Optimal solution: %f, bitset: %s", calculateSolution(optimalBitSet), optimalBitSet));
		KnapsackMetric metric = new KnapsackMetric();
		metric.putDataOfBestInFile(1);
	}

	public void runTournamentGenerationalStableState() {

		System.out.println("Runing tournament generational stable state demo.");
		Function<BitSet, Double> knapsackFitnessFunction = new KnapsackFitness(capacity, parseElements());
		Space<BitSet> space = new BitSetSpace(weights.length);

		GeneticSelector<BitSet, Double> rouletteSelector = new RouletteGeneticSelector<>(1);
		GeneticSelector<BitSet, Double> tournamentSelector = new TournamentGeneticSelector<>(POP_SIZE,
				rouletteSelector);
		GeneticCrossover<BitSet, Double> crossover = new BinaryCrossover<>(0.9);
		GeneticOperator<BitSet, Double> geneticOperator = new CustomGeneticOperator<>(crossover);
		GeneticReplacement<BitSet, Double> replacement = new GenerationalStableStateReplacement<>();
		Tracer.add(Population.class);
		Tracer.start();

		Search<BitSet, Double> search = new GeneticAlgorithm<>(POP_SIZE, NUM_ITER, tournamentSelector, geneticOperator,
				replacement);
		OptimizationProblem<BitSet, Double> problem = new OptimizationProblem<>(space, knapsackFitnessFunction);
		Solution<BitSet, Double> foundSolution = search.solve(problem);

		System.out.println(String.format("Best found solution: %f, bitset: %s",
				calculateSolution(foundSolution.getSolution()), foundSolution.getSolution()));

		BitSet optimalBitSet = parseOptimalBitSet();
		System.out.println(
				String.format("Optimal solution: %f, bitset: %s", calculateSolution(optimalBitSet), optimalBitSet));
		KnapsackMetric metric = new KnapsackMetric();
		metric.putDataOfBestInFile(1);
	}

	public void runElitistGenerational() {

		System.out.println("Runing elitist generational demo.");
		Function<BitSet, Double> knapsackFitnessFunction = new KnapsackFitness(capacity, parseElements());
		Space<BitSet> space = new BitSetSpace(weights.length);

		GeneticSelector<BitSet, Double> elitistSelector = new ElitistSelector<>(POP_SIZE);
		GeneticCrossover<BitSet, Double> crossover = new BinaryCrossover<>(0.9);
		GeneticOperator<BitSet, Double> geneticOperator = new CustomGeneticOperator<>(crossover);
		GeneticReplacement<BitSet, Double> replacement = new GenerationalReplacement<>();
		Tracer.add(Population.class);
		Tracer.start();

		Search<BitSet, Double> search = new GeneticAlgorithm<>(POP_SIZE, NUM_ITER, elitistSelector, geneticOperator,
				replacement);
		OptimizationProblem<BitSet, Double> problem = new OptimizationProblem<>(space, knapsackFitnessFunction);
		Solution<BitSet, Double> foundSolution = search.solve(problem);

		System.out.println(String.format("Best found solution: %f, bitset: %s",
				calculateSolution(foundSolution.getSolution()), foundSolution.getSolution()));

		BitSet optimalBitSet = parseOptimalBitSet();
		System.out.println(
				String.format("Optimal solution: %f, bitset: %s", calculateSolution(optimalBitSet), optimalBitSet));
		KnapsackMetric metric = new KnapsackMetric();
		metric.putDataOfBestInFile(1);
	}

	public void runElitistFlipMutationGenerationalStableState() {

		System.out.println("Runing elitist flip mutator generational stable state demo.");
		Function<BitSet, Double> knapsackFitnessFunction = new KnapsackFitness(capacity, parseElements());
		Space<BitSet> space = new BitSetSpace(weights.length);

		GeneticSelector<BitSet, Double> elitistSelector = new ElitistSelector<>(POP_SIZE);
		GeneticCrossover<BitSet, Double> crossover = new BinaryCrossover<>(0.9);
		GeneticMutator<BitSet, Double> mutator = new BitMutator<>();
		GeneticOperator<BitSet, Double> geneticOperator = new CustomGeneticOperator<>(crossover, mutator);
		GeneticReplacement<BitSet, Double> replacement = new GenerationalStableStateReplacement<>();
		Tracer.add(Population.class);
		// Tracer.start();

		Search<BitSet, Double> search = new GeneticAlgorithm<>(POP_SIZE, NUM_ITER, elitistSelector, geneticOperator,
				replacement);
		OptimizationProblem<BitSet, Double> problem = new OptimizationProblem<>(space, knapsackFitnessFunction);
		Solution<BitSet, Double> foundSolution = search.solve(problem);

		System.out.println(String.format("Best found solution: %f, bitset: %s",
				calculateSolution(foundSolution.getSolution()), foundSolution.getSolution()));
		BitSet optimalBitSet = parseOptimalBitSet();
		System.out.println(
				String.format("Optimal solution: %f, bitset: %s", calculateSolution(optimalBitSet), optimalBitSet));

		// KnapsackMetric metric = new KnapsackMetric();
		// metric.putDataOfBestInFile(1);
	}

	/**
	 * Parses properties file.
	 * 
	 * @param fileName
	 */
	private void parseFileProperties(String fileName) {

		Properties properties = new Properties();
		try (InputStream input = new FileInputStream(fileName)) {
			properties.load(input);
			this.capacity = Double.parseDouble(properties.getProperty("capacity").trim());
			this.weights = parseValue(properties.getProperty("weights"), Double.class, Double::parseDouble);
			this.values = parseValue(properties.getProperty("values"), Double.class, Double::parseDouble);
			this.optimal = parseValue(properties.getProperty("optimal"), Boolean.class, Boolean::parseBoolean);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private <R> R[] parseValue(String property, Class<R> clazz, java.util.function.Function<String, R> parseFunction) {

		String[] strValues = property.split(",");
		R[] values = (R[]) Array.newInstance(clazz, strValues.length);
		for (int i = 0; i < strValues.length; i++) {
			values[i] = parseFunction.apply(strValues[i].trim());
		}
		return values;
	}

	/**
	 * @return
	 */
	private List<KnapsackElement> parseElements() {

		List<KnapsackElement> elements = new ArrayList<>(weights.length);
		for (int i = 0; i < weights.length; i++) {
			elements.add(new KnapsackElement(weights[i], values[i]));
		}
		return elements;
	}

	private double calculateSolution(BitSet solution) {

		double result = 0;
		for (int i = 0; i < solution.length(); i++) {
			if (solution.get(i)) {
				result += values[i];
			}
		}
		return result;
	}

	private BitSet parseOptimalBitSet() {

		BitSet optimalBitSet = new BitSet(optimal.length);
		for (int i = 0; i < optimal.length; i++) {
			optimalBitSet.set(i, optimal[i]);
		}
		return optimalBitSet;
	}

}
