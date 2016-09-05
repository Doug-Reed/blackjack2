package com.blackjack;

import java.util.LinkedList;

public class PlayerManager {

	LinkedList<Player> players;

	public final int MAX_PLAYERS = 10; //for a single deck, anyway


	public PlayerManager(LinkedList<Player> players){
		this.players = players;

		//only take the first 10 players
		while (players.size() > MAX_PLAYERS) {
			System.out.println("More than 10 players, removing players from end of list");
			System.out.println(players.removeLast().getName());
		}
		setDealer();
	}

	public PlayerManager() {
		this.players = new LinkedList<Player>();
	}


	public void add(Player player) {
		//refuse to add more than 10 players

		if (players.size() < MAX_PLAYERS) {
			player.setManager(this);
			players.add(player);
			setDealer();
		} else {
			Game.ui.output("There's already " + MAX_PLAYERS + " in the game. Can't add " + player.getName());
		}
	}

	private void setDealer() {
		//Make the last player in the list the dealer
		players.getLast().setDealer(true);
	}



	public int getPlayersLeftToPlay() {

		int playersLeft = 0;

		for (Player p : players) {
			if (p.hasPlayed) {
				playersLeft++;
			}
		}

		return playersLeft;

	}

	//
	public int[] getScoreOfPlayersWhoHavePlayed() {
//		LinkedList<Player> theOtherPlayers = (LinkedList)players.clone();
//		theOtherPlayers.remove(butNotThisPlayer);
//		int[] otherScores = new int[theOtherPlayers.size()];
//		for (int p = 0 ; p < theOtherPlayers.size() ; p++) {
//			otherScores[p] = theOtherPlayers.get(p).getHandOfCards().getScoreClosestTo21();
//		}

		LinkedList<Player> havePlayed = new LinkedList();
		for (Player p : players) {
			if (p.hasPlayed) {
				havePlayed.add(p);
			}
		}

		int[] otherScores = new int[havePlayed.size()];

		for (int p = 0 ; p < havePlayed.size() ; p++) {
			otherScores[p] = havePlayed.get(p).getHandOfCards().getScoreClosestTo21();
		}

		return otherScores;
	}


	//
	public int getMaxScore() {

		int max = 0;
		for (Player p : players) {
			int score = p.getHandOfCards().getScoreClosestTo21();
			if (score > max) {
				max = score;
			}
		}

		return max;

	}


	public boolean everyoneElseBust(Player exceptThisPlayer){

		LinkedList<Player> theOtherPlayers = (LinkedList)players.clone();
		theOtherPlayers.remove(exceptThisPlayer);

		for (Player p : theOtherPlayers) {
			if (!p.getHandOfCards().isBust()) {
				return false;
			}
		}

		return true;

	}


	public void printWins() {

		for (Player p : players){
			int wins = p.getWins();
			System.out.println(p.getName() + " won " + wins + " times");
		}
	}


	public Player getDealer() {
		for (Player p : players) {
			if (p.isDealer()) {
				return p;
			}
		}
		return null;
	}


	/* Enable all players to have a chance at being the dealer, in turn */
	// TODO moves dealer from end of list to start.
	public void rotateDealer() {
	
		Player lastDealer = players.removeLast();  //remove last player,
		lastDealer.setDealer(false);
		players.addFirst(lastDealer);              //add them at the beginning.
		//set last player to be dealer
		setDealer();
	}

	public void printFinalWins() {

		int mostWins= 0;
		String winnerName = "Computer";
		for (Player p : players){
			int wins = p.getWins();
			System.out.println(p.getName() + " won " + wins + " times");
			if (wins > mostWins) {
				mostWins = wins;
				winnerName = p.getName();
			}
		}
		System.out.println("The winner is " + winnerName);
		
	}
	

	Player determineRoundWinner() {

		boolean everyoneBust = true;

		int maxScore = 0;

		Player winner = players.get(0);  //Assume first player is winner.. unless someone else is

		for (Player p : players) {
			//If there is a tie, the dealer wins
			//The dealer is the LAST players in allPlayers
			//So if we use >= then the dealer's will overwrite the other player
			//TODO make better use of isDealer flag

			int playerScore = p.getHandOfCards().getScoreClosestTo21();

			if (playerScore > 0) {
				//This player did not go bust
				everyoneBust = false;
			}

			if (playerScore >= maxScore) {
				maxScore = playerScore;
				winner = p;
			}

		}

		if (everyoneBust) {
			return null;
		}

		return winner;

	}

		public LinkedList<Player> getPlayersList() {
			return players;
		}
}
