package com.blackjack;

import java.util.Arrays;

public class ComputerPlayer extends Player {

	ComputerPlayer(String name) {
		super(name);
		
	}


	/* TODO The logic here:
	* Examine other hands for the players who have already played.
	*
Last player should not bother taking another card if everyone else
is bust since they will win by default

If a computer has over 17, it won't take another card. Maybe if computer has
(e.g.) 18 and other player haves 20, should still take card just in case.   */


	public void playHand(Deck cards){
		


		this.hasPlayed = true;


		Game.ui.output(name + " has " + handOfCards + " ( totals " + handOfCards.getScoreClosestTo21() + " )");
		Game.ui.output(name + " is thinking....");
		
		//boolean bust = false;
		//int numberOfCards = 2;

		while (shouldContinue()) {
		//while (this.handOfCards.getScoreClosestTo21() < 17) {
			//take another card
			handOfCards.addCard(cards.deal());

			Game.ui.output(name + " takes another card and now has " + handOfCards + " ( totals " + handOfCards.getScoreClosestTo21() + " )");

//			bust = handOfCards.isBust();
//
//			if (bust){
//				System.out.println(name + " goes bust, score = 0");
//				handOfCards.setBust();
//				break;
//			}

		}

		Game.ui.output(this.name + "'s turn is over\n");
		
	}

	private boolean shouldContinue() {

		if (handOfCards.isBust()) {
			Game.ui.output("Goes bust!");
			return false;
		}

		if (handOfCards.size() == 5) {
			Game.ui.output("I've got 5 cards, can't take another");
			return false;
		}

		if (manager.everyoneElseBust(this)) {
			Game.ui.output("Everyone else is bust. No need to play!");
			return false;
		}


		int[] otherScores = manager.getScoreOfPlayersWhoHavePlayed();
		Game.ui.output(Arrays.toString(otherScores));

		int startScore = handOfCards.getScoreClosestTo21();

		int playersLeftToPlay = manager.getPlayersLeftToPlay();

		int maxScoreBySomeoneElse = manager.getMaxScore();
		//If no-one else left to play, try to beat other players max

		//If this is the last player....

		if (playersLeftToPlay == 1 && maxScoreBySomeoneElse < startScore) {
			Game.ui.output("My score is higher than everyone else's, no need to play.");
			return false;
		}


		//If dealer, must play until at least 17

		if (isDealer && startScore < 17) {
			return true;
		}


		//Other players still in game with valid scores

		if (playersLeftToPlay > 1  && startScore < 17) {
			return true;
		}

		if (startScore > 17) {
			return false;
		}


		return true;

	}
	

}
