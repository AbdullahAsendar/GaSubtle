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

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.algorithm.object.SelectionStrategy;
import org.hu.hom.api.algorithm.object.impl.CrossoverDefaultImpl;
import org.hu.hom.api.algorithm.object.impl.EvaluationDefaultImpl;
import org.hu.hom.api.algorithm.object.impl.MutationDefaultImpl;
import org.hu.hom.api.algorithm.object.impl.selection.Selection;
import org.hu.hom.api.config.Config;
import org.hu.hom.api.listener.MessageListener;
import org.hu.hom.core.object.HigherOrderMutant;

import com.google.common.collect.Lists;

/**
 * 
 * @author Asendar
 *
 */
public class CmdLauncher {

	/**
	 * 
	 */
	public static final Log LOG = LogFactory.getLog(CmdLauncher.class);

	public static void main(String args[]) {

		// print the application name
		sign();
		
		// handle cmd commands
		Config config = CommandHandler.handle(args);
		
		// print current configuration
		print(config);
		
		// launch the GA
		if (CommandHandler.getSelectionStrategy() != null) {
			startGeneticAlgorithm(config, CommandHandler.getSelectionStrategy());
		} else {

			for (Selection selection : Selection.values()) {
				startGeneticAlgorithm(config, selection.getSelectionStrategy());

			}

		}

	}

	/**
	 * <p>
	 * Starts the {@link GeneticAlgorithm}
	 */
	private static void startGeneticAlgorithm(Config config, SelectionStrategy<HigherOrderMutant> selection) {
		
		GeneticAlgorithm
		.builder()
		.config(config)
		.evaluation(new EvaluationDefaultImpl())
		.evaluation(new EvaluationDefaultImpl())
		.crossover(new CrossoverDefaultImpl())
		.mutation(new MutationDefaultImpl())
		.selection(selection)
		.messageListener(new MessageListener() {
			
			@Override
			public void info(String value) {
				LOG.info(value);
			}
			
			@Override
			public void error(String value) {
				LOG.error(value);
			}
			
			@Override
			public void debug(String value) {
				LOG.debug(value);
			}
		})
		.geneticAlgorithmListener((int generation, int populationSize, int liveMutants, int subtleMutants)
				-> LOG.info(String.format("Generation [%s] Population [%s] Live Mutants [%s] Subtle Mutants [%s]",
						generation, populationSize, liveMutants, subtleMutants)))
				.build()
		.run();
	}

	/**
	 * <p>
	 * prints the application name on the console
	 * 
	 */
	private static final void sign() {
		try {
			LOG.info("\n\n" + IOUtils.toString(CmdLauncher.class.getResource("/signature"), Charset.defaultCharset()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void print(Config config) {
		
		LOG.info("Original file      : " + config.getOriginalFile());
		LOG.info("Selection Strategy : " + ((CommandHandler.getSelectionStrategy() == null) ? Lists.newArrayList(Selection.values()) : CommandHandler.getSelectionStrategy()));
		LOG.info("Output Path        : " + config.getResultPath());
		
	}
}