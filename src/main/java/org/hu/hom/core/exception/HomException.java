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
package org.hu.hom.core.exception;


/**
 * 
 * <p>
 * This is exception is typically thrown in case of a fatal error
 * whenever this is thrown it means either the app can not launch 
 * or can not continue running
 * 
 * @author Asendar
 *
 */
public class HomException extends RuntimeException {

	private static final long serialVersionUID = -5781734650566659255L;

	/**
	 * @param message string formatted
	 * @param arg of the formatted string
	 * 
	 * @see String#format(String, Object...)
	 */
	private HomException(String message, Object... arg) {
		super(String.format(message, arg));
	}

	/**
	 * @param message string formatted
	 * @param arg of the formatted string
	 * 
	 * @see String#format(String, Object...)
	 */
	public static final void throwException(String message, Object... arg) {
		throw new HomException(message, arg);
	}
	
	/**
	 * @param throwable to be re thrown 
	 */
	public static final void throwException(Throwable throwable) {
		throw new HomException(throwable.getMessage());
	}

}
