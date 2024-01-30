package org.pokergame.deck;

/**
 * Enums for the allowed suits
 * @author Vedrana Zeba
 */
public enum Suit {
	HEARTS('H'),
	SPADES('S'),
	CLUBS('C'),
	DIAMONDS('D');

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