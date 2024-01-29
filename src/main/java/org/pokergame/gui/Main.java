package org.pokergame.gui;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Main method to start the program.
 *
 * @author Lykke Levin
 * @version 1.0
 *
 */

public class Main extends Application {
    public static Stage window;
    public ChangeScene cs = new ChangeScene();

    /**
     * The applications calls start(Stage primaryStage) after launch has been
     * executed.
     */
    public void start(Stage primaryStage) throws Exception {
        cs.prepGame();

        window = primaryStage;
        window.setTitle("TeachMePoker");
        window.setResizable(true);
        window.setOnCloseRequest(e -> closeProgram());

        window.setScene(cs.firstScene());
        window.show();

    }

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);

    }

    /**
     * Closes the window and exits the program.
     */
    public void closeProgram() {
        window.close();
    }
}
