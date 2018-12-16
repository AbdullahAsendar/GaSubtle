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
package org.hu.hom.api.listener;

import org.hu.hom.api.algorithm.GeneticAlgorithm;

/**
 * <p>
 * This interface is used to listen to {@link GeneticAlgorithm} each generation changes. 
 * 
 * @author Asendar
 *
 */
public interface GeneticAlgorithmListener {
	/**
	 * @param generation current generation number
	 * @param populationSize of current population
	 * @param liveMutants generated so far
	 * @param subtleMutants generated so far
	 */
	void stateChanged(int generation, int populationSize, int liveMutants, int subtleMutants);
}
