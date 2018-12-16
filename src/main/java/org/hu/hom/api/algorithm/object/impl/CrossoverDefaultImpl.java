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

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.algorithm.object.Crossover;
import org.hu.hom.core.object.FirstOrderMutant;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.utils.ListUtils;

import com.google.common.collect.Lists;

/**
 * <p>
 * This class does crossover to {@link HigherOrderMutant}s as one of the
 * operations done by the {@link GeneticAlgorithm}.
 * 
 * <p>
 * It can crossover {@link HigherOrderMutant}s only as they contains a set of
 * {@link FirstOrderMutant}s
 * 
 * 
 * <p>
 * Takes two {@link HigherOrderMutant}s and swaps their
 * {@link FirstOrderMutant}s.
 * 
 * <p>
 * Example: <br>
 * HOM_1 : FOM_1_1, FOM_1_2 <br>
 * HOM_2 : FOM_2_1, FOM_2_2 <br>
 * <br>
 * result : <br>
 * HOM_1 : FOM_1_1, FOM_2_2 <br>
 * HOM_2 : FOM_2_1, FOM_1_2
 * 
 * 
 * @author Asendar
 * 
 */
public class CrossoverDefaultImpl implements Crossover<HigherOrderMutant> {

	@Override
	public List<HigherOrderMutant> crossover(HigherOrderMutant firstMutant, HigherOrderMutant secondMutant) {
		HigherOrderMutant newMutant_1 = firstMutant.copy();
		HigherOrderMutant newMutant_2 = secondMutant.copy();

		List<FirstOrderMutant> oldMutants_1 = Lists.newArrayList(firstMutant.getFirstOrderMutants());
		List<FirstOrderMutant> oldMutants_2 = Lists.newArrayList(secondMutant.getFirstOrderMutants());

		List<FirstOrderMutant> newMutants_1 = Lists.newArrayList();
		List<FirstOrderMutant> newMutants_2 = Lists.newArrayList();

		newMutants_1.addAll(ListUtils.chopped(oldMutants_1, oldMutants_1.size() / 2).get(0));
		try {
			newMutants_1.addAll(ListUtils.chopped(oldMutants_2, oldMutants_2.size() / 2).get(1));
		} catch (Exception e) {
			// has 1 mutant
		}

		newMutants_2.addAll(ListUtils.chopped(oldMutants_2, oldMutants_2.size() / 2).get(0));

		try {
			newMutants_2.addAll(ListUtils.chopped(oldMutants_1, oldMutants_1.size() / 2).get(1));
		} catch (Exception e) {
			// has 1 mutant
		}

		newMutant_1.setFirstOrderMutants(newMutants_1);
		newMutant_2.setFirstOrderMutants(newMutants_2);

		return Lists.newArrayList(newMutant_1, newMutant_2);
	}
}
