package comp303.fivehundred.model;

import static org.junit.Assert.*;
import org.junit.Test;

import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card.Joker;
import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;

/*
 * @author toby welch-richards
 */
public class TestTrick
{
	
	@Test
	public void testNoTrumps1()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aAH);
		myTrick.add(AllCards.aKH);
		myTrick.add(AllCards.aHJo);
		myTrick.add(AllCards.aLJo);
		assertTrue(myTrick.highest().getJokerValue()==Joker.HIGH);
	}
	
	@Test
	public void testNoTrumps2()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQH);
		myTrick.add(AllCards.aLJo);
		myTrick.add(AllCards.aAH);
		myTrick.add(AllCards.aKH);
		assertTrue(myTrick.highest().getJokerValue()==Joker.LOW);
	}
	
	@Test
	public void testNoTrumps3()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQH);
		myTrick.add(AllCards.aJH);
		myTrick.add(AllCards.aAH);
		myTrick.add(AllCards.aKH);
		assertEquals(AllCards.aAH,myTrick.highest());
	}
	
	@Test
	public void testNoTrumps4()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aTH);
		myTrick.add(AllCards.aQD);
		myTrick.add(AllCards.aAD);
		myTrick.add(AllCards.aKD);
		assertTrue(myTrick.highest().getRank() == Rank.TEN && myTrick.highest().getSuit() == Suit.HEARTS);
	}

	@Test
	public void testHearts1()
	{
		Bid myBid = new Bid(3);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aAH);
		myTrick.add(AllCards.aKH);
		myTrick.add(AllCards.aHJo);
		myTrick.add(AllCards.aLJo);
		assertTrue(myTrick.highest().getJokerValue()==Joker.HIGH);
	}
	
	@Test
	public void testHearts2() 

	{
		Bid myBid = new Bid(3);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQH);
		myTrick.add(AllCards.aLJo);
		myTrick.add(AllCards.aAH);
		myTrick.add(AllCards.aKH);
		assertTrue(myTrick.highest().getJokerValue()==Joker.LOW);
	}

	@Test
	public void testHearts3()
	{
		Bid myBid = new Bid(3);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQH);
		myTrick.add(AllCards.aJH);
		myTrick.add(AllCards.aAH);
		myTrick.add(AllCards.aKH);
		assertEquals(AllCards.aJH,myTrick.highest());
	}
	
	@Test
	public void testHearts4()
	{
		Bid myBid = new Bid(3);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4H);
		myTrick.add(AllCards.aJD);
		myTrick.add(AllCards.aAD);
		myTrick.add(AllCards.aKD);
		assertEquals(AllCards.aJD,myTrick.highest());
	}
	
	@Test
	public void testHearts5()
	{
		Bid myBid = new Bid(3);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4H);
		myTrick.add(AllCards.aJC);
		myTrick.add(AllCards.aAD);
		myTrick.add(AllCards.aKD);
		assertTrue(myTrick.highest().getRank() == Rank.FOUR && myTrick.highest().getSuit() == Suit.HEARTS);
	}
	
	@Test
	public void testDiamonds1()
	{
		Bid myBid = new Bid(2);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aAD);
		myTrick.add(AllCards.aKD);
		myTrick.add(AllCards.aHJo);
		myTrick.add(AllCards.aLJo);
		assertTrue(myTrick.highest().getJokerValue()==Joker.HIGH);
	}
	
	@Test
	public void testDiamonds2()

	{
		Bid myBid = new Bid(2);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQD);
		myTrick.add(AllCards.aLJo);
		myTrick.add(AllCards.aAD);
		myTrick.add(AllCards.aKD);
		assertTrue(myTrick.highest().getJokerValue()==Joker.LOW);
	}

	@Test
	public void testDiamonds3()
	{
		Bid myBid = new Bid(2);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQD);
		myTrick.add(AllCards.aJD);
		myTrick.add(AllCards.aAD);
		myTrick.add(AllCards.aKD);
		assertEquals(AllCards.aJD,myTrick.highest());
	}
	
	@Test
	public void testDiamonds4()
	{
		Bid myBid = new Bid(2);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4D);
		myTrick.add(AllCards.aJH);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		assertEquals(AllCards.aJH,myTrick.highest());
	}
	
	@Test
	public void testDiamonds5()
	{
		Bid myBid = new Bid(2);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4D);
		myTrick.add(AllCards.aJC);
		myTrick.add(AllCards.aAC);
		myTrick.add(AllCards.aKC);
		assertTrue(myTrick.highest().getRank() == Rank.FOUR && myTrick.highest().getSuit() == Suit.DIAMONDS);
	}

	@Test
	public void testSpades1()
	{
		Bid myBid = new Bid(0);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		myTrick.add(AllCards.aHJo);
		myTrick.add(AllCards.aLJo);
		assertTrue(myTrick.highest().getJokerValue()==Joker.HIGH);
	}
	
	@Test
	public void testSpades2()
	{
		Bid myBid = new Bid(0);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQS);
		myTrick.add(AllCards.aLJo);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		assertTrue(myTrick.highest().getJokerValue()==Joker.LOW);
	}

	@Test
	public void testSpades3()
	{
		Bid myBid = new Bid(0);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQS);
		myTrick.add(AllCards.aJS);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		assertEquals(AllCards.aJS,myTrick.highest());
	}
	
	@Test
	public void testSpades4()
	{
		Bid myBid = new Bid(0);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4S);
		myTrick.add(AllCards.aJC);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		assertEquals(AllCards.aJC,myTrick.highest());
	}

	@Test
	public void testSpades5()
	{
		Bid myBid = new Bid(0);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4S);
		myTrick.add(AllCards.aJD);
		myTrick.add(AllCards.aAC);
		myTrick.add(AllCards.aKC);
		assertTrue(myTrick.highest().getRank() == Rank.FOUR && myTrick.highest().getSuit() == Suit.SPADES);
	}

	@Test
	public void testClubs1()
	{
		Bid myBid = new Bid(1);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aAC);
		myTrick.add(AllCards.aKC);
		myTrick.add(AllCards.aHJo);
		myTrick.add(AllCards.aLJo);
		assertTrue(myTrick.highest().getJokerValue()==Joker.HIGH);
	}
	
	@Test
	public void testClubs2()
	{
		Bid myBid = new Bid(1);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQC);
		myTrick.add(AllCards.aLJo);
		myTrick.add(AllCards.aAC);
		myTrick.add(AllCards.aKC);
		assertTrue(myTrick.highest().getJokerValue()==Joker.LOW);
	}

	@Test
	public void testClubs3()
	{
		Bid myBid = new Bid(1);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aQC);
		myTrick.add(AllCards.aJC);
		myTrick.add(AllCards.aAC);
		myTrick.add(AllCards.aKC);
		assertEquals(AllCards.aJC,myTrick.highest());
	}
	
	@Test
	public void testClubs4()
	{
		Bid myBid = new Bid(1);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4C);
		myTrick.add(AllCards.aJS);
		myTrick.add(AllCards.aAC);
		myTrick.add(AllCards.aKC);
		assertEquals(AllCards.aJS,myTrick.highest());
	}

	@Test
	public void testClubs5()
	{
		Bid myBid = new Bid(1);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4C);
		myTrick.add(AllCards.aJD);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		assertTrue(myTrick.highest().getRank() == Rank.FOUR && myTrick.highest().getSuit() == Suit.CLUBS);
	}

	@Test
	public void testEqual1()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aHJo);
		myTrick.add(AllCards.aHJo);
		assertTrue(myTrick.highest().getJokerValue() == Joker.HIGH);
	}
	
	@Test
	public void testJokers()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aLJo);
		myTrick.add(AllCards.aHJo);
		assertTrue(myTrick.highest().getJokerValue() == Joker.HIGH);
	}

	@Test
	public void testGetTrump()
	{
		Bid myBid1 = new Bid(4);
		Trick myTrick1 = new Trick(myBid1);
		assertTrue(myTrick1.getTrumpSuit()==null);
		Bid myBid2 = new Bid(3);
		Trick myTrick2 = new Trick(myBid2);
		assertTrue(myTrick2.getTrumpSuit()==Suit.HEARTS);
		Bid myBid3 = new Bid(2);
		Trick myTrick3 = new Trick(myBid3);
		assertTrue(myTrick3.getTrumpSuit()==Suit.DIAMONDS);
		Bid myBid4 = new Bid(1);
		Trick myTrick4 = new Trick(myBid4);
		assertTrue(myTrick4.getTrumpSuit()==Suit.CLUBS);
		Bid myBid5 = new Bid(0);
		Trick myTrick5 = new Trick(myBid5);
		assertTrue(myTrick5.getTrumpSuit()==Suit.SPADES);
	}

	@Test
	public void testGetLed()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4C);
		myTrick.add(AllCards.aJD);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		assertTrue(myTrick.getSuitLed()==Suit.CLUBS);
		
	}
	
	@Test
	public void testJokerLed()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.aHJo);
		myTrick.add(AllCards.aJD);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		assertTrue(myTrick.jokerLed());
		
	}
	
	@Test
	public void testCardLed()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4C);
		myTrick.add(AllCards.aJD);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		assertTrue(myTrick.cardLed()==AllCards.a4C);
		
	}

	@Test
	public void testWinnerIndex()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4C);
		myTrick.add(AllCards.aJD);
		myTrick.add(AllCards.aAS);
		myTrick.add(AllCards.aKS);
		assertEquals(0,myTrick.winnerIndex());
		
	}
	
	@Test
	public void testWinnerIndex2()
	{
		Bid myBid = new Bid(4);
		Trick myTrick = new Trick(myBid);
		myTrick.add(AllCards.a4C);
		myTrick.add(AllCards.aJD);
		myTrick.add(AllCards.a5C);
		myTrick.add(AllCards.aKC);
		assertEquals(3,myTrick.winnerIndex());
		
	}
	



}
