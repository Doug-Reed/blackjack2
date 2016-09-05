import com.blackjack.Card;
import com.blackjack.Deck;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Created by admin on 9/5/16.
 */
public class TestDeck {

    @Test
    public void testDeckGeneration() {
        Deck deck = new Deck();
        //Replace the regular Random number generator with the Mock one
        deck.setRng(new MockRandomNumberGenerator(0));

        // Check that there are 52 cards in the deck
        Assert.assertEquals(52, deck.cardsLeft());

        Card dealFirstCard = deck.deal();
        Card expectedFirstCard = new Card(1, "H", Card.ACE);

        //Deck is filled H, C, S, D
        Assert.assertEquals(expectedFirstCard, dealFirstCard);

        Card dealSecondCard = deck.deal();  //Should be 2 of H
        Card expectedSecondCard = new Card(2, "H", Card.NOT_FACE_NOT_ACE);

        Assert.assertEquals(dealSecondCard, expectedSecondCard);


    }


    class MockRandomNumberGenerator extends Random {

        int theNextNumber;
        MockRandomNumberGenerator(int nextNumber) {
            theNextNumber = nextNumber;
        }

        public int nextInt(int cardsLeft){
            //Ignore cardsLeft
            return theNextNumber;
        }
    }

}
