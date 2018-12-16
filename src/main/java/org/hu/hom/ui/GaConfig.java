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
import org.hu.hom.core.object.HigherOrderMutant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 
 * 
 * @author Asendar
 *
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GaConfig {

	private @Setter @Getter static Integer mutationPercentage = 10;
	private @Setter @Getter static Integer maxOrder = 2;
	private @Setter @Getter static Integer runRepeat = 1;	


	/**
	 * 
	 * 
	 */
	private @Setter @Getter static Integer requiredSubtleHoms;
	private @Setter @Getter static Integer maxHoms;
	private @Setter @Getter static Integer maxGeneration;
	private @Setter @Getter static Long timeout;	
	/**
	 * 
	 * 
	 */
	private @Setter @Getter static String originalFile;
	private @Setter @Getter static String testCasesPath;
	private @Setter @Getter static String resultPath;
	
	private @Setter @Getter static SelectionStrategy<HigherOrderMutant> selectionStrategy;
}