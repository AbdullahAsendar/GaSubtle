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

import java.text.SimpleDateFormat;

import org.hu.hom.ui.graphical.model.log.LogRecord;
import org.hu.hom.ui.graphical.model.log.Logger;
import org.hu.hom.ui.graphical.model.log.LogRecord.Level;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Duration;

/**
 * @author Asendar
 *
 */
public class LogViewer extends ListView<LogRecord> {
	private static final int MAX_ENTRIES = 10_000;

	private final static PseudoClass debug = PseudoClass.getPseudoClass("debug");
	private final static PseudoClass info = PseudoClass.getPseudoClass("info");
	private final static PseudoClass warn = PseudoClass.getPseudoClass("warn");
	private final static PseudoClass error = PseudoClass.getPseudoClass("error");

	private final static SimpleDateFormat timestampFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

	private final BooleanProperty showTimestamp = new SimpleBooleanProperty(false);
	private final ObjectProperty<Level> filterLevel = new SimpleObjectProperty<>(null);
	private final BooleanProperty tail = new SimpleBooleanProperty(false);
	private final BooleanProperty paused = new SimpleBooleanProperty(false);
	private final DoubleProperty refreshRate = new SimpleDoubleProperty(60);

	private final ObservableList<LogRecord> logItems = FXCollections.observableArrayList();

	public BooleanProperty showTimeStampProperty() {
		return showTimestamp;
	}

	public ObjectProperty<Level> filterLevelProperty() {
		return filterLevel;
	}

	public BooleanProperty tailProperty() {
		return tail;
	}

	public BooleanProperty pausedProperty() {
		return paused;
	}

	public DoubleProperty refreshRateProperty() {
		return refreshRate;
	}

	public void setLogger(Logger logger) {
		getStyleClass().add("log-view");

		Timeline logTransfer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			logger.getLog().drainTo(logItems);

			if (logItems.size() > MAX_ENTRIES) {
				logItems.remove(0, logItems.size() - MAX_ENTRIES);
			}

			if (tail.get()) {
				scrollTo(logItems.size());
			}
		}));
		logTransfer.setCycleCount(Timeline.INDEFINITE);
		logTransfer.rateProperty().bind(refreshRateProperty());

		this.pausedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue && logTransfer.getStatus() == Animation.Status.RUNNING) {
				logTransfer.pause();
			}

			if (!newValue && logTransfer.getStatus() == Animation.Status.PAUSED && getParent() != null) {
				logTransfer.play();
			}
		});

		logTransfer.play();

		filterLevel.addListener((observable, oldValue, newValue) -> {
			setItems(new FilteredList<LogRecord>(logItems,
					logRecord -> logRecord.getLevel().ordinal() >= filterLevel.get().ordinal()));
		});
		filterLevel.set(Level.DEBUG);

		setCellFactory(param -> new ListCell<LogRecord>() {
			{
				showTimestamp.addListener(observable -> updateItem(this.getItem(), this.isEmpty()));
			}

			@Override
			protected void updateItem(LogRecord item, boolean empty) {
				super.updateItem(item, empty);

				pseudoClassStateChanged(debug, false);
				pseudoClassStateChanged(info, false);
				pseudoClassStateChanged(warn, false);
				pseudoClassStateChanged(error, false);

				if (item == null || empty) {
					setText(null);
					return;
				}

				String context = (item.getContext() == null) ? "" : item.getContext() + " ";

				if (showTimestamp.get()) {
					String timestamp = (item.getTimestamp() == null) ? ""
							: timestampFormatter.format(item.getTimestamp()) + " ";
					setText(timestamp + context + item.getMessage());
				} else {
					setText(context + item.getMessage());
				}

				switch (item.getLevel()) {
				case DEBUG:
					pseudoClassStateChanged(debug, true);
					break;

				case INFO:
					pseudoClassStateChanged(info, true);
					break;

				case WARN:
					pseudoClassStateChanged(warn, true);
					break;

				case ERROR:
					pseudoClassStateChanged(error, true);
					break;
				}
			}
		});
	}

}
