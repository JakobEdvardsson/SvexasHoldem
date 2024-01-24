package com.example.demo.hand;

import java.util.ArrayList;
import com.example.demo.deck.Card;

/**
 * The hand-class that will guide and help the noob player.
 * 
 * @author Max Frennessen 17-05-25
 * @version 2.0 
 */
public class Hand {
	private HandCalculation calc;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<String> aiCards = new ArrayList<String>();
	private ArrayList<String> toHighlight = new ArrayList<String>();
	private String helper;
	private String advice;
	private int pwrBar;
	private int handStrenght;

	/**
	 * Constructor
	 * @param cards gets card that are important for this turn.
	 */
	public Hand(ArrayList<Card> cards) {
		this.cards = cards;
		convertToReadable();

		calc = new HandCalculation(aiCards);
		helper = calc.Help();
		advice = calc.advice();
		pwrBar = calc.calcPwrBarLvl();
		toHighlight = calc.toHiglight();

		System.out.println(" -NEW HAND- ");
		System.out.println(aiCards);
		System.out.println("Helper - " + helper);
		System.out.println("");
		System.out.println("Advice - " + advice);
		System.out.println("");
		System.out.println("pwrBar - " + pwrBar);
		System.out.println("toHighlight - " + toHighlight);
		System.out.println("");

	}

	
	/**
	 * Converts the cards into readable Strings.
	 */
	public void convertToReadable() {

		for (int i = 0; i < cards.size(); i++) {
			Card cardTemp = cards.get(i);
			char A = cardTemp.getCardSuit().charAt(0);
			String temp = cardTemp.getCardValue() + "," + String.valueOf(A);
			aiCards.add(temp);
		}
	}

	
	/**
	 * Recalculates advice and which cards to highlight. Required when adding and removing cards.
	 */
	public void reCalc() {
		this.calc = new HandCalculation(aiCards);

		this.advice = calc.advice();
		this.toHighlight = calc.toHiglight();
	}
/**
 * returns a number that will be used to set a image to visualize the users handStrength
 * @return a int that represents the users cardStregnth
 */
	public int toPowerBar() { 
		return pwrBar;
	}
/**
 * returns the Text that will be shown to the user
 * @return a String of text to help the user
 */
	public String theHelp() {
		return helper;
	}
/**
 * returns the advice the program gives the user this turn.
 * @return returns the advice the program gives the user this turn.
 */
	public String theAdvice() {
		return advice;
	}

	/**
	 * @return returns what is supposed to be highlighted.
	 */
	public ArrayList<String> getHighlightedCards() {
		return toHighlight;
	}
/**
 * returns the current handstrength
 * @return returns the current handstrength
 */
	public int getHandStrenght() {
		handStrenght = calc.calcHandstrenght();
		return handStrenght;
	}
}
