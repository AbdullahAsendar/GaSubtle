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
 * <p>
 * Selection strategy that picks a pair of candidates at random and then
 * selects the fitter of the two candidates with probability p, where p
 * is the configured selection probability (therefore the probability of
 * the less fit candidate being selected is 1 - p).
 * 
 * 
 * @author Daniel Dyer
 * @author Asendar
 *
 */
public class TournamentSelection implements SelectionStrategy<HigherOrderMutant> {

	private static final Random random = new Random();

	@Override
	public List<HigherOrderMutant> select(Population<HigherOrderMutant> population, int selectionSize) {

		List<HigherOrderMutant> selection = Lists.newArrayList();
		List<HigherOrderMutant> mutants = Lists.newArrayList(population.getMutants());

		for (int i = 0; i < selectionSize; i++) {

			// Pick two candidates at random.
			HigherOrderMutant candidate1 = mutants.get(random.nextInt(mutants.size()));
			HigherOrderMutant candidate2 = mutants.get(random.nextInt(mutants.size()));

			selection.add(candidate2.getFitness() > candidate1.getFitness() ? candidate2 : candidate1);
		}
		
		return selection;
	}

}
