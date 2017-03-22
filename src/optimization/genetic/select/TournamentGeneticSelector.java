/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.ArrayList;
import java.util.List;

import optimization.function.fitness.Function;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class TournamentGeneticSelector<D, C> extends AbstractGeneticSelector<D, C> implements GeneticSelector<D, C> {

	/**
	 * Tournament size.
	 */
	public static final int TOURNAMENT_SIZE = 4;

	/**
	 * Uniform genetic selector.
	 */
	private final GeneticSelector<D, C> uniformSelector;

	/**
	 * Roulette genetic selector.
	 */
	private final GeneticSelector<D, C> tournamentSelector;

	/**
	 * @param parentsSampleSize
	 */
	public TournamentGeneticSelector(int parentsSampleSize, GeneticSelector<D, C> tournamentSelector) {

		super(parentsSampleSize);
		if (tournamentSelector.getParentsSampleSize() != 1) {
			throw new IllegalArgumentException("Tournament selector must chosen only one individual.");
		}
		this.uniformSelector = new UniformGeneticSelector<>(TOURNAMENT_SIZE);
		this.tournamentSelector = tournamentSelector;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.select.GeneticSelector#selectParent(java.util.List,
	 * optimization.function.Function)
	 */
	@Override
	public List<Solution<D, C>> selectParent(Population<D, C> population, Function<D, C> function) {

		List<Solution<D, C>> selectedParents = new ArrayList<>();
		for (int i = 0; i < super.parentsSampleSize; i++) {
			selectedParents.add(playTournament(population, function));
		}
		return selectedParents;
	}

	/**
	 * Plays tournament until one wins.
	 * 
	 * @param population
	 * @param function
	 * @return Winner of the tournament.
	 */
	private Solution<D, C> playTournament(Population<D, C> population, Function<D, C> function) {

		List<Solution<D, C>> tournamentCompetitors = this.uniformSelector.selectParent(population, function);
		int size = TOURNAMENT_SIZE;
		while (size > 1) {
			List<Solution<D, C>> newParentList = new ArrayList<>();
			for (int i = 0; i < size; i += 2) {
				newParentList.addAll(this.tournamentSelector
						.selectParent(new Population<>(tournamentCompetitors.subList(i, i + 1)), function));
			}
			size /= 2;
			tournamentCompetitors = newParentList;
		}
		return tournamentCompetitors.get(0);
	}

}
