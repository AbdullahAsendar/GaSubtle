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
package org.hu.hom.ui.cmd;

import java.io.File;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;
import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.algorithm.object.SelectionStrategy;
import org.hu.hom.api.algorithm.object.impl.selection.Selection;
import org.hu.hom.api.config.Config;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.utils.PropertiesUtils;

import lombok.Getter;

/**
 * <p>
 * This class handles the arguments passed using cmd
 * 
 * 
 * @author asendar
 * 
 * @see ArgCommand
 * @see CommandLine
 *
 */
public class CommandHandler {
	
	private static @Getter SelectionStrategy<HigherOrderMutant> selectionStrategy;

	/**
	 * @param args of the cmd
	 * @return config of the {@link GeneticAlgorithm}
	 */
	public static Config handle(String[] args) {

		Options options = new Options();

		ArgCommand.options().forEach(options::addOption);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		formatter.setWidth(200);
		formatter.setLeftPadding(2);
		formatter.setDescPadding(2);

		try {
			cmd = parser.parse(options, args);
		} catch (Exception e) {
			formatter.printHelp("GaSubtle", options);

			System.exit(1);
		}

		String strBenchmark = cmd.getOptionValue(ArgCommand.BENCHMARK.getLongOpt());
		String strHome = cmd.getOptionValue(ArgCommand.HOME.getLongOpt());
		String strSelection = cmd.getOptionValue(ArgCommand.SELECTION_STRATEGY.getLongOpt());

		String home = StringUtils.isNotBlank(strHome) ? strHome : Constants.DEFAULT_HOME;
		String benchmarkPath = home + File.separator + strBenchmark + File.separator;

		Config config = 
				Config
				.builder()
				.mutantsPath(benchmarkPath + Constants.MUTANTS_PATH)
				.originalFile(benchmarkPath + Constants.ORIGINAL_FILES_PATH + File.separator + strBenchmark + ".java")
				.resultPath(benchmarkPath + Constants.OUTPUT_PATH)
				.testCasesPath(benchmarkPath + Constants.TEST_CASES_PATH)
				.build();

		try {
			selectionStrategy = Selection.valueOf(strSelection).getSelectionStrategy();
		} catch (Exception e) {

		}


		Properties properties = null;

		try {
			properties = PropertiesUtils.getPropertiesFromFile(home + File.separator + Constants.APP_CONFIG_FILE);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		if (properties.get("ga.mutation.percentage") != null)
			config.setMutationPercentage(Integer.valueOf(properties.get("ga.mutation.percentage").toString()));

		if (properties.get("max.order") != null)
			config.setMaxOrder(Integer.valueOf(properties.get("max.order").toString()));

		if (properties.get("run.repeat") != null)
			config.setRunRepeat(Integer.valueOf(properties.get("run.repeat").toString()));

		if (properties.get("required.subtle.homs") != null)
			config.setRequiredSubtleHoms(Integer.valueOf(properties.get("required.subtle.homs").toString()));

		if (properties.get("time.out.seconds") != null)
			config.setTimeout(Long.valueOf(properties.get("time.out.seconds").toString()));

		if (properties.get("max.homs") != null)
			config.setMaxHoms(Integer.valueOf(properties.get("max.homs").toString()));

		if (properties.get("max.generation") != null)
			config.setMaxGeneration(Integer.valueOf(properties.get("max.generation").toString()));

		return config;

	}

}
