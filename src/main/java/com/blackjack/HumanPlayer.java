package com.blackjack;


public class HumanPlayer extends Player {

	public HumanPlayer(String name) {
		super(name);
	}

	
	public void playHand(Deck cards) {

		//Display hand
		//hit (take another card) or stand (don't want any more cards)? 

		Game.ui.output(this.name + " is dealt " + handOfCards + " ( totals " + handOfCards.getScoreClosestTo21() + " )");
	
		boolean wantMoreCards = true ;
		int numberOfCards;

		while(wantMoreCards) {

			String hit = Game.ui.input("hit or stand? h to hit; s to stand");

			if (hit.equals("h")) {
				//get card
				//bust? 
				//numberOfCards = handOfCards.addCard(cards.deal());
				handOfCards.addCard(cards.deal());
				numberOfCards = handOfCards.size();
				Game.ui.output(this.name + " now has " + handOfCards + " ( totals " + handOfCards.getScoreClosestTo21() + " )");
				boolean bust = handOfCards.isBust();
				if (bust) {
					Game.ui.output(this.name + " went bust ");
					handOfCards.setBust();
					break;
				}
				if (numberOfCards == Game.MAX_CARDS_IN_HAND){
					break;
				}
			}

			else {
				//Assume anything other than h is stand. Turn done, return. Round can look at the cards and figure out the score. 
				wantMoreCards = false;
			}

		}



	}

}
