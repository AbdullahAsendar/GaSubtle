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
package org.hu.hom.api.algorithm;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.hu.hom.api.algorithm.object.Crossover;
import org.hu.hom.api.algorithm.object.Evaluation;
import org.hu.hom.api.algorithm.object.Mutation;
import org.hu.hom.api.algorithm.object.SelectionStrategy;
import org.hu.hom.api.algorithm.object.impl.CrossoverDefaultImpl;
import org.hu.hom.api.algorithm.object.impl.EvaluationDefaultImpl;
import org.hu.hom.api.algorithm.object.impl.MutationDefaultImpl;
import org.hu.hom.api.algorithm.object.impl.selection.Selection;
import org.hu.hom.api.algorithm.report.ReportEngine;
import org.hu.hom.api.config.Constants;
import org.hu.hom.api.listener.GeneticAlgorithmListener;
import org.hu.hom.api.listener.MessageListener;
import org.hu.hom.core.exception.HomException;
import org.hu.hom.core.mutation.MutationModel;
import org.hu.hom.core.object.AbstractMutant;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.object.Population;
import org.hu.hom.core.test.TestExecutor;
import org.hu.hom.core.utils.FileUtils;
import org.hu.hom.core.utils.IDUtils;
import org.hu.hom.core.utils.Validator;
import org.hu.hom.core.utils.FileUtils.FileType;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.Builder;
import lombok.Setter;
import lombok.Singular;


/**
 * 
 * <p>
 * A Genetic Algorithm that searches for subtle mutants using crossover and mutation.
 * 
 * <p>
 * The algorithm generates a random {@link Population} to begin with, it then enters the 
 * generated population to a loop that crossovers and mutates the population, which generates
 * a new population. The process is then repeated until reaching a terminating condition.
 * 
 * <p>
 * At each generation the subtle and live mutants found by the algorithm are stored in a separate list.
 * 
 * 
 * 
 * @author Asendar
 *
 * @see CrossoverDefaultImpl
 * @see MutationDefaultImpl
 * @see EvaluationDefaultImpl
 * @see Selection
 */
@Builder
public class GeneticAlgorithm  {


	private final Set<HigherOrderMutant> liveMutants = Sets.newHashSet();
	private final Set<HigherOrderMutant> subtleMutants = Sets.newHashSet();
	private final Population<HigherOrderMutant> population = Population.newPopulation(HigherOrderMutant.class);
	private final Stopwatch stopwatch = Stopwatch.createUnstarted();
	private final ReportEngine<HigherOrderMutant> reportEngine = ReportEngine.build();;

	private File outputDir;

	private Evaluation<HigherOrderMutant> evaluation;
	private Crossover<HigherOrderMutant> crossover;
	private Mutation<HigherOrderMutant> mutation;
	private SelectionStrategy<HigherOrderMutant> selection;

	private @Singular Set<GeneticAlgorithmListener> geneticAlgorithmListeners;
	private @Singular Set<MessageListener> messageListeners;
	
	/**
	 * The percentage of mutants that will be picked to do operations on
	 */
	private @Builder.Default Integer mutationPercentage = 10;
	/**
	 * The order of the {@link HigherOrderMutant} to not be exceeded
	 */
	private @Builder.Default Integer maxOrder = 2;
	/**
	 * The number of times the algorithm will run
	 */
	private @Builder.Default Integer runRepeat = 1;
	/**
	 * The maximum number of subtle {@link HigherOrderMutant}s to generate
	 */
	private Integer requiredSubtleHoms;
	/**
	 * The maximum number of {@link HigherOrderMutant}s to generate
	 */
	private Integer maxHoms;
	/**
	 * The max generation to reach
	 */
	private Integer maxGeneration;
	/**
	 * Timeout in seconds
	 */
	private Long timeout;
	/**
	 * Path to original <i>.java</i> file under execution
	 */
	private @Setter String originalFile;
	/**
	 * Path to test suites <i>.class</i> files
	 */
	private String testCasesPath;
	/**
	 * Path to store the results to
	 */
	private String resultPath;
	/**
	 * 
	 */
	private @Builder.Default String mutantsPath = Constants.DEFAULT_MUTANTS_PATH;

	
	private void init() {
		
		validate();
		
		messageListeners.forEach(listener -> listener.debug("Configuring"));
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> FileUtils.forceDelete(Constants.WORKBENCH)));
		
		try {
			originalFile = MutationModel.init(originalFile, mutantsPath, testCasesPath, messageListeners);
		} catch (Exception e) {
			HomException.throwException(e);
		}
	}

	/**
	 * <p>
	 * Launches the genetic algorithm 
	 * 
	 * <p>
	 * The algorithm does evaluation, crossover, mutation and selection
	 * 
	 *  
	 */
	public void run()  {
		
		init();
		
		IntStream.rangeClosed(1, runRepeat).forEach(this::run);

		messageListeners.forEach(listener -> listener.info("==========="));
		messageListeners.forEach(listener -> listener.info("Done"));
		messageListeners.forEach(listener -> listener.info("==========="));
		
	}
	
	private void run(int runCount) {

		outputDir = new File(String.format("%s%s%s%s%s", resultPath, File.separator,
				selection.getClass().getSimpleName(), File.separator, "run_" + IDUtils.newID()));

		outputDir.mkdirs();
						
		messageListeners.forEach(listener -> listener.info("Starting"));
		
		stopwatch.reset();
		stopwatch.start();
				
		messageListeners.forEach(listener -> listener.info("Creating initial population"));

		population.addMutants(MutationModel.generateRandomPopulation(originalFile, testCasesPath, messageListeners).getMutants());

		int generatedHOMs = 0;

		evaluation.evaluate(population.getMutants());
		
		reportEngine.record(0, population, Sets.newHashSet(population.getMutants()), liveMutants, subtleMutants);

		int generationNumber = 1;

		generatedHOMs += population.getMutants().size();
		
		geneticAlgorithmListeners.forEach(listener -> listener.stateChanged(1, population.getMutants().size(), liveMutants.size(), subtleMutants.size()));

		while (!stopConditionMet(stopwatch, subtleMutants.size(), generatedHOMs, generationNumber)) {
			try {
				
				final int currentGenerationNumber = generationNumber;
								
				List<HigherOrderMutant> selectedMutants = selection.select(population, getSelectionSize(population.getMutants().size()));
								
				Set<HigherOrderMutant> offspring = Sets.newHashSet();
				offspring.addAll(mutation.mutate(crossover.excute(selectedMutants)));
				
				messageListeners.forEach(listener -> listener.debug(String.format("new [%s] mutatns generated", offspring.size())));

				TestExecutor.instance.execute(Lists.newArrayList(offspring), originalFile, testCasesPath, messageListeners);

				population.addMutants(Lists.newArrayList(offspring));
				
				population.setMutants(population.getMutants().subList(0, Math.min(population.getMutants().size(), Specifications.getPopulationSize(originalFile))));
				
				evaluation.evaluate(population.getMutants());

				liveMutants.addAll(population.getLiveMutants());
				subtleMutants.addAll(population.getSubtleMutants());
				
				generatedHOMs += population.getMutants().size();

				reportEngine.record(generationNumber, population, offspring, liveMutants, subtleMutants);
				
				population.removeNonCompilableMutants();
				
				geneticAlgorithmListeners.forEach(listener -> listener.stateChanged(currentGenerationNumber, population.getMutants().size(), liveMutants.size(), subtleMutants.size()));

				generationNumber++;
								
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		stopwatch.stop();
		
		reportEngine.export(outputDir, maxOrder, stopwatch);
		
		report(liveMutants, "live_mutants");
		report(subtleMutants, "subtle_mutants");
	}


	/**
	 * @param stopwatch of the current run
	 * @param subtle_homs generated so far
	 * @param homs generated so far
	 * @param generation reached
	 * @return true if the stop condition is met and false otherwise
	 */
	private boolean stopConditionMet(Stopwatch stopwatch, int subtle_homs, int homs, int generation) {

		if (requiredSubtleHoms != null && (subtle_homs >= requiredSubtleHoms)) 
			return true;
		

		if (maxHoms != null && (homs >= maxHoms)) 
			return true;
		

		if (timeout != null && ((int) stopwatch.elapsed().getSeconds() >= timeout)) 
			return true;
		
		
		if (maxGeneration != null && (generation >= maxGeneration))
			return true;

		return false;
	}
	
	
	/**
	 * @param mutatns to be stored
	 * @param title of the directory to store the mutants to
	 */
	private void report(Set<HigherOrderMutant> mutatns, String title) {
		

		
		File output = new File(String.format("%s%s%s", outputDir.getPath(), File.separator, title));
		output.mkdirs();

		int number = 1;

		for (AbstractMutant mutant : mutatns) {
			File file = new File(String.format("%s%s%s%s", output.getPath(), File.separator, "mutant_" + number++,
					FileType.JAVA.getExtension()));
			try {
				FileUtils.writeStringToFile(file, mutant.getCode(), Charset.defaultCharset());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param populationSize of the current generation
	 * @return number of mutants that shall be selected
	 */
	public int getSelectionSize(int populationSize) {
		int selectionSize = (int) ((mutationPercentage / 100.0) * populationSize);
		if (selectionSize <= 0)
			selectionSize = 1;

		return selectionSize;
	}
	
	/**
	 * <p>
	 * Makes sure that all options are provided;
	 * 
	 * <p>
	 * Throws {@link HomException} if some of the options are null;
	 * 
	 * @see Validator#assertNotNull(Object, String)
	 */
	private void validate() {

		Validator.assertNotNull(selection, "selection");
		Validator.assertNotNull(evaluation, "evaluation");
		Validator.assertNotNull(crossover, "crossover");
		Validator.assertNotNull(mutation, "mutation");
		Validator.assertNotNull(mutationPercentage, "mutationPercentage");
		Validator.assertNotNull(maxOrder, "maxOrder");
		Validator.assertNotNull(runRepeat, "runRepeat");
		Validator.assertNotNull(originalFile, "originalFile");
		Validator.assertNotNull(testCasesPath, "testCasesPath");
		Validator.assertNotNull(resultPath, "resultPath");

		if (requiredSubtleHoms == null && maxHoms == null && maxGeneration == null && timeout == null)
			HomException.throwException("No termination condition provided");

	}

}