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

import java.util.Collection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 
 * 
 * @author Asendar
 *
 */

public class Log {
	private static final int MAX_LOG_ENTRIES = 1_000_000;

	private final BlockingDeque<LogRecord> log = new LinkedBlockingDeque<>(MAX_LOG_ENTRIES);

	public void drainTo(Collection<? super LogRecord> collection) {
		log.drainTo(collection);
	}

	public void offer(LogRecord record) {
		log.offer(record);
	}
}