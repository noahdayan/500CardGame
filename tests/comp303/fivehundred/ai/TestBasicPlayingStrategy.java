package comp303.fivehundred.ai;

import static org.junit.Assert.*;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card;

import org.junit.Test;

/**
 * 
 * @author Noah Dayan
 *
 */
public class TestBasicPlayingStrategy
{
	@Test
	public void testPlay()
	{
		BasicPlayingStrategy testBPS = new BasicPlayingStrategy();
		Trick testTrick = new Trick(new Bid(24));
		Hand aHand = new Hand();
		aHand.add(AllCards.aLJo);
		aHand.add(AllCards.aHJo);
		assertTrue(testBPS.play(testTrick, aHand).isJoker());
		aHand = new Hand();
		aHand.add(AllCards.aLJo);
		aHand.add(AllCards.a4S);
		assertFalse(testBPS.play(testTrick, aHand).isJoker());
		aHand = new Hand();
		aHand.add(AllCards.aLJo);
		assertEquals(testBPS.play(testTrick, aHand),AllCards.aLJo);
		aHand.add(AllCards.a4S);
		testTrick = new Trick(new Bid(1));
		Card testCard = testBPS.play(testTrick, aHand);
		assertTrue(testCard == (AllCards.a4S) || testCard == (AllCards.aLJo));
		testTrick.add(AllCards.a5S);
		aHand.add(AllCards.a6S);
		aHand.add(AllCards.a8S);
		aHand.add(AllCards.aKD);
		assertEquals(testBPS.play(testTrick, aHand),AllCards.a6S);
		testTrick.add(AllCards.a9S);
		aHand.remove(AllCards.aLJo);
		assertEquals(testBPS.play(testTrick, aHand),AllCards.a4S);
		aHand = new Hand();
		aHand.add(AllCards.a4C);
		aHand.add(AllCards.aQD);
		assertEquals(testBPS.play(testTrick, aHand),AllCards.a4C);
		testTrick.add(AllCards.a7C);
		aHand.add(AllCards.a4H);
		assertEquals(testBPS.play(testTrick, aHand),AllCards.a4H);
		aHand.remove(AllCards.a4C);
		assertEquals(testBPS.play(testTrick, aHand),AllCards.a4H);
		testTrick.add(AllCards.aLJo);
		assertEquals(testBPS.play(testTrick, aHand),AllCards.a4H);
		aHand.add(AllCards.aHJo);
		assertEquals(testBPS.play(testTrick, aHand),AllCards.aHJo);
	}
}
