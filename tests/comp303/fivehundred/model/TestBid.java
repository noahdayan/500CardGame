package comp303.fivehundred.model;

import org.junit.Test;
import comp303.fivehundred.util.Card.Suit;
import static org.junit.Assert.*;
import org.junit.Before;

import comp303.fivehundred.model.ModelException;

/*
 * @author Eliot Hautefeuille
 */
// VERSION 1.6 :: 2012 OCT 1 02:20PM :: Eliot Hautefeuille

public class TestBid
{
	private Bid _TMP;
	private Bid _00;
	private Bid _01;
	private Bid _02;
	private Bid _03;
	private Bid _04;
	private Bid _05;
	private Bid _06;
	private Bid _07;
	private Bid _08;
	private Bid _09;
	private Bid _10;
	private Bid _11;
	private Bid _12;
	private Bid _13;
	private Bid _14;
	private Bid _15;
	private Bid _16;
	private Bid _17;
	private Bid _18;
	private Bid _19;
	private Bid _20;
	private Bid _21;
	private Bid _22;
	private Bid _23;
	private Bid _24;

	
	@Before
	public void init(){
		RunConstructorByIndex();
		_TMP = new Bid();
	}
	
	@Test
	public void RunConstructorByDesc(){
		_00 = new Bid(6, Suit.SPADES);
		_01 = new Bid(6, Suit.CLUBS);
		_02 = new Bid(6, Suit.DIAMONDS);
		_03 = new Bid(6, Suit.HEARTS);
		_05 = new Bid(7, Suit.SPADES);
		_06 = new Bid(7, Suit.CLUBS);
		_07 = new Bid(7, Suit.DIAMONDS);
		_08 = new Bid(7, Suit.HEARTS);
		_10 = new Bid(8, Suit.SPADES);
		_11 = new Bid(8, Suit.CLUBS);
		_12 = new Bid(8, Suit.DIAMONDS);
		_13 = new Bid(8, Suit.HEARTS);
		_15 = new Bid(9, Suit.SPADES);
		_16 = new Bid(9, Suit.CLUBS);
		_17 = new Bid(9, Suit.DIAMONDS);
		_18 = new Bid(9, Suit.HEARTS);
		_20 = new Bid(10, Suit.SPADES);
		_21 = new Bid(10, Suit.CLUBS);
		_22 = new Bid(10, Suit.DIAMONDS);
		_23 = new Bid(10, Suit.HEARTS);
		//Shouldn't occur while playing, but here for testing purposes
		_04 = new Bid(6, null);
		_09 = new Bid(7, null);
		_14 = new Bid(8, null);
		_19 = new Bid(9, null);
		_24 = new Bid(10, null);
	}
	
	@Test
	public void RunConstructorByIndex(){
		_00 = new Bid(0);
		_01 = new Bid(1);
		_02 = new Bid(2);
		_03 = new Bid(3);
		_04 = new Bid(4);
		_05 = new Bid(5);
		_06 = new Bid(6);
		_07 = new Bid(7);
		_08 = new Bid(8);
		_09 = new Bid(9);
		_10 = new Bid(10);
		_11 = new Bid(11);
		_12 = new Bid(12);
		_13 = new Bid(13);
		_14 = new Bid(14);
		_15 = new Bid(15);
		_16 = new Bid(16);
		_17 = new Bid(17);
		_18 = new Bid(18);
		_19 = new Bid(19);
		_20 = new Bid(20);
		_21 = new Bid(21);
		_22 = new Bid(22);
		_23 = new Bid(23);
		_24 = new Bid(24);
	}
	
	@Test
	public void assertSuitEquivalency(){
		assertEquals(_00.getSuit(), Suit.SPADES);
		assertEquals(_05.getSuit(), Suit.SPADES);
		assertEquals(_10.getSuit(), Suit.SPADES);
		assertEquals(_15.getSuit(), Suit.SPADES);
		assertEquals(_20.getSuit(), Suit.SPADES);
		assertEquals(_01.getSuit(), Suit.CLUBS);
		assertEquals(_06.getSuit(), Suit.CLUBS);
		assertEquals(_11.getSuit(), Suit.CLUBS);
		assertEquals(_16.getSuit(), Suit.CLUBS);
		assertEquals(_21.getSuit(), Suit.CLUBS);
		assertEquals(_02.getSuit(), Suit.DIAMONDS);
		assertEquals(_07.getSuit(), Suit.DIAMONDS);
		assertEquals(_12.getSuit(), Suit.DIAMONDS);
		assertEquals(_17.getSuit(), Suit.DIAMONDS);
		assertEquals(_22.getSuit(), Suit.DIAMONDS);
		assertEquals(_03.getSuit(), Suit.HEARTS);
		assertEquals(_08.getSuit(), Suit.HEARTS);
		assertEquals(_13.getSuit(), Suit.HEARTS);
		assertEquals(_18.getSuit(), Suit.HEARTS);
		assertEquals(_23.getSuit(), Suit.HEARTS);
		assertEquals(_04.getSuit(), null);
		assertEquals(_09.getSuit(), null);
		assertEquals(_14.getSuit(), null);
		assertEquals(_19.getSuit(), null);
		assertEquals(_24.getSuit(), null);
	}
	
	@Test(expected = ModelException.class)
	public void assertPassSuitEquivalency(){
		_TMP = new Bid();
		_TMP.getSuit();
	}
	
	@Test
	public void assertTricksEquivalency(){
		assertEquals(_00.getTricksBid(), 6);
		assertEquals(_01.getTricksBid(), 6);
		assertEquals(_02.getTricksBid(), 6);
		assertEquals(_03.getTricksBid(), 6);
		assertEquals(_04.getTricksBid(), 6);
		assertEquals(_05.getTricksBid(), 7);
		assertEquals(_06.getTricksBid(), 7);
		assertEquals(_07.getTricksBid(), 7);
		assertEquals(_08.getTricksBid(), 7);
		assertEquals(_09.getTricksBid(), 7);
		assertEquals(_10.getTricksBid(), 8);
		assertEquals(_11.getTricksBid(), 8);
		assertEquals(_12.getTricksBid(), 8);
		assertEquals(_13.getTricksBid(), 8);
		assertEquals(_14.getTricksBid(), 8);
		assertEquals(_15.getTricksBid(), 9);
		assertEquals(_16.getTricksBid(), 9);
		assertEquals(_17.getTricksBid(), 9);
		assertEquals(_18.getTricksBid(), 9);
		assertEquals(_19.getTricksBid(), 9);
		assertEquals(_20.getTricksBid(), 10);
		assertEquals(_21.getTricksBid(), 10);
		assertEquals(_22.getTricksBid(), 10);
		assertEquals(_23.getTricksBid(), 10);
		assertEquals(_24.getTricksBid(), 10);	
	}
	
	@Test(expected = ModelException.class)
	public void assertPassTricksEquivalency(){
		_TMP = new Bid();
		_TMP.getTricksBid();
	}
	
	@Test
	public void assertScoresEquivalency(){
		assertEquals(_00.getScore(), 40);
		assertEquals(_01.getScore(), 60);
		assertEquals(_02.getScore(), 80);
		assertEquals(_03.getScore(), 100);
		assertEquals(_04.getScore(), 120);
		assertEquals(_05.getScore(), 140);
		assertEquals(_06.getScore(), 160);
		assertEquals(_07.getScore(), 180);
		assertEquals(_08.getScore(), 200);
		assertEquals(_09.getScore(), 220);
		assertEquals(_10.getScore(), 240);
		assertEquals(_11.getScore(), 260);
		assertEquals(_12.getScore(), 280);
		assertEquals(_13.getScore(), 300);
		assertEquals(_14.getScore(), 320);
		assertEquals(_15.getScore(), 340);
		assertEquals(_16.getScore(), 360);
		assertEquals(_17.getScore(), 380);
		assertEquals(_18.getScore(), 400);
		assertEquals(_19.getScore(), 420);
		assertEquals(_20.getScore(), 440);
		assertEquals(_21.getScore(), 460);
		assertEquals(_22.getScore(), 480);
		assertEquals(_23.getScore(), 500);
		assertEquals(_24.getScore(), 520);
	}
	
	@Test(expected=ModelException.class)
	public void assertPassScoresEquivalency(){
		_TMP = new Bid();
		_TMP.getScore();
	}
	
	@Test
	public void assertIndexesEquivalency(){
		assertEquals(_00.toIndex(), 0);
		assertEquals(_01.toIndex(), 1);
		assertEquals(_02.toIndex(), 2);
		assertEquals(_03.toIndex(), 3);
		assertEquals(_04.toIndex(), 4);
		assertEquals(_05.toIndex(), 5);
		assertEquals(_06.toIndex(), 6);
		assertEquals(_07.toIndex(), 7);
		assertEquals(_08.toIndex(), 8);
		assertEquals(_09.toIndex(), 9);
		assertEquals(_10.toIndex(), 10);
		assertEquals(_11.toIndex(), 11);
		assertEquals(_12.toIndex(), 12);
		assertEquals(_13.toIndex(), 13);
		assertEquals(_14.toIndex(), 14);
		assertEquals(_15.toIndex(), 15);
		assertEquals(_16.toIndex(), 16);
		assertEquals(_17.toIndex(), 17);
		assertEquals(_18.toIndex(), 18);
		assertEquals(_19.toIndex(), 19);
		assertEquals(_20.toIndex(), 20);
		assertEquals(_21.toIndex(), 21);
		assertEquals(_22.toIndex(), 22);
		assertEquals(_23.toIndex(), 23);
		assertEquals(_24.toIndex(), 24);
	}
	
	@Test(expected=ModelException.class)
	public void assertPassIndexesEquivalency(){
		_TMP = new Bid();
		_TMP.toIndex();
	}
	
	@Test
	public void testHashCode(){
		_TMP = new Bid(20);
		assertTrue(_20.hashCode() ==_TMP.hashCode());
		_TMP = new Bid(19);
		assertTrue(_20.hashCode() != _TMP.hashCode());
		_TMP = new Bid();
		assertTrue(_TMP.hashCode() == -1);
	}
	
	@Test
	public void testEquals(){
		_TMP = new Bid(20);
		assertTrue(_20.equals(_TMP));
		_TMP = new Bid(19);
		assertTrue(!_20.equals(_TMP));
	}
	
	@Test
	public void testCompareTo(){
		_TMP = new Bid(20);
		assertTrue(_20.compareTo(_TMP) == 0);
		_TMP = new Bid(19);
		assertTrue(_20.compareTo(_TMP) == 1 );
		_TMP = new Bid(21);
		assertTrue(_20.compareTo(_TMP) == -1);
		_TMP = new Bid(18);
		assertTrue(_20.compareTo(_TMP) == 2 );
		_TMP = new Bid(22);
		assertTrue(_20.compareTo(_TMP) == -2);
		
	}
	
	@Test
	public void testIsNoTrump(){
		assertTrue(_04.isNoTrump());
		assertTrue(!_05.isNoTrump());
		assertTrue(_09.isNoTrump());
		assertTrue(!_06.isNoTrump());
	}
	
	@Test
	public void testIsPass(){
		_TMP = new Bid();
		assertTrue(_TMP.isPass());
		assertTrue(!_00.isPass());
		assertTrue(!_20.isPass());
	}
	
	@Test
	public void testToString(){
		assertTrue(_00.toString().equals("6 SPADES"));
		assertTrue(_16.toString().equals("9 CLUBS"));
		assertTrue(_24.toString().equals("10 NO TRUMP"));
	}
	
	@Test
	public void testMax(){
		_TMP = new Bid();
		Bid[] array1 = {_00, _01, _02};
		Bid[] array2 = {_12, _13, _11};
		Bid[] array3 = {_01, _01, _01};
		Bid[] array4 = {_TMP, _07, _08};
		assertTrue(Bid.max(array1).equals(_02));
		assertTrue(Bid.max(array2).equals(_13));
		assertTrue(Bid.max(array3).equals(_01));
		Bid.max(array4);
	}
	
}
