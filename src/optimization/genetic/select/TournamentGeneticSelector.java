/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.genetic.select;

import java.util.ArrayList;
import java.util.List;

import optimization.function.Function;

/**
 * @author Oscar Garavito
 *
 */
public class TournamentGeneticSelector<D> extends AbstractGeneticSelector<D> implements GeneticSelector<D> {

	/**
	 * Tournament size.
	 */
	public static final int TOURNAMENT_SIZE = 4;

	/**
	 * Uniform genetic selector.
	 */
	private final GeneticSelector<D> uniformSelector;

	/**
	 * Roulette genetic selector.
	 */
	private final GeneticSelector<D> rouletteSelector;

	/**
	 * @param parentsSampleSize
	 */
	protected TournamentGeneticSelector(int parentsSampleSize) {

		super(parentsSampleSize);
		this.uniformSelector = new UniformGeneticSelector<>(TOURNAMENT_SIZE);
		this.rouletteSelector = new RouletteGeneticSelector<>(1);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * optimization.genetic.select.GeneticSelector#selectParent(java.util.List,
	 * optimization.function.Function)
	 */
	@Override
	public List<D> selectParent(List<D> population, Function<D, Double> function) {

		List<D> selectedParents = new ArrayList<>();
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
	private D playTournament(List<D> population, Function<D, Double> function) {

		List<D> tournamentCompetitors = this.uniformSelector.selectParent(population, function);
		int size = TOURNAMENT_SIZE;
		while (size > 1) {
			List<D> newParentList = new ArrayList<>();
			for (int i = 0; i < size; i += 2) {
				newParentList
						.addAll(this.rouletteSelector.selectParent(tournamentCompetitors.subList(i, i + 1), function));
			}
			size /= 2;
			tournamentCompetitors = newParentList;
		}
		return tournamentCompetitors.get(0);
	}

}
