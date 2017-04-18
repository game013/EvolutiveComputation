/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import optimization.util.metric.CommonMetric;

public class KnapsackPlotDemo extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6810570069609343398L;

	/**
	 * @param title
	 */
	public KnapsackPlotDemo(final String title) {

		super(title);
		final XYDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1340, 670));
		chartPanel.setMouseZoomable(true, false);
		setContentPane(chartPanel);
	}

	/**
	 * @param numberOfFiles
	 * @param rows
	 * @return
	 */
	private double[][] readFiles(int numberOfFiles, int rows) {

		double[][] data = new double[rows][numberOfFiles];
		for (int i = 1; i <= numberOfFiles; i++) {
			try (Scanner scanner = new Scanner(new FileInputStream(String.format("/tmp/small/bests_%d.csv", i)))) {
				for (int j = 0; j < rows; j++) {
					data[j][i - 1] = scanner.nextDouble();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("loaded file " + i);
		}
		return data;
	}

	private XYDataset createDataset() {

		int numberOfFiles = 30;
		int rows = 100_000;
		final YIntervalSeries series = new YIntervalSeries("Data");
		double value = 100.0;
		double[][] data = readFiles(numberOfFiles, rows);
		for (int i = 0; i < rows; i++) {
			CommonMetric metric = new CommonMetric(data[i]);
			try {
				value = value + Math.random() - 0.5;
				series.add(i, metric.getAverage(), metric.getAverage() + metric.getStandardDeviationMedian(),
						metric.getAverage() - metric.getStandardDeviationMedian());
			} catch (SeriesException e) {
				System.err.println("Error adding to series");
			}
		}

		try (BufferedWriter medianWriter = new BufferedWriter(new FileWriter("/tmp/small_median.dat"));
				BufferedWriter stDevWriter = new BufferedWriter(new FileWriter("/tmp/small_st_dev.dat"));
				BufferedWriter maxWriter = new BufferedWriter(new FileWriter("/tmp/small_max.dat"));
				BufferedWriter minWriter = new BufferedWriter(new FileWriter("/tmp/small_min.dat"))) {
			for (int i = 0; i < rows; i++) {
				CommonMetric metric = new CommonMetric(data[i]);
				medianWriter.write(String.format("%d\t%f\n", i + 1, metric.getMedian()));
				maxWriter.write(String.format("%d\t%f\n", i + 1, metric.getMax()));
				minWriter.write(String.format("%d\t%f\n", i + 1, metric.getMin()));
				if (i % 10_000 == 0) {
					stDevWriter.write(String.format("%d\t%f\t%f\n", i + 1, metric.getMedian(),
							metric.getStandardDeviationMedian()));
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();
		dataset.addSeries(series);
		return dataset;
	}

	private JFreeChart createChart(final XYDataset dataset) {

		// create the chart...
		JFreeChart chart = ChartFactory.createXYLineChart("Deviation Renderer - Knapsack Problem", // chart
				// title
				"# Iterations", // x axis label
				"Value", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
		);

		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		DeviationRenderer renderer = new DeviationRenderer(true, false);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		// renderer.setSeriesStroke(0, new BasicStroke(3.0f));
		renderer.setSeriesFillPaint(0, new Color(200, 200, 255));
		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
		// yAxis.setAutoRangeIncludesZero(false);
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		yAxis.setRange(0, 700_000);
		// yAxis.setRange(13_160_000, 13_560_000);

		return chart;
	}

	public static void main(final String[] args) {
		final String title = "Time Series Management";
		final KnapsackPlotDemo demo = new KnapsackPlotDemo(title);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}

}
