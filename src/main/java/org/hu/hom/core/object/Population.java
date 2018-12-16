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
package org.hu.hom.core.object;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hu.hom.api.algorithm.GeneticAlgorithm;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>
 * This class holds a set of {@link AbstractMutant}s
 * 
 * <p>
 * It is used to represent a set of chromosomes of the {@link GeneticAlgorithm}
 * 
 * 
 * @author Asendar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Population<T extends AbstractMutant> {

	/**
	 * Set of chromosomes that this {@link Population} holds
	 */
	private final Set<T> mutants = Sets.newHashSet();
	

	/**
	 * @param <T> instance of {@link AbstractMutant}
	 * @param clazz type of the {@link Population} to be created [must extend {@link AbstractMutant}]
	 * @return an empty population of type <code>clazz</code>
	 */
	public static <T extends AbstractMutant> Population<T> newPopulation(Class<T> clazz) {
		return new Population<>();
	}
	
	/**
	 * @return identical copy of the current {@link Population}
	 */
	public Population<T> copy() {
		Population<T> population = new Population<>();
		population.mutants.addAll(mutants);
		return population;
	}


	/**
	 * @return sorted list of the {@link Population}'s {@link AbstractMutant}s
	 */
	public List<T> getMutants() {
		return Lists.newArrayList(mutants).stream().sorted().collect(Collectors.toList());
	}
	
	/**
	 * @param mutants to be added to the {@link Population}
	 */
	public void addMutants(List<T> mutants) {
		mutants.forEach(this::addMutant);
	}
	
	/**
	 * @param mutants to be set for the {@link Population}
	 */
	public void setMutants(List<T> mutants) {
		this.mutants.clear();
		mutants.forEach(this::addMutant);
	}

	/**
	 * @param mutant to be added to the {@link Population}
	 */
	public void addMutant(T mutant) {
		
		if(mutants.contains(mutant))
			removeMutant(mutant);
		
		mutants.add(mutant);
	}
	

	/**
	 * @param mutant to be removed from the {@link Population}
	 */
	public void removeMutant(T mutant) {
		mutants.remove(mutant);
	}
	
	/**
	 * @param mutants to be removed from the {@link Population}
	 */
	public void removeMutants(List<T> mutants) {
		mutants.removeAll(mutants);
	}

	/**
	 * @return filtered list of the {@link Population}'s {@link AbstractMutant}s, based on {@link AbstractMutant#isSubtle()}
	 * 
	 * @see HigherOrderMutant#isSubtle()
	 */
	public List<T> getSubtleMutants() {
		return getMutants().stream().filter(T::isSubtle).collect(Collectors.toList());
	}

	/**
	 * @return filtered list of the {@link Population}'s {@link AbstractMutant}s, based on {@link AbstractMutant#isLive()}
	 */
	public List<T> getLiveMutants() {
		return getMutants().stream().filter(T::isLive).collect(Collectors.toList());
	}
	
	/**
	 * omits all non compilable mutants from the population
	 */
	public void removeNonCompilableMutants() {
		setMutants(getMutants().stream().filter(T::isCompilable).collect(Collectors.toList()));
	}
}
