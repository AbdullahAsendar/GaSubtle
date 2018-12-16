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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hu.hom.core.utils.FileUtils;
import org.hu.hom.core.utils.IDUtils;

import com.google.common.collect.Lists;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * This class is used to represent a higher order mutated class.
 * 
 * <p>
 * It holds the injected faults into a the HOM <i>(higher order mutant)</i> that
 * it presents using {@link HigherOrderMutant#first_order_mutants}
 * 
 * <p>
 * This class can be transformed to <i>.java</i> file that contains either the HOM
 * or the original code that it presents.
 * 
 * <p>
 * All the constructors of this class are private, to create an object of this
 * class use {@link HigherOrderMutant#build(String)}
 * 
 * 
 * @author Asendar
 * 
 * @see AbstractMutant
 * @see FirstOrderMutant
 *
 */
@AllArgsConstructor(staticName = "build", access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HigherOrderMutant extends AbstractMutant{

	/**
	 * The degree that the {@link HigherOrderMutant#fitness} should 
	 * equal or exceed to be considered as subtle {@link HigherOrderMutant}
	 * 
	 * @see HigherOrderMutant#isSubtle()
	 */
	public static final double SUBTLE_DEGREE = 1;

	/**
	 * Faults injected to this {@link HigherOrderMutant}
	 */
	private final Set<FirstOrderMutant> first_order_mutants = new HashSet<>();
	/**
	 * The fitness of the {@link HigherOrderMutant} calculated using {@link EvaluationDefaultImpl#setFitness(HigherOrderMutant)}
	 */
	private @Setter @Getter double fitness;

	/**
	 * @param originalPath of the file that this mutant represents
	 * @return empty {@link HigherOrderMutant}
	 */
	public static HigherOrderMutant build(String originalPath) {
		HigherOrderMutant mutant = new HigherOrderMutant();
		mutant.id = IDUtils.newID();
		mutant.originalPath = originalPath;
		return mutant;
	}

	/**
	 * @return identical copy of the current {@link HigherOrderMutant}
	 */
	public HigherOrderMutant copy() {
		HigherOrderMutant mutant = build(this.originalPath);
		mutant.id = this.id;
		mutant.fitness = this.fitness;
		mutant.setFirstOrderMutants(first_order_mutants);
		mutant.setKilledBy(getKilledBy());
		return mutant;
	}
	
	/**
	 * Sets the id on the {@link HigherOrderMutant} based on the {@link FirstOrderMutant}s it is created from
	 * 
	 * @return unique id for the {@link HigherOrderMutant}
	 */
	@Override
	public String getId() {
		StringBuilder strBuilder = new StringBuilder();
		getFirstOrderMutants().stream().sorted().forEach(mutant -> strBuilder.append(String.format("%s;", mutant.getId())));
		this.id = strBuilder.toString();
		return this.id;
	}

	/**
	 * 
	 * The degree of a {@link HigherOrderMutant} is the count of its {@link FirstOrderMutant}s
	 *
	 **/
	@Override
	public int getOrder() {
		return getFirstOrderMutants().size();
	}

	/**
	 * 
	 * <p>
	 * Each {@link HigherOrderMutant} has a Set of {@link FirstOrderMutant}s, as each {@link FirstOrderMutant}
	 * holds the path to the mutated and the original <i>.java</i> files, this method reads these
	 * files and spots the differences between these two files <i>(the original and the mutated)</i>
	 * and applies these differences to a copy of the original file.
	 * 
	 * @return the mutated code that this {@link HigherOrderMutant} represents
	 */
	@Override
	public String getCode() {

		List<String> lines = Lists.newArrayList(getOriginalCode().split("\n"));

		Map<Integer, String> line_map = new HashMap<>();

		for (FirstOrderMutant mutant : getFirstOrderMutants()) {

			List<String> mutantLines = Lists.newArrayList(mutant.getCode().split("\n"));

			for (int i = 0; i < lines.size(); i++) {
				if (!lines.get(i).equals(mutantLines.get(i))) {
					line_map.put(i, mutantLines.get(i));
				}
			}

		}

		line_map.keySet().forEach(key -> lines.set(key, line_map.get(key)));

		StringBuilder stringBuilder = new StringBuilder();
		lines.forEach(line -> stringBuilder.append(line + "\n"));

		return stringBuilder.toString();
	}

	/**
	 * @param mutant {@link FirstOrderMutant} to be added to this {@link HigherOrderMutant}
	 */
	public void addFirstOrderMutant(FirstOrderMutant mutant) {
		if (mutant == null)
			return;
		first_order_mutants.add(mutant);
	}
	/**
	 * @param mutant {@link FirstOrderMutant} to be removed from this {@link HigherOrderMutant}
	 */
	public void removeFirstOrderMutant(FirstOrderMutant mutant) {
		first_order_mutants.remove(mutant);
	}
	
	/**
	 * @return {@link List} of {@link FirstOrderMutant}s that this {@link HigherOrderMutant} is created from
	 */
	public List<FirstOrderMutant> getFirstOrderMutants() {
		return Lists.newArrayList(first_order_mutants);
	}

	/**
	 * @param mutants to replace all the current {@link FirstOrderMutant}s
	 */
	public void setFirstOrderMutants(Collection<FirstOrderMutant> mutants) {
		first_order_mutants.clear();
		first_order_mutants.addAll(mutants);
	}

	/**
	 * @return the test cases that killed all the {@link FirstOrderMutant}s that this {@link HigherOrderMutant} is created from
	 */
	public Set<String> getFirstOrderMutantsKilledBy() {
		Set<String> set = new HashSet<>();
		first_order_mutants.forEach(mutant -> set.addAll(mutant.getKilledBy()));
		return set;
	}

	/**
	 * @return the original code that this {@link HigherOrderMutant} represents 
	 */
	public String getOriginalCode() {
		return FileUtils.readJavaFile(originalPath);
	}
	/**
	 * Sorts based on {@link HigherOrderMutant}'s fitness
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AbstractMutant abstractMutant) {
		HigherOrderMutant mutant = (HigherOrderMutant) abstractMutant;
		return Double.valueOf(mutant.getFitness()).compareTo(this.getFitness());
	}

	/**
	 * @return true if the fitness is larger than or equals {@link HigherOrderMutant#SUBTLE_DEGREE}
	 */
	@Override
	public boolean isSubtle() {
		return getFitness() >= SUBTLE_DEGREE;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof HigherOrderMutant))
			return false;

		HigherOrderMutant mutant = (HigherOrderMutant) o;

		return getId().equals(mutant.getId());
	}
}
