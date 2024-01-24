package com.example.demo.deck;

/**
 * Enums for the allowed suits
 * @author Vedrana Zeba
 */
public enum Suit {
	HEARTS('h'),
	SPADES('s'),
	CLUBS('c'),
	DIAMONDS('d');

	private char suit;
	
	
	/**
	 * Creates the suits
	 * @param firstletter h, s, c or d
	 */
	private Suit(char firstLetter) {
		this.suit = firstLetter;
	}

	
	/**
	 * Returns the suit
	 * @return the suit
	 */
	public char getSuitLetter() {
		return suit;
	}
}