package comp303.fivehundred.ai;

import static org.junit.Assert.*;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.AllCards;

import org.junit.Test;

/**
 * 
 * @author Noah Dayan
 *
 */
public class TestRandomPlayingStrategy
{
	@Test
	public void testPlay()
	{	
		RandomPlayingStrategy testRPS = new RandomPlayingStrategy();
		Trick testTrick = new Trick(new Bid(24));
		Hand aHand = new Hand();
		aHand.add(AllCards.aLJo);
		aHand.add(AllCards.a4S);
		assertFalse(testRPS.play(testTrick, aHand).isJoker());
		testTrick = new Trick(new Bid(0));
		Card testCard = testRPS.play(testTrick, aHand);
		assertTrue(testCard == (AllCards.a4S) || testCard == (AllCards.aLJo));
		testTrick.add(AllCards.a5S);
		aHand = new Hand();
		aHand.add(AllCards.a4S);
		aHand.add(AllCards.a6D);
		assertTrue(testRPS.play(testTrick, aHand) == AllCards.a4S);
		aHand = new Hand();
		aHand.add(AllCards.aLJo);
		aHand.add(AllCards.aAH);
		testCard = testRPS.play(testTrick, aHand);
		assertTrue(testCard == (AllCards.aAH) || testCard == (AllCards.aLJo));
	}
}
