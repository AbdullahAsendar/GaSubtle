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
package org.hu.hom.api.algorithm.object;

import java.util.List;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.algorithm.object.impl.CrossoverDefaultImpl;
import org.hu.hom.core.object.AbstractMutant;
import org.hu.hom.core.object.Population;

import com.google.common.collect.Lists;

/**
 * <p>
 * Crossover that can be passed to the {@link GeneticAlgorithm} to be executed on each generation.
 * 
 * @author Asendar
 * 
 * @param <T> The type of {@link Population} to execute the operation on.
 * 
 * @see CrossoverDefaultImpl
 *
 */
public interface Crossover<T extends AbstractMutant> {

	default List<T> excute(List<T> mutants) {

		List<T> newMutants = Lists.newArrayList();

		for (int index = 0; index < mutants.size(); index += 2) {

			if (mutants.size() < index + 2)
				break;

			T chr_1 = mutants.get(index);
			T chr_2 = mutants.get(index + 1);

			newMutants.addAll(crossover(chr_1, chr_2));

		}

		return newMutants;
	}

	List<T> crossover(T firstMutant, T secondMutant);
}
