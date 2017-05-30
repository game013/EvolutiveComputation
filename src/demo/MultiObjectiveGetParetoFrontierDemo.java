/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Oscar Garavito
 *
 */
public class MultiObjectiveGetParetoFrontierDemo {

	public static void main(String[] args) {

		new MultiObjectiveGetParetoFrontierDemo().run();
	}

	private void run() {

		Set<double[]> distinctSolutions = new HashSet<>();
		for (int i = 1; i <= 30; i++) {
			try (Scanner scanner = new Scanner(new FileInputStream(String.format("/tmp/mo/best_%d.txt", i)))) {
				String[] line = scanner.nextLine().split(" ");
				double f1 = Double.parseDouble(line[0].replaceAll(",", "."));
				double f2 = Double.parseDouble(line[1].replaceAll(",", "."));
				distinctSolutions.add(new double[] { f1, f2 });
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Loaded file " + i);
		}
		System.out.println(distinctSolutions.size());
		distinctSolutions.stream().map(Arrays::toString).forEach(System.out::println);
	}

}
