package comp303.fivehundred.util;

import static org.junit.Assert.*;
import comp303.fivehundred.util.Card;

import java.util.Comparator;
import java.util.Iterator;

import org.junit.Test;

/**
 * @author Benjamin Hockley
 */
public class TestCardList
{
	private CardList lInitiallyEmpty = new CardList();
	@Test
	public void testGetFirst()
	{
		CardList lList = new CardList();
		lList.add(AllCards.a4C);
		assertEquals(AllCards.a4C, lList.getFirst());
	}
	
	@Test(expected=GameUtilException.class)
	public void testGetLast()
	{
		CardList lList = new CardList();
		lList.add(AllCards.a4C);
		assertEquals(AllCards.a4C, lList.getLast());
		lInitiallyEmpty.getLast();
		lInitiallyEmpty.add(AllCards.a4D);
		assertEquals(AllCards.a4D, lInitiallyEmpty.getFirst());
	}
	
	@Test
	public void testSize()
	{
		CardList lList = new CardList();
		lList.add(AllCards.a4C);
		assertEquals(1 , lList.size());
	}
	
	@Test(expected = GameUtilException.class)
	public void testClone()
	{
		CardList lList = new CardList();
		lList.add(AllCards.a4C);
		CardList lListTest = lList.clone();
		assertEquals(AllCards.a4C , lListTest.getFirst());
		lListTest.remove(AllCards.a4C);
		lListTest.getFirst();
		
	}

	@Test
	public void testToString()
	{
		CardList lList = new CardList();
		assertEquals("[]",lList.toString());
		lList.add(AllCards.aAC);
		assertEquals("[ACE of CLUBS]",lList.toString());
		lList.add(AllCards.aKC);
		assertEquals("[ACE of CLUBS, KING of CLUBS]",lList.toString());
	}
	
	@Test(expected = GameUtilException.class)
	public void testGet()
	{
		CardList lList = new CardList();
		lList.add(AllCards.a4C);
		assertEquals(AllCards.a4C, lList.get(0));
		lList.remove(AllCards.a4C);
		assertEquals(0, lList.get(0));
	}
	
	@Test
	public void testIterator()
	{
		CardList lList = new CardList();
		lList.add(AllCards.a4C);
		if(!(lList.iterator() instanceof Iterator<?>))
		{
			fail();
		}
	}
	
	@Test
	public void testRandom()
	{
		CardList lList = new CardList();
		lList.add(AllCards.a4C);
		lList.add(AllCards.a5C);
		lList.add(AllCards.a6C);
		lList.add(AllCards.a7C);
		Card current = null;
		Card random = lList.random();
		Iterator<Card> iterator = lList.iterator();
		while(iterator.hasNext())
		{
	         current = iterator.next();
	         if(current.equals(random))
	         {
	        	 return;
	         }
		}
	}
	
	@Test
	public void testSort()
	{
		Comparator<Card> byRank = new Card.ByRankComparator();
		CardList lList = new CardList();
		lList.add(AllCards.a4C);
		lList.add(AllCards.a5H);
		lList.sort(byRank);
		assertEquals(AllCards.a5H,lList.getFirst());
	}
}
