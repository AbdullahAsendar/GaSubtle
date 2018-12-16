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
package org.hu.hom.ui.graphical.model.utils;

import java.io.File;

import org.springframework.stereotype.Component;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Asendar
 *
 */
@Component
public class FileGenerator {

	public File getDirectory() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Choose Directory");
		chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File selectedDirectory = chooser.showDialog(null);
		return selectedDirectory;
	}

	public File getJavaFile() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose File");
		chooser.getExtensionFilters().add(new ExtensionFilter("Java Files", "*.java"));
		chooser.setInitialDirectory(new File(System.getProperty("user.dir")));

		File selectedFile = chooser.showOpenDialog(null);
		return selectedFile;
	}

	public boolean isValidDirectory(String path) {
		if (path.replaceAll(" ", "").equals(""))
			return false;

		try {
			File file = new File(path);
			return file.isDirectory() && file.exists();
		} catch (Exception e) {

			return false;
		}

	}
	
	public boolean isValidFile(String path) {
		if (path.replaceAll(" ", "").equals(""))
			return false;

		try {
			File file = new File(path);
			return !file.isDirectory() && file.exists();
		} catch (Exception e) {

			return false;
		}

	}

}
