/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import funico.interpreter.ExampleException;
import funico.interpreter.Extractor;
import funico.language.LexicalException;
import funico.language.SyntacticalException;
import optimization.function.fitness.Function;
import optimization.function.fitness.FunicoFunction;
import optimization.function.space.FunicoSpace;
import optimization.function.space.Space;
import optimization.genetic.operator.CustomGeneticOperator;
import optimization.genetic.operator.GeneticOperator;
import optimization.genetic.operator.crossover.FunicoCrossover;
import optimization.genetic.operator.crossover.GeneticCrossover;
import optimization.genetic.operator.mutation.FunicoMutator;
import optimization.genetic.operator.mutation.GeneticMutator;
import optimization.genetic.replace.GenerationalStableStateReplacement;
import optimization.genetic.replace.GeneticReplacement;
import optimization.genetic.select.ElitistSelector;
import optimization.genetic.select.GeneticSelector;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.search.genetic.GeneticAlgorithm;
import optimization.util.type.Solution;
import optimization.util.type.tree.FunicoNode;

/**
 * @author Oscar Garavito
 *
 */
public class FunicoDemo {

	public static final int MAX_EQUATIONS = 3;

	public static final int MAX_NODES_PER_EQUATION = 10;

	private static final int POP_SIZE = 100;

	private static final int MAX_ITERS = 6_00;

	/**
	 * @param args
	 * @throws ExampleException
	 * @throws SyntacticalException
	 * @throws LexicalException
	 */
	public static void main(String[] args) {

		new FunicoDemo().run();
	}

	public void run() {

		try {
			/*
			 * String extractorExamples =
			 * "geq(0,1) = false; geq(0,0) = true; geq(1,0) = true"; String
			 * examples = extractorExamples +
			 * "geq(1,1) = true; geq(1,2) = false; geq(2,1) = true; geq(2,5) = false;"
			 * + "geq(5,2) = true; geq(3,3) = true";
			 */
			Input input = readInput();

			Extractor ext = new Extractor(input.getEquations());
			Map<String, String> examplesMap = new HashMap<>();
			for (String example : input.getEquations().split(";")) {
				String[] eq = example.split("=");
				examplesMap.put(eq[0].trim(), eq[1].trim());
			}
			GeneticSelector<List<FunicoNode>, Integer> selector = new ElitistSelector<>(POP_SIZE);
			Function<List<FunicoNode>, Integer> funicoFunction = new FunicoFunction(examplesMap);
			Space<List<FunicoNode>> space = new FunicoSpace(input.maxNodesPerEquation, input.maxEquations,
					ext.getTableFunctors(), ext.getTableVariables(), ext.getTableTerminals());
			GeneticCrossover<List<FunicoNode>, Integer> crossover = new FunicoCrossover(0.7);
			GeneticMutator<List<FunicoNode>, Integer> mutator = new FunicoMutator(0.6);
			OptimizationProblem<List<FunicoNode>, Integer> problem = new OptimizationProblem<>(space, funicoFunction,
					Comparator.naturalOrder());

			GeneticOperator<List<FunicoNode>, Integer> geneticOperator = new CustomGeneticOperator<>(crossover,
					mutator);
			GeneticReplacement<List<FunicoNode>, Integer> replacement = new GenerationalStableStateReplacement<>();
			Search<List<FunicoNode>, Integer> search = new GeneticAlgorithm<>(POP_SIZE, MAX_ITERS, selector,
					geneticOperator, replacement);

			for (int i = 0; i < 10; i++) {
				Solution<List<FunicoNode>, Integer> solution = search.solve(problem);
				System.out.println(solution);
			}

		} catch (ExampleException | LexicalException | SyntacticalException ex) {
			System.out.println(ex);
		}
	}

	private Input readInput() {

		try (Scanner scan = new Scanner(System.in)) {
			int maxEq = scan.nextInt();
			int maxNodPerEq = scan.nextInt();
			List<String> eqs = new ArrayList<>();
			String line = scan.nextLine();
			while ((line = scan.nextLine()).length() > 0) {
				eqs.add(line);
			}
			return new Input(maxEq, maxNodPerEq, eqs);
		}
	}

	class Input {
		int maxEquations;
		int maxNodesPerEquation;
		List<String> equations;

		public Input(int maxEquations, int maxNodesPerEquation, List<String> equations) {

			this.maxEquations = maxEquations;
			this.maxNodesPerEquation = maxNodesPerEquation;
			this.equations = equations;
		}

		public String getEquations() {

			return this.equations.stream().collect(Collectors.joining(";"));
		}
	}

}
