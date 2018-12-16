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
package org.hu.hom.ui.cmd;

import java.io.File;

import org.hu.hom.core.utils.IDUtils;

/**
 * <p>
 * Constants to be used by the {@link CommandHandler}
 * 
 * @author Asendar
 *
 */
public interface Constants {
	
	String RUN_ID = IDUtils.newID();

	String DEFAULT_HOME = System.getProperty("user.dir") + File.separator + "GaSubtle-home";

	String APP_CONFIG_FILE = "app_config.properties";

	String ORIGINAL_FILES_PATH = "original_files";

	String MUTANTS_PATH = "mutants";

	String OUTPUT_PATH = "output";

	String CLASS_PATH = "jars";
	
	String TEST_CASES_PATH = "test_set";

	String WORKING_PATH = "tmp"  + File.separator + RUN_ID;

}
