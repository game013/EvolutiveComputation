/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.problem;

import java.util.List;

import optimization.function.fitness.Function;
import optimization.function.space.Space;

/**
 * @author Oscar Garavito
 *
 */
public class ClassificationProblem<D> {

	/**
	 * Search space.
	 */
	private final Space<D> space;

	/**
	 * 
	 */
	private final List<Function<D, Long>> classificationFunctions;

	/**
	 * 
	 */
	private final int numberOfClasses;

	/**
	 * @param space
	 */
	public ClassificationProblem(Space<D> space, List<Function<D, Long>> classificationFunctions, int numberOfClasses) {

		if (numberOfClasses != classificationFunctions.size()) {
			throw new IllegalArgumentException("Number of classes must match with number of classification functions.");
		}
		this.space = space;
		this.classificationFunctions = classificationFunctions;
		this.numberOfClasses = numberOfClasses;
	}

	/**
	 * @return the space
	 */
	public Space<D> getSpace() {
		return space;
	}

	/**
	 * @return the classificationFunctions
	 */
	public List<Function<D, Long>> getClassificationFunctions() {
		return classificationFunctions;
	}

	/**
	 * @return the numberOfClasses
	 */
	public int getNumberOfClasses() {
		return numberOfClasses;
	}

}
