package com.example.demo.gui;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class which creates a progress dialog.
 * @author Rikard Almgren, Internet
 * @version 1.0
 *
 */
public class ProgressForm {
  private final Stage dialogStage;
  private final ProgressBar pb = new ProgressBar();
  private final ProgressIndicator pin = new ProgressIndicator();

  public ProgressForm() {
      dialogStage = new Stage();
      dialogStage.initStyle(StageStyle.UTILITY);
      dialogStage.setResizable(false);
      dialogStage.initModality(Modality.APPLICATION_MODAL);

      // PROGRESS BAR
      final Label label = new Label();
      label.setText("Loading...");

      pb.setProgress(-1F);
      pin.setProgress(-1F);

      final HBox hb = new HBox();
      hb.setSpacing(5);
      hb.setAlignment(Pos.CENTER);
      hb.getChildren().add(label);
      hb.getChildren().addAll(pb, pin);

      Scene scene = new Scene(hb);
      dialogStage.setScene(scene);
  }
/**
 * Method which binds the progress to a task's completion state
 * @param task
 */
  public void activateProgressBar(final Task<?> task)  {
      pb.progressProperty().bind(task.progressProperty());
      pin.progressProperty().bind(task.progressProperty());
      dialogStage.show();
  }
/**
 * Method which returns the dialog
 * @return the dialog
 */
  public Stage getDialogStage() {
      return dialogStage;
  }
}