/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package optimization.search.hillclimbing;

import optimization.genetic.operator.mutation.GeneticMutator;
import optimization.genetic.operator.mutation.MutatorAdapter;
import optimization.problem.OptimizationProblem;
import optimization.search.Search;
import optimization.util.type.Solution;

/**
 *
 * @author Oscar.Garavito
 */
public class HillClimbing<D, C> implements Search<D, C> {

	/**
	 * Number of iterations proposed for current try.
	 */
	private final int numberOfIterations;

	/**
	 * Mutation process.
	 */
	private GeneticMutator<D, C> mutator;

	/**
	 * Mutator adapter.
	 */
	private final MutatorAdapter<D, C> mutatorAdapter;

	/**
	 * @param numberOfIterations
	 * @param mutator
	 */
	public HillClimbing(int numberOfIterations, GeneticMutator<D, C> mutator) {

		this(numberOfIterations, mutator, (m, b) -> m);
	}

	/**
	 * @param numberOfIterations
	 * @param mutator
	 * @param mutatorAdapter
	 */
	public HillClimbing(int numberOfIterations, GeneticMutator<D, C> mutator, MutatorAdapter<D, C> mutatorAdapter) {

		this.numberOfIterations = numberOfIterations;
		this.mutator = mutator;
		this.mutatorAdapter = mutatorAdapter;
	}

	@Override
	public Solution<D, C> solve(OptimizationProblem<D, C> problem) {

		D solution = problem.getSpace().pick();
		for (int i = 0; i < this.numberOfIterations; i++) {
			D newSolution = problem.getSpace()
					.repair(this.mutator.mutate(solution, problem.getFitnessFunction(), problem.getSpace()));
			boolean wasLastBetter = false;
			if (problem.getGoal().compare(problem.getFitnessFunction().calculate(newSolution),
					problem.getFitnessFunction().calculate(solution)) <= 0) {
				wasLastBetter = true;
				solution = newSolution;
			}
			this.mutator = this.mutatorAdapter.adaptMutator(this.mutator, wasLastBetter);
		}
		return new Solution<>(solution, problem.getFitnessFunction());
	}

	/**
	 * @return the mutator
	 */
	public GeneticMutator<D, C> getMutator() {
		return mutator;
	}

}
