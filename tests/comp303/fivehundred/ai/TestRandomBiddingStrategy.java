package comp303.fivehundred.ai;

import static org.junit.Assert.*;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.AllCards;

import org.junit.Test;

/**
 * 
 * @author Noah Dayan
 *
 */
public class TestRandomBiddingStrategy
{
	@Test
	public void testBid()
	{	
		RandomBiddingStrategy testRBS = new RandomBiddingStrategy();
		Hand aHand = new Hand();
		aHand.add(AllCards.a4C);
		aHand.add(AllCards.a4D);
		aHand.add(AllCards.a4H);
		aHand.add(AllCards.a4S);
		aHand.add(AllCards.a5C);
		aHand.add(AllCards.a5D);
		aHand.add(AllCards.a5H);
		aHand.add(AllCards.a5S);
		aHand.add(AllCards.aHJo);
		aHand.add(AllCards.aLJo);
		Bid[] previousBids = new Bid[1];
		previousBids[0] = new Bid();
		Bid aTestBid = testRBS.selectBid(previousBids, aHand);
		assertTrue(aTestBid.isPass() || (aTestBid.toIndex() >= 0 && aTestBid.toIndex() <= 24));
		previousBids[0] = new Bid(24);
		aTestBid = testRBS.selectBid(previousBids, aHand);
		assertTrue(aTestBid.isPass());
		previousBids[0] = new Bid(0);
		aTestBid = testRBS.selectBid(previousBids, aHand);
		assertTrue(aTestBid.isPass() || (aTestBid.toIndex() > 0 && aTestBid.toIndex() <= 24));
		testRBS = new RandomBiddingStrategy(100);
	}
}
