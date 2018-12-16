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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.hu.hom.core.utils.FileUtils.FileType;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.SpoonModelBuilder.InputType;

/**
 * <p>
 * Compiles <i>.java</i> files using {@link SpoonAPI}
 * 
 * @author Asendar
 *
 */
public class Compiler {

	/**
	 * @param fileName of java file to be compiled
	 * @param code of the java file to be stored
	 * @param dest to save the compiled file to
	 * @return the compiled file
	 * @throws IOException if the file does not exist
	 */
	public static File compile(String fileName, String code, String dest) throws IOException {

		fileName = FileUtils.clean(fileName);

		File file = new File(dest + File.separator + FileType.noExtension(fileName) + FileType.JAVA.getExtension());
		file.delete();

		FileUtils.write(file, code, Charset.defaultCharset());

		Launcher launcher = new Launcher();
		launcher.addInputResource(file.getPath());
		launcher.getEnvironment().setNoClasspath(true);
		launcher.getEnvironment().setAutoImports(true);
		launcher.setBinaryOutputDirectory(dest);
		launcher.setSourceOutputDirectory(dest);

		launcher.getModelBuilder().compile(InputType.FILES);
		
		file.delete();

		return new File(dest + File.separator + FileType.noExtension(fileName) + FileType.CLASS.getExtension());

	}
}
