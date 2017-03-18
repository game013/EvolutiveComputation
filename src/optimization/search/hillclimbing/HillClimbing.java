/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package optimization.search.hillclimbing;

import optimization.problem.OptimizationProblem;
import optimization.search.Search;

/**
 *
 * @author Oscar.Garavito
 */
public class HillClimbing<D, C extends Comparable<C>> implements Search<D, C> {

	/**
	 * Number of iterations proposed for current try.
	 */
	private final int numberOfIterations;

	/**
	 * Mutation process.
	 */
	private final Mutation<D> mutation;

	/**
	 * @param numberOfIterations
	 * @param mutation
	 */
	public HillClimbing(int numberOfIterations, Mutation<D> mutation) {

		this.numberOfIterations = numberOfIterations;
		this.mutation = mutation;
	}

	@Override
	public C solve(OptimizationProblem<D, C> problem) {

		D solution = problem.getSpace().pick();
		for (int i = 0; i < this.numberOfIterations; i++) {
			D newSolution = problem.getSpace().repair(this.mutation.mutate(solution));
			if (problem.getFitnessFunction().calculate(newSolution)
					.compareTo(problem.getFitnessFunction().calculate(solution)) <= 0) {
				solution = newSolution;
			}
		}
		return problem.getFitnessFunction().calculate(solution);
	}

}
