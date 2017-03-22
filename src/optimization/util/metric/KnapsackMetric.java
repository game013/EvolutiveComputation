/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.metric;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import optimization.util.goal.Goal;
import optimization.util.type.Population;
import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class KnapsackMetric {

	private List<Object> populationCollection;

	private static final String BESTS_FILE_NAME = "/tmp/bests_%d.csv";

	public KnapsackMetric() {

		populationCollection = Tracer.get(Population.class);
	}

	public void putDataOfBestInFile(int runId) {

		List<Solution<?, Double>> solution = extractBests();
		try (FileWriter fw = new FileWriter(String.format(BESTS_FILE_NAME, runId))) {
			String result = solution.stream().map(Solution::getFitnessValue).map(String::valueOf)
					.reduce((t, u) -> t + "\n" + u).get();
			fw.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public <C extends Comparable<C>> List<Solution<?, C>> extractBests() {

		List<Solution<?, C>> bestSolutions = new ArrayList<>();
		for (Object population : populationCollection) {
			Solution<?, C> best = Goal.getBestFromPopulation(((Population<?, C>) population));
			System.out.println(String.format("Best each it: %s -> %f", best.getSolution(), best.getFitnessValue()));
			bestSolutions.add(best);
		}
		return bestSolutions;
	}

}
