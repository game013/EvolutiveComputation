/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import optimization.util.metric.CommonMetric;

public class GenericPlotDemo {
	
	public static final String GW = "gw/";
	public static final String T1 = "evolutionary/";
	public static final String T2 = "particle_swarm/";
	public static final String T3 = "differential_evolution/";
	public static final String T4 = "hill_climbing/";
	public static final String MO = "mo/";

	// private static final String PATH_TEMPLATE = "/tmp/gw/" + T4;
	private static final String PATH_TEMPLATE = "/tmp/" + MO;

	private static final int NUMBER_OF_FILES = 30;

	private static final int ROWS = 10_000;

	/**
	 * @param numberOfFiles
	 * @param rows
	 * @return
	 */
	private double[][] readFiles(int numberOfFiles, int rows) {

		double[][] data = new double[rows][numberOfFiles];
		for (int i = 1; i <= numberOfFiles; i++) {
			try (Scanner scanner = new Scanner(new FileInputStream(String.format(PATH_TEMPLATE + "errors_%d.txt", i)))) {
				for (int j = 0; j < rows; j++) {
					// data[j][i - 1] = scanner.nextDouble();
					String[] line = scanner.nextLine().split(" ");
					data[j][i - 1] = Double.parseDouble(line[1].replaceAll(",", "."));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("loaded file " + i);
		}
		return data;
	}

	private void createDataset() {

		double[][] data = readFiles(NUMBER_OF_FILES, ROWS);

		try (BufferedWriter medianWriter = new BufferedWriter(new FileWriter(PATH_TEMPLATE + "median.dat"));
				BufferedWriter stDevWriter = new BufferedWriter(new FileWriter(PATH_TEMPLATE + "st_dev.dat"));
				BufferedWriter maxWriter = new BufferedWriter(new FileWriter(PATH_TEMPLATE + "max.dat"));
				BufferedWriter minWriter = new BufferedWriter(new FileWriter(PATH_TEMPLATE + "min.dat"))) {
			for (int i = 0; i < ROWS; i++) {
				CommonMetric metric = new CommonMetric(data[i]);
				medianWriter.write(String.format("%d\t%f\n", i + 1, metric.getMedian()));
				maxWriter.write(String.format("%d\t%f\n", i + 1, metric.getMax()));
				minWriter.write(String.format("%d\t%f\n", i + 1, metric.getMin()));
				if ((i + 1) % 1_000 == 0 || i == 0) {
					stDevWriter.write(String.format("%d\t%f\t%f\n", i + 1, metric.getMedian(),
							metric.getStandardDeviationMedian()));
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public static void main(final String[] args) {
		new GenericPlotDemo().createDataset();
	}

}
