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
package org.hu.hom.api.algorithm.report;

import java.util.List;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.core.object.AbstractMutant;
import org.hu.hom.core.object.Population;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class is used to record the values of each generation of the
 * {@link GeneticAlgorithm}
 * 
 * @author Asendar
 * 
 * @see ReportEngine
 * @see AbstractMutant
 * @see Population
 */
@Getter
@AllArgsConstructor(staticName = "build")
public class Record<T extends AbstractMutant> {

	private int generationNumber;

	private Population<T> population;

	private List<T> generatedMutants;
	private List<T> liveMutants;
	private List<T> subtleMutants;
}
