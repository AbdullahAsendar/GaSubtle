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
package org.hu.hom.api.algorithm.object.impl.selection;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.hu.hom.api.algorithm.object.AbstractSelectionStrategy;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.object.Population;

import com.google.common.collect.Lists;

/**
 * 
 * <p>
 * Implements selection of <i>n</i> candidates from a population by selecting
 * <i>n</i> candidates at random where the probability of each candidate getting
 * selected is proportional to its fitness score.  This is analogous to each
 * candidate being assigned an area on a roulette wheel proportionate to its fitness
 * and the wheel being spun <i>i</i> times.  Candidates may be selected more than
 * once.
 * 
 *
 * <p>
 * In some instances, particularly with small population sizes, the randomness
 * of selection may result in excessively high occurrences of particular candidates.
 * 
 * @author Daniel Dyer
 * @author Asendar
 * 
 *
 */
public class RouletteWheelSelection implements AbstractSelectionStrategy<HigherOrderMutant> {

	private static final Random random = new Random();

	@Override
	public List<HigherOrderMutant> select(Population<HigherOrderMutant> population, int selectionSize) {

		double[] cumulativeFitnesses = new double[population.getMutants().size()];

		for (int i = 1; i < population.getMutants().size(); i++) {
			double fitness = population.getMutants().get(i).getFitness();
			cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
		}

		List<HigherOrderMutant> selection = Lists.newArrayList();

		for (int i = 0; i < selectionSize; i++) {
			double randomFitness = random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];
			int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);
			if (index < 0) {

				index = Math.abs(index + 1);
			}
			selection.add(population.getMutants().get(index));
		}
		return selection;
	}

}
