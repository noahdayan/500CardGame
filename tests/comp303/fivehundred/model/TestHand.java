package comp303.fivehundred.model;

import static org.junit.Assert.*;
import org.junit.Test;

import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Suit;

/*
 * @author toby welch-richards
 */
public class TestHand
{
	
	@Test
	public void testCanLead(){
		Hand myHand1 = new Hand();
		myHand1.add(AllCards.a4H);
		myHand1.add(AllCards.a5H);
		myHand1.add(AllCards.a6H);
		myHand1.add(AllCards.a7H);
		
		Hand myHand2 = new Hand();
		myHand2.add(AllCards.aHJo);
		
		Hand myHand3 = new Hand();
		myHand3.add(AllCards.aHJo);
		myHand3.add(AllCards.a4H);
		
		assertEquals(myHand1.canLead(false),(CardList)myHand1);
		assertEquals(myHand2.canLead(true),(CardList)myHand2);
		assertEquals(myHand3.canLead(true).getFirst(),AllCards.a4H);
		
	}

	@Test
	public void testGetJokers(){
		Hand myHand1 = new Hand();
		myHand1.add(AllCards.a4S);
		myHand1.add(AllCards.aHJo);
		
		assertEquals(myHand1.getJokers().getFirst(),AllCards.aHJo);
	}

	@Test
	public void testGetNonJokers(){
		Hand myHand1 = new Hand();
		myHand1.add(AllCards.aHJo);
		myHand1.add(AllCards.a4S);
		
		assertEquals(myHand1.getNonJokers().getFirst(),AllCards.a4S);
	}

	@Test
	public void testGetTrumpCards(){
		Hand myHand1 = new Hand();
		myHand1.add(AllCards.aAS);
		myHand1.add(AllCards.aLJo);
		
		Hand myHand2 = new Hand();
		myHand2.add(AllCards.a4H);
		
		assertEquals(myHand1.getTrumpCards(Suit.HEARTS).getFirst(),AllCards.aLJo);
		assertEquals(myHand2.getTrumpCards(Suit.HEARTS).getFirst(),AllCards.a4H);
	}
	
	@Test
	public void testGetNonTrumpCards(){
		Hand myHand1 = new Hand();
		myHand1.add(AllCards.aAH);
		myHand1.add(AllCards.aLJo);
		myHand1.add(AllCards.a4S);
		
		assertEquals(myHand1.getNonTrumpCards(Suit.HEARTS).getFirst(),AllCards.a4S);
	}	
	
	@Test
	public void testSelectLowest(){
		Hand myHand1 = new Hand();
		myHand1.add(AllCards.aAH);
		myHand1.add(AllCards.aLJo);
		
		Hand myHand2 = new Hand();
		myHand2.add(AllCards.aAH);
		myHand2.add(AllCards.aAD);
		
		Hand myHand3 = new Hand();
		myHand3.add(AllCards.aAH);
		myHand3.add(AllCards.aJH);
		myHand3.add(AllCards.aAC);
		
		assertTrue(myHand1.selectLowest(null)==AllCards.aAH);
		assertTrue(myHand2.selectLowest(Suit.HEARTS)==AllCards.aAD);
		assertTrue(myHand2.selectLowest(Suit.DIAMONDS)==AllCards.aAH);
		assertTrue(myHand3.selectLowest(Suit.HEARTS)==AllCards.aAC);
	}	

	@Test
	public void testPlayableCards(){
		Hand myHand1 = new Hand();
		myHand1.add(AllCards.aLJo);
		
		Hand myHand2 = new Hand();
		myHand2.add(AllCards.aAH);
		myHand2.add(AllCards.aLJo);
		
		assertEquals(myHand1.playableCards(Suit.HEARTS,Suit.HEARTS).getFirst(),AllCards.aLJo);
		assertEquals(myHand2.playableCards(Suit.HEARTS,null).getFirst(),AllCards.aAH);
	}	

	@Test
	public void testGetNumberOfCards(){
		Hand myHand1 = new Hand();
		myHand1.add(AllCards.aAH);
		myHand1.add(AllCards.aLJo);
		assertTrue(myHand1.numberOfCards(Suit.HEARTS, Suit.CLUBS)==1);
		assertTrue(myHand1.numberOfCards(Suit.HEARTS, Suit.HEARTS)==1);
		
		Hand myHand2 = new Hand();
		myHand2.add(AllCards.aAH);
		myHand2.add(AllCards.aJD);
		assertTrue(myHand2.numberOfCards(Suit.HEARTS, Suit.CLUBS)==1);
		assertTrue(myHand2.numberOfCards(Suit.HEARTS, Suit.HEARTS)==2);
		
		Hand myHand3 = new Hand();
		myHand3.add(AllCards.aAH);
		myHand3.add(AllCards.aJD);
		assertTrue(myHand3.numberOfCards(Suit.HEARTS, Suit.CLUBS)==1);
		assertTrue(myHand3.numberOfCards(Suit.HEARTS, Suit.HEARTS)==2);
		
		Hand myHand4 = new Hand();
		myHand4.add(AllCards.aJH);
		myHand4.add(AllCards.aJD);
		assertTrue(myHand4.numberOfCards(Suit.HEARTS, Suit.CLUBS)==1);
		assertTrue(myHand4.numberOfCards(Suit.HEARTS, Suit.HEARTS)==2);
	}
}
