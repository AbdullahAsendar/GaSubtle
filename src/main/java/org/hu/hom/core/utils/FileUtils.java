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
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Extends {@link org.apache.commons.io.FileUtils} to add some 
 * other specific methods to that class.
 * 
 * @author Asendar
 * 
 * @see org.apache.commons.io.FileUtils
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils extends org.apache.commons.io.FileUtils {

	/**
	 * @param path to a <i>.java</i> file
	 * @return the code that file contains
	 */
	public static String readJavaFile(String path) {

		try {
			return FileUtils.readFileToString(new File(path), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * @param path to a <i>.java</i> file
	 * @return number of lines of code in that file
	 */
	public static int getLoc(String path) {

		try {
			return FileUtils.readLines(new File(path), Charset.defaultCharset()).size();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;

	}

	/**
	 * @param file to be deleted
	 */
	public static void forceDelete(String file) {
		try {
			FileUtils.forceDelete(new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @param files directories to make
	 */
	public static void mkdirs(String... files) {
		for (String file : files)
			new File(file).mkdirs();
	}
	
	/**
	 * @param srcFile an existing file to copy
	 * @param destDir the directory to place the copy in
	 */
	public static void copyFileToDirectory(String srcFile, String destDir) {
		try {
			copyFileToDirectory(new File(srcFile), new File(destDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param resource an existing resource to copy
	 * @param destFile the file to export the resource to
	 */
	public static void copyResourceToFile(String resource, String destFile) {
		InputStream source = FileUtils.class.getResourceAsStream(resource);
		
		try {
			Files.copy(source, Paths.get(destFile), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param srcDir an existing directory to copy
	 * @param destDir the new directory
	 */
	public static void copyDirectory(String srcDir, String destDir) {
		try {
			copyDirectory(new File(srcDir), new File(destDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @param directory to delete
	 */
	public static void deleteDirectory(String directory) {
		try {
			deleteDirectory(new File(directory));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>
	 * Returns the file name only of a given path
	 * 
	 * <p>
	 * 
	 * <b>Example:</b>
	 * <br><br>
	 * file to clean: /home/user/path/to/file/file.java
	 * <br>
	 * returns: file.java
	 * 
	 * 
	 * @param file to be cleaned
	 * @return clean name of the file
	 */
	public static String clean(String file) {

		String fileName = file;

		int separator_index = fileName.lastIndexOf(File.separator);

		if (separator_index >= 0)
			fileName = fileName.substring(separator_index + 1, fileName.length());

		return fileName;
	}

	/**
	 * <p>
	 * Set of file types that the applications uses
	 *  
	 * 
	 * @author Asendar
	 *
	 */
	public static enum FileType {
		CLASS, JAVA, NONE;

		/**
		 * @return the extension of the file
		 */
		public String getExtension() {
			if (this.equals(CLASS))
				return ".class";

			if (this.equals(JAVA))
				return ".java";

			return "";

		}
		
		/**
		 * @param fileName to be cleaned from extension
		 * @return fileName with no extension
		 */
		public static String noExtension(String fileName) {
			return fileName.replaceAll(JAVA.getExtension(), "").replaceAll(CLASS.getExtension(), "");
		}
	}
}
