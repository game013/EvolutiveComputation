/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.type;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oscar Garavito
 *
 */
public class Population<D, C> {

	/**
	 * Size of the population.
	 */
	private final int size;

	/**
	 * List of elements in the population.
	 */
	private final List<Solution<D, C>> population;

	public Population(int size) {

		this.size = size;
		this.population = new ArrayList<>(size);
	}

	public Population(List<Solution<D, C>> population) {

		this.population = population;
		this.size = population.size();
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the population
	 */
	public List<Solution<D, C>> getPopulation() {
		return population;
	}

	/**
	 * @param index
	 * @return
	 */
	public Solution<D, C> get(int index) {

		return population.get(index);
	}

	/**
	 * @param index
	 * @param solution
	 */
	public void set(int index, Solution<D, C> solution) {

		population.set(index, solution);
	}

	/**
	 * @return
	 */
	public int size() {

		return population.size();
	}

	@Override
	public String toString() {

		return this.getPopulation().toString();
	}

}
