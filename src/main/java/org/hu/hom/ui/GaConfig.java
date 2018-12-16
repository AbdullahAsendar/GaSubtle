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
package org.hu.hom.ui;

import org.hu.hom.api.algorithm.object.SelectionStrategy;
import org.hu.hom.api.config.Constants;
import org.hu.hom.core.object.HigherOrderMutant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * 
 * 
 * @author Asendar
 *
 */
@Builder
public class GaConfig {

	private @Builder.Default @Setter @Getter Integer mutationPercentage = 10;
	private @Builder.Default @Setter @Getter Integer maxOrder = 2;
	private @Builder.Default @Setter @Getter Integer runRepeat = 1;

	/**
	 * 
	 * 
	 */
	private  @Setter @Getter Integer requiredSubtleHoms;
	private @Setter @Getter Integer maxHoms;
	private @Setter @Getter Integer maxGeneration;
	private @Setter @Getter Long timeout;
	/**
	 * 
	 * 
	 */
	private @Setter @Getter String originalFile;
	private @Setter @Getter String testCasesPath;
	private @Setter @Getter String resultPath;
	private @Builder.Default @Setter @Getter String mutantsPath = Constants.DEFAULT_MUTANTS_PATH;

	private @Setter @Getter SelectionStrategy<HigherOrderMutant> selectionStrategy;
}