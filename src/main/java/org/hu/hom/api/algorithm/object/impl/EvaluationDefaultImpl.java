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
import java.util.Set;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.algorithm.object.Evaluation;
import org.hu.hom.core.object.FirstOrderMutant;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.utils.ListUtils;

import com.google.common.collect.Sets;

/**
 * <p>
 * Evaluation of the {@link HigherOrderMutant}s based on a Fitness function for 
 * the {@link GeneticAlgorithm}
 * 
 * 
 * @author Asendar
 *
 */
public class EvaluationDefaultImpl implements Evaluation<HigherOrderMutant> {
	
	@Override
	public List<HigherOrderMutant> evaluate(List<HigherOrderMutant> mutants) {
		mutants.stream().forEach(this::setFitness);
		return mutants;
	}
	/**
	 * @param mutant to set the fitness for
	 */
	private void setFitness(HigherOrderMutant mutant) {
		mutant.setFitness(getFitnessValue(mutant.getFirstOrderMutantsKilledBy(), mutant.getKilledBy()));
	}

	/**
	 * @param first test cases that kills the {@link FirstOrderMutant}s of the {@link HigherOrderMutant}
	 * @param second test cases that kills the {@link HigherOrderMutant}
	 * @return fitness value
	 */
	private double getFitnessValue(Set<String> first, Set<String> second) {
		if (first.isEmpty())
			return 0;

		double union = Sets.union(first, second).size();
		double fomKiller = union - second.size();

		if (union == 0)
			return 0;

		double xor = ListUtils.xorSet(first, second).size();
		return ((fomKiller / union) * 0.75) + ((xor / union) * 0.25);
	}


}
