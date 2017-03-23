/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.metric;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import optimization.util.type.Solution;

/**
 * @author Oscar Garavito
 *
 */
public class KnapsackMetric {

	private List<Object> solutionCollection;

	private static final String BESTS_FILE_NAME = "/tmp/bests_%d.csv";

	public KnapsackMetric() {

		solutionCollection = Tracer.get(Solution.class);
	}

	public void putDataOfBestInFile(int runId) {

		try (FileWriter fw = new FileWriter(String.format(BESTS_FILE_NAME, runId))) {
			for (Object solution : solutionCollection) {
				Solution<?, ?> sol = (Solution<?, ?>) solution;
				fw.write(String.format("%f\n", (double) sol.getFitnessValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
