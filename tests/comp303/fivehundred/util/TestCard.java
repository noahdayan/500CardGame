package comp303.fivehundred.util;

import static comp303.fivehundred.util.AllCards.*;
import static org.junit.Assert.*;

import org.junit.Test;

import comp303.fivehundred.util.Card.Joker;
import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;

/**
 * @author Benjamin Hockley
 */

public class TestCard
{
	@Test
	public void testToString()
	{
		assertEquals( "ACE of CLUBS", aAC.toString());
		assertEquals( "TEN of CLUBS", aTC.toString());
		assertEquals( "JACK of CLUBS", aJC.toString());
		assertEquals( "QUEEN of HEARTS", aQH.toString());
		assertEquals( "KING of SPADES", aKS.toString());
		assertEquals( "QUEEN of DIAMONDS", aQD.toString());
		assertEquals( "LOW Joker", aLJo.toString());
	}
	
	@Test
	public void testGetConverse()
	{
		assertEquals(AllCards.a4C.getSuit(), Suit.SPADES.getConverse());
		assertEquals(AllCards.a4H.getSuit(), Suit.DIAMONDS.getConverse());
		assertEquals(AllCards.a4S.getSuit(), Suit.CLUBS.getConverse());
		assertEquals(AllCards.a4D.getSuit(), Suit.HEARTS.getConverse());
	}
	
	@Test
	public void testJoker()
	{
		Card joker = AllCards.aLJo;
		assertEquals(true,joker.isJoker());
		assertEquals(Joker.LOW,joker.getJokerValue());	
	}
	
	@Test
	public void testGetRank()
	{
		Card tester = AllCards.a4C;
		assertEquals(Rank.FOUR,tester.getRank());
	}
	
	@Test
	public void testGetEffectiveSuit()
	{
		Card tester = AllCards.aJC;
		assertEquals(Suit.CLUBS,tester.getEffectiveSuit(null));
		assertEquals(Suit.SPADES,tester.getEffectiveSuit(Suit.SPADES));
		assertEquals(Suit.CLUBS,tester.getEffectiveSuit(Suit.CLUBS));
	}
	
	@Test
	public void testToShortString()
	{
		assertEquals( "4C", a4C.toShortString());
		assertEquals( "QD", aQD.toShortString());
		assertEquals( "LJ", aLJo.toShortString());
	}
	
	@Test
	public void testCompareTo()
	{
		Card jokerL = AllCards.aLJo;
		Card jokerH = AllCards.aHJo;
		Card king = AllCards.aKC;
		Card four = AllCards.a4C;
		assertEquals(1,jokerL.compareTo(four));
		assertEquals(-1,four.compareTo(jokerL));
		assertEquals(1,jokerH.compareTo(jokerL));
		assertEquals(9,king.compareTo(four));
		assertEquals(-1,AllCards.a4C.compareTo(AllCards.aHJo));
		assertEquals(-1,AllCards.a4C.compareTo(AllCards.aLJo));
	}
	
	@Test
	public void testEquals()
	{
		Card jokerH1= AllCards.aHJo;
		Card jokerH2= AllCards.aHJo;
		Card fourC1 = AllCards.a4C;
		Card fourC2 = AllCards.a4C;
		assertTrue(jokerH1.equals(jokerH2));
		assertTrue(fourC1.equals(fourC2));
	}
	
	@Test
	public void testHashCode()
	{
		Card fourHearts = AllCards.a4H;
		assertEquals(33,fourHearts.hashCode());
	}
}

