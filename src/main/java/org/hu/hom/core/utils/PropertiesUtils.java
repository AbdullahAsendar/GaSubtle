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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 
 * <p>
 * Reads properties from files, VM arguments or environment variables
 * 
 * @author Asendar
 *
 */
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public final class PropertiesUtils {

	/**
	 * @param file to read the properties from
	 * @return list of {@link Properties}
	 * @throws Exception if the file is not accessible or does not exist
	 */
	public static Properties getPropertiesFromFile(String file) throws Exception {

		Properties prop = new Properties();

		InputStream inputStream = new FileInputStream(file);

		prop.load(inputStream);

		inputStream.close();

		return prop;
	}


	/**
	 * 
	 * <p>
	 * Reads the key value from the VM arguments or environment variables
	 * 
	 * @param key to read the value of
	 * @return the value of that key
	 */
	public static String getProperty(String key) {
		String envVariable = System.getenv(key);

		if (envVariable != null)
			return envVariable;

		return System.getProperty(key);

	}
}
