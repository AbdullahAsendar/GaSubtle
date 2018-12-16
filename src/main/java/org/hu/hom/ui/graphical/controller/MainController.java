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
package org.hu.hom.ui.graphical.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.hu.hom.api.algorithm.object.impl.selection.Selection;
import org.hu.hom.ui.GaConfig;
import org.hu.hom.ui.graphical.UiLauncher;
import org.hu.hom.ui.graphical.model.constants.SharedConfig;
import org.hu.hom.ui.graphical.model.utils.CommonUtils;
import org.hu.hom.ui.graphical.model.utils.FileGenerator;
import org.hu.hom.ui.graphical.view.GaMonitor;
import org.springframework.beans.factory.annotation.Autowired;

import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Asendar
 *
 */
@FXMLController
public class MainController implements Initializable {

	private @FXML TextField txtOriginalFile, txtTestCases;

	private @FXML ComboBox<Selection> cmbSelctionStrategy;

	private @FXML ComboBox<Integer> cmbTimeout, cmbMaxGeneration, cmbMaxNumHoms, cmbMaxNumSubtleHoms, cmbRepeat, cmbPercentage;
	
	private @FXML Label lblTermination;
	
	private @Autowired FileGenerator fileGenerator;
	
	private @Autowired CommonUtils commonUtils;

	private @FXML void chooseOriginalFile() {
		File file = fileGenerator.getJavaFile();
		if(file == null) return;
		txtOriginalFile.setText(file.getPath());
	}

	private @FXML void chooseTestCases() {
		File file = fileGenerator.getDirectory();
		if(file == null) return;
		txtTestCases.setText(file.getPath());
	}

	private @FXML void runAlgorithm() {
		
		if(!validate()) return;
		
		GaConfig gaConfig = 
				GaConfig
				.builder()
				.originalFile(txtOriginalFile.getText())
				.testCasesPath(txtTestCases.getText())
				.selectionStrategy(cmbSelctionStrategy.getSelectionModel().getSelectedItem().getSelectionStrategy())
				.resultPath(new File(txtOriginalFile.getText()).getParent())
				.requiredSubtleHoms(cmbMaxNumSubtleHoms.getSelectionModel().getSelectedItem())
				.maxHoms(cmbMaxNumHoms.getSelectionModel().getSelectedItem())
				.maxGeneration(cmbMaxGeneration.getSelectionModel().getSelectedItem())
						.timeout(cmbTimeout.getSelectionModel().getSelectedItem() != null
								? Long.valueOf(cmbTimeout.getSelectionModel().getSelectedItem() * 60)
								: null)
				.build();
		
		SharedConfig.setGaConfig(gaConfig);

		
		UiLauncher.showView(GaMonitor.class);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		{
			List<Integer> range = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList());

			cmbTimeout.getItems().addAll(range);
			cmbMaxGeneration.getItems().addAll(range);
			cmbMaxNumHoms.getItems().addAll(range);
			cmbMaxNumSubtleHoms.getItems().addAll(range);
			cmbRepeat.getItems().addAll(range);
//			cmbPopulationSize.getItems().addAll(range);
			
			cmbPercentage.getItems().addAll(IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList()));
			
//			cmbPopulationSize.getSelectionModel().select(Integer.valueOf(100));
			cmbPercentage.getSelectionModel().select(Integer.valueOf(5));
		}
		
		cmbSelctionStrategy.getItems().addAll(Selection.values());
		
		
	}
	
	private boolean validate() {
		if(txtOriginalFile.getText().replaceAll(" ", "").equals("")) {
			commonUtils.showErrorPopOver("Required field", txtOriginalFile);
			return false;
		}
		
		if(!fileGenerator.isValidFile(txtOriginalFile.getText())) {
			commonUtils.showErrorPopOver("Not a valid file", txtOriginalFile);
			return false;
		}
		
		if(txtTestCases.getText().replaceAll(" ", "").equals("")) {
			commonUtils.showErrorPopOver("Required field", txtTestCases);
			return false;
		}
		
		if(!fileGenerator.isValidDirectory(txtTestCases.getText())) {
			commonUtils.showErrorPopOver("Not a valid directory", txtTestCases);
			return false;
		}
		
		if (notSelected(cmbSelctionStrategy)) {
			commonUtils.showErrorPopOver("Required field", cmbSelctionStrategy);
			return false;
		}
		
		if (notSelected(cmbTimeout) && notSelected(cmbMaxGeneration) && notSelected(cmbMaxNumHoms)
				&& notSelected(cmbMaxNumSubtleHoms)) {
			commonUtils.showErrorPopOver("At least one termination condition is required", lblTermination);
			return false;
		}
		
		
		if (notSelected(cmbRepeat)) {
			commonUtils.showErrorPopOver("Required field", cmbRepeat);
			return false;
		}
		
		return true;
	}
	
	private boolean notSelected(ComboBox<?> cmb) {
		return cmb.getSelectionModel().getSelectedItem() == null;
	}


}