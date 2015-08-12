package com.boobaskaya.cocclanmanager;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutDialogController implements Initializable {

	private static final Logger LOGGER = Logger.getLogger("COC");

	@FXML
	private Button okButton;

	private Stage stage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// nothing to do here
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void okAction(ActionEvent event) {

		// release dialog
		if (stage != null) {
			stage.close();
		} else {
			LOGGER.severe("No stage has been set, unable to release dialog");
		}

	}

}
