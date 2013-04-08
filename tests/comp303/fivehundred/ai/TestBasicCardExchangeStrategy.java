package comp303.fivehundred.ai;

import static org.junit.Assert.*;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.util.AllCards;

import org.junit.Test;

/**
 * 
 * @author Noah Dayan
 *
 */
public class TestBasicCardExchangeStrategy
{
	@Test
	public void testExchange()
	{
		BasicCardExchangeStrategy testBCES = new BasicCardExchangeStrategy();
		Hand aHand = new Hand();
		aHand.add(AllCards.a7S);
		aHand.add(AllCards.a6S);
		aHand.add(AllCards.a5S);
		aHand.add(AllCards.a4S);
		aHand.add(AllCards.a4C);
		aHand.add(AllCards.a5D);
		aHand.add(AllCards.a6H);
		aHand.add(AllCards.a7C);
		aHand.add(AllCards.a8D);
		aHand.add(AllCards.a9H);
		aHand.add(AllCards.aTC);
		aHand.add(AllCards.aJD);
		aHand.add(AllCards.aQH);
		aHand.add(AllCards.aKC);
		aHand.add(AllCards.aAD);
		aHand.add(AllCards.aLJo);
		Bid[] bids = new Bid[1];
		bids[0] = new Bid(0);
		CardList discardedCards = testBCES.selectCardsToDiscard(bids, 0, aHand);
		assertEquals(discardedCards.size(),6);
		assertEquals(aHand.size(),16);
		CardList cardsToDiscard = new CardList();
		cardsToDiscard.add(AllCards.a4C);
		cardsToDiscard.add(AllCards.a5D);
		cardsToDiscard.add(AllCards.a6H);
		cardsToDiscard.add(AllCards.a7C);
		cardsToDiscard.add(AllCards.a8D);
		cardsToDiscard.add(AllCards.a9H);
		for(int i = 0; i < 6; i++)
		{
			assertEquals(discardedCards.get(i),cardsToDiscard.get(i));
		}
	}
}
