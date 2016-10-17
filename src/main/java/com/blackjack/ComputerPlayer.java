package com.blackjack;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name) {
		super(name);
	}

	public final int SENSIBLE_TARGET = 17;

	/*
	* Three cases:
	*
	* There are other players left to play
	* 	- Take cards until score is higher than (Max of other players OR 17, whichever is larger)
	*
	* This player is the last player to play and everyone else is bust
	*   - Win by default. Don't play.
	*
	* This player is the last player to play and at least one other player is still in the game
	* 	- Play until sensible target, stop at 17 (or if goes bust)
	*
	*/


	public void playHand(Deck cards){

		Game.ui.output(name + " has " + handOfCards + " ( totals " + handOfCards.getScoreClosestTo21() + " )");
		Game.ui.output(name + " is thinking....");

		while (shouldContinue()) {
			//take another card
			handOfCards.addCard(cards.deal());
			Game.ui.output(name + " takes another card and now has " + handOfCards + " ( totals " + handOfCards.getScoreClosestTo21() + " )");
		}

		this.hasPlayed = true;
		Game.ui.output(this.name + "'s turn is over\n");
	}

	private boolean shouldContinue() {

		int thisPlayerScore = handOfCards.getScoreClosestTo21();
		int playersLeftToPlay = manager.getPlayersLeftToPlay();
		int maxScoreBySomeoneElse = manager.getMaxScore();

		if (handOfCards.getScoreClosestTo21() == Game.TARGET_SCORE) {
			Game.ui.output("21 - max score!");
			return false;
		}

		if (handOfCards.isBust()) {
			Game.ui.output("Goes bust!");
			return false;
		}

		if (handOfCards.size() == Game.MAX_CARDS_IN_HAND) {
			Game.ui.output("I've got 5 cards, can't take another");
			return false;
		}

		//If dealer, must play until at least 17
		if (isDealer && thisPlayerScore < Game.DEALER_MUST_HIT_UNTIL) {
			Game.ui.output("I am the dealer and score less than 21, must take another card");
			return true;
		}


		/* Three cases:
		*
		* This player is the last player to play and everyone else is bust
		*   - Win by default. Don't play.
		*
		* There are other players left to play
		* 	- Take cards until score is higher than (Max of other players OR 17, whichever is larger)
		*
		* This player is the last player to play and at least one other player is still in the game
		* 	- Play until sensible target, stop at 17 (or if goes bust)
		*
		*/


		// If there are other players left to play after this one
		//If this player's score is less or equal to other's max (or 17 sensible target) return true to keep playing

		if (playersLeftToPlay > 1) {
			int targetScore = Math.max(SENSIBLE_TARGET, maxScoreBySomeoneElse);
			if (thisPlayerScore <= targetScore) {
				Game.ui.output("my score less than or equal to the target " + targetScore);
				return true;
			}
			Game.ui.output("my score is more than my target of " + targetScore);
			return false;
		}

		// If this player is the last one to play. Consider other player's hands.
		else {

			if (manager.everyoneElseBust(this)) {
				Game.ui.output("Everyone else is bust. No need to play!");
				return false;
			}

			if (thisPlayerScore > maxScoreBySomeoneElse) {
				Game.ui.output("I'm last and my score is more than everyone else's ");
				return false;
			}

			else {
				//need to beat maxScoreBySomeoneElse
				Game.ui.output("I'm last but someone else has a higher score than me");
				return true;
			}
		}
	}
}
