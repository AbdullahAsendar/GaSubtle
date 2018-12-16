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

import org.hu.hom.core.utils.FileUtils;
import org.hu.hom.core.utils.IDUtils;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>
 * This class is used to represent a first order mutated class.
 * 
 * <p>
 * It holds the path to the mutated file using {@link FirstOrderMutant#mutantPath}
 * 
 * @author Asendar
 * 
 * @see AbstractMutant
 * @see HigherOrderMutant
 *
 */
@Getter
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FirstOrderMutant extends AbstractMutant{

	/**
	 * Path to the mutated <i>.java</i> file
	 */
	private String mutantPath;


	public static FirstOrderMutant build(String mutantPath) {
		FirstOrderMutant mutant = new FirstOrderMutant();

		mutant.id = IDUtils.newID();
		mutant.mutantPath = mutantPath;

		return mutant;
	}

	/* (non-Javadoc)
	 * @see org.hu.hom.main.model.core.AbstractMutant#getCode()
	 */
	@Override
	public String getCode() {
		return FileUtils
				.readJavaFile(mutantPath);
	}

	@Override
	public int getOrder() {
		return 1;
	}

}
