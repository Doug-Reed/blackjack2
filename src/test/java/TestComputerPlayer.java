import com.blackjack.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

/*TODO finish tests!*/

import static junit.framework.TestCase.assertEquals;

public class TestComputerPlayer {

    ComputerPlayer linux;
    ComputerPlayer mac;
    ComputerPlayer pc;

    PlayerManager manager;

    Card fourHearts = new Card(4, Card.Suit.HEARTS);
    Card fiveHearts = new Card(5, Card.Suit.HEARTS);
    Card sixHearts = new Card(6, Card.Suit.HEARTS);
    Card sevenHearts = new Card(7, Card.Suit.HEARTS);
    Card kingHearts = new Card(10, Card.Suit.HEARTS, Card.KING);

    Card threeClubs = new Card(3, Card.Suit.CLUBS);
    Card tenClubs = new Card(10, Card.Suit.CLUBS);

    Card aceSpaces = new Card(1, Card.Suit.SPADES, Card.ACE);
    Card twoSpades = new Card(2, Card.Suit.SPADES);

    Card aceDiamonds = new Card(1, Card.Suit.DIAMONDS, Card.ACE);
    Card fourDiamonds = new Card(4, Card.Suit.DIAMONDS);


    Card mockCard = new Card(10, Card.Suit.DIAMONDS);

    class MockDeck extends Deck {

        LinkedList<Card> mockCards = new LinkedList<Card>();

        public Card deal() {
            return mockCards.removeFirst();
        }

        public void addNextCard(Card c){
            mockCards.addLast(c);
        }

    }


    @Before
    public void setUpUI() {
        Game.createUI();
    }

    @Before
    public void createComputerPlayers() {
        linux = new ComputerPlayer("linux");
        mac = new ComputerPlayer("mac");
        pc = new ComputerPlayer("pc");

        manager = new PlayerManager();

        manager.add(linux);
        manager.add(mac);
        manager.add(pc);

    }



    //Create two players

    //Start round

        		/* Three cases:
		*
		* 1.  There are other players left to play
		*    	- Take cards until score is higher than (Max of other players OR 17, whichever is larger)


		* 2.  This player is the last player to play and everyone else is bust
		*        - Win by default. Don't play.
		*
		*
		* 3.  This player is the last player to play and at least one other player is still in the game
		* 	- Play until sensible target, stop at 17 (or if goes bust)
		*
		*/


    @Test
    public void testStrategyComputerPlayersFirstPlayerToPlay() {


        MockDeck mockDeck = new MockDeck();
        mockDeck.addNextCard(threeClubs);
        mockDeck.addNextCard(tenClubs);

        Hand linuxHand = new Hand();
        linuxHand.addCard(twoSpades);
        linuxHand.addCard(fiveHearts);    //Score = 7

        linux.setHand(linuxHand);

        Hand pcHand = new Hand();
        pcHand.addCard(sevenHearts);
        pcHand.addCard(kingHearts);      // score = 17
        pc.setHand(pcHand);

        linux.playHand(mockDeck);     // Linux should take the two mock cards, total score 20

        //Verify Linux took two cards

        //Assert.assertTrue(linux.getHandOfCards().toString().contains(mockCardString));
        assertEquals(linux.getHandOfCards().size(), 4);

        //Add some more cards to MockDeck
        mockDeck.addNextCard(twoSpades);     //score will be 19...
        mockDeck.addNextCard(kingHearts);      // and bust

        pc.playHand(mockDeck);       // PC should try to beat Linux by taking another card, then another

        Assert.assertTrue(pc.getHandOfCards().isBust());


    }


    @Test
    public void testPlayGameOneHumanOneComputerPlayer() {

        //Create two players

        //Assert PlayerManger has two players

        //Assert they both have two cards

        //Assert the deck has 48 cards

        //Start round

    }


    @Test
    public void testPlayGameTwoHumanPlayers() {

        //Create two players

        //Assert PlayerManger has two players

        //Assert they both have two cards

        //Assert the deck has 48 cards

        //Start round

    }


}
