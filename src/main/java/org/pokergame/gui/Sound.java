package org.pokergame.gui;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 * Sound class containing all the sounds that the program uses.
 * 
 * @author Lykke Levin
 * @version 1.1
 *
 */
public class Sound {

	private static final String SOUND_BASE_PATH = "org/pokergame/sounds/";

	//private static Media m = new Media(Sound.class.getClassLoader().getResource(  "org/pokergame/sounds/cool_struttin.mp3").toString());
	private static Media m = new Media(Sound.class.getClassLoader().getResource("org/pokergame/sounds/cool_struttin.mp3").toString());

	public static MediaPlayer mp = new MediaPlayer(m);
	public AudioClip checkSound = new AudioClip(Sound.class.getClassLoader().getResource(SOUND_BASE_PATH + "checkMeSound.m4a").toString());
	public AudioClip shuffleSound = new AudioClip(Sound.class.getClassLoader().getResource(SOUND_BASE_PATH + "cardShuffle.wav").toString());
	public AudioClip singleCard = new AudioClip(Sound.class.getClassLoader().getResource(SOUND_BASE_PATH + "cardSlide8.wav").toString());
	public AudioClip cardFold = new AudioClip(Sound.class.getClassLoader().getResource(SOUND_BASE_PATH + "cardShove3.wav").toString());
	public AudioClip chipSingle = new AudioClip(Sound.class.getClassLoader().getResource(SOUND_BASE_PATH + "chipsStacksSingle.wav").toString());
	public AudioClip chipMulti = new AudioClip(Sound.class.getClassLoader().getResource(SOUND_BASE_PATH + "ChipMe.m4a").toString());
	public AudioClip coinSound = new AudioClip(Sound.class.getClassLoader().getResource(SOUND_BASE_PATH + "ChingChingChip.m4a").toString());
	public AudioClip wrongSound = new AudioClip(Sound.class.getClassLoader().getResource(SOUND_BASE_PATH + "buttonSoundWrong.mp3").toString());


	/**
	 * Plays the AudioClip.
	 * @param type Name of sound that is being sent from the different classes that uses the audio objects.
	 */
	public void playSound(String type) {
		String whatSound = type;

		if (whatSound.equals("check")) {
			checkSound.play();
		} else if (whatSound.equals("fold")) {
			cardFold.play();
		} else if (whatSound.equals("shuffle")) {
			shuffleSound.play();
		} else if (whatSound.equals("singleCard")) {
			singleCard.play();
		} else if (whatSound.equals("chipSingle")) {
			chipSingle.play();
		} else if (whatSound.equals("chipMulti")) {
			chipMulti.play();
		} else if (whatSound.equals("coinSound")) {
			coinSound.play();
		} else if (whatSound.equals("wrong")) {
			wrongSound.play();
		}

	}

	/**
	 * Starts playing the background music.
	 */
	public void playBackgroundMusic() {
		mp.play();

	}

}