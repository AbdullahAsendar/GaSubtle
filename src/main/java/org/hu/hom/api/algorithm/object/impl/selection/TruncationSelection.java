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
import java.util.stream.Collectors;

import org.hu.hom.api.algorithm.object.SelectionStrategy;
import org.hu.hom.core.object.HigherOrderMutant;
import org.hu.hom.core.object.Population;


/**
 * <p>
 * Implements selection of <i>n</i> candidates from a population by simply
 * selecting the <i>n</i> candidates with the highest fitness scores (the
 * rest are discarded).  A candidate is never selected more than once.
 * 
 * 
 * @author Daniel Dyer
 * @author Asendar
 *
 */
public class TruncationSelection implements SelectionStrategy<HigherOrderMutant>{


	@Override
	public List<HigherOrderMutant> select(Population<HigherOrderMutant> population, int selectionSize) {

		return population.getMutants().stream().sorted().collect(Collectors.toList()).subList(0, selectionSize);
	}

}
