package com.example.demo.gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Controller for the FXML-doc Rules.fxml that handles the Rules-state. 
 * @author Lykke
 *
 */
public class RuleController{

	public Stage window = new Stage();
	private static final String BASE_PATH = "/com/example/demo/";
	
	/**
	 * Creates a window and sets the correct FXML as the scene. 
	 * @throws IOException
	 */
	public void rules() throws IOException{
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Regler");
		window.setWidth(1100);
		window.setHeight(633);
		window.setOnCloseRequest(e -> closeProgram());
		Pane mainPane = (Pane) FXMLLoader.load(getClass().getResource(BASE_PATH + "Rules.fxml"));
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * Closes the window.
	 */
	public void closeProgram() {
		window.close();
	}

}
