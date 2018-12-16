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
import java.util.Set;

import com.google.common.collect.Sets;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * This class is used to represent a mutated class.
 * 
 * @author Asendar
 * 
 * @see FirstOrderMutant
 * @see HigherOrderMutant
 *
 */
@EqualsAndHashCode(of = "id")
public abstract class AbstractMutant implements Comparable<AbstractMutant>{

	
	protected String originalPath;
	/**
	 * The unique if of the {@link AbstractMutant}
	 */
	protected @Getter String id;
	
	/**
	 * <p>
	 * Determine if the mutant is compilable or not
	 *
	 *<p>
	 * true by default
	 */
	protected @Getter @Setter boolean compilable = true;
	
	public boolean isNonCompiled() {
		return !compilable;
	}

	/**
	 * Test cases that killed this {@link AbstractMutant}, the string value is the
	 * failed test case header
	 */
	private final Set<String> killed_by = Sets.newHashSet();

	/**
	 * @param value header of a test case
	 */
	public void addKilledBy(String value) {
		killed_by.add(value);
	}

	/**
	 * clear all the test cases that killed this {@link AbstractMutant}
	 */
	public void clearKilledBy() {
		killed_by.clear();
	}

	/**
	 * @return all the test cases that killed this {@link AbstractMutant}
	 */
	public Set<String> getKilledBy() {
		return killed_by;
	}

	/**
	 * @param killedBy to replace all the current killed by test cases
	 */
	public void setKilledBy(Collection<String> killedBy) {
		clearKilledBy();
		killedBy.forEach(this::addKilledBy);
	}

	/**
	 * 
	 * @return true if {@link AbstractMutant#getKilledBy()} is empty and false otherwise
	 */
	public boolean isLive() {
		return getKilledBy().isEmpty();
	}
	
	/**
	 * 
	 * @return calls {@link AbstractMutant#isLive()}
	 */
	public boolean isSubtle() {
		return isLive();
	}
	

	/**
	 * Sorts based on {@link AbstractMutant#getKilledBy()} size
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(AbstractMutant abstractMutant) {
		return Integer.valueOf(getKilledBy().size()).compareTo(
				Integer.valueOf(abstractMutant.getKilledBy().size()));
	}

	/**
	 * @return the code of the {@link AbstractMutant} as {@link String}
	 */
	public abstract String getCode();
	
	/**
	 * @return the order of the mutant
	 */
	public abstract int getOrder();
}
