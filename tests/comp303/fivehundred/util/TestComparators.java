package comp303.fivehundred.util;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;

/**
 * @author Benjamin Hockley
 */

public class TestComparators
{
	@Test
	public void testCompareCards(){
		
		Comparator<Card> ByRank = new Card.ByRankComparator();
		Comparator<Card> SuitNT = new Card.BySuitNoTrumpComparator();
		Comparator<Card> BySuit = new Card.BySuitComparator(Suit.HEARTS);
		
		assertTrue(ByRank.compare(AllCards.aAC, AllCards.aAC)==0);
		assertTrue(ByRank.compare(AllCards.aAC, AllCards.aKC)>0);
		assertTrue(ByRank.compare(AllCards.aKC, AllCards.aAC)<0);
		assertTrue(ByRank.compare(AllCards.aAH, AllCards.aAH)==0);
		assertTrue(ByRank.compare(AllCards.aHJo, AllCards.aLJo)>0);
		assertTrue(ByRank.compare(AllCards.aLJo, AllCards.aHJo)<0);
		assertTrue(ByRank.compare(AllCards.aHJo, AllCards.aHJo)==0);
		assertTrue(ByRank.compare(AllCards.aLJo, AllCards.aLJo)==0);
		
		assertTrue(SuitNT.compare(AllCards.aAH, AllCards.aAS)>0);
		assertTrue(SuitNT.compare(AllCards.aAC, AllCards.aKC)>0);
		assertTrue(SuitNT.compare(AllCards.aAH, AllCards.aAH)==0);
		assertTrue(SuitNT.compare(AllCards.aHJo, AllCards.aLJo)>0);
		assertTrue(SuitNT.compare(AllCards.aLJo, AllCards.aHJo)<0);
		assertTrue(SuitNT.compare(AllCards.aHJo, AllCards.aHJo)==0);
		assertTrue(SuitNT.compare(AllCards.aHJo, AllCards.aHJo)==0);
		
		Card tBower = new Card(Rank.JACK, Suit.HEARTS);
		Card ctBower = new Card(Rank.JACK, Suit.HEARTS.getConverse());
		Card tAce = new Card(Rank.ACE, Suit.HEARTS);
		
		assertTrue(BySuit.compare(AllCards.aHJo, AllCards.aLJo)>0);
		assertTrue(BySuit.compare(AllCards.aLJo, tBower)>0);
		assertTrue(BySuit.compare(tBower, ctBower)>0);
		assertTrue(BySuit.compare(ctBower, tAce)>0);
		
		assertFalse(BySuit.compare(AllCards.a4C, AllCards.aHJo)>0);
		assertFalse(BySuit.compare(AllCards.a4C, AllCards.a4H)>0);
	}
}
