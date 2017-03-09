/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package hilldescent.function;

/**
 *
 * @author Estudiante
 */
public interface Function {

	public double calculate(double... params);

	public default double getLowerBound() {

		return Double.MIN_VALUE;
	}

	public default double getUpperBound() {

		return Double.MAX_VALUE;
	}

}
