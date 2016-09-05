package com.blackjack;


public abstract class Player {

	int wins = 0;
	String name;
	Hand handOfCards;
	PlayerManager manager;
	boolean hasPlayed = false;   //Has played this round
	boolean isDealer = false;

	Player(String name){
		this.name=name;
	}

	Player(String name, PlayerManager manager){
		this.name=name;
		this.manager=manager;
	}

	//Insist that subclasses implement this method.
	public abstract void playHand(Deck allCards);

	public void wonRound(){
		wins+=1;
	}

	//Getters and setters...
	public void setManager(PlayerManager manager) {
		this.manager = manager;
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
	public boolean isDealer() {
		return isDealer;
	}
	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}
	public void setHasPlayed(boolean hasPlayed) {this.hasPlayed = hasPlayed; }

	@Override
	public String toString(){
		String dealer = (isDealer) ? "* is current dealer *" : "(is not dealer)";
		return name + " " + dealer;
	}



}
