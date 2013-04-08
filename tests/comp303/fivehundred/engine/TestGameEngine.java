package comp303.fivehundred.engine;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import comp303.fivehundred.engine.GameEngine.mode;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.ModelException;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;

/**
 * @author Benjamin Hockley
 */
public class TestGameEngine
{
	@Test
	public void testNewGame()
	{
		GameEngine testGame = new GameEngine(mode.CONSOLE);
		testGame.newgame();
		Player[] aPlayerList = testGame.getPlayerList();
		assertEquals("BasicB", aPlayerList[0].getName());
		assertEquals("RandomA", aPlayerList[1].getName());
		assertEquals("BasicA", aPlayerList[2].getName());
		assertEquals("RandomB", aPlayerList[3].getName());
		assertEquals(aPlayerList[1], aPlayerList[0].getNextPlayer());
		assertEquals(aPlayerList[2], aPlayerList[1].getNextPlayer());
		assertEquals(aPlayerList[3], aPlayerList[2].getNextPlayer());
		assertEquals(aPlayerList[0], aPlayerList[3].getNextPlayer());
		assertEquals(aPlayerList[2].getTeam(), aPlayerList[0].getTeam());
		assertEquals(aPlayerList[3].getTeam(), aPlayerList[1].getTeam());
		assertEquals(aPlayerList[0].getTeam(), aPlayerList[2].getTeam());
		assertEquals(aPlayerList[1].getTeam(), aPlayerList[3].getTeam());
	}

	@Test
	public void testDeal()
	{
		GameEngine testGame = new GameEngine(mode.CONSOLE);
		testGame.newgame();
		testGame.deal();
		Player[] aPlayerList = testGame.getPlayerList();
		CardList aWidow = testGame.getWidow();
		for(Player p: aPlayerList)
			assertEquals(10, p.getHand().getTrumpCards(Suit.HEARTS).size() + p.getHand().getNonTrumpCards(Suit.HEARTS).size());
		assertEquals(6,aWidow.size());
	}

	@Test
	public void testBid()
	{
		GameEngine testGame = new GameEngine(mode.CONSOLE);
		testGame.newgame();
		testGame.deal();
		try
		{
			testGame.bid();
		}
		catch(IOException e)
		{
			fail();
		}
	}
	
	@Test
	public void testExchange()
	{
		GameEngine testGame = new GameEngine(mode.CONSOLE);
		testGame.newgame();
		testGame.deal();
		try
		{
			testGame.bid();
			CardList aWidow = testGame.getWidow();
			Player aBidWinner = testGame.getBidWinner();
			testGame.exchange();
			assertEquals(6, aWidow.size());
			assertEquals(10, aBidWinner.getHand().size());
		}
		catch(ModelException e)
		{
		}
		catch (IOException e)
		{
			fail();
		}
	}
	
	@Test
	public void testPlayTrick()
	{
		GameEngine testGame = new GameEngine(mode.CONSOLE);
		testGame.newgame();
		testGame.deal();
		try
		{
			testGame.bid();		
			testGame.playTrick();
			Player[] aPlayerList = testGame.getPlayerList();
			Player aLastTrickWinner = testGame.getLastTrickWinner();
			assertEquals(9,aPlayerList[0].getHand().size());
			assertEquals(1, aLastTrickWinner.getTricksWon());
		}
		catch (IOException e)
		{
			fail();
		}
		catch (ModelException e)
		{
		}

	}
	
	@Test
	public void testComputeScore()
	{
		GameEngine testGame = new GameEngine(mode.CONSOLE);
		boolean team0ContainsBidWinner = false;
		testGame.newgame();
		testGame.deal();
		Team[] aTeamList = testGame.getTeamList();
		Team team0 = aTeamList[0];
		Team team1 = aTeamList[1];
		team0.setScore(150);
		team1.setScore(100);
		try
		{
			testGame.bid();
			Bid aHighBid = testGame.getHighBid();
			Player aBidWinner = testGame.getBidWinner();
			int OppositeBid = 10 - aHighBid.getTricksBid();
			for(int i = 0; i < aHighBid.getTricksBid(); i++)
			{
				aBidWinner.incTricks();
			}
			for(int y = 0; y < OppositeBid; y++)
			{
				aBidWinner.getNextPlayer().incTricks();
			}
			int teamZeroTricks = team0.computeTricksWon();
			int teamOneTricks = team1.computeTricksWon();
			for(Player p: team0.getPlayers())
			{
				if(aBidWinner.getName().equals(p.getName()))
				{
					team0ContainsBidWinner = true;
				}
			}
			if(team0ContainsBidWinner)
			{
				testGame.computeScore();
				assertEquals(150 + aHighBid.getScore(), team0.getScore());
				assertEquals(100 + teamOneTricks*(10), team1.getScore());
			}
			else
			{
				testGame.computeScore();
				assertEquals(150 + teamZeroTricks*(10), team0.getScore());
				assertEquals(100 + aHighBid.getScore(), team1.getScore());
			}
		}
		catch(IOException e)
		{
			fail();
		}
		catch(ModelException e)
		{
		}
	}
	@Test
	public void testGameStillGoingOn1()
	{
		GameEngine testGame = new GameEngine(mode.CONSOLE);
		testGame.newgame();
		Team[] aTeamList = testGame.getTeamList();
		Team team0 = aTeamList[0];
		Team team1 = aTeamList[1];
		team0.setScore(500);
		team1.setScore(250);
		assertFalse(testGame.gameStillOnGoing());
		testGame.newgame();
		team0.setScore(280);
		team1.setScore(250);
		assertTrue(testGame.gameStillOnGoing());
	}
	@Test
	public void testGameStillGoingOn2()
	{
		GameEngine testGame = new GameEngine(mode.CONSOLE);
		testGame.newgame();
		Team[] aTeamList = testGame.getTeamList();
		Team team0 = aTeamList[0];
		Team team1 = aTeamList[1];
		team0.setScore(250);
		team1.setScore(500);
		assertFalse(testGame.gameStillOnGoing());
	}
}
