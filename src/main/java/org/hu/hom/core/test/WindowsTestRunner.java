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
package org.hu.hom.core.test;

import java.io.File;

import org.hu.hom.api.config.Constants;

/**
 * <p>
 * Runs test cases on Windows
 * 
 * @author Asendar
 * 
 * @see LinuxTestRunner
 */
public class WindowsTestRunner extends AbstractTestRunner{

	/* (non-Javadoc)
	 * @see org.hu.hom.main.model.core.utils.AbstractTestRunner#getCommand(java.lang.String)
	 */
	@Override
	public String getCommand(String testSuites) {
		return String.format("java -cp ;%s;%s org.junit.runner.JUnitCore %s", 
				Constants.TMP,
				Constants.JUNIT + File.separator + "*", 
				testSuites);
	}

}
