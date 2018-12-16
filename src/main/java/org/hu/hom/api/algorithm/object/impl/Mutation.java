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
import java.util.Random;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.algorithm.object.AbstractMutation;
import org.hu.hom.core.mutation.MutationModel;
import org.hu.hom.core.object.FirstOrderMutant;
import org.hu.hom.core.object.HigherOrderMutant;

import com.google.common.collect.Lists;

/**
 * 
 * <p>
 * Mutates a population by randomly adding or removing a
 * {@link FirstOrderMutant} to a selected subset of the populations
 * {@link HigherOrderMutant}s as one of the operations done by the
 * {@link GeneticAlgorithm}.
 * 
 * 
 * @author Asendar
 * 
 *
 */
public class Mutation implements AbstractMutation<HigherOrderMutant> {

	/**
	 * 
	 */
	private static final Random RANDOM = new Random();
	private static final Integer MAX_ORDER = 4;

	@Override
	public List<HigherOrderMutant> mutate(List<HigherOrderMutant> mutants) {
		List<HigherOrderMutant> newMutants = Lists.newArrayList();

		for (HigherOrderMutant mutant : mutants) {

			mutant = mutant.copy();

			int y = RANDOM.nextInt(4);

			if ((y == 0 && !(mutant.getOrder() >= MAX_ORDER)) || mutant.getOrder() == 1)
				newMutants.add(applyRandomMutation(mutant));
			else
				newMutants.add(removeAMutation(mutant));

		}

		return newMutants;
	}

	/**
	 * <p>
	 * removes a {@link FirstOrderMutant} from a given {@link HigherOrderMutant}
	 * 
	 * @param mutant {@link HigherOrderMutant} to mutate
	 * @return mutated {@link HigherOrderMutant}
	 */
	private HigherOrderMutant removeAMutation(HigherOrderMutant mutant) {

		if (mutant.getFirstOrderMutants().size() < 2) {
			return mutant;
		}

		int x = RANDOM.nextInt(mutant.getFirstOrderMutants().size());

		List<FirstOrderMutant> mutatns = Lists.newArrayList(mutant.getFirstOrderMutants());

		mutant.removeFirstOrderMutant(mutatns.get(x));

		return mutant;
	}

	/**
	 * <p>
	 * adds a {@link FirstOrderMutant} to a given {@link HigherOrderMutant}
	 * 
	 * @param mutant {@link HigherOrderMutant} to mutate
	 * @return mutated {@link HigherOrderMutant}
	 */
	private HigherOrderMutant applyRandomMutation(HigherOrderMutant mutant) {
		mutant.addFirstOrderMutant(MutationModel.getRandomMutant(mutant));

		return mutant;
	}

}
