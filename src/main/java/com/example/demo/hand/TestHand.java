package com.example.demo.hand;

import java.util.ArrayList;
import java.util.Random;
import com.example.demo.deck.Card;
import com.example.demo.deck.Deck;

/**
 * Class for testing the hand-class and has also been used for the WHitebox Testing of the hand-class.
 * @author Max Frennessen 17-05-25
 * @version 1.0
 */
public class TestHand {
  Deck deck;
  Hand hand;
  Card card1;
  Card card2;
  Card card3;
  Card card4;
  Card card5;
  
  private ArrayList<Card> cards = new ArrayList<Card>();
  private int[] turn = {2, 5, 6, 7};

  /**
   * Creates a deck and then calls on specifik cards that is then
   * send to a ai that calculates and makes a decicion based on the cards strenght.
   * All commented code is used for diffirent parts of testing, and is needed for those parts.
   */
  public TestHand() {

//		 1=2h   14 = 2s   27 = 2c   40=2d 

	  deck = new Deck();
//    deck.shuffle();

		for (int i = 0; i < 14; i++) {
			card1 = deck.getCard();
		}
		deck = new Deck();
		for (int i = 0; i < 2; i++) {
			card2 = deck.getCard();
		}
    //FLOPP
		deck = new Deck();
		for (int i = 0; i < 3; i++) {
			card3 = deck.getCard();
		}
		
		deck = new Deck();
		for (int i = 0; i < 4; i++) {
			card4 = deck.getCard();
		}
		deck = new Deck();
		for (int i = 0; i < 5; i++) {
			card5 = deck.getCard();
		}
	  
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);
		cards.add(card5);

		
//    for (int testAlot = 0; testAlot < 15; testAlot++) {
//      cards.clear();
//      deck = new Deck();
//      deck.shuffle();
//
//      Random rand = new Random();
//      int RandomSize = rand.nextInt(4);
//
//      int thisTurn = turn[RandomSize];
//
//      for (int i = 0; i < thisTurn; i++) {
//        cards.add(deck.getCard());
//      }

      hand = new Hand(cards);
      System.out.print(hand.getHandStrenght());
    
//    }
		

  }

  public static void main(String[] args) {
    TestHand run = new TestHand();
  }
}
