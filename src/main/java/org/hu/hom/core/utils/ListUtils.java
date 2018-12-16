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
package org.hu.hom.core.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

/**
 * 
 * <p>
 * Common operations for {@link Collection}s
 * 
 * @author Asendar
 *
 */
public class ListUtils {
	
	/**
	 * @param <T> any {@link Object}
	 * @param list to be chopped
	 * @param L number of pieces to chop the list to
	 * @return a chopped list
	 */
	public static <T> List<List<T>> chopped(List<T> list, int L) {
		
		// to avoid infinite loop
		if (L < 1) L = 1;
		
		List<List<T>> parts = Lists.newArrayList();
		final int N = list.size();
		for (int i = 0; i < N; i += L) {
			parts.add(Lists.newArrayList(list.subList(i, Math.min(N, i + L))));
		}
		return parts;
	}

	/**
	 * @param <T> any {@link Object}
	 * @param list to remove the duplicates from
	 * @return list with no duplicates
	 */
	public static <T> List<T> unique(List<T> list) {

		Set<T> set = new HashSet<>();
		set.addAll(list);

		return Lists.newArrayList(set);
	}

	
	/**
	 * @param <T> any {@link Object}
	 * 
	 * @param first set to be xored
	 * @param second set to be xored
	 * @return xored set
	 */
	public static <T> Set<T> xorSet(Set<T> first, Set<T> second) {

		Set<T> result = new HashSet<>();

		List<T> allItems = Lists.newArrayList();
		allItems.addAll(first);
		allItems.addAll(second);

		for (T item : allItems) {
			if ((first.contains(item) && !second.contains(item)) || (!first.contains(item) && second.contains(item)))
				result.add(item);
		}

		return result;
	}
}
