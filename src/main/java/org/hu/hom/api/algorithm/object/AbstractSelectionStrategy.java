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

import org.hu.hom.core.object.AbstractMutant;
import org.hu.hom.core.object.Population;

/**
 * <p>
 * Strategy interface for "natural" selection.
 * 
 * <p>
 * Idea originally taken from <a href="https://watchmaker.uncommons.org/">watchmaker</a>
 * 
 * @param <T> The type of evolved entity that we are selecting.
 * @author Daniel Dyer
 * @author Asendar
 * 
 */
public interface AbstractSelectionStrategy<T extends AbstractMutant> {
    /**
     * <p>Select the specified number of candidates from the population.
     * Implementations may assume that the population is sorted in descending
     * order according to fitness (so the fittest individual is the first item
     * in the list).</p>
     * 
     * <p>It is an error to call this method with an empty or null population.</p>
     * 
     * @param population The {@link Population} from which to select.
     * @param selectionSize The number of individual selections to make.
     * @return A list containing the selected candidates.
     */
	List<T> select(Population<T> population, int selectionSize);
}