import com.blackjack.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by admin on 9/5/16.
 */
public class TestPlayerManager {

    HumanPlayer testPlayerAbby;
    HumanPlayer testPlayerBob;
    HumanPlayer testPlayerCat;
    HumanPlayer testPlayerDave;



    @Before
    public void setUp() {
        testPlayerAbby = new HumanPlayer("Abby");
        testPlayerBob = new HumanPlayer("Bob");
        testPlayerCat = new HumanPlayer("Cat");
        testPlayerDave = new HumanPlayer("Dave");

    }

    @Test
    public void testDealer() {

        PlayerManager manager = new PlayerManager();

        manager.add(testPlayerAbby);
        manager.add(testPlayerBob);

        //Check we've got 2 players
        Assert.assertEquals(manager.getPlayersList().size(), 2);

        //Bob should be the dealer
        Assert.assertEquals(manager.getDealer(), testPlayerBob);
        //And there's only one dealer
        Assert.assertEquals(countDealers(manager), 1);

        //Add another player
        manager.add(testPlayerCat);

        //Check we've got 3 players
        Assert.assertEquals(manager.getPlayersList().size(), 3);
        //Now Cat is the dealer
        Assert.assertEquals(manager.getDealer(), testPlayerCat);
        //And there's only one dealer
        Assert.assertEquals(countDealers(manager), 1);

        //Rotate dealer - Bob should be the dealer
        manager.rotateDealer();
        Assert.assertEquals(manager.getDealer(), testPlayerBob);
        //And there's only one dealer
        Assert.assertEquals(countDealers(manager), 1);


        //Rotate dealer - Abby should be the dealer
        manager.rotateDealer();
        Assert.assertEquals(manager.getDealer(), testPlayerAbby);
        //And there's still only one dealer
        Assert.assertEquals(countDealers(manager), 1);

    }


    @Test
    public void testGetPlayersLeftToPlay(){

        PlayerManager manager = new PlayerManager();
        manager.add(testPlayerAbby);
        manager.add(testPlayerBob);
        manager.add(testPlayerCat);
        manager.add(testPlayerDave);

        Assert.assertEquals(manager.getPlayersLeftToPlay(), 4);

        //Abby plays, so 3 players left
        testPlayerAbby.setHasPlayed(true);
        Assert.assertEquals(manager.getPlayersLeftToPlay(), 3);

        //Bob plays, so 2 players left
        testPlayerBob.setHasPlayed(true);
        Assert.assertEquals(manager.getPlayersLeftToPlay(), 2);

        //Cat plays, so 1 players left
        testPlayerCat.setHasPlayed(true);
        Assert.assertEquals(manager.getPlayersLeftToPlay(), 1);

        //Dave plays, so 0 players left
        testPlayerDave.setHasPlayed(true);
        Assert.assertEquals(manager.getPlayersLeftToPlay(), 0);

        //Cat hasn't played
        testPlayerCat.setHasPlayed(false);
        Assert.assertEquals(manager.getPlayersLeftToPlay(), 1);

    }

    @Test
    public void testGetMaxScore(){

        //test cards

        Card fourHearts = new Card(4, Card.Suit.HEARTS);
        Card sevenHearts = new Card(7, Card.Suit.HEARTS);
        Card threeClubs = new Card(3, Card.Suit.CLUBS);
        Card kingHearts = new Card(10, Card.Suit.HEARTS, Card.KING);
        Card aceSpaces = new Card(1, Card.Suit.SPADES, Card.ACE);
        Card aceDiamonds = new Card(1, Card.Suit.DIAMONDS, Card.ACE);
        Card twoSpades = new Card(2, Card.Suit.SPADES);

        PlayerManager manager = new PlayerManager();

        manager.add(testPlayerAbby);
        manager.add(testPlayerBob);


        Hand abbyHand = new Hand();
        abbyHand.addCard(fourHearts);
        abbyHand.addCard(sevenHearts);
        abbyHand.addCard(threeClubs);    //Abby's hand scores 14
        testPlayerAbby.setHand(abbyHand);

        Hand bobHand = new Hand();
        bobHand.addCard(aceDiamonds);
        bobHand.addCard(twoSpades);     // Bob's hand is 13
        testPlayerBob.setHand(bobHand);

        //Before anyone has played, their score is 0
        Assert.assertEquals(manager.getMaxScore(), 0);

        //Abby plays
        testPlayerAbby.setHasPlayed(true);
        Assert.assertEquals(manager.getMaxScore(), 14);

        //And Bob plays
        testPlayerBob.setHasPlayed(true);
        Assert.assertEquals(manager.getMaxScore(), 14);


        //What if Abby hasn't played? Bob's score should be max
        testPlayerAbby.setHasPlayed(false);
        Assert.assertEquals(manager.getMaxScore(), 13);

        //Add another player

        manager.add(testPlayerCat);

        Hand catHand = new Hand();
        catHand.addCard(kingHearts);
        catHand.addCard(aceSpaces);     //Cat's hand is 21
        testPlayerCat.setHand(catHand);

        testPlayerCat.setHasPlayed(true);

        Assert.assertEquals(manager.getMaxScore(), 21);

    }


    @Test
    public void testEveryoneElseBust() {


        PlayerManager manager = new PlayerManager();
        manager.add(testPlayerAbby);
        manager.add(testPlayerBob);
        manager.add(testPlayerCat);
        manager.add(testPlayerDave);

        testPlayerAbby.setHand(new Hand());  //Blank hands...
        testPlayerAbby.getHandOfCards().setBust();

        testPlayerBob.setHand(new Hand());  //Blank hands...
        testPlayerBob.getHandOfCards().setBust();

        testPlayerCat.setHand(new Hand());  //Blank hands...
        //Cat not bust...

        testPlayerDave.setHand(new Hand());  //Blank hands...
        testPlayerDave.getHandOfCards().setBust();

        Assert.assertTrue(manager.everyoneElseBust(testPlayerCat));
        Assert.assertFalse(manager.everyoneElseBust(testPlayerAbby));
        Assert.assertFalse(manager.everyoneElseBust(testPlayerBob));
        Assert.assertFalse(manager.everyoneElseBust(testPlayerDave));

    }

    @Test
    public void determineRoundWinner() {

        //Player with the highest score should win
        //In the event of a tie: the dealer is the winner
        //A tie between non-dealers - player with 5 cards is the winner

        //1. One player wins outright

        Card fourHearts = new Card(4, Card.Suit.HEARTS);
        Card sevenHearts = new Card(7, Card.Suit.HEARTS);
        Card threeClubs = new Card(3, Card.Suit.CLUBS);
        Card kingHearts = new Card(10, Card.Suit.HEARTS, Card.KING);
        Card aceSpaces = new Card(1, Card.Suit.SPADES, Card.ACE);
        Card aceDiamonds = new Card(1, Card.Suit.DIAMONDS, Card.ACE);
        Card twoSpades = new Card(2, Card.Suit.SPADES);
        Card tenHearts = new Card(10, Card.Suit.HEARTS);
        Card aceClubs = new Card(1, Card.Suit.CLUBS, Card.ACE);

        PlayerManager manager = new PlayerManager();

        manager.add(testPlayerAbby);
        manager.add(testPlayerBob);

        Hand abbyHand = new Hand();
        abbyHand.addCard(fourHearts);
        abbyHand.addCard(sevenHearts);
        abbyHand.addCard(threeClubs);    //Abby's hand scores 14
        testPlayerAbby.setHand(abbyHand);

        Hand bobHand = new Hand();
        bobHand.addCard(aceDiamonds);
        bobHand.addCard(twoSpades);     // Bob's hand is 13
        testPlayerBob.setHand(bobHand);


        //Abby is the winner

        Assert.assertEquals(manager.determineRoundWinner(), testPlayerAbby);

        //Add another player

        manager.add(testPlayerCat);

        Hand catHand = new Hand();
        catHand.addCard(kingHearts);
        catHand.addCard(aceSpaces);     //Cat's hand is 21
        testPlayerCat.setHand(catHand);

        testPlayerCat.setHasPlayed(true);

        Assert.assertEquals(manager.determineRoundWinner(), testPlayerCat);

        // 2. Check that the dealer wins in event of a tie

        Hand daveHand = new Hand();
        daveHand.addCard(tenHearts);
        daveHand.addCard(aceClubs);

        manager.add(testPlayerDave);    //Since dave is added last, he's the dealer
        testPlayerDave.setHand(daveHand);     //Dave's hand is also 21, but is the dealer so wins

        Assert.assertEquals(manager.determineRoundWinner(), testPlayerDave);

        //Since the method we are testing isn't finished, add these fail tests so we remember to finish it
        Assert.fail("5 cards equal score should win");
        Assert.fail("21 with Ace and K, Q or J beats 21 with 3 or more cards");
        Assert.fail("No way to check for ties");

    }


    private int countDealers(PlayerManager manager) {
        //Get all players and check only one is the dealer

        LinkedList<Player> allPlayers = manager.getPlayersList();

        int dealerCount = 0;

        for (Player p : allPlayers) {
            if (p.isDealer()) {
                dealerCount++;
            }
        }

        return dealerCount;

    }


}
