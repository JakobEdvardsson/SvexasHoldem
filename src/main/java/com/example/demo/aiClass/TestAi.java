package com.example.demo.aiClass;


import java.util.ArrayList;
import com.example.demo.deck.Card;
import com.example.demo.deck.Deck;
/**
 * Class that is for testing of the AI.
 * And also been used for doing the WhiteBox testing of the ai-player.
 * @author Max Frennessen 17-05-25
 * @version 1.0
 */
public class TestAi {
  Deck deck;
  Ai ai;
  private Card card1;
  private Card card2;
  private Card[] flop = new Card[3];
  private Card cardTurn;
  private Card cardRiver;

  private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
  private ArrayList<Card> decken = new ArrayList<Card>();
  
  /**
   * Creates a deck and then calls on specifik cards that is then
   * send to a ai that calculates and makes a decicion based on the cards strenght.
   * All commented code is used for diffirent parts of testing, and is needed for those parts.
   */
  public TestAi() {
	  decken = new ArrayList<Card>();

//	 1=2h   14 = 2s   27 = 2c   40=2d 
//	  2H, 2S , 2D,4C, 3D
	  deck = new Deck();
//    deck.shuffle();

		for (int i = 0; i < 1; i++) {
			card1 = deck.getCard();
		}
		deck = new Deck();
		for (int i = 0; i < 2; i++) {
			card2 = deck.getCard();
		}
    //FLOPP
		deck = new Deck();
		for (int i = 0; i < 3; i++) {
			flop[0] = deck.getCard();
		}
		
		deck = new Deck();
		for (int i = 0; i < 4; i++) {
			flop[1] = deck.getCard();
		}
		deck = new Deck();
		for (int i = 0; i < 5; i++) {
			flop[2] = deck.getCard();
		}
//   //TURN
//		deck = new Deck();
//		for (int i = 0; i < 16; i++) {
//			cardTurn = deck.getCard();
//		}
//	//RIVER
//		deck = new Deck();
//		for (int i = 0; i < 16; i++) {
//			cardRiver = deck.getCard();
//		}
			
	
//    card1 = deck.getCard();
//    card2 = deck.getCard();
//    cardTurn = deck.getCard();
//    cardRiver = deck.getCard();

//    for (int i = 0; i < 3; i++) { // 3 kort för flopp.
////      deck.shuffle();
//      flop[i] = deck.getCard();
//    }

    ai = new Ai(808, "TestAiName");

    ai.setStartingHand(card1, card2);
//    ai.makeDecision(150);
//    System.out.println(ai.getDecision());

    System.out.println("\n\n-Test FLOP-");
    ai.makeDecision(32, flop);
    System.out.println(ai.getDecision());
//
//    System.out.println("\n\n-Test TURN-");
//    ai.makeDecision(32, cardTurn);
//    System.out.println(ai.getDecision());
//
//    System.out.println("\n\n-Test RIVER-");
//    ai.makeDecision(32, cardRiver);
//    System.out.println(ai.getDecision());

//    ai.updateWinner(1032);
//    System.out.println("\n\nAI-pot - " + ai.aiPot());
  }


  
  public static void main(String[] args) {
    TestAi run = new TestAi();
  }
}
