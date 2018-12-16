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

import org.hu.hom.api.algorithm.object.AbstractSelectionStrategy;
import org.hu.hom.core.object.HigherOrderMutant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Asendar
 *
 */
@AllArgsConstructor
public enum Selection {
	
	
	ROULETTE_WHEEL(new RouletteWheelSelection()), 
	SUS(new StochasticUniversalSampling()), 
	RANDOM(new RandomSelection()), 
	TOURNAMENT(new TournamentSelection()), 
	TRUNCATION(new TruncationSelection());

	private @Getter AbstractSelectionStrategy<HigherOrderMutant> selectionStrategy;
}
