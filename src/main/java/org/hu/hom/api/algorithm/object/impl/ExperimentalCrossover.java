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
package org.hu.hom.api.algorithm.object.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.algorithm.object.AbstractCrossover;
import org.hu.hom.core.object.FirstOrderMutant;
import org.hu.hom.core.object.HigherOrderMutant;

import com.google.common.collect.Lists;

/**
 * <p>
 * An enhanced version of crossover operation done by the {@link GeneticAlgorithm}.
 * 
 * <p>
 * The idea was presented by Dr. Fadi Wedyan
 * 
 * <p>
 * Takes two {@link HigherOrderMutant}s and mixes their {@link FirstOrderMutant}s 
 * based on the fitness values of these mutants.
 * 
 * <p>
 * The order of the resulted children is always the same of the parent.
 * 
 * <p>
 * It basically creates the first child using the most fit FOMs and the second
 * child using the least fit FOMs.
 * 
 * <p>
 * If the parents does not have the same order <i>(ex. crossover between a third order 
 * and second order mutants)</i>, the smaller order will always get the most fit children.
 * 
 * <p>
 * Example: <i>the value inside [] is the fitness of the mutant</i>
 * <br> <br>
 * HOM_1 : FOM_1_1[0.5] , FOM_1_2[0.75] , FOM_1_3[0.3]
 * <br>
 * HOM_2 : FOM_2_1[0.4] , FOM_2_2[0.2]
 * <br>
 * <br>
 * result : 
 * <br><br>
 * HOM_1 : FOM_1_2[0.75] , FOM_1_1[0.5]
 * <br>
 * HOM_2 : FOM_2_1[0.4] , FOM_1_3[0.3] , FOM_2_2[0.2]
 * 
 * 
 * 
 * @author Asendar
 *
 */
public class ExperimentalCrossover implements AbstractCrossover<HigherOrderMutant> {

	@Override
	public List<HigherOrderMutant> crossover(HigherOrderMutant firstMutant, HigherOrderMutant secondMutant) {
		int moreFitSize = Math.min(firstMutant.getFirstOrderMutants().size(), secondMutant.getFirstOrderMutants().size());

		List<FirstOrderMutant> allMutants = Lists.newArrayList(firstMutant.getFirstOrderMutants());
		allMutants.addAll(secondMutant.getFirstOrderMutants());

		List<FirstOrderMutant> moreFit = Lists.newArrayList(allMutants.stream().sorted().collect(Collectors.toList()).subList(0, moreFitSize));
		List<FirstOrderMutant> leastFit = Lists.newArrayList(allMutants.stream().sorted().collect(Collectors.toList()).subList(moreFitSize, allMutants.size()));

		HigherOrderMutant newfirstMutant = firstMutant.copy();
		HigherOrderMutant newsecondMutant = secondMutant.copy();

		newfirstMutant.setFirstOrderMutants(moreFit);
		newsecondMutant.setFirstOrderMutants(leastFit);

		return Lists.newArrayList(newfirstMutant, newsecondMutant);
	}

}
