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
package org.hu.hom.core.utils;

import org.hu.hom.core.exception.HomException;

/**
 * @author Asendar
 *
 */
public class Validator {

	/**
	 * @param object to be checked
	 * @param name to log if the object is null
	 */
	public static void assertNotNull(Object object, String name) {
		if (object != null)
			return;

		HomException.throwException(String.format("Can not launch the algorithm, reason: [%s] is not provided", name));

	}

}
