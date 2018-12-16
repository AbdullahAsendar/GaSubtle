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
package org.hu.hom.ui.graphical.view;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * @author Asendar
 *
 */
public class SplashScreen extends de.felixroske.jfxsupport.SplashScreen {

	private static final String SPLASH_IMAGE = "/ui/images/splash.jpg";

	@Override
	public Parent getParent() {
		final ImageView imageView = new ImageView(getClass().getResource(getImagePath()).toExternalForm());
		imageView.setFitHeight(600);
		imageView.setFitWidth(600);

		final VBox vbox = new VBox();
		vbox.getChildren().addAll(imageView);

		return vbox;
	}

	@Override
	public boolean visible() {
		return true;
	}

	@Override
	public String getImagePath() {
		return SPLASH_IMAGE;
	}

}