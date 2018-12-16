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
package org.hu.hom.ui.graphical.model.log;

import org.hu.hom.ui.graphical.model.log.LogRecord.Level;


/**
 * 
 * 
 * @author Asendar
 *
 */

public class Logger {
	private final Log log;
	private final String context;

	public Logger(Log log, String context) {
		this.log = log;
		this.context = context;
	}

	public void log(LogRecord record) {
		log.offer(record);
	}

	public void debug(String msg) {
		log(new LogRecord(Level.DEBUG, context, msg));
	}

	public void info(String msg) {
		log(new LogRecord(Level.INFO, context, msg));
	}

	public void warn(String msg) {
		log(new LogRecord(Level.WARN, context, msg));
	}

	public void error(String msg) {
		log(new LogRecord(Level.ERROR, context, msg));
	}

	public Log getLog() {
		return log;
	}
}