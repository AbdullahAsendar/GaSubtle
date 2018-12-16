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

import java.io.File;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.core.utils.IDUtils;


/**
 * 
 * <p>
 * Constants to be used by the {@link GeneticAlgorithm}
 * 
 * @author Asendar
 *
 */
public interface Constants {
	
	String RUN_ID = IDUtils.newID();

	String WORKBENCH = System.getProperty("user.dir") + File.separator + "workbench" + RUN_ID;
	
	String DEFAULT_MU_JAVA_HOME = WORKBENCH + File.separator + "muJava";

	String DEFAULT_MUTANTS_PATH = WORKBENCH + File.separator + "mutants";
	
	String TMP = WORKBENCH + File.separator + "tmp";
	
	String JUNIT = WORKBENCH + File.separator + "junit" + File.separator;
}
