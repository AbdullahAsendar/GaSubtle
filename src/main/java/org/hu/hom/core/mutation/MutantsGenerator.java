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
package org.hu.hom.core.mutation;

import java.io.File;
import java.io.IOException;

import org.hu.hom.core.exception.HomException;
import org.hu.hom.core.utils.Compiler;
import org.hu.hom.core.utils.FileUtils;
import org.hu.hom.core.utils.FileUtils.FileType;

import lombok.Builder;
import mujava.ClassMutantsGenerator;
import mujava.MutationSystem;
import mujava.TraditionalMutantsGenerator;

/**
 * 
 * Code was copied from <a href="https://github.com/jeffoffutt/muJava">muJava
 * repo</a>
 * 
 * 
 * @author Asendar
 *
 */
@Builder
public class MutantsGenerator {
	
	/**
	 * Path to the file to be mutated
	 */
	private String originalFile;
	/**
	 * Path to store the results to
	 */
	private String outputPath;
	/**
	 * Working directory of MuJava
	 */
	private String muJavaHome;
	
	
	/**
	 * @return the new path of the original file
	 * @throws IOException if mutants could not be exported
	 */
	public String generate() throws IOException {

		setPaths(muJavaHome);

		creatStructure(originalFile);

		runMutationSystem();

		FileUtils.copyFileToDirectory(MutationSystem.ORIGINAL_PATH + File.separator + FileUtils.clean(originalFile), outputPath);

		FileUtils.deleteDirectory(MutationSystem.ORIGINAL_PATH);

		FileUtils.copyDirectory(MutationSystem.MUTANT_HOME + File.separator + FileType.noExtension(FileUtils.clean(originalFile)),
				outputPath);

		FileUtils.deleteDirectory(muJavaHome);
		
		return outputPath + File.separator + FileUtils.clean(originalFile);
		
		
//		LOG.info("Original file :" + config.getOriginalFile());

	}

	/**
	 * MuJava's mutants generator
	 */
	private void runMutationSystem() {

		String file_name = FileUtils.clean(originalFile);

		String[] class_ops = MutationSystem.cm_operators;

		String[] traditional_ops = MutationSystem.tm_operators;

		try {

			String temp = file_name.substring(0, file_name.length() - ".java".length());
			String class_name = "";

			for (int j = 0; j < temp.length(); j++) {
				if ((temp.charAt(j) == '\\') || (temp.charAt(j) == '/')) {
					class_name = class_name + ".";
				} else {
					class_name = class_name + temp.charAt(j);
				}
			}

			MutationSystem.recordInheritanceRelation();

			int class_type = MutationSystem.getClassType(class_name);

			if (class_type == MutationSystem.MAIN_ONLY) {
				HomException.throwException(
						"Could not generate mutants because class %s has only the 'static void main()' method",
						file_name);

				return;
			}

			setMutationSystemPathFor(file_name);

			File original_file = new File(MutationSystem.SRC_PATH, file_name);

			ClassMutantsGenerator cmGenEngine;

			if (class_ops != null) {
				cmGenEngine = new ClassMutantsGenerator(original_file, class_ops);
				cmGenEngine.makeMutants();
			}

			if (traditional_ops != null) {
				TraditionalMutantsGenerator tmGenEngine;

				tmGenEngine = new TraditionalMutantsGenerator(original_file, traditional_ops);
				tmGenEngine.makeMutants();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

//		LOG.info("All files are handled");
	}


	/**
	 * @param file_name of java class under execution
	 */
	private void setMutationSystemPathFor(String file_name) {
		try {
			String temp;
			temp = file_name.substring(0, file_name.length() - ".java".length());
			temp = temp.replace('/', '.');
			temp = temp.replace('\\', '.');
			int separator_index = temp.lastIndexOf(".");

			if (separator_index >= 0) {
				MutationSystem.CLASS_NAME = temp.substring(separator_index + 1, temp.length());
			} else {
				MutationSystem.CLASS_NAME = temp;
			}

			String mutant_dir_path = MutationSystem.MUTANT_HOME + "/" + temp;
			File mutant_path = new File(mutant_dir_path);
			mutant_path.mkdir();

			String class_mutant_dir_path = mutant_dir_path + "/" + MutationSystem.CM_DIR_NAME;
			File class_mutant_path = new File(class_mutant_dir_path);
			class_mutant_path.mkdir();

			String traditional_mutant_dir_path = mutant_dir_path + "/" + MutationSystem.TM_DIR_NAME;
			File traditional_mutant_path = new File(traditional_mutant_dir_path);
			traditional_mutant_path.mkdir();

			String original_dir_path = mutant_dir_path + "/" + MutationSystem.ORIGINAL_DIR_NAME;
			File original_path = new File(original_dir_path);
			original_path.mkdir();

			MutationSystem.CLASS_MUTANT_PATH = class_mutant_dir_path;
			MutationSystem.TRADITIONAL_MUTANT_PATH = traditional_mutant_dir_path;
			MutationSystem.ORIGINAL_PATH = original_dir_path;
			MutationSystem.DIR_NAME = temp;
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * @param home_path of MuJava
	 */
	private void setPaths(String home_path) {

		MutationSystem.SYSTEM_HOME = home_path;
		MutationSystem.SRC_PATH = home_path + "/src";
		MutationSystem.CLASS_PATH = home_path + "/classes";
		MutationSystem.MUTANT_HOME = home_path + "/result";
		MutationSystem.TESTSET_PATH = home_path + "/testset";
	}


	private void creatStructure(String file) throws IOException {
		FileUtils.mkdirs(MutationSystem.SRC_PATH, MutationSystem.CLASS_PATH, MutationSystem.MUTANT_HOME);
		FileUtils.copyFileToDirectory(file, MutationSystem.SRC_PATH);

		Compiler.compile(FileUtils.clean(file),
				FileUtils.readJavaFile(MutationSystem.SRC_PATH + File.separator + FileUtils.clean(file)),
				MutationSystem.CLASS_PATH);
	}
	


}
