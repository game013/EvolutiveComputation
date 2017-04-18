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
public class GenericMetric {

	private List<Object> solutionCollection;

	private static final String FILE_PATH_TEMPLATE = "/tmp/%s/bests_%d.csv";

	public GenericMetric() {

		solutionCollection = Tracer.get(Solution.class);
	}

	public void putDataOfBestInFile(int runId, String dirPath) {

		try (FileWriter fw = new FileWriter(String.format(FILE_PATH_TEMPLATE, dirPath, runId))) {
			for (Object solution : solutionCollection) {
				Solution<?, ?> sol = (Solution<?, ?>) solution;
				fw.write(String.format("%f\n", (double) sol.getFitnessValue()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
