package com.blackjack;

/* To do list

TODO Getting 5 cards beats other hands of the same value

TODO Add functionality to add as many players as needed, ask for names.

TODO Tests for ComputerPlayer strategy

TODO TESTS!! mocking input for HumanPlayer objects

TODO clean up code. Organize methods, add comments, remove magic numbers
*/


public class Game {

	final static int MAX_CARDS_IN_HAND = 5; //variable in Hand and Player
	final static int TARGET_SCORE = 21;
	final static int DEALER_MUST_HIT_UNTIL = 17;

	static UI ui;

	public static void main(String[] args) {

		createUI();

		Game game = new Game();
		game.play();

		UI.close();

	}

	public static void createUI(){
		ui = new UI();
	}

	private void play() {

		//Can modify this to add more human players and computer players if needed

		PlayerManager playerManager = new PlayerManager();

		ComputerPlayer mac = new ComputerPlayer("Mac");
		//HumanPlayer human = new HumanPlayer("Clara");      //TODO ask the user for their name
		//HumanPlayer human2 = new HumanPlayer("Andy");
		ComputerPlayer linux = new ComputerPlayer("Linux");
		ComputerPlayer pc = new ComputerPlayer("PC");

		playerManager.add(mac);
		playerManager.add(linux);
		playerManager.add(pc);

//		playerManager.add(human);      // If you want to play too, uncomment this
//		playerManager.add(human2);


		boolean playAgain = true;
		String anotherRound;

		while (playAgain){
			
			Round round = new Round(playerManager);
			round.play();
			playerManager.printWins();
			
			anotherRound = ui.input("Another hand? n to quit, anything else to continue... ");

			playAgain = !anotherRound.equals("n");   // another.equals("n") returns a boolean
			
			//rotate the dealer, so everyone gets turns to be the dealer.
			playerManager.rotateDealer();
			
		}
		
		playerManager.printFinalWins();

	}

}
