package com.example.demo.hand;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * A test-class that has been used during developing and testing the whole hand-class.
 * @author Max Frennessen	
 * version 1.0
 * 17-05-25
 *
 */
public class testStraight {

	private String[] fromCards = new String[10];
	private String whatToDo;
	private ArrayList<String> finalHihglight = new ArrayList<String>();
	private ArrayList<String> nbrForStraight = new ArrayList<String>();
	private ArrayList<String> nbrForStraight1 = new ArrayList<String>();
	private ArrayList<String> aiCards = new ArrayList<String>();
	private ArrayList<Integer> cardNbr = new ArrayList<Integer>();
	private ArrayList<String> cardClr = new ArrayList<String>();

	public testStraight() {

		aiCards.add("5,h");
		aiCards.add("4,d");
		aiCards.add("6,h");
		aiCards.add("7,d");
		aiCards.add("8,h");
		aiCards.add("14,d");
		aiCards.add("9,h");

		getCardValues();
		int straight = testMethod();
		finalHihglight = getToHighlight();
		System.out.println("aiCards -  " + aiCards);
		System.out.println("cardNbr -  " + cardNbr);
		System.out.println("Straight -  " + straight);
		System.out.println("finalHihglight -  " + finalHihglight);

	}

	/**
	 * The containt of the method has been most method of the handCalculation-class
	 * and has been is this controlled enviroment 
	 * for testing a specifik method on its own. Last method that was tested what the checkStraight and its
	 * toHighlight -part.
	 * 
	 * @return returns a testresult.
	 */
	public int testMethod() {

		int threshold = 0;

		for (int i = 0; i < cardNbr.size(); i++) {
			if (cardNbr.get(i) == 14) {
				cardNbr.add(1);
			}
		}

		int[] CurrentCardsArray = new int[cardNbr.size()];

		for (int i = 0; i < cardNbr.size(); i++) {
			CurrentCardsArray[i] = cardNbr.get(i);
		}

		Arrays.sort(CurrentCardsArray);
		int inStraight = 0;
		int check = 4;

		for (int x = 0; x < CurrentCardsArray.length; x++) {
			int CurrentHighestInStraight = CurrentCardsArray[x] + check;
			int CurrentLowestInStraight = CurrentCardsArray[x];

			inStraight = 0;
	
			
			for(int i = 0; i<CurrentCardsArray.length; i++){
			  
				if(CurrentCardsArray[i]<=CurrentHighestInStraight && !(CurrentCardsArray[i]<CurrentLowestInStraight)){
						
					if(i==0){							//kollar om 0 är samma som 1.
				
							inStraight++;
							if(CurrentCardsArray[i]==1){
								nbrForStraight.add(String.valueOf(CurrentCardsArray[CurrentCardsArray.length-1]));
							}
							else{
								nbrForStraight.add(String.valueOf(CurrentCardsArray[i]));
							}
						
					}
					
					if(i>=1){							
						if(!(CurrentCardsArray[i]==CurrentCardsArray[i-1])){		//kollar om 1-4 är samma som nån annan.
							inStraight++;
							nbrForStraight.add(String.valueOf(CurrentCardsArray[i]));
						}
					}

				}
			}

			if (inStraight >= threshold) {

				threshold = inStraight;

				nbrForStraight1.clear();
				for (String a : nbrForStraight) {
					nbrForStraight1.add(a);
				}
			}
			nbrForStraight.clear();
		}

		return threshold;
	}
		
		
	public ArrayList<String> getToHighlight() {
		for (int y = 0; y < nbrForStraight1.size(); y++) {
			int same = 1;
			for (int i = 0; i < aiCards.size(); i++) {
				
				String temp = aiCards.get(i);
				String[] tempSplit = temp.split(",");
				if (nbrForStraight1.get(y).equals(tempSplit[0])) {
					if(same==1){
					finalHihglight.add(aiCards.get(i));
					same++;
					}
				}
			}
		}
		return finalHihglight;
	}
		
		public void getCardValues() {
			for (int i = 0; i < aiCards.size(); i++) { 		// CardNumber
				String temp = aiCards.get(i);
				String[] splitter = temp.split(",");
				int tempInt = Integer.parseInt(splitter[0]);
				cardNbr.add(tempInt);
			}

			for (int i = 0; i < aiCards.size(); i++) { 		// CardColor
				String temp = aiCards.get(i);
				String[] splitter = temp.split(",");
				String tempString = splitter[1];
				cardClr.add(tempString);
			}
		}
		
		public static void main(String [] args){
			testStraight run = new testStraight();
		}
	}

