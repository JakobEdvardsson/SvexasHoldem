package com.example.demo.gui;

import java.io.IOException;
import com.example.demo.controller.SPController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Class that handles the switching of scenes in the main window and the gui controll class that
 * manages that scene.
 * 
 * @author Lykke Levin & Rikard Almgren
 * @version 1.0
 *
 */

public class ChangeScene {

  Pane rootMenu;
  Pane rootNewGame;
  Pane root2;
  Scene bestScene;
  private FMController fmController;
  private SettingsController settingsController;
  private GameController gameController;

  private static final String BASE_PATH = "/com/example/demo/";

  /**
   * Method which prepares the FXMLs and by extension the game itself.
   * 
   * @throws IOException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public void prepGame() throws IOException, InstantiationException, IllegalAccessException {
    new Sound().playBackgroundMusic();

    // Updated FXMLLoader for FirstMenu.fxml
    FXMLLoader loaderFM = new FXMLLoader(getClass().getResource("/com/example/demo/FirstMenu.fxml"));
    this.rootMenu = loaderFM.load();
    this.fmController = loaderFM.getController();

    // Updated FXMLLoader for GameSettingMenu.fxml
    FXMLLoader loaderSS = new FXMLLoader(getClass().getResource("/com/example/demo/GameSettingMenu.fxml"));
    this.rootNewGame = loaderSS.load();
    this.settingsController = loaderSS.getController();

    // Updated FXMLLoader for GameState.fxml
    FXMLLoader loaderGS = new FXMLLoader(getClass().getResource("/com/example/demo/GameState.fxml"));
    this.root2 = loaderGS.load();
    this.gameController = loaderGS.getController();

    this.bestScene = new Scene(rootMenu);

    gameController.setChangeScene(this);
    settingsController.setChangeScene(this);
    fmController.setChangeScene(this);

  }

  /**
   * Method which switches the scene to the settings menu.
   * 
   * @throws IOException
   */
  public void switchScenetoSetting() throws IOException {
    Main.window.getScene().setRoot(rootNewGame);
  }

  /**
   * Method which switches the scene to the GameState.
   * 
   * @throws IOException
   */
  public void switchScenetoGame() throws IOException {

    Main.window.getScene().setRoot(root2);
    gameController.setUsername(settingsController.getName());


  }

  /**
   * Method which returns the Scene(and First/main menu).
   * 
   * @return bestScene the scene for the game.
   * @throws IOException
   */
  public Scene firstScene() throws IOException {
    return bestScene;
  }

  /**
   * Method which switches to the main menu.
   * 
   * @throws IOException
   * @throws InstantiationException
   * @throws IllegalAccessException
   */
  public void switchToMainMenu()
      throws IOException, InstantiationException, IllegalAccessException {
    prepGame();
    Main.window.setScene(bestScene);
  }

  /**
   * Method which sets the SPController (the controller that runs the actual game behind the
   * scenes).
   * 
   * @param spController
   */
  public void setSPController(SPController spController) {
    gameController.setSPController(spController);
  }

}
