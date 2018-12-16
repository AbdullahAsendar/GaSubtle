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

import org.hu.hom.api.algorithm.object.AbstractSelectionStrategy;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.object.Population;

import com.google.common.collect.Lists;


/**
 * <p>
 * A selection strategy that picks candidates from a population at absolute randomness
 * 
 * @author Asendar
 *
 */
public class RandomSelection implements AbstractSelectionStrategy<HigherOrderMutant>{

	private static final Random random = new Random();

	@Override
	public List<HigherOrderMutant> select(Population<HigherOrderMutant> population, int selectionSize) {

		List<HigherOrderMutant> mutants = Lists.newArrayList(population.getMutants());
		List<HigherOrderMutant> picked = Lists.newArrayList();

		

		for (int index = 0; index < selectionSize; index++) {
			picked.add(mutants.get(random.nextInt(mutants.size())).copy());
		}

		return picked;
	}

}
