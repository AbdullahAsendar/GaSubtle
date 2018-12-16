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

import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

/**
 * 
 * <p>
 * Windows Command Line or Linux Terminal utilities
 * 
 * @author Asendar
 *
 */
public class CmdUtils {

	private static final ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * @param command to be executed
	 * @param timeout of the command
	 * @param timeUnit of the timeout
	 * @return the string output of the execution
	 * @throws Exception in case anything went down
	 */
	public static String excute(String command, int timeout, TimeUnit timeUnit) throws Exception {

		Process p = Runtime.getRuntime().exec(command);

		executor.execute(() -> {
			try {
				if (!p.waitFor(timeout, timeUnit)) {
					// timeout - kill the process.
					p.destroyForcibly();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		String theString = IOUtils.toString(p.getInputStream(), Charset.defaultCharset());

		return theString;
	}

}
