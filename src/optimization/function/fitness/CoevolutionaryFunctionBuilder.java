/**
 * COPYRIGHT (C) 2015. All Rights Reserved.
 */
package optimization.function.fitness;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import optimization.util.BitUtils;
import optimization.util.type.DataType;

/**
 * @author Oscar Garavito
 *
 */
public final class CoevolutionaryFunctionBuilder {

	/**
	 * Private constructor.
	 */
	private CoevolutionaryFunctionBuilder() {

		// Nothing to do.
	}

	/**
	 * @param numberOfClasses
	 * @param dataSize
	 * @param dataToValidate
	 * @return
	 */
	public static List<Function<BitSet, Long>> build(final int numberOfClasses, final int dataSize,
			final List<DataType> dataToValidate) {

		List<Function<BitSet, Long>> functions = new ArrayList<>();
		for (int i = 0; i < numberOfClasses; i++) {
			functions.add(createFunction(i, dataSize, dataToValidate));
		}
		return functions;
	}

	/**
	 * @param classId
	 * @param dataSize
	 * @param dataToValidate
	 * @return
	 */
	private static Function<BitSet, Long> createFunction(final int classId, final int dataSize,
			final List<DataType> dataToValidate) {

		return params -> {
			long result = 0;
			for (DataType data : dataToValidate) {
				int atomSize = dataSize / data.getFeatures().length;
				double weight = 0;
				for (int i = 0; i < data.getFeatures().length; i++) {
					double feature = data.getFeatures()[i];
					long multiplier = BitUtils.extractLong(params, atomSize * i, atomSize * (i + 1));
					weight += (feature * multiplier);
				}
				weight /= Math.pow(2, atomSize);
				if ((data.getType() == classId && weight >= 0.5) || (data.getType() != classId && weight < 0.5)) {
					result++;
				}
			}
			return result;
		};
	}

}
