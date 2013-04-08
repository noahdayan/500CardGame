// Eliot hautefeuille Oct 27th 2012 22:40 (Logging and stats)
// Non-Statistics and logging = Toby L Welch-Richards
package comp303.fivehundred.engine;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
//Eliot : Statistics and Logging : Dependencies
import java.util.Observable;
import java.util.Observer;

import comp303.fivehundred.ai.IBiddingStrategy;
import comp303.fivehundred.ai.ICardExchangeStrategy;
import comp303.fivehundred.ai.IPlayingStrategy;
import comp303.fivehundred.engine.Player.Strategy;
import comp303.fivehundred.gui.GUI;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Deck;

/**
 * 
 * Game Engine class.
 *
 */
public class GameEngine extends Observable //Eliot : extends Observable
{
	private static final int MAXBID = 10;
	private static final int MINBID = 6;
	private static final int NUMBER_CARDS = 10;
	private static final int WIDOW_SIZE = 6;
	private static final int POINTS = 10;
	private static final int MAXSCORE = 500;
	private static final int MINSCORE = -500;
	private static final int SLAM_SCORE = 250;
	private static final int MAX_TRICKS = 10;
	private static final int WAIT_TIME = 1500;

	// Eliot Notify v2 [ ####-####-#### 
	private GameStatistics aStats = new GameStatistics();
	private GUI aGUI;
	/**
	 *
	 */
	public static enum state
	{
		GAME_CONSTRUCTED, GAME_NEW, GAME_WIDOW_EXCHANGE, GAME_TRICK, GAME_TRICK_STATS, GAME_SCORE, GAME_END,
		CARDS_DEALT, GAME_WIDOW_EXCHANGE_DONE, CARDS_PLAY, CARDS_UPDATE, 
		PLAYER_BIDS, PLAYER_GETS_CONTRACT, 
		TEAM_MAKES_CONTRACT, TEAM_LOSES_CONTRACT, TEAM_DEFENDERS_SCORE,
		GUI_WAITING_BID, GUI_WAITING_EXCHANGE, GUI_WAITING_PLAYCARD, GUI_WAITING_GAMESTART,
		GAME_AI_CHANGE
	}
	
	/**
	 * 
	 *
	 */
	public static enum mode
	{
		GUI, CONSOLE, NULL
	}
	
	private state aCurrentState;
	private Player aStateRelativePlayer;
	private Team aStateRelativeTeam;
	private Bid aStateRelativeBid;
	private CardList aStateRelativeCardList;
	private Card aStateRelativeCard;
	private int aStateRelativeInt;
	private Thread aStateRelativeThread;
	
	private Strategy aFirstPlayerStrategy = Strategy.BASIC;
	private Strategy aSecondPlayerStrategy = Strategy.ADVANCED;
	private Strategy aThirdPlayerStrategy = Strategy.BASIC;
	private Strategy aFourthPlayerStrategy = Strategy.ADVANCED;
	
	private Bid aGUIBidReply = null;
	private CardList aGUICardListReply = null;
	private Card aGUICardReply = null;
	private boolean aGUICardAutoReply = false;
	
	private int aTrickCounter;
	// Used to force Java to stay in Waiting Loops and not exit them automatically
	private int aLooper;
	
	// ####-####-#### ] Eliot Notify v2
	
	//game variables
	private int aTricksBid;
	private Player aBidWinner;
	private Player aLastTrickWinner;
	private Bid aHighBid;
	private Bid[] aAllBids = new Bid[4];
	private Player[] aTmpPlayerList = new Player[4];
	private Player[] aPlayerList = new Player[4];
	private Team [] aTeamList = new Team[2];
	private Scanner aKeyboardReader = new Scanner(System.in);
	private boolean aHumansAllowed = false;
	private mode aMode = mode.CONSOLE;
	private CardList aWidow = new CardList();
	private int aStateRelativePlayerIndex;
	private int aStateRelativeTeamIndex;
	private boolean aGUIStartReply;
	private Strategy aStateRelativeStrategy;
	private int aStateRelativeStrategyIndex;
	

	
	/**
	 * Game constructor.
	 * @param pMode GameEngine Mode.
	 */
	public GameEngine(mode pMode)
	{
		this.aMode = pMode;
		// Eliot Notify v2 [ ####-####-#### 
		this.addObserver((Observer) aStats);
		if(pMode == mode.GUI)
		{
			this.aGUI = new GUI();
			String[] args = {"", ""};
			GUI.themain(args);
			this.addObserver((Observer) aGUI);
		}
		this.setCurrentState(state.GAME_CONSTRUCTED);
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
	}
	
	/**
	 * 
	 * @param pHumansAllowed Boolean determining if the game will ask for human player input or not
	 */
	public void setHumansAllowed(boolean pHumansAllowed)
	{
		this.aHumansAllowed = pHumansAllowed;
	}
	
	/**
	 * 
	 * @return whether or not to continue playing
	 */
	public boolean shouldContinue()
	{
		return !GUI.getWantToEnd();
	}
	
	/**
	 * 
	 * @param pStrat The player Strategy
	 * @param pIndex The player Index
	 */
	public void setPlayerStrategy(int pStrat, int pIndex)
	{
		this.setCurrentState(state.GAME_AI_CHANGE);
		Strategy lStrat = getStratFromIndex(pStrat);
		switch(pIndex)
		{
		case 0:
//			this.aFirstPlayerStrategy = lStrat;
			break;
		case 1:
			this.aSecondPlayerStrategy = lStrat;
			break;
		case 2:
			this.aThirdPlayerStrategy = lStrat;
			break;
		case 3:
			this.aFourthPlayerStrategy = lStrat;
			break;
		default:
			
		}
		this.setStateRelativeStrategy(lStrat);
		this.setStateRelativeInt(pIndex);
		this.notifyObservers();
	}
	
	private int getIndexFromStrat(Strategy pStrat)
	{
		switch(pStrat)
		{
		case RANDOM:
			return 0;
		case BASIC:
			return 1;
		case ADVANCED:
			return 2;
		default:
			return -1;
		}
	}
	
	private Strategy getStratFromIndex(int pIndex)
	{
		Strategy lStrat;
		switch(pIndex)
		{
		case 0:
			lStrat = Strategy.RANDOM;
			break;
		case 1:
			lStrat = Strategy.BASIC;
			break;
		case 2:
			lStrat = Strategy.ADVANCED;
			break;
		default:
			lStrat = Strategy.RANDOM;
		}
		return lStrat;
	}
	
	private void setStateRelativeStrategy(Strategy pStrat)
	{
		this.aStateRelativeStrategy = pStrat;
		this.setStateRelativeStrategyIndex(getIndexFromStrat(pStrat));
	}
	
	/**
	 * 
	 * @return the State Relative strategy
	 */
	public Strategy getStateRelativeStrategy()
	{
		return this.aStateRelativeStrategy;
	}
	
	private void setStateRelativeStrategyIndex(int pIndex)
	{
		this.aStateRelativeStrategyIndex = pIndex;
	}
	
	/**
	 * 
	 * @return the State Relative strategy
	 */
	public int getStateRelativeStrategyIndex()
	{
		return this.aStateRelativeStrategyIndex;
	}

	/**
	 * Creates new game.
	 */
	public void newgame()
	{
		if(this.aMode == mode.GUI)
		{
			receiveGUIStart();
		}
		aTeamList[0] = new Team();
		aTeamList[1] = new Team();
		aPlayerList = new Player[4];
		int interactive = 0;
		if(this.aHumansAllowed)
		{
			System.out.println("interactive mode 1/0");
			interactive = aKeyboardReader.nextInt();
		}
		switch(aMode)
		{
		case GUI:
			aPlayerList[0] = new Player("Player GUI", Strategy.GUI, aTeamList[0]);
			aPlayerList[1] = new Player("Player  2 ", aSecondPlayerStrategy, aTeamList[1]);
			aPlayerList[2] = new Player("Player  3 ", aThirdPlayerStrategy , aTeamList[0]);
			aPlayerList[3] = new Player("Player  4 ", aFourthPlayerStrategy, aTeamList[1]);
			break;
		default:
			if(interactive == 1)
			{
				aPlayerList[0] = new Player("Player IOC", Strategy.PLAYER, aTeamList[0]);
			}
			else
			{
				aPlayerList[0] = new Player("BasicA    ", aFirstPlayerStrategy, aTeamList[0]);
			}
			aPlayerList[1] = new Player("AdvancedA ", aSecondPlayerStrategy, aTeamList[1]);
			aPlayerList[2] = new Player("BasicB    ", aThirdPlayerStrategy , aTeamList[0]);
			aPlayerList[3] = new Player("AdvancedB ", aFourthPlayerStrategy, aTeamList[1]);
		}


		
		aPlayerList[0].setNextPlayer(aPlayerList[1]);
		aPlayerList[1].setNextPlayer(aPlayerList[2]);
		aPlayerList[2].setNextPlayer(aPlayerList[3]);
		aPlayerList[3].setNextPlayer(aPlayerList[0]);
		aPlayerList[0].setShortName("P1");
		aPlayerList[1].setShortName("P2");
		aPlayerList[2].setShortName("P3");
		aPlayerList[3].setShortName("P4");
		aTeamList[1].setPlayer(aPlayerList[1], 0);
		aTeamList[1].setPlayer(aPlayerList[3], 1);
		aTeamList[0].setPlayer(aPlayerList[0], 0);
		aTeamList[0].setPlayer(aPlayerList[2], 1);
		Random randomGen = new Random();
		aLastTrickWinner = aPlayerList[randomGen.nextInt(4)];
		for(int i = 0; i < 4; i++)
		{
			this.getPlayerList()[i].setIndex(i);
			if(this.getPlayerList()[i].getAdvPlayStrat() != null)
			{
				this.addObserver((Observer) this.getPlayerList()[i].getAdvPlayStrat());
			}
		}
		
		// Eliot Notify v2 [ ####-####-#### 
		this.setCurrentState(state.GAME_NEW);
		this.setTrickCounter(0);
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
		
	}
	
	/**
	 * Deals.
	 */
	public void deal()
	{
		for(Player p: aPlayerList)
		{
			p.removeAllCards();
		}
		aWidow = new CardList();
		
		Deck shuffledDeck = new Deck();
		shuffledDeck.shuffle();
		
		for(Player p: aPlayerList)
		{
			for(int i = 0; i < NUMBER_CARDS; i++)
			{
				p.addCard(shuffledDeck.draw());
			}
		}
		while(shuffledDeck.size()>0)
		{
			aWidow.add(shuffledDeck.draw());
		}
		
		// Eliot Notify v2 [ ####-####-#### 
		this.setCurrentState(state.CARDS_DEALT);
		this.setStateRelativeCardList(this.getPlayerList()[0].getHand());
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
	}
	
	/**
	 * 
	 * @throws IOException throws I/0 exception.
	 */
	public void bid() throws IOException
	{
		printStringBoolean(aHumansAllowed, "-----BIDDING START-----");
		int inc = 0;
		Player p = aLastTrickWinner;
		for(int y = 0; y < 4; y++)
		{
			Bid playerBid = new Bid();
			Bid[] partialBids = new Bid[inc];
			Strategy playerStrategy = p.getStrat();
			IBiddingStrategy playerObjectStrategy = p.getObjectBiddingStrat();
			switch(playerStrategy)
			{
				case PLAYER:
					playerBid = getBidFromPlayer(p);
					aAllBids[inc] = playerBid;
					aTmpPlayerList[inc] = p;
					break;
				case GUI:
					playerBid = receiveGUIBid();
					aAllBids[inc] = playerBid;
					aTmpPlayerList[inc] = p;
					break;
				default:
					for(int i = 0; i < inc; i++)
					{
						partialBids[i] = aAllBids[i];
					}
					playerBid = playerObjectStrategy.selectBid(partialBids, p.getHand());
					aAllBids[inc] = playerBid;
					aTmpPlayerList[inc] = p;
			}
			
			// Eliot Notify v2 [ ####-####-#### 
			this.setCurrentState(state.PLAYER_BIDS);
			this.setStateRelativePlayer(p);
			this.setStateRelativeBid(playerBid);
			this.notifyObservers();
			// ####-####-#### ] Eliot Notify v2
			

			if(aHumansAllowed)
			{
				System.out.println(p.getName() + " bid " + playerBid.toString());
			}
			
			p = p.getNextPlayer();
			inc++;
			
		}
		
		aHighBid = Bid.max(aAllBids);
		if(aHumansAllowed)
		{
			System.out.println("HIGH BID = " + aHighBid.toString());
		}
		
		for(int i = 0; i < 4; i++)
		{
			if(aAllBids[i] == aHighBid)
			{
				aBidWinner = this.aTmpPlayerList[i];
			}
		}
		
//		for(int i = 0; i < 4; i++)
//		{
//			if(aAllBids[i] == aHighBid)
//			{
//				aBidWinner = aPlayerList[i];
//			}
//		}

		// Eliot Notify v2 [ ####-####-#### 
		this.setCurrentState(state.PLAYER_GETS_CONTRACT);
		this.setStateRelativePlayer(aBidWinner);
		this.setStateRelativeBid(aHighBid);
		this.setStateRelativeTeam(aBidWinner.getTeam());
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
		
		aLastTrickWinner = aBidWinner;
		if(!aHighBid.isPass())
		{
			aTricksBid = aHighBid.getTricksBid();
		}
		printStringBoolean(aHumansAllowed, "-----BIDDING END-----");
	}
	
	/**
	 * Exchanges cards.
	 */
	public void exchange()
	{
		// Eliot Notify v2 [ ####-####-#### 
		this.setCurrentState(state.GAME_WIDOW_EXCHANGE);
		this.setStateRelativePlayer(aBidWinner);
		this.setStateRelativeCardList(aWidow);
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
		
		printStringBoolean(aHumansAllowed, "----EXCHANGE START-----");
		
		Strategy playerStrategy = aBidWinner.getStrat();
		
		while(aWidow.size()>0)
		{
			Card tempCard = aWidow.getFirst();
			aBidWinner.addCard(tempCard);
			aWidow.remove(tempCard);
		}
		
		switch(playerStrategy)
		{
		case PLAYER:			
			runPlayerExchange();	
			break;
		case GUI:
			CardList discardGUI = receiveGUICardList();
			for(Card c: discardGUI)
			{
				aWidow.add(c);
				aBidWinner.removeCard(c);
			}

			// Eliot Notify v2 [ ####-####-#### 
			this.setStateRelativeCardList(discardGUI);
			// ####-####-#### ] Eliot Notify v2
			
			break;
		default:
			ICardExchangeStrategy playerObjectStrategy = aBidWinner.getObjectExchangeStrat();
			CardList discardRandom = new CardList();
			int playerIndex = 0;
			for(int i = 0; i < 4; i++)
			{
				if(this.getPlayerList()[i] == aBidWinner)
				{
					playerIndex = i; 
				}
			}
			discardRandom = playerObjectStrategy.selectCardsToDiscard(
					aAllBids, playerIndex, aBidWinner.getHand());
			for(Card c: discardRandom)
			{
				aWidow.add(c);
				aBidWinner.removeCard(c);
			}

			// Eliot Notify v2 [ ####-####-#### 
			this.setStateRelativeCardList(discardRandom);
			// ####-####-#### ] Eliot Notify v2
			
		}
		
		aLastTrickWinner = aBidWinner;
		if(aHumansAllowed)
		{
			System.out.println("-----EXCHANGE END-----");
		}

		// Eliot Notify v2 [ ####-####-#### 
		this.setCurrentState(state.GAME_WIDOW_EXCHANGE_DONE);
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
		
		// Eliot Notify v2 [ ####-####-#### 
		this.setCurrentState(state.CARDS_UPDATE);
		this.setStateRelativePlayer(aBidWinner);
		this.setStateRelativeCardList(aBidWinner.getHand());
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
		
	}
	
	/**
	 * 
	 * @throws IOException throws I/0 exception.
	 */
	public void playTrick() throws IOException
	{
		// Eliot Notify v2 [ ####-####-#### 
		this.setCurrentState(state.GAME_TRICK);
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
		
		if(aHumansAllowed)
		{
			System.out.println("-----TRICK-----");
		}
		Trick playedTrick = new Trick(aHighBid);
		Player p = aLastTrickWinner;
		Player [] trickOrder = new Player[4];

		

		
		for(int i = 0; i < 4; i++)
		{
			Strategy strat = p.getStrat();
			IPlayingStrategy playerObjectStrategy = p.getObjectPlayingStrat();
			Card playingCard = null;
			
			// Eliot Notify v2 [ ####-####-#### 
			this.setStateRelativePlayer(p);
			// ####-####-#### ] Eliot Notify v2

			switch(strat)
			{
			case PLAYER:
				playingCard = getCardFromPlayer(p, playedTrick);
				break;
			case GUI:
				this.setStateRelativeCard(playerObjectStrategy.play(playedTrick, p.getHand()));
				if(playedTrick.size() != 0)
				{
					this.setStateRelativeCardList(p.getHand().playableCards(
							playedTrick.getSuitLed(), playedTrick.getTrumpSuit()));
				}
				else if(p.getHand().getNonJokers().size() != 0)
				{
					this.setStateRelativeCardList(p.getHand().getNonJokers());
				}
				else
				{
					this.setStateRelativeCardList(p.getHand());
				}
				playingCard = receiveGUICard();
				break;
			default:
				playingCard = playerObjectStrategy.play(playedTrick, p.getHand());
				if(aHumansAllowed)
				{
					System.out.println("Player " + p.getName() + " played " + playingCard.toShortString());
				}
			}
			
			if(playingCard == null)
			{
				System.out.println("Engine received NULL card");
			}
				
			// Eliot Notify v2 [ ####-####-#### 
			// Notifies of the card being played
			this.setCurrentState(state.CARDS_PLAY);
			this.setStateRelativePlayer(p);
			this.setStateRelativeCard(playingCard);
			this.notifyObservers();
			// ####-####-#### ] Eliot Notify v2
			
			trickOrder[i] = p;
			playedTrick.add(playingCard);
			p.removeCard(playingCard);
			
			// Eliot Notify v2 [ ####-####-#### 
			// Notifies of the updated player hand
			this.setCurrentState(state.CARDS_UPDATE);
			this.setStateRelativeCardList(p.getHand());
			this.notifyObservers();
			// ####-####-#### ] Eliot Notify v2
			
			p = p.getNextPlayer();
			
		}
		
		int winner = playedTrick.winnerIndex();
		if(aHumansAllowed)
		{
			System.out.println("Winning card = " + playedTrick.highest().toShortString());
			System.out.println("Trick winner = " + trickOrder[winner].getName());
		}
		
		// Eliot Notify v2 [ ####-####-#### 
		// Notifies of the trick statistics
		this.setCurrentState(state.GAME_TRICK_STATS);
		this.setStateRelativePlayer(trickOrder[winner]);
		this.setStateRelativeTeam(trickOrder[winner].getTeam());
		this.setStateRelativeCard(playedTrick.highest());
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
		
		trickOrder[winner].incTricks();
		aLastTrickWinner = trickOrder[winner];
	}

	/**
	 * 
	 * @return if everyone passes.
	 */
	public boolean allPasses()
	{
		if(aHighBid.isPass())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * computes the score.
	 */
	public void computeScore()
	{
		Team team0 = aTeamList[0];
		Team team1 = aTeamList[1];
		
		// Eliot Notify v2 [ ####-####-#### 
		// Notifies of the trick statistics
		this.setCurrentState(state.GAME_SCORE);
		this.setStateRelativePlayer(aBidWinner);
		this.setStateRelativeTeam(aBidWinner.getTeam());
		this.setStateRelativeBid(aHighBid);
		this.notifyObservers();
		// ####-####-#### ] Eliot Notify v2
		
		boolean team0ContainsBidWinner = false;
		for(Player p: team0.getPlayers())
		{
			if(aBidWinner == p)
			{
				team0ContainsBidWinner = true;
			}
		}
		if(team0ContainsBidWinner)
		{
			//System.out.println("TEAM 0 MAKES IT");
			///
			int team0Tricks = 0;
			team0Tricks = team0.computeTricksWon();
			if(team0Tricks >= aTricksBid)
			{
				if(team0Tricks == MAX_TRICKS && aHighBid.getScore() < SLAM_SCORE)
				{
					team0.setScore(team0.getScore()+SLAM_SCORE);
				}
				else
				{
					team0.setScore(team0.getScore()+aHighBid.getScore());
				}
				
				// Eliot Notify v2 [ ####-####-#### 
				// Notifies of the trick statistics
				this.setCurrentState(state.TEAM_MAKES_CONTRACT);
				this.setStateRelativeTeam(team0);
				this.setStateRelativeInt(aHighBid.getScore());
				this.notifyObservers();
				// ####-####-#### ] Eliot Notify v2
				
				
			}
			else
			{
				team0.setScore(team0.getScore()-aHighBid.getScore());
				
				// Eliot Notify v2 [ ####-####-#### 
				// Notifies of the trick statistics
				this.setCurrentState(state.TEAM_LOSES_CONTRACT);
				this.setStateRelativeTeam(team0);
				this.setStateRelativeInt(aHighBid.getScore());
				this.notifyObservers();
				// ####-####-#### ] Eliot Notify v2
				
				
				
			}
			team1.setScore(team1.getScore()+(team1.computeTricksWon()*POINTS));
			
			// Eliot Notify v2 [ ####-####-#### 
			// Notifies of the trick statistics
			this.setCurrentState(state.TEAM_DEFENDERS_SCORE);
			this.setStateRelativeTeam(team1);
			this.setStateRelativeInt(team1.computeTricksWon()*POINTS);
			this.notifyObservers();
			// ####-####-#### ] Eliot Notify v2
		}
		else
		{
			//System.out.println("TEAM 1 MAKES IT");
			//////////////
			int team1Tricks = 0;
			team1Tricks = team1.computeTricksWon();
			if(team1Tricks >= aTricksBid)
			{
				if(team1Tricks == MAX_TRICKS && aHighBid.getScore() < SLAM_SCORE)
				{
					team1.setScore(team1.getScore()+SLAM_SCORE);
				}
				else
				{
					team1.setScore(team1.getScore()+aHighBid.getScore());
				}
				
				// Eliot Notify v2 [ ####-####-#### 
				// Notifies of the trick statistics
				this.setCurrentState(state.TEAM_MAKES_CONTRACT);
				this.setStateRelativeTeam(team1);
				this.setStateRelativeInt(aHighBid.getScore());
				this.notifyObservers();
				// ####-####-#### ] Eliot Notify v2
			}
			else
			{
				team1.setScore(team1.getScore()-aHighBid.getScore());
				
				// Eliot Notify v2 [ ####-####-#### 
				// Notifies of the trick statistics
				this.setCurrentState(state.TEAM_LOSES_CONTRACT);
				this.setStateRelativeTeam(team1);
				this.setStateRelativeInt(aHighBid.getScore());
				this.notifyObservers();
				// ####-####-#### ] Eliot Notify v2
			}
			team0.setScore(team0.getScore()+(team0.computeTricksWon()*POINTS));
			
			// Eliot Notify v2 [ ####-####-#### 
			// Notifies of the trick statistics
			this.setCurrentState(state.TEAM_DEFENDERS_SCORE);
			this.setStateRelativeTeam(team0);
			this.setStateRelativeInt(team0.computeTricksWon()*POINTS);
			this.notifyObservers();
			// ####-####-#### ] Eliot Notify v2
		}
		if(aHumansAllowed)
		{
			System.out.println("Team 0 score = " + team0.getScore());
			System.out.println("Team 1 score = " + team1.getScore());
		}
		
		for(Player p: aPlayerList)
		{
			p.setTricksToZero();
		}
		

		waitForABit();
		
	}
	
	private void waitForABit()
	{
		try
		{
			if(this.aMode == mode.GUI)
			{
			Thread.sleep(WAIT_TIME);
			}
		}
		catch(InterruptedException e)
		{
			//
		}
	}
	
	/**
	 * 
	 * @return if the game is still on going.
	 */
	public boolean gameStillOnGoing()
	{
		Team team0 = aTeamList[0];
		Team team1 = aTeamList[1];
		if(team0.getScore() >= MAXSCORE || team0.getScore() <= MINSCORE || team1.getScore() >= MAXSCORE || team1.getScore() <= MINSCORE)
		{
			
			// Eliot Notify v2 [ ####-####-#### 
			// Notifies of the trick statistics
			this.setCurrentState(state.GAME_END);
			

			
			if(team0.getScore() > team1.getScore())
			{				
				this.setStateRelativeTeam(team0);
			}
			else
			{
				this.setStateRelativeTeam(team1);				
			}
			
			this.notifyObservers();
			// ####-####-#### ] Eliot Notify v2
			
			
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param pPlayer a player.
	 */
	private static void printHand(Player pPlayer)
	{
		System.out.print("Your hand: ");
		for(Card c: pPlayer.getHand())
		{
			System.out.print(c.toShortString() + " ");
		}
		System.out.println();
	}
	
	/**
	 * 
	 * @param pPlayer a player.
	 * @param pTrick a trick.
	 */
	private static void printPlayableCardsHand(Player pPlayer, Trick pTrick)
	{
		System.out.print("Your hand (playable): ");
		for(Card c: pPlayer.getHand().playableCards(pTrick.getSuitLed(), pTrick.getTrumpSuit()))
		{
			System.out.print(c.toShortString() + " ");
		}
		System.out.println();
	}
	
	/**
	 * 
	 * @param pPlayer a player.
	 * @param pTrick a trick.
	 */
	private static void printPlayableCardsHandNoJoker(Player pPlayer, Trick pTrick)
	{
		System.out.print("Your hand (playable): ");
		for(Card c: pPlayer.getHand().getNonJokers())
		{
			System.out.print(c.toShortString() + " ");
		}
		System.out.println();
	}
	
	/**
	 * gets the player bid.
	 * @param pPlayer the player you want to get input from.
	 * this is for HUMANS
	 * @return Bid a player selects
	 * @throws IOException if input is out of range
	 */
	private Bid getBidFromPlayer(Player pPlayer) throws IOException
	{
		Bid playerBid = new Bid();
		//prompt for player input
		printHand(pPlayer);
		System.out.println("Pass 1/0");
		int pass = aKeyboardReader.nextInt();
		if(pass == 1)
		{
			return playerBid;
		}
		else if(pass != 0)
		{
			throw new IOException();
		}
		System.out.println("Bid");
		System.out.println("s = 0");
		System.out.println("c = 1");
		System.out.println("d = 2");
		System.out.println("h = 3");
		System.out.println("No Trumps = 4");
		int suit = aKeyboardReader.nextInt();
		if(suit > 4 || suit < 0)
		{
			throw new IOException();
		}
		System.out.println("Enter number of tricks, max 10, min 6");
		int playerRank = aKeyboardReader.nextInt();
		if(playerRank > MAXBID || playerRank < MINBID)
		{
			throw new IOException();
		}
		Suit playerSuit = null;
		switch(suit)
		{
		case 0:
			playerSuit = Suit.CLUBS;
			break;
		case 1:
			playerSuit = Suit.SPADES;
			break;
		case 2:
			playerSuit = Suit.DIAMONDS;
			break;
		case 3:
			playerSuit = Suit.HEARTS;
			break;
		case 4:
			playerSuit = null;
			break;
		default:
			break;
		}
		playerBid = new Bid(playerRank, playerSuit);
		return playerBid;
	}
	
	private void runPlayerExchange()
	{
		int cardsDiscarded = 0;
		
		// Eliot Notify v2 [ ####-####-#### 
		this.setStateRelativeCardList(new CardList());
		// ####-####-#### ] Eliot Notify v2
		
		while(cardsDiscarded < WIDOW_SIZE)
		{
			Card removeThisCard = null;
			for(Card c: aBidWinner.getHand())
			{
				System.out.print("Your hand: ");
				for(Card d: aBidWinner.getHand())
				{
					System.out.print(d.toShortString() + " ");
				}
				System.out.println();
				System.out.println("You have to discard " + (WIDOW_SIZE - cardsDiscarded) + " more cards");

				System.out.println("Do you want to discard this card? " + c.toShortString() + " 1/0");
				int input = aKeyboardReader.nextInt();
				if(input == 1)
				{
					removeThisCard = c;
					break;
				}
			}
			aWidow.add(removeThisCard);
			aBidWinner.removeCard(removeThisCard);
			
			// Eliot Notify v2 [ ####-####-#### 
			this.getStateRelativeCardList().add(removeThisCard);
			// ####-####-#### ] Eliot Notify v2
			
			cardsDiscarded++;
		}
		System.out.println("6 cards discarded, now lets play");
	}
	
	
	/**
	 * gets the player card for a trick.
	 * @param pPlayer the player you want to get input from.
	 * @param pTrick the trick you are currently in
	 * this is for HUMANS
	 * @return Card a player selects
	 * @throws IOException if input is out of range
	 */
	private Card getCardFromPlayer(Player pPlayer, Trick pTrick) throws IOException
	{
		boolean hasPlayed = false;
		Card playingCard = null;
		while(!hasPlayed)
		{
			if(pTrick.size() != 0)
			{
				for(Card c: pPlayer.getHand().playableCards(pTrick.getSuitLed(), pTrick.getTrumpSuit()))
				{
					printHand(pPlayer);
					printPlayableCardsHand(pPlayer, pTrick);
					System.out.println("do you want to play this card? " + c.toShortString() +" 1/0");
					int input = aKeyboardReader.nextInt();
					if(input > 1 || input < 0)
					{
						throw new IOException();
					}
					if(input == 1)
					{
						hasPlayed = true;

						System.out.println("You played " + c.toShortString());
						playingCard = c;
						break;
					}
				}
			}
			else if(pPlayer.getHand().getNonJokers().size() != 0)
			{
				for(Card c: pPlayer.getHand().getNonJokers())
				{
					printPlayableCardsHandNoJoker(pPlayer, pTrick);
					System.out.println("do you want to play this card? " + c.toShortString() +" 1/0");
					int input = aKeyboardReader.nextInt();
					if(input > 1 || input < 0)
					{
						throw new IOException();
					}
					if(input == 1)
					{
						hasPlayed = true;
						
						System.out.println("You played " + c.toShortString());
						playingCard = c;
						break;
					}
				}
			}
			else
			{
				for(Card c: pPlayer.getHand())
				{
					printHand(pPlayer);
					System.out.println("do you want to play this card? " + c.toShortString() +" 1/0");
					int input = aKeyboardReader.nextInt();
					if(input > 1 || input < 0)
					{
						throw new IOException();
					}
					if(input == 1)
					{
						hasPlayed = true;
						System.out.println("You played " + c.toShortString());
						playingCard = c;
						break;
					}
				}
			}
		}
		return playingCard;
	}
	
	/**
	 * prints the inputted string if the boolean is true.
	 * @param pBool the boolean that controls printing
	 * @param pString the string you want printed
	 * this is for reducing complexity
	 */
	private static void printStringBoolean(boolean pBool, String pString)
	{
		if(pBool)
		{
			System.out.println(pString);
		}
	}
	/**
	 * Testing purposes.
	 * @return PlayerList
	 */
	public Player[] getPlayerList()
	{
		return this.aPlayerList;
	}
	/**
	 * Testing purposes.
	 * @return TeamList
	 */
	public Team[] getTeamList()
	{
		return this.aTeamList;
	}
	/**
	 * Testing purposes.
	 * @return Widow
	 */
	public CardList getWidow()
	{
		return this.aWidow;
	}
	/**
	 * Testing purposes.
	 * @return BidWinner
	 */
	public Player getBidWinner()
	{
		return this.aBidWinner;
	}
	/**
	 * Testing purposes.
	 * @return LastTrickWinner
	 */
	public Player getLastTrickWinner()
	{
		return this.aLastTrickWinner;
	}
	/**
	 * Testing purposes.
	 * @return HighBid
	 */
	public Bid getHighBid()
	{
		return this.aHighBid;
	}
	
	
	
	
	// Eliot Notify v2 [ ####-####-#### 

	/**
	 * @return .
	 */
	public state getCurrentState()
	{
		return aCurrentState;
	}

	private void setCurrentState(state pCurrentState)
	{
		this.aCurrentState = pCurrentState;
		this.setChanged();
	}

	/**
	 * @return the aStateRelativePlayer
	 */
	public Player getStateRelativePlayer()
	{
		return aStateRelativePlayer;
	}
	
	/**
	 * @return the aStateRelativePlayer
	 */
	public int getStateRelativePlayerIndex()
	{
		return aStateRelativePlayerIndex;
	}

	private void setStateRelativePlayerIndex(int pIndex)
	{
		this.aStateRelativePlayerIndex = pIndex;
		
	}
	
	private void setStateRelativePlayer(Player pPlayer)
	{
		this.aStateRelativePlayer = pPlayer;
		this.setStateRelativePlayerIndex(pPlayer.getIndex());
	}

	/**
	 * @return the aStateRelativeBid
	 */
	public Bid getStateRelativeBid()
	{
		return aStateRelativeBid;
	}

	private void setStateRelativeBid(Bid pStateRelativeBid)
	{
		this.aStateRelativeBid = pStateRelativeBid;
	}

	/**
	 * @return the aStateRelativeCardList
	 */
	public CardList getStateRelativeCardList()
	{
		return aStateRelativeCardList;
	}

	private void setStateRelativeCardList(CardList pStateRelativeCardList)
	{
		this.aStateRelativeCardList = pStateRelativeCardList;
	}

	/**
	 * @return the aTrickCounter
	 */
	public int getTrickCounter()
	{
		return aTrickCounter;
	}

	private void setTrickCounter(int pTrickCounter)
	{
		this.aTrickCounter = pTrickCounter;
	}

	/**
	 * @return the aStateRelativeCard
	 */
	public Card getStateRelativeCard()
	{
		return aStateRelativeCard;
	}

	private void setStateRelativeCard(Card pStateRelativeCard)
	{
		this.aStateRelativeCard = pStateRelativeCard;
	}

	/**
	 * @return the aStateRelativeTeam
	 */
	public Team getStateRelativeTeam()
	{
		return aStateRelativeTeam;
	}

	private void setStateRelativeTeam(Team pStateRelativeTeam)
	{
		this.aStateRelativeTeam = pStateRelativeTeam;
		for(int i = 0; i < 2; i++)
		{
			if(this.getTeamList()[i].equals(pStateRelativeTeam))
			{
				this.setStateRelativeTeamIndex(i);
			}
		}
	}
	
	/**
	 * @return the aStateRelativeTeamIndex
	 */
	public int getStateRelativeTeamIndex()
	{
		return aStateRelativeTeamIndex;
	}

	private void setStateRelativeTeamIndex(int pStateRelativeTeamIndex)
	{
		this.aStateRelativeTeamIndex = pStateRelativeTeamIndex;
	}

	/**
	 * @return the aStateRelativeInt
	 */
	public int getStateRelativeInt()
	{
		return aStateRelativeInt;
	}

	private void setStateRelativeInt(int pStateRelativeInt)
	{
		this.aStateRelativeInt = pStateRelativeInt;
	}
	
	private Bid receiveGUIBid()
	{
		this.setCurrentState(state.GUI_WAITING_BID);
		this.setStateRelativeThread(Thread.currentThread());
		this.notifyObservers();
		for(int i = 0; i >= 0; i++)
		{ 
			// Wait for GUI to push result
			if(this.aGUIBidReply != null)
			{
				break;
			}
			else
			{
				this.setLooper(this.getLooper() + 1);
				System.out.print("");
			}
		}
//		while(this.aGUIBidReply == null)
//		{
//			System.out.print("");
//			// Wait for GUI to push result
//		}
		Bid lReturnBid = this.aGUIBidReply;
		this.aGUIBidReply = null;
		return lReturnBid;
	}
	
	private CardList receiveGUICardList()
	{
		this.setCurrentState(state.GUI_WAITING_EXCHANGE);
		this.setStateRelativeThread(Thread.currentThread());
		this.notifyObservers();
		for(int i = 0; i >= 0; i++)
		{ 
			// Wait for GUI to push result
			if(this.aGUICardListReply != null)
			{
				break;
			}
			else
			{
				this.setLooper(this.getLooper() + 1);
				System.out.print("");
			}
		}
		CardList lReturnCardList = this.aGUICardListReply;
		this.aGUICardListReply = null;
		return lReturnCardList;
	}
	
	private void receiveGUIStart()
	{
		this.setCurrentState(state.GUI_WAITING_GAMESTART);
		this.setStateRelativeThread(Thread.currentThread());
		this.notifyObservers();
		for(int i = 0; i >= 0; i++)
		{ 
			// Wait for GUI to push result
			if(this.aGUIStartReply)
			{
				this.aGUIStartReply = false;
				break;
			}
			else
			{
				this.setLooper(this.getLooper() + 1);
				System.out.print("");
			}
		}
	}
	
	private Card receiveGUICard()
	{
		this.setCurrentState(state.GUI_WAITING_PLAYCARD);
		this.setStateRelativeThread(Thread.currentThread());
		this.notifyObservers();
		for(int i = 0; i >= 0; i++)
		{ 
			// Wait for GUI to push result
			if(this.aGUICardReply != null || this.aGUICardAutoReply)
			{
				break;
			}
			else
			{
				this.setLooper(this.getLooper() + 1);
				System.out.print("");
			}
		}
		if(this.aGUICardAutoReply)
		{
			this.aGUICardAutoReply = false;
			return this.aStateRelativeCard;
		}
		else
		{
			Card lReturnCard = this.aGUICardReply;
			this.aGUICardReply = null;
			return lReturnCard;
		}
	}
	
	/**
	 * 
	 * @param pBid Bid that the player currently bids
	 */
	public void setGUIPlayerBid(Bid pBid)
	{
		this.aGUIBidReply = pBid;
	}
	
	/**
	 * 
	 * @param pCardList CardList of the cards being discarded by the player
	 */
	public void setGUIPlayerExchange(CardList pCardList)
	{
		this.aGUICardListReply = pCardList;
	}
	
	/**
	 * 
	 * @param pCard Card that the player chooses to play
	 */
	public void setGUIPlayerCard(Card pCard)
	{
		this.aGUICardReply = pCard;
	}
	
	/**
	 * 
	 */
	public void setGUIPlayerCardAuto()
	{
		this.aGUICardAutoReply = true;
	}

	/**
	 * @return the aStateRelativeThread
	 */
	public Thread getStateRelativeThread()
	{
		return aStateRelativeThread;
	}

	private void setStateRelativeThread(Thread pStateRelativeThread)
	{
		this.aStateRelativeThread = pStateRelativeThread;
	}
	
	/**
	 * 
	 */
	public void setGUIGameStart()
	{
		this.aGUIStartReply = true;
	}
	
	/**
	 * 
	 */
	public void resetStatistics()
	{
		this.aStats.resetStatistics();
	}

	/**
	 * @return the aLooper
	 */
	public int getLooper()
	{
		return aLooper;
	}

	/**
	 * @param pLooper the aLooper to set
	 */
	public void setLooper(int pLooper)
	{
		this.aLooper = pLooper;
	}
	
	
	// ####-####-#### ] Eliot Notify v2
}