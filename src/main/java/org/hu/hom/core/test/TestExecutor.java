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

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.SystemUtils;
import org.hu.hom.api.listener.MessageListener;
import org.hu.hom.core.object.AbstractMutant;
import org.hu.hom.core.object.Population;

/**
 * 
 * <p>
 * This class is a man in the middle to execute test cases based on the OS
 * 
 * <p>
 * This class constructors are private and can be accessed using {@link TestExecutor#instance} 
 * 
 * @author Asendar
 * 
 * @see AbstractTestRunner
 * @see WindowsTestRunner
 * @see LinuxTestRunner
 *
 */
public class TestExecutor {

	/**
	 * Singleton instance [eager]
	 */
	public static final TestExecutor instance = new TestExecutor();

	/**
	 * Test runner that depends on the OS
	 */
	private final AbstractTestRunner testRunner;

	/**
	 * <p>
	 * Instantiates an object of {@link TestExecutor#testRunner}.
	 * 
	 * <p>
	 * It will use {@link WindowsTestRunner} if the OS is Windows and {@link LinuxTestRunner} otherwise.
	 * 
	 */
	private TestExecutor() {
		if (SystemUtils.IS_OS_WINDOWS)
			testRunner = new WindowsTestRunner();
		else
			testRunner = new LinuxTestRunner();
	}

	/**
	 * @param population to be executed
	 * @param originalFile file underExecution
	 * @param testCasesPath path to test cases
	 * @param messageListeners for logging
	 * @param <T> The type of {@link Population}.
	 */
	public <T extends AbstractMutant> void execute(Population<T> population, String originalFile, String testCasesPath, Set<MessageListener> messageListeners) {
		testRunner.execute(population, originalFile, testCasesPath, messageListeners);
	}

	/**
	 * @param mutants to be executed
	 * @param originalFile file underExecution
	 * @param testCasesPath path to test cases
	 * @param messageListeners for logging
	 * @param <T> The type of mutants.
	 */
	public <T extends AbstractMutant> void execute(List<T> mutants, String originalFile, String testCasesPath, Set<MessageListener> messageListeners) {
		testRunner.execute(mutants, originalFile, testCasesPath, messageListeners);
	}

}
