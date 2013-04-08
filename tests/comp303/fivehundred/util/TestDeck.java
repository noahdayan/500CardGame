package comp303.fivehundred.util;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Benjamin Hockley
 */

public class TestDeck
{
	@Test
	public void testInit()
	{
		Deck lDeck = new Deck();
		assertEquals(46, lDeck.size());
		Set<Card> lSet = new HashSet<Card>();
		while( lDeck.size() > 0)
		{
			Card lCard = lDeck.draw();
			if( lSet.contains(lCard) )
			{
				fail();
			}
			else
			{
				lSet.add(lCard);
			}
		}
	}
}