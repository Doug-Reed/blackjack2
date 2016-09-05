package com.blackjack;

public class Round {

	//This class manages one specific round. Deals cards, then gives each player a turn, and decides who won that round
	
	Deck deck;
	
	PlayerManager playerManager;
	
	Round(PlayerManager playerManager){
	
		//Create deck and shuffle. No card counting :)
		deck = new Deck();

		this.playerManager = playerManager;

	}


	public void play(){

		Game.ui.output("New round. The dealer is " + playerManager.getDealer());

		//deal hands to computer and players
	

		for (Player p : playerManager.getPlayersList()){

			Hand hand = new Hand();
			hand.addCard(deck.deal());
			hand.addCard(deck.deal());
			p.setHand(hand);
			
		}
		

		//Abstract class Player insists that all subclasses implement playHand(Deck cards)
		for (Player p : playerManager.getPlayersList()){
			p.playHand(deck);
		}
		
		
		Player winner = playerManager.determineRoundWinner();
		if (winner != null) {
			winner.wonRound();
			Game.ui.output("******************** " + winner.getName() + " wins! ******************** ");
		}
		else {
			Game.ui.output("******************** Everyone went bust ********************");
		}

		//If there's no winner, no-one gets more points.
		
	}

	}
