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

import java.util.List;
import java.util.Random;

import org.hu.hom.api.algorithm.object.SelectionStrategy;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.object.Population;

import com.google.common.collect.Lists;

/**
 * An alternative to {@link RouletteWheelSelection} as a fitness-proportionate
 * selection strategy. Ensures that the frequency of selection for each
 * candidate is consistent with its expected frequency of selection.
 * 
 * @author Daniel Dyer
 * @author Asendar
 */
public class StochasticUniversalSampling implements SelectionStrategy<HigherOrderMutant> {

	private static final Random random = new Random();

	@Override
	public List<HigherOrderMutant> select(Population<HigherOrderMutant> population, int selectionSize) {

		double aggregateFitness = population.getMutants().stream().mapToDouble(HigherOrderMutant::getFitness).sum();

		List<HigherOrderMutant> selection = Lists.newArrayList();

		// Pick a random offset between 0 and 1 as the starting point for selection.
		double startOffset = random.nextDouble();
		double cumulativeExpectation = 0;
		int index = 0;
		for (HigherOrderMutant candidate : population.getMutants()) {
			// Calculate the number of times this candidate is expected to
			// be selected on average and add it to the cumulative total
			// of expected frequencies.
			cumulativeExpectation += candidate.getFitness() / aggregateFitness * selectionSize;

			// If f is the expected frequency, the candidate will be selected at
			// least as often as floor(f) and at most as often as ceil(f). The
			// actual count depends on the random starting offset.
			while (cumulativeExpectation > startOffset + index) {
				selection.add(candidate);
				index++;
			}
		}
		return selection;
	}

}
