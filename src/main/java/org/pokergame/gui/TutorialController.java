package org.pokergame.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.pokergame.utils.PathUtil;

import java.io.IOException;

/**
 * Tutorial state.
 *
 * @author Vedrana Zeba
 */
public class TutorialController {

    @FXML
    private ImageView imgTutorial;
    @FXML
    private Pane tutorialPane;
    @FXML
    private ImageView btnNext;

    public int tutorialProgress;
    public SettingsController settingsController;
    public GameController gameController;
    public Stage window = new Stage();
    public int gc;

    private PathUtil pathUtil = new PathUtil();

    /**
     * Creates the tutorial window object, does not show the window.
     *
     * @param settingsController settingsController-object (self)
     */
    public TutorialController(SettingsController settingsController, int nr) {
        gc = nr;
        this.settingsController = settingsController;
    }

    /**
     * Creates the tutorial window object, does not show the window.
     *
     * @param gameController gameController-object (self)
     */
    public TutorialController(GameController gameController) {

        this.gameController = gameController;
    }

    /**
     * In order to prevent crash (fx:controller in Tutorial.fxml)
     */
    public TutorialController() {
    }

    /**
     * Initializes the tutorial window and all UI objects. Loads tutorial.fxml and starts the "button-listener" for next.
     * If the user cancels the tutorial mid-way, the user is sent to the game state.
     *
     * @throws IOException
     */
    public void setupUI() throws IOException {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Tutorial");
        window.setWidth(1285);
        window.setHeight(730);
        window.setOnCloseRequest(e -> settingsController.startGameWindow());
        this.tutorialPane = (Pane) FXMLLoader.load(getClass().getResource("/org/pokergame/Tutorial.fxml"));
        Scene scene = new Scene(tutorialPane);
        window.setScene(scene);
        window.show();

        this.tutorialProgress = 0;
        placeImg();

    }

    /**
     * Initializes the tutorial window and all UI objects. Loads tutorial.fxml and starts the "button-listener" for next.
     * If the user cancels the tutorial mid-way, the window closes and the user is sent back to the game.
     *
     * @throws IOException
     */
    public void setupUIinGame() throws IOException {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Tutorial");
        window.setWidth(1285);
        window.setHeight(730);
        window.setOnCloseRequest(e -> closeProgram());
        this.tutorialPane = (Pane) FXMLLoader.load(getClass().getResource("/org/pokergame/Tutorial.fxml"));
        Scene scene = new Scene(tutorialPane);
        window.setScene(scene);
        window.show();

        this.tutorialProgress = 0;
        placeImg();
    }

    /**
     * Activates correct listener based on tutorialProgress. There are 17 steps, the last step launches the game.
     */
    public void placeImg() {
        this.tutorialProgress = tutorialProgress + 1;
        System.out.println(tutorialProgress);
        String buttonName = "nextButton";
        if (tutorialProgress == 17) {
            buttonName = "spelaButton";
        }
        tutorialPane.requestLayout();
        Image image = new Image(pathUtil.getImage("tutorial" + tutorialProgress + ".png"), 1280, 720, true, true);
        imgTutorial = new ImageView(image);
        tutorialPane.getChildren().add(imgTutorial);

        image = new Image(pathUtil.getImage(buttonName + ".png"), 170, 95, true, true);
        btnNext = new ImageView(image);
        btnNext.setX(1090);
        btnNext.setY(570.5);
        tutorialPane.getChildren().add(btnNext);


        if (tutorialProgress == 17) {
            addButtonListenerPlay();
        } else {
            addButtonListenerNext();
        }
    }

    /**
     * Listener for next picture.
     */
    public void addButtonListenerNext() {
        btnNext.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                placeImg();
            }
        });
    }

    /**
     * Listener for start game.
     */
    public void addButtonListenerPlay() {
        btnNext.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (gc == 1) {
                    settingsController.startGameWindow();
                }
                closeProgram();
            }
        });
    }

    /**
     * Closes the tutorial window.
     */
    public void closeProgram() {
        window.close();
    }
}
