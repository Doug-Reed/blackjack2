package com.blackjack;


public abstract class Player {

	int wins = 0;
	String name;
	Hand handOfCards;
	PlayerManager manager;
	boolean hasPlayed = false;   //Has played this round
	boolean isDealer = false;


	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}
	public void setHasPlayed(boolean hasPlayed) {this.hasPlayed = hasPlayed; }


	Player(String name){
		this.name=name;
	}

	Player(String name, PlayerManager manager){
		this.name=name;
		this.manager=manager;
	}

	public void setManager(PlayerManager manager) {
		this.manager = manager;
	}


	//Insist that subclasses implement this method.
	public abstract void playHand(Deck allCards);
	
	public void wonRound(){
		wins+=1;
	}
	
	public String getName() {
		return name;
	}


	public Hand getHandOfCards() {
		return handOfCards;
	}


	public void setHand(Hand handOfCards) {
		this.handOfCards = handOfCards;
	}

	public int getWins() {
		return wins;
	}

	@Override
	public String toString(){
		return name;
	}



}
