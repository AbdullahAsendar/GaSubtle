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

import java.util.Date;


/**
 * 
 * 
 * @author Asendar
 *
 */

public class LogRecord {
	private Date timestamp;
	private Level level;
	private String context;
	private String message;

	public LogRecord(Level level, String context, String message) {
		this.timestamp = new Date();
		this.level = level;
		this.context = context;
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public Level getLevel() {
		return level;
	}

	public String getContext() {
		return context;
	}

	public String getMessage() {
		return message;
	}
	
	
	public static enum Level {
		DEBUG, INFO, WARN, ERROR
	}
}