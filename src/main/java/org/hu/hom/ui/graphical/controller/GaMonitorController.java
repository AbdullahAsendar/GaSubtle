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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hu.hom.api.algorithm.GeneticAlgorithm;
import org.hu.hom.api.algorithm.object.impl.CrossoverDefaultImpl;
import org.hu.hom.api.algorithm.object.impl.EvaluationDefaultImpl;
import org.hu.hom.api.algorithm.object.impl.MutationDefaultImpl;
import org.hu.hom.api.config.Config;
import org.hu.hom.api.listener.GeneticAlgorithmListener;
import org.hu.hom.api.listener.MessageListener;
import org.hu.hom.ui.GaConfig;
import org.hu.hom.ui.graphical.model.log.Log;
import org.hu.hom.ui.graphical.model.log.Logger;
import org.hu.hom.ui.graphical.view.LogViewer;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * @author Asendar
 *
 */
@FXMLController
public class GaMonitorController implements Initializable {

	private @FXML Label lblGeneration, lblPopulation, lblLiveMutants, lblLSubtleMutants;

	private @FXML LogViewer logViewer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		Log log = new Log();
		Logger logger = new Logger(log, "Logger ");


		logViewer.setLogger(logger);
		
		logViewer.pausedProperty().set(false);
		logViewer.showTimeStampProperty().set(true);

		MessageListener messageListener = new MessageListener() {
			
			@Override
			public void info(String value) {
				logger.info(value);
			}
			
			@Override
			public void error(String value) {
				logger.error(value);
			}
			
			@Override
			public void debug(String value) {
				logger.debug(value);
			}
		};

		GeneticAlgorithmListener gaListener = (int generation, int populationSize, int liveMutants,
				int subtleMutants) -> Platform.runLater(() -> {
					lblGeneration.setText(String.valueOf(generation));
					lblPopulation.setText(String.valueOf(populationSize));
					lblLiveMutants.setText(String.valueOf(liveMutants));
					lblLSubtleMutants.setText(String.valueOf(subtleMutants));
				});

		Config config = Config
				.builder()
				.maxOrder(GaConfig.getMaxOrder())
				.maxGeneration(GaConfig.getMaxGeneration())
				.maxHoms(GaConfig.getMaxHoms())
				.mutationPercentage(GaConfig.getMutationPercentage())
				.originalFile(GaConfig.getOriginalFile())
				.requiredSubtleHoms(GaConfig.getRequiredSubtleHoms())
				.resultPath(GaConfig.getResultPath())
				.runRepeat(GaConfig.getRunRepeat())
				.testCasesPath(GaConfig.getTestCasesPath())
				.timeout(GaConfig.getTimeout())
				.build();
		
		GeneticAlgorithm geneticAlgorithm = 
				GeneticAlgorithm
				.builder()
				.config(config)
				.evaluation(new EvaluationDefaultImpl())
				.crossover(new CrossoverDefaultImpl())
				.mutation(new MutationDefaultImpl())
				.selection(GaConfig.getSelectionStrategy())
				.messageListener(messageListener)
				.geneticAlgorithmListener(gaListener)
				.build();
		
		executor.execute(()-> geneticAlgorithm.run());

		Platform.runLater(()-> lblGeneration.getScene().getWindow().setOnCloseRequest(e -> System.exit(1)));

	}
}