import com.blackjack.Card;
import com.blackjack.Deck;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class TestDeck {

    @Test
    public void testDeckGeneration() {
        Deck deck = new Deck();

        //Replace the regular Random number generator with the Mock one
        MockRandomNumberGenerator mockRng = new MockRandomNumberGenerator(0);
        deck.setRng(mockRng);

        // Check that there are 52 cards in the deck
        Assert.assertEquals(52, deck.cardsLeft());

        Card dealFirstCard = deck.deal();
        Card expectedFirstCard = new Card(1, Card.Suit.HEARTS, Card.ACE);

        //Deck is filled H, C, S, D
        Assert.assertEquals(dealFirstCard, expectedFirstCard);
        //And now there are 51 cards in the deck
        Assert.assertEquals(51, deck.cardsLeft());


        Card dealSecondCard = deck.deal();  //Should be 2 of H
        Card expectedSecondCard = new Card(2, Card.Suit.HEARTS);
        //And now there are 50 cards in the deck
        Assert.assertEquals(50, deck.cardsLeft());
        Assert.assertEquals(expectedSecondCard, dealSecondCard);

        //So now the 20th card (after 2 have been dealt) should be 9 of Clubs
        //Use mock rng to force 20th card
        mockRng.setTheNextNumber(19);

        Card deal20Card = deck.deal();  //Should be 9 of C
        Card expected20Card = new Card(9, Card.Suit.SPADES);
        //And now there are 49 cards in the deck
        Assert.assertEquals(49, deck.cardsLeft());
        Assert.assertEquals(expected20Card, deal20Card);

    }


    class MockRandomNumberGenerator extends Random {

        int theNextNumber;
        MockRandomNumberGenerator(int nextNumber) {
            theNextNumber = nextNumber;
        }

        void setTheNextNumber(int number) {
            theNextNumber = number;
        }
        public int nextInt(int cardsLeft){
            //Ignore cardsLeft to force the rng to generate
            return theNextNumber;
        }
    }

}
