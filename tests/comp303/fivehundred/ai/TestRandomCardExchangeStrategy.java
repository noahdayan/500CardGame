package comp303.fivehundred.ai;

import static org.junit.Assert.*;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.AllCards;

import org.junit.Test;

/**
 * 
 * @author Noah Dayan
 *
 */
public class TestRandomCardExchangeStrategy
{
	@Test
	public void testExchange()
	{	
		RandomCardExchangeStrategy testRCES = new RandomCardExchangeStrategy();
		Hand aHand = new Hand();
		aHand.add(AllCards.a4C);
		aHand.add(AllCards.a4D);
		aHand.add(AllCards.a4H);
		aHand.add(AllCards.a4S);
		aHand.add(AllCards.a5C);
		aHand.add(AllCards.a5D);
		aHand.add(AllCards.a5H);
		aHand.add(AllCards.a5S);
		aHand.add(AllCards.a6C);
		aHand.add(AllCards.a6D);
		aHand.add(AllCards.a6H);
		aHand.add(AllCards.a6S);
		aHand.add(AllCards.a7C);
		aHand.add(AllCards.a7D);
		aHand.add(AllCards.a7H);
		aHand.add(AllCards.a7S);
		assertEquals(testRCES.selectCardsToDiscard(null, 0, aHand).size(),6);
		assertEquals(aHand.size(),16);
	}
}
