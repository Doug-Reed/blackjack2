package com.blackjack;

public class Card {

	Suit suit;
	int value;
	String specialTypeOfCard;
	
	public final static String NOT_FACE_NOT_ACE = "not a face card";
	public final static String ACE = "ace";
	public final static String JACK = "jack";
	public final static String QUEEN = "queen";
	public final static String KING = "king";


	public enum Suit {
		HEARTS("h"), SPADES("s"), CLUBS("c"), DIAMONDS("d");
		private String value;
		private Suit(String value) {
			this.value = value;
		}
	}


	// specialTypeOfCard represents a card whose value is not the same as the face value
	// So Ace, J Q, and K
	public Card(int v, Suit s, String special){
		this.value = v;
		this.suit = s;
		this.specialTypeOfCard = special;
		
	}

	//For creating cards in range 2 - 10 - these have values equivalent to face value
	public Card(int v, Suit s){
		this.value = v;
		this.suit = s;
		this.specialTypeOfCard = NOT_FACE_NOT_ACE;
		
	}

	public Suit getSuit() {
		return this.suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public int getMinValue() {
		return value;
	}

	public int getMaxValue() {
		if (this.specialTypeOfCard.equals(ACE)){
			return 11;
		} else {
			return this.value;
		}
	}
	

	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString(){
		
		if (specialTypeOfCard.equals(ACE)) {
			return ("A"+this.suit);
			
		} else if (specialTypeOfCard.equals(JACK)){
			return ("J"+this.suit);
		
		} else if (specialTypeOfCard.equals(QUEEN)){
		return ("Q"+this.suit);
		
		} else if (specialTypeOfCard.equals(KING)){
			return ("K"+this.suit);
		}

		return (this.value + "" + this.suit);
		
		
	}

	public boolean isAce() {
		return this.specialTypeOfCard.equals(ACE);
	}


	@Override
	public boolean equals(Object other) {

		Card anotherCard;

		try {
			anotherCard = (Card) other;
		} catch (ClassCastException cce) {
			return false;
		}

		if (this.value == anotherCard.value &&
				this.suit.equals(anotherCard.suit) &&
				this.specialTypeOfCard.equals(anotherCard.specialTypeOfCard)) {
			return true;
		}

		return false;
	}


}
