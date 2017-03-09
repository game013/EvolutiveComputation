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
public class RastriginFunction implements Function {

	public final double A;

	public RastriginFunction(double A) {

		this.A = A;
	}

	@Override
	public double calculate(double... params) {

		double result = 0.0;
		for (double param : params) {
			result += Math.pow(param, 2.0) - this.A * Math.cos(2 * Math.PI * param);
		}
		return result + (this.A * params.length);
	}

	@Override
	public double getLowerBound() {

		return -5.12;
	}

	@Override
	public double getUpperBound() {

		return 5.12;
	}

}
