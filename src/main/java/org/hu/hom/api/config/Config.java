/**
 *  Copyright (C) 2018  Abdullah Al-Shishani
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * 
 */
package org.hu.hom.api.config;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.core.exception.HomException;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.utils.Validator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Provides the {@link GeneticAlgorithm} with the options:
 * 
 * <ul>
 * 
 * <li>mutationPercentage: The percentage of mutants that will be picked to do operations on.</li>
 * <li>maxOrder: The order of the {@link HigherOrderMutant} to not be exceeded.</li>
 * <li>runRepeat: The number of times the algorithm will run.</li>
 * </ul>
 * 
 * 
 * <p>
 * And provides the {@link GeneticAlgorithm} with the termination conditions:
 * 
 * <ul>
 * 
 * <li>maxHoms: The maximum number of {@link HigherOrderMutant}s to generate.</li>
 * <li>requiredSubtleHoms: The maximum number of subtle {@link HigherOrderMutant}s to generate.</li>
 * <li>maxGeneration: The max generation to reach.</li>
 * <li>timeout: Timeout in seconds.</li>
 * </ul>
 * 
 * <p>
 * And provides the {@link GeneticAlgorithm} with paths:
 * 
 * <ul>
 * 
 * <li>originalFile: Path to original <i>.java</i> file under execution.</li>
 * <li>testCasesPath: Path to test suites <i>.class</i> files.</li>
 * <li>resultPath: Path to store the results to.</li>
 * <li>classPath: Class path to be used for test execution, must contain <i>junit.jar</i> and <i>hamcrest.jar</i>.</li>
 * </ul>
 * 
 * @author Asendar
 *
 */
@Getter
@Setter
@Builder
public class Config implements Constants {

	private @Builder.Default Integer mutationPercentage = 10;
	private @Builder.Default Integer maxOrder = 2;
	private @Builder.Default Integer runRepeat = 1;

	/**
	 * 
	 * 
	 */
	private Integer requiredSubtleHoms;
	private Integer maxHoms;
	private Integer maxGeneration;
	private Long timeout;
	/**
	 * 
	 * 
	 */
	private @Setter String originalFile;
	private String testCasesPath;
	private String resultPath;
	private @Builder.Default String mutantsPath = DEFAULT_MUTANTS_PATH;

	/**
	 * @param populationSize of the current generation
	 * @return number of mutants that shall be selected
	 */
	public int getSelectionSize(int populationSize) {
		int selectionSize = (int) ((getMutationPercentage() / 100.0) * populationSize);
		if (selectionSize <= 0)
			selectionSize = 1;

		return selectionSize;
	}

	/**
	 * <p>
	 * Makes sure that all options are provided;
	 * 
	 * <p>
	 * Throws {@link HomException} if some of the options are null;
	 * 
	 * @see Validator#assertNotNull(Object, String)
	 */
	public void validate() {

		Validator.assertNotNull(mutationPercentage, "mutationPercentage");
		Validator.assertNotNull(maxOrder, "maxOrder");
		Validator.assertNotNull(runRepeat, "runRepeat");
		Validator.assertNotNull(originalFile, "originalFile");
		Validator.assertNotNull(testCasesPath, "testCasesPath");
		Validator.assertNotNull(resultPath, "resultPath");

		if (requiredSubtleHoms == null && maxHoms == null && maxGeneration == null && timeout == null)
			HomException.throwException("No termination condition provided");
	}
}