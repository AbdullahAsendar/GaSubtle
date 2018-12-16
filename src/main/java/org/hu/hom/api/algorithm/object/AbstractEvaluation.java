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
import org.hu.hom.api.algorithm.object.impl.Evaluation;
import org.hu.hom.core.object.AbstractMutant;
import org.hu.hom.core.object.Population;

/**
 * <p>
 * Evaluation that can be passed to the {@link GeneticAlgorithm} to be executed on each generation.
 * 
 * @author Asendar
 * 
 * @param <T> The type of {@link Population} to execute the operation on.
 * 
 * @see Evaluation
 *
 */
public interface AbstractEvaluation<T extends AbstractMutant> {
	List<T> evaluate(List<T> mutants);
}
