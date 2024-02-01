package org.pokergame.gui;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.pokergame.utils.PathUtil;

/**
 * Sound class containing all the sounds that the program uses.
 * 
 * @author Lykke Levin
 * @version 1.1
 *
 */
public class Sound {
  private static final PathUtil pathUtil = new PathUtil();

  private static final Media m = new Media(pathUtil.getSound("cool_struttin.mp3"));

  public static MediaPlayer mp = new MediaPlayer(m);
  public AudioClip checkSound = new AudioClip(pathUtil.getSound("checkMeSound.m4a"));
  public AudioClip shuffleSound = new AudioClip(pathUtil.getSound("cardShuffle.wav"));
  public AudioClip singleCard = new AudioClip(pathUtil.getSound("cardSlide8.wav"));
  public AudioClip cardFold = new AudioClip(pathUtil.getSound("cardSlide8.wav"));
  public AudioClip chipSingle = new AudioClip(pathUtil.getSound("chipsStacksSingle.wav"));
  public AudioClip chipMulti = new AudioClip(pathUtil.getSound("ChipMe.m4a"));
  public AudioClip coinSound = new AudioClip(pathUtil.getSound("ChingChingChip.m4a"));
  public AudioClip wrongSound = new AudioClip(pathUtil.getSound("buttonSoundWrong.mp3"));

  /**
   * Plays the AudioClip.
   * 
   * @param type Name of sound that is being sent from the different classes that
   *             uses the audio objects.
   */
  public void playSound(String type) {
    switch (type) {
      case "check" -> checkSound.play();
      case "fold" -> cardFold.play();
      case "shuffle" -> shuffleSound.play();
      case "singleCard" -> singleCard.play();
      case "chipSingle" -> chipSingle.play();
      case "chipMulti" -> chipMulti.play();
      case "coinSound" -> coinSound.play();
      case "wrong" -> wrongSound.play();
    }
  }

  /**
   * Starts playing the background music.
   */
  public void playBackgroundMusic() {
    // mp.play();

  }

}
