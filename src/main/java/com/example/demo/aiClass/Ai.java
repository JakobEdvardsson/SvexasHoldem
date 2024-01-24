package com.example.demo.aiClass;

import java.util.ArrayList;
import com.example.demo.deck.Card;


/**
 * Main class for Ai-player that depending on turn, creates a calculation and returns a respond to
 * controller.
 * 
 * @author Max Frennessen 17-05-25
 * @version 2.0
 */

public class Ai {

  private AiDecide aiDecide;
  private boolean isSmallBlind = false;
  private boolean isBigBlind = false;
  private boolean sameTurn = false;
  private int paidThisTurn = 0;
  private String name;
  private String whatToDo = "";
  private ArrayList<String> aiCards = new ArrayList<String>(); // Lista som lägger till alla kort
                                                               // som kommer in och som skickas till
                                                               // turns.
  private int aiPot; // AIPOT - KOMMER IN VIA CONTROLLER.
  private int highCard;
  private int handStrength;
  private int AllInViability = 99;


  /**
   * sets the starting potsize and the ai's name.
   * 
   * @param aiPot The potsize that the ai will start from.
   * @param name the Ai's name for the entire name.
   */
  public Ai(int aiPot, String name) {
    this.name = name;
    this.aiPot = aiPot;
  }


  /**
   * Receives the Ai-players two first cards.
   * 
   * @param card1 The first card the ai-player gets.
   * @param card2 The second card the ai-player gets.
   */
  public void setStartingHand(Card card1, Card card2) { // set starting hand

    aiCards.clear(); // resets the arraylist.
    char A = card1.getCardSuit().charAt(0);
    char B = card2.getCardSuit().charAt(0);
    String firstCard = card1.getCardValue() + "," + String.valueOf(A);
    String secondCard = card2.getCardValue() + "," + String.valueOf(B);
    this.highCard = card1.getCardValue();
    if (card2.getCardValue() > highCard) {
      highCard = card2.getCardValue();
    }
    aiCards.add(firstCard);
    aiCards.add(secondCard);
  }


  /**
   * Makes decision for the starting hand
   * 
   * @param currentBet How much the Ai-player as to bet to be able to play this turn.
   */
  public void makeDecision(int currentBet) {

    aiDecide = new AiDecide(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
    whatToDo = aiDecide.decision();
    System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
    this.paidThisTurn += aiPot - aiDecide.updateAiPot();
    handStrength = aiDecide.gethandStrength();
    aiPot = aiDecide.updateAiPot();
    System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
    System.out.println("Decision: " + whatToDo);
    System.out.println("AiPot after round: " + aiPot);
  }


  /**
   * Makes the decision for the flop
   * 
   * @param currentBet How much the Ai-player as to bet to be able to play this turn.
   * @param flop The three cards that will be set on the table that all players can use.
   */
  public void makeDecision(int currentBet, Card[] flop) {

    if (!sameTurn) {
      for (Card card : flop) {
        char A = card.getCardSuit().charAt(0);
        aiCards.add(card.getCardValue() + "," + String.valueOf(A));
      }
    }

    aiDecide = new AiDecide(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
    whatToDo = aiDecide.decision();
    System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
    this.paidThisTurn += aiPot - aiDecide.updateAiPot();
    aiPot = aiDecide.updateAiPot();
    handStrength = aiDecide.gethandStrength();
    System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
    System.out.println("Decision: " + whatToDo);
    System.out.println("AiPot after round: " + aiPot);
  }


  /**
   * Makes the decision for the last two turns of the game. If there are less than 7 cards its the
   * second last turn and it calls for a calculation on that turn. And if its 7 cards its the last
   * turn and it does a calculation for that turn instead. So never two calculations och one call.
   * 
   * @param currentBet How much the Ai-player as to bet to be able to play this turn.
   * @param turn Another cards gets added to the table that all the players can use.
   */
  public void makeDecision(int currentBet, Card turn) {

    if (!sameTurn) {
      char A = turn.getCardSuit().charAt(0);
      aiCards.add(turn.getCardValue() + "," + String.valueOf(A));
    }
    // IF its not the last turn, this gets called.
    if (aiCards.size() < 7) {
      aiDecide = new AiDecide(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
      whatToDo = aiDecide.decision();
      System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
      this.paidThisTurn += aiPot - aiDecide.updateAiPot();
      aiPot = aiDecide.updateAiPot();
      handStrength = aiDecide.gethandStrength();
      System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
      System.out.println("Decision: " + whatToDo);
      System.out.println("AiPot after round: " + aiPot);
      // IF its the last turn this is called.
    } else if (aiCards.size() == 7) {
      aiDecide = new AiDecide(aiCards, aiPot, currentBet, paidThisTurn, sameTurn);
      whatToDo = aiDecide.decision();
      System.out.println("PaidBeforeThisTurn: " + this.paidThisTurn);
      this.paidThisTurn += aiPot - aiDecide.updateAiPot();
      aiPot = aiDecide.updateAiPot();
      handStrength = aiDecide.gethandStrength();
      System.out.println("PaidThisTurn(including what was paid before): " + this.paidThisTurn);
      System.out.println("Decision: " + whatToDo);
      System.out.println("AiPot after round: " + aiPot);
    }

  }


  /**
   * Makes sure that AI-players decision from last this isnt making a problem for current turns
   * decision.
   * 
   * @param resets whatToDo.
   */
  public void setDecision(String reset) {

    whatToDo = reset;
  }


  /**
   * Returns the Decision the ai-player made this turn.
   * 
   * @return returns the Decision the ai-player made.
   */
  public String getDecision() {

    return whatToDo;
  }


  /**
   * Returns how much the ai-player has left in his pot.
   * 
   * @return returns the ai potSize
   */
  public int aiPot() {

    return aiPot;
  }


  /**
   * If ai-player wins the round this gets updated with the winning amount added to its current
   * potsize.
   * 
   * @param Updates the Ai's pot Size if it would win.
   */
  public void updateWinner(int aiPot) {

    this.aiPot += aiPot;
  }


  /**
   * Returns the name of the AI-player
   * 
   * @return returns the name of the ai-player
   */
  public String getName() {

    return name;
  }


  /**
   * Checks if ai-player is bigblind or not and if it is. It takes a amount from the ai's pot
   * 
   * @param bigBlind The amount the bigBlind is at, at this turn.
   * @param b if the ai-player is or isnt the bigBlind.
   */
  public void setBigBlind(int bigBlind, boolean b) {

    this.isBigBlind = b;
    if (bigBlind > 0) {
      System.out.println("AI " + name + " paid the big Blind (" + bigBlind + ")");
    }

    aiPot -= bigBlind;
    this.paidThisTurn += bigBlind;
  }


  /**
   * Checks if ai-player is smallBlind or not and if it is. it takes a amount from the ai's pot
   * 
   * @param smallBlind The amount the smallBlind is at, at this turn.
   * @param b if the ai-player is or isnt the smallBlind.
   */
  public void setSmallBlind(int smallBlind, boolean b) {

    this.isSmallBlind = b;
    if (smallBlind > 0) {
      System.out.println("AI " + name + " paid the small Blind (" + smallBlind + ")");
    }

    aiPot -= smallBlind;
    this.paidThisTurn += smallBlind;
  }


  /**
   * Returns if the the ai-player is or isnt the smallBlind
   * 
   * @return Returns if the the ai-player is or isnt the smallBlind
   */
  public boolean getIsSmallBlind() {

    return isSmallBlind;
  }


  /**
   * Returns if the the ai-player is or isnt the bigBlind
   * 
   * @return Returns if the the ai-player is or isnt the bigBlind
   */
  public boolean getIsBigBlind() {

    return isBigBlind;
  }


  /**
   * Returns how much the ai-player has paid this turn
   * 
   * @return Returns how much the ai-player has paid this turn
   */
  public int getPaidThisTurn() {

    return paidThisTurn;
  }


  /**
   * Sets how much the ai-player as already paid this turn
   * 
   * @param paidThisTurn Sets how much the ai-player as already paid this turn
   */
  public void setPaidThisTurn(int paidThisTurn) {

    this.paidThisTurn = paidThisTurn;
  }


  /**
   * sets if its the same turn or not.
   * 
   * @param sameTurn sets if it is the same turn or not.
   */
  public void setSameTurn(boolean sameTurn) {

    this.sameTurn = sameTurn;
  }


  /**
   * Returns the ai-players highest card
   * 
   * @return returns the ai-players highest card
   */
  public int getHighCard() {

    return highCard;
  }


  /**
   * returns the handstrength of the ai-player
   * 
   * @return returns the handstrength of the ai-player
   */
  public int handStrength() {

    return handStrength;
  }


  /**
   * returns if the ai-players viable for the currentpot.
   * 
   * @return returns if the ai-players viable for the currentpot.
   */
  public int getAllInViability() {

    return AllInViability;
  }


  /**
   * sets if the ai-players viable for the currentpot or not.
   * 
   * @param allInViability sets if the ai-players viable for the currentpot or not.
   */
  public void setAllInViability(int allInViability) {

    if (allInViability < AllInViability) {
      AllInViability = allInViability;
    } else {
      System.out.println("AI was already viable");
    }
  }

}

