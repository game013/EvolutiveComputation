/**
 * 
 */
package hilldescent.metric;

/**
 * @author Estudiante
 *
 */
public class CommonMetric {

	private final double[] results;

	private final double average;

	private final double median;

	private final double standardDeviationAverage;

	private final double standardDeviationMedian;

	public CommonMetric(double[] results) {

		this.results = results;
		this.average = calculateAverage();
		this.median = calculateMedian();
		this.standardDeviationAverage = calculateStandardDeviation(true);
		this.standardDeviationMedian = calculateStandardDeviation(false);
	}

	private double calculateAverage() {

		double average = 0;
		for (int i = 0; i < this.results.length; i++) {
			average += this.results[i];
		}
		average /= (double) this.results.length;
		return average;
	}

	private double calculateMedian() {

		double median = 0;
		if (this.results.length % 2 == 1) {
			median = this.results[this.results.length / 2];
		} else {
			median = this.results[this.results.length / 2] + this.results[(this.results.length / 2) + 1];
			median /= 2.0;
		}
		return median;
	}

	private double calculateStandardDeviation(boolean useAverage) {

		double med = useAverage ? this.average : this.median;
		double standardDeviation = 0;
		for (double res : this.results) {
			standardDeviation += Math.pow(res - med, 2);
		}
		standardDeviation /= (double) this.results.length;
		standardDeviation = Math.sqrt(standardDeviation);
		return standardDeviation;
	}

	public double[] getResults() {
		return results;
	}

	public double getAverage() {
		return average;
	}

	public double getMedian() {
		return median;
	}

	public double getStandardDeviationAverage() {
		return standardDeviationAverage;
	}

	public double getStandardDeviationMedian() {
		return standardDeviationMedian;
	}

	@Override
	public String toString() {

		return String.format(
				"Number of executions: [%d]\nAverage: [%f]\nMedium: [%f]"
						+ "\nStandar Deviation using average: [%f]\nStandar Deviation using median: [%f]",
				this.results.length, this.average, this.median, this.standardDeviationAverage,
				this.standardDeviationMedian);
	}

}
