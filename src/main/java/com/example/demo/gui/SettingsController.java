package com.example.demo.gui;

import java.io.IOException;
import com.example.demo.controller.SPController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Controller for FXML-doc GameSettingMenu.fxml
 * 
 * @author Lykke Levin
 * @version 1.0
 *
 */
public class SettingsController {
	private SPController spController;

	private ChangeScene changeScene;
	private ConfirmBox confirmBox;
	private String name;
	private int aiValue;
	private int potValue;

	@FXML
	private TextField tfNameInput;
	@FXML
	private Slider aiSlider;
	@FXML
	private Slider potSlider;
	@FXML
	private CheckBox cbOn;
	@FXML
	private CheckBox cbOff;
	@FXML
	private ImageView ivStartGame;
	@FXML
	private ImageView ivQuestionAi;
	@FXML
	private ImageView ivQuestionPot;
	@FXML
	private ImageView ivQuestionTutorial;
	@FXML
	private Label lblAiInfo;
	@FXML
	private Label lblPotInfo;
	@FXML
	private Label lblTutorialInfo;
	@FXML
	private ImageView ivBack;

	@FXML
	private ImageView imgTutorial;
	@FXML
	private Pane tutorialPane;
	@FXML
	private ImageView btnNext;

	private Sound sound = new Sound();
	private TutorialController tutorialWindow;

	/**
	 * Method for initializing FXML. 
	 * @throws Exception
	 */
	public void initialize() throws Exception {
		potSlider.setSnapToTicks(true);
		potSlider.setValue(5000);
		aiSlider.setSnapToTicks(true);

	}

	/**
	 * Stores the name from the TextField that the user has inserted. 
	 */
	public void tfNameInputChange() {
		this.name = tfNameInput.getText();
	}

	/**
	 * Sets the changeScene for this SettingsController
	 * @param sceneChanger an instance of the class ChangeScene
	 */
	public void setChangeScene(ChangeScene sceneChanger) {

		this.changeScene = sceneChanger;
	}

	/**
	 * Stores the value from the Slider that the user has chosen. 
	 */
	public void aiSliderChange() {
		Double val = aiSlider.getValue();
		aiValue = val.intValue();

	}

	/**
	 * Stores the value from the Slider that the user has chosen. 
	 */
	public void potSliderChange() {

		Double val = potSlider.getValue();
		potValue = val.intValue();

	}

	/**
	 * If ComboBox is selected by the user, disable the button true. 
	 */
	public void cbOnClicked() {

		if (cbOff.isSelected()) {
			cbOff.setSelected(false);
			cbOff.setDisable(false);
			cbOn.setSelected(true);
			cbOn.setDisable(true);

		}
	}

	/**
	 * If ComboBox is selected by the user, disable the button true. 
	 */
	public void cbOffClicked() {

		if (cbOn.isSelected()) {
			cbOn.setSelected(false);
			cbOn.setDisable(false);
			cbOff.setSelected(true);
			cbOff.setDisable(true);

		}
	}

	/**
	 * Starts the game and checks so the Username it not empty and checks if the Tutorial should be playing at the beginning. 
	 * @throws IOException
	 */
	public void startGame() throws IOException {


		potSliderChange();
		aiSliderChange();
		if (!tfNameInput.getText().isEmpty()) {
			name = tfNameInput.getText();
			spController = new SPController();
			changeScene.setSPController(spController);


			if (cbOn.isSelected()) {
				System.out.println("Tutorial ska visas");
				Platform.runLater(() -> {

				try {
					this.tutorialWindow = new TutorialController(this, 1);
					tutorialWindow.setupUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				});

			} else{
				//do it here
				startGameWindow();
			}
		} else if (tfNameInput.getText().isEmpty()) {
			sound.playSound("wrong");
			confirmBox = new ConfirmBox();
			boolean result =
					confirmBox.display("Varning", "Du måste välja ett användarnamn för att starta spelet");
			System.out.println("Du måste välja ett användarnamn");
			System.out.println(result);

		}

	}
	
	/**
	 * Creates the progressForm and the loadingbar. 
	 */
	public void startGameWindow(){
		ProgressForm pForm = new ProgressForm();
		// In real life this task would do something useful and return
		// some meaningful result:
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws InterruptedException {
				for (int i = 0; i < 10; i++) {
					updateProgress(i += 1, 10);
					Thread.sleep(200);

				}
				updateProgress(10, 10);
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.start();
		// binds progress of progress bars to progress of task:
		pForm.activateProgressBar(task);

		// in real life this method would get the result of the task
		// and update the UI based on its value:
		task.setOnSucceeded(event -> {
			pForm.getDialogStage().close();

			try {
				changeScene.switchScenetoGame();
				ConfirmBox cfBox = new ConfirmBox();

				if (cfBox.display("Snart börjar spelet", "Är du redo att spela poker?")) {
					spController.startGame(aiValue, potValue, name);
					Sound.mp.stop();
					sound.playSound("shuffle");
				} else {
					changeScene.switchToMainMenu();
				}
			} catch (IOException | InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("Spel startas!");
	}
	
	/**
	 * Shows a label if question mark is hovered. 
	 */
	public void ivQuestionAiHovered() {

		lblAiInfo.setVisible(true);
		ivQuestionAi.setOnMouseExited(e -> lblAiInfo.setVisible(false));

	}

	/**
	 * Shows a label if question mark is hovered. 
	 */
	public void ivQuestionPotHovered() {

		lblPotInfo.setVisible(true);
		ivQuestionPot.setOnMouseExited(e -> lblPotInfo.setVisible(false));

	}

	/**
	 * Shows a label if question mark is hovered. 
	 */
	public void ivQuestionTutorialHovered() {

		lblTutorialInfo.setVisible(true);
		ivQuestionTutorial.setOnMouseExited(e -> lblTutorialInfo.setVisible(false));
	}

	/**
	 *  Tells class changeScene to perform the swithScene-action. 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void back() throws InstantiationException, IllegalAccessException {
		try {
			changeScene.switchToMainMenu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Main.window.setScene(changeScene.sceneMenu);
	}

	/**
	 * Name of the user.
	 * @return String name of the user. 
	 */
	public String getName() {
		return name;
	}
}
