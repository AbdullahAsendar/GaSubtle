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
package org.hu.hom.ui.graphical;

import org.hu.hom.ui.graphical.view.MainView;
import org.hu.hom.ui.graphical.view.SplashScreen;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;

/**
 * @author Asendar
 *
 */
@SpringBootApplication
@ComponentScan("org.hu.hom")
public class UiLauncher extends AbstractJavaFxApplicationSupport{

	public static void main(String args[]) {
		launch(UiLauncher.class, MainView.class, new SplashScreen(), args);
	}

}
