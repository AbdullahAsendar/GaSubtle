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

import java.security.SecureRandom;

/**
 * 
 * <p>
 * Generates ids that can be used to identify anything
 * 
 * @author Asendar
 *
 */
public class IDUtils {
	private static volatile SecureRandom numberGenerator = new SecureRandom();
	private static volatile short sequence = (short) numberGenerator.nextInt();

	/**
	 * 
	 * <p>
	 * Uses {@link System#currentTimeMillis()} in id generation so you will never have a duplicate id
	 * 
	 * 
	 * @return a unique id
	 */
	public static synchronized String newID() {
        return String.valueOf((System.currentTimeMillis() << 20) | ((sequence++ & 0xFFFF) << 4) | (numberGenerator.nextInt() & 0xF));
    }
}
