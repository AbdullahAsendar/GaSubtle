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
package org.hu.hom.core.mutation;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.algorithm.Specifications;
import org.hu.hom.api.listener.MessageListener;
import org.hu.hom.core.config.Config;
import org.hu.hom.core.exception.HomException;
import org.hu.hom.core.object.FirstOrderMutant;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.object.Population;
import org.hu.hom.core.test.TestExecutor;
import org.hu.hom.core.utils.FileUtils;

import com.google.common.collect.Lists;

/**
 * 
 * <p>
 * This class reads, combines and generates {@link FirstOrderMutant}s and
 * {@link HigherOrderMutant}s
 * 
 * 
 * 
 * @author Asendar
 * 
 */
public class MutationModel {

	/**
	 * 
	 */
//	private static final Log LOG = LogFactory.getLog(MutationModel.class);

	private static final Random random = new Random();

	/**
	 * The original code of the mutatns
	 */
	private static String originalCode;

	/**
	 * <p>
	 * Provided {@link FirstOrderMutant}s map
	 * 
	 * <p>
	 * The String key of the map is {@link FirstOrderMutant#getId()}
	 */
	private static Map<String, FirstOrderMutant> first_order_mutants;

	/**
	 * <p>
	 * Initializes the model by filling {@link MutationModel#first_order_mutants}
	 * and setting {@link MutationModel#originalCode}
	 * 
	 * <p>
	 * After reading the {@link MutationModel#first_order_mutants} this class
	 * executes these mutants using {@link TestExecutor#execute(List, Config, Set)}
	 *
	 * @param config of the {@link GeneticAlgorithm}
	 * @param messageListeners for logging
	 * @throws Exception if anything goes down
	 */
	public static void init(Config config, Set<MessageListener> messageListeners) throws Exception {

		messageListeners.forEach(listner -> listner.info("Generating FOMs"));
		
		config.setOriginalFile(
				MutantsGenerator
				.builder()
				.originalFile(config.getOriginalFile())
				.outputPath(config.getMutantsPath())
				.muJavaHome(Config.DEFAULT_MU_JAVA_HOME)
				.build()
				.generate());

		
		first_order_mutants = new HashMap<>();

		originalCode = FileUtils.readJavaFile(config.getOriginalFile());
		
		messageListeners.forEach(listner -> listner.debug("Reading generated FOMs"));

		Collection<File> files = FileUtils.listFiles(new File(config.getMutantsPath()), new String[] { "java" }, true);

		messageListeners.forEach(listner -> listner.info(String.format("Generated %s FOMs", files.size())));

		List<FirstOrderMutant> mutants = Lists.newArrayList();

		messageListeners.forEach(listner -> listner.debug("Converting files to mutants"));

		files.forEach(file -> mutants.add(FirstOrderMutant.build(file.getPath())));

		mutants.stream().filter(MutationModel::validate)
				.forEach(mutant -> first_order_mutants.put(mutant.getId(), mutant));

		messageListeners.forEach(listner -> listner.debug("Excuting generated mutants"));

		TestExecutor.instance.execute(Lists.newArrayList(first_order_mutants.values()), config, messageListeners);
		
		messageListeners.forEach(l -> l.error("live mutants:"));
		first_order_mutants.entrySet().stream().filter(a -> a.getValue().isLive())
				.forEach(a -> messageListeners.forEach(l -> l.error(a.getValue().getMutantPath())));

		first_order_mutants = first_order_mutants.entrySet().stream().filter(a -> a.getValue().isCompilable())
				.filter(a -> !a.getValue().isLive()).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		
		messageListeners.forEach(listner -> listner.info(String.format("[%s] mutants left after removing non compile-able and live mutants",
				first_order_mutants.size())));


		if (first_order_mutants.isEmpty()) {
			messageListeners.forEach(listner -> listner.error("Can not run the Genetic Algorithm, reason: 0 mutants left after removing non compile-able and live mutants"));

			HomException.throwException(
					"Can not run the Genetic Algorithm, reason: 0 mutants left after removing non compile-able and live mutants");
		}

	}


	/**
	 * @param config of the {@link GeneticAlgorithm}
	 * @param messageListeners to log 
	 * @return a random population
	 * 
	 * 
	 * @see Specifications
	 */
	public static Population<HigherOrderMutant> generateRandomPopulation(Config config, Set<MessageListener> messageListeners) {

		Population<HigherOrderMutant> population = Population.newPopulation(HigherOrderMutant.class);
		
		int populationSize = Specifications.getPopulationSize(config);

		IntStream.rangeClosed(1, Specifications.getFirstOrderSize(populationSize)).forEach(i -> {
			HigherOrderMutant mutant = HigherOrderMutant.build(config.getOriginalFile());
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			population.addMutant(mutant);
		});
		
		IntStream.rangeClosed(1, Specifications.getSecondOrderSize(populationSize)).forEach(i -> {
			HigherOrderMutant mutant = HigherOrderMutant.build(config.getOriginalFile());
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			population.addMutant(mutant);
		});
		
		IntStream.rangeClosed(1, Specifications.getThirdOrderSize(populationSize)).forEach(i -> {
			HigherOrderMutant mutant = HigherOrderMutant.build(config.getOriginalFile());
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			population.addMutant(mutant);
		});
		
		IntStream.rangeClosed(1, Specifications.getFourthOrderSize(populationSize)).forEach(i -> {
			HigherOrderMutant mutant = HigherOrderMutant.build(config.getOriginalFile());
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			mutant.addFirstOrderMutant(getRandomMutant(mutant));
			population.addMutant(mutant);
		});


		TestExecutor.instance.execute(population, config, messageListeners);

		return population;
	}

	/**
	 * 
	 * <p>
	 * Returns a random {@link FirstOrderMutant} that can be applied to the provided
	 * {@link HigherOrderMutant}
	 * 
	 * <p>
	 * The returned {@link FirstOrderMutant} should be valid to be added to the
	 * {@link HigherOrderMutant}
	 * 
	 * <p>
	 * The validation is done by the following criterion: <br>
	 * The {@link FirstOrderMutant}s of a {@link HigherOrderMutant} should have
	 * unique mutated lines.
	 * 
	 * <p>
	 * example: <br>
	 * <br>
	 * 
	 * original code: <br>
	 * int z = x + y; <br>
	 * return z; <br>
	 * <br>
	 * 
	 * Mutant #1 <br>
	 * int z = x - y; <br>
	 * return z; <br>
	 * <br>
	 * 
	 * Mutant #2 <br>
	 * int z = x * y; <br>
	 * return z; <br>
	 * <br>
	 * 
	 * Mutant #3 <br>
	 * int z = x + y; <br>
	 * return --z; <br>
	 * <br>
	 * 
	 * 
	 * <p>
	 * Mutants [1 and 2] can not be added to the same {@link HigherOrderMutant}
	 * because they have same mutated line index
	 * 
	 * <p>
	 * Mutants [1 and 3] or [2 and 3] can be added to the same
	 * {@link HigherOrderMutant}
	 * 
	 * 
	 * @param mutant
	 *            for validation
	 * @return a random {@link FirstOrderMutant}
	 */
	public static final FirstOrderMutant getRandomMutant(HigherOrderMutant mutant) {

		// get all the first order mutants
		List<FirstOrderMutant> mutants = Lists.newArrayList(first_order_mutants.values());

		// mutated line indices will be stored here
		List<Integer> mutatedLines = Lists.newArrayList();

		// filling the indices list
		mutant.getFirstOrderMutants().forEach(foMutant -> mutatedLines.add(getMutatedLine(foMutant)));

		// get filtered list of first order mutants
		List<FirstOrderMutant> filteredMutants = mutants.stream()
				.filter(foMutant -> !mutatedLines.contains(getMutatedLine(foMutant))).collect(Collectors.toList());

		FirstOrderMutant randomMutant = null;
		
		// return random mutant
		try {
			randomMutant = filteredMutants.get(random.nextInt(filteredMutants.size()));
		} catch (Exception e) {
			// program is too small?
		}
		return randomMutant;
	}

	/**
	 * <p>
	 * Checks if a {@link FirstOrderMutant} is valid or not
	 * 
	 * <p>
	 * A {@link FirstOrderMutant} is valid iff its lines count is the same as the
	 * original code lines count
	 * 
	 * @param mutant
	 *            to be validated
	 * @return true if the mutant is valid and false otherwise
	 */
	private static boolean validate(FirstOrderMutant mutant) {
		try {
			getMutatedLine(mutant);
		} catch (Exception e) {
			return false;
		}
		return mutant.getCode().split("\n").length == originalCode.split("\n").length;
	}

	private static int getMutatedLine(FirstOrderMutant mutant) {
		List<String> originalLines = Lists.newArrayList(originalCode.split("\n"));
		List<String> mutantLines = Lists.newArrayList(mutant.getCode().split("\n"));

		for (int i = 0; i < originalLines.size(); i++) {
			if (!originalLines.get(i).equals(mutantLines.get(i)))
				return i;
		}
		
		HomException.throwException("not a mutant");
		
		return 0;
	}
}
