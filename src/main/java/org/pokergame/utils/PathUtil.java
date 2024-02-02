package org.pokergame.utils;

import org.pokergame.gui.Sound;

import java.net.URL;

public class PathUtil {
  private static final String BASE_PATH = "/org/pokergame/";
  private static final String SOUND_PATH = "org/pokergame/";

  public String getImage(String filename) {
    return (BASE_PATH + "images/" + filename);
  }

  public String getSound(String filename) {
    return Sound.class.getClassLoader().getResource(SOUND_PATH + "sounds/" + filename).toString();
  }

  public URL getFXML(String filename) {
    return getClass().getResource(BASE_PATH + filename + ".fxml");
  }
}
