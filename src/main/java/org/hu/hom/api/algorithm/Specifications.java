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
package org.hu.hom.api.algorithm;

import org.hu.hom.core.config.Config;

/**
 * 
 * <p>
 * This class contains specifications for the {@link GeneticAlgorithm}.
 * 
 * @author Asendar
 *
 */
public class Specifications {

	private static final double FOM_PERCENTAGE = 0.1;
	private static final double SOM_PERCENTAGE = 0.8;
	private static final double TOM_PERCENTAGE = 0.0_5;
	private static final double FUOM_PERCENTAGE = 0.0_5;

//	private static final int ALPHA = 3;
	private static final int MAX_GENERATION_SIZE = 150;

	/**
	 * <p>
	 * This method returns the population size to be 
	 * used based on the number of LOC of the original file.
	 * 
	 * @param config of the {@link GeneticAlgorithm}
	 * @return the population size to use
	 */
	public static int getPopulationSize(Config config) {
		return MAX_GENERATION_SIZE;
//		return Math.min(ALPHA * FileUtils.getLoc(config.getOriginalFile()), MAX_GENERATION_SIZE);
	}

	/**
	 * @param populationSize of the population
	 * @return number of first order mutants it should contain
	 */
	public static int getFirstOrderSize(int populationSize) {
		int size = (int) (populationSize * FOM_PERCENTAGE);
		return size < 1 ? 1 : size;
	}
	/**
	 * @param populationSize of the population
	 * @return number of second order mutants it should contain
	 */
	public static int getSecondOrderSize(int populationSize) {
		int size = (int) (populationSize * SOM_PERCENTAGE);
		return size < 1 ? 1 : size;
	}
	/**
	 * @param populationSize of the population
	 * @return number of third order mutants it should contain
	 */
	public static int getThirdOrderSize(int populationSize) {
		int size = (int) (populationSize * TOM_PERCENTAGE);
		return size < 1 ? 1 : size;
	}
	/**
	 * @param populationSize of the population
	 * @return number of fourth order mutants it should contain
	 */
	public static int getFourthOrderSize(int populationSize) {
		int size = (int) (populationSize * FUOM_PERCENTAGE);
		return size < 1 ? 1 : size;
	}

}
