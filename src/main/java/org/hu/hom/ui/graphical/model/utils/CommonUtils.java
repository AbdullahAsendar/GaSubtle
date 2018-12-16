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

import org.controlsfx.control.PopOver;
import org.springframework.stereotype.Component;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * @author Asendar
 *
 */
@Component
public class CommonUtils {

	private PopOver popOver;
	private Label lblMessage;
	
	public PopOver showErrorPopOver(String message, Node owner) {
		return showPopOver(message, "-fx-text-fill: red; -fx-font-size:13;", owner);
	}

	public PopOver showPopOver(String message, String style, Node owner) {

		if (lblMessage == null) {
			lblMessage = new Label();
			lblMessage.setPadding(new Insets(0, 10, 0, 10));
		}

		lblMessage.setText(message);
		lblMessage.setStyle(style);

		if (popOver == null) {
			popOver = getPopOver("", lblMessage);
			popOver.setDetachable(false);
			popOver.setCornerRadius(0);
		}
		popOver.hide();
		

		try {
			popOver.show(owner);
		} catch (Exception e1) {
			System.err.println("error showing popover");
		}

		return popOver;
	}

	private PopOver getPopOver(String title, Node content) {

		PopOver popOver = new PopOver();
		if (content != null) {
			popOver.setContentNode(content);
		}

		popOver.setTitle(title);
		popOver.setDetachable(false);
		popOver.setCornerRadius(0);
		popOver.setArrowSize(5);
		popOver.setConsumeAutoHidingEvents(false);

		return popOver;
	}

}
