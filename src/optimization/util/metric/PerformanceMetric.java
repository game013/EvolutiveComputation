/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.util.metric;

import java.time.Duration;
import java.time.Instant;

/**
 * @author Oscar Garavito
 *
 */
public final class PerformanceMetric {

	private Instant startTime;

	private Duration partialAccumulated;

	private int totalAvg;

	/**
	 * Returns a new instance of performance metric starting the chronometer if
	 * given start parameter is {@code true}.
	 * 
	 * @param start
	 *            Indicates if chronometer is started.
	 * @return
	 */
	public static PerformanceMetric getInstance(boolean start) {

		PerformanceMetric performance = new PerformanceMetric();
		if (start) {
			performance.start();
		}
		return performance;
	}

	/**
	 * Returns a new instance of performance metric starting the chronometer.
	 * 
	 * @return
	 */
	public static PerformanceMetric getInstance() {

		return getInstance(true);
	}

	/**
	 * 
	 */
	public void start() {

		startTime = Instant.now();
		partialAccumulated = Duration.ZERO;
	}

	/**
	 * @return
	 */
	public Duration finish() {

		Duration duration = Duration.between(startTime, Instant.now());
		System.out.println(format(duration));
		startTime = Instant.now();
		return duration;
	}

	/**
	 * 
	 */
	public void partialAvg() {

		partialAccumulated = partialAccumulated.plus(Duration.between(startTime, Instant.now()));
		startTime = Instant.now();
		totalAvg++;
	}

	public Duration finishAvg() {

		System.out.println(format(partialAccumulated.dividedBy(totalAvg)));
		Duration partial = partialAccumulated;
		partialAccumulated = Duration.ZERO;
		return partial;
	}

	private String format(Duration duration) {

		return duration.toString().substring(2);
	}

}
