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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.config.Config;
import org.hu.hom.api.config.Constants;
import org.hu.hom.api.listener.MessageListener;
import org.hu.hom.core.object.AbstractMutant;
import org.hu.hom.core.object.Population;
import org.hu.hom.core.utils.CmdUtils;
import org.hu.hom.core.utils.FileUtils;
import org.hu.hom.core.utils.FileUtils.FileType;
import org.junit.runners.JUnit4;

import com.google.common.collect.Lists;

/**
 * 
 * <p>
 * Runs {@link JUnit4} test cases against a given set of {@link AbstractMutant}s.
 * 
 * <p>
 * The execution is done by saving the compiled code of {@link AbstractMutant} 
 * to {@link Constants#TMP} and then executing a OS dependent command to execute
 * provided test suite with {@link Config}.
 * 
 * <p>
 * As Windows, Linux and MacOs commands vary, this class should be extended to implement
 * the intended OS command by overriding {@link AbstractTestRunner#getCommand(String)}
 * 
 * <p>
 * After executing test suites the result is matched using {@link Pattern} to recognize
 * exceptions thrown by {@link JUnit4}. The header of the exceptions thrown are stored 
 * in the {@link AbstractMutant} using {@link AbstractMutant#addKilledBy(String)}.
 * 
 * <p>
 * This class is not intended to be accessed directly, to run test cases use {@link TestExecutor}.
 * 
 * @author Asendar
 * 
 * @see TestExecutor
 * @see LinuxTestRunner
 * @see WindowsTestRunner
 */
public abstract class AbstractTestRunner {
	

	/**
	 * @param config of the {@link GeneticAlgorithm}
	 * @return list of test cases located at {@link Config#getTestCasesPath()}
	 */
	private List<String> getTestSuites(Config config) {
		Collection<File> testSuites = FileUtils.listFiles(new File(config.getTestCasesPath()), new String[] { "class" },
				true);

		List<String> testSet = Lists.newArrayList();
		testSuites.forEach(file -> testSet.add(file.getName().replaceAll(".class", "")));

		return testSet;

	}
	
	/**
	 * @param population to be executed against test suites got from {@link AbstractTestRunner#getTestSuites(Config)}
	 * @param config of the {@link GeneticAlgorithm}
	 * @param messageListeners for logging
	 * 
	 * @param <T> The type of {@link Population}.
	 */
	public final <T extends AbstractMutant> void execute(Population<T> population, Config config, Set<MessageListener> messageListeners) {
		execute(population.getMutants(), config, messageListeners);
	}
	
	/**
	 * @param mutants to be executed against test suites got from {@link AbstractTestRunner#getTestSuites(Config)}
	 * @param config of the {@link GeneticAlgorithm}
	 * @param messageListeners for logging
	 * @param <T> The type of {@link Population}.
	 */
	public synchronized final <T extends AbstractMutant> void execute(List<T> mutants, Config config, Set<MessageListener> messageListeners) {
				
		messageListeners.forEach(listener -> listener.info("Executing Tests"));
		
		FileUtils.mkdirs(Config.JUNIT);
		FileUtils.copyResourceToFile("/jar-files/hamcrest.jar", Config.JUNIT + "/hamcrest.jar");
		FileUtils.copyResourceToFile("/jar-files/junit.jar", Config.JUNIT + "/junit.jar");
		
				
		for(AbstractMutant mutant : mutants) {
			try {
				excute(mutant, config);
				messageListeners.forEach(listener -> listener.info(String.format("Mutant [%s] is killed by [%s] test cases", mutant.getId(), mutant.getKilledBy().size())));
			} catch (Exception e) {
				mutant.setCompilable(false);
				messageListeners.forEach(listener -> listener.error(String.format("Error while excuting mutant [%s]", mutant.getId())));
			}
		}
	}

	/**
	 * Exception header regex
	 */
	private final String exception_regex = "[a-zA-Z]+[.][a-zA-Z]+[:]";

	private final Pattern pattern = Pattern.compile(exception_regex);

	/**
	 * @param mutant to be executed
	 * @return set of failed test cases
	 * @throws Exception if the execution is failed for any reason
	 */
	private List<String> excute(AbstractMutant mutant, Config config) throws Exception {

		org.hu.hom.core.utils.Compiler.compile(FileType.noExtension(config.getOriginalFile()), mutant.getCode(), Config.TMP);
		
		FileUtils.copyDirectory(config.getTestCasesPath(),
				Config.TMP);

		StringBuilder testSuites = new StringBuilder();

		getTestSuites(config).forEach(testSuite -> testSuites.append(testSuite + " "));

		String command = getCommand(testSuites.toString());
		
		List<String> errors = Lists.newArrayList();

		String theString = CmdUtils.excute(command, 5, TimeUnit.SECONDS);

		List<String> lines = new ArrayList<>(Arrays.asList(theString.split("\n")));

		lines.forEach(line -> {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find() && !line.startsWith("\t"))
				errors.add(line.substring(0, line.length() - 1));
		});

		mutant.setKilledBy(errors);

		return errors;
	}
	
	/**
	 * @param testSuites provided by {@link Config}
	 * @return {@link JUnit4} test execution command
	 */
	public abstract String getCommand(String testSuites);
}
