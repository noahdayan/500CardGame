// Eliot hautefeuille Oct 27th 2012 22:40
package comp303.fivehundred.engine;

import java.util.Observable;
import java.util.Observer;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Game Statistics class.
 * @author Eliot Hautefeuille
 */
public class GameStatistics implements Observer
{	
	public static final String NEW_GAME = "============================== NEW GAME ==============================";
	public static final String GAME_INITIALIZED_BY = "Game initialized. Initial dealer: ";
	public static final String NEW_DEAL = "******************** NEW DEAL ********************";
	public static final String CARDS_DEALT = "Players dealt cards";
	public static final String HAS_CARDS = "\t has cards :  ";
	public static final String WIDOW_HAS_CARDS = "The widow contains:   ";
	public static final String HAS_CONTRACT = " has the contract of ";
	public static final String DISCARDS_CARDS = " discards :   ";
	public static final String BIDS = "\t bids ";
	public static final String PLAYS = "\t plays ";
	public static final String WINS_TRICK = "\t wins the trick";
	public static final String MAKE_CONTRACT = " make their contract!";
	public static final String LOSE_CONTRACT = " lose their contract!";
	public static final String WON = " won ";
	public static final String TRICKS = " tricks";
	public static final String CONTRACTORS_SCORE = "Contractor round score: ";
	public static final String DEFENDERS_SCORE = "Defender round score: ";
	public static final String TOTAL_SCORE = " Total score: ";
	public static final int NUMBER_TRICKS = 10;
	
	private static Map<String, PlayerStatistics> aCumPlayerData = new HashMap<String, PlayerStatistics>();
	private static int aGameSum = 0;
	private static int aPreviousGameSum = 0;
	
	private static boolean aLoggingOn = false;
	private static boolean aShowProgress = false;
	
	private Logger aLog = null;
	private File aFile = null;
	private FileWriter aFileWriter = null;
	private int aTrickNum = 0; 

	
	private Map<String, PlayerStatistics> aCurPlayerData = new HashMap<String, PlayerStatistics>();
	
	/**
	 * Stores and computes individual current and cumulative player statistics.
	 */ // 
	private class PlayerStatistics
	{
		private static final int PERCENT = 100;
		private static final int FIVE_HUNDRED = 500;
		private static final int ONE = 1;
		private static final int TWO = 2;
		private static final int THREE = 3;
		private static final int FOUR = 4;
		private static final int FIVE = 5;
		private static final int SIX = 6;
		private static final int SEVEN = 7;
		private static final int EIGHT = 8;
		
		private String aName = null;
		private int aTricksWon = 0;
		private int aTricksLost = 0;
		private int aContractWon = 0;
		private int aContractLost = 0;
		private int aMadeWon = 0;
		private int aMadeLost = 0;
		private int aGamesWon = 0;
		private int aGamesLost = 0;	
		private int aScore = 0;
		
		public PlayerStatistics(String pName)
		{
			this.aName = pName;
		}
		public void reset()
		{
			this.aContractLost = 0;
			this.aContractWon = 0;
			this.aMadeLost = 0;
			this.aMadeWon = 0;
			this.aTricksLost = 0;
			this.aTricksWon = 0;
			this.aScore = 0;
			this.aGamesWon = 0;
			this.aGamesLost = 0;
		}
		
		public void wonTrick()
		{
			this.aTricksWon++;
		}
		public void lostTrick()
		{
			this.aTricksLost++;
		}
		public void wonContract()
		{
			this.aContractWon++;
		}
		public void lostContract()
		{
			this.aContractLost++;
		}
		public void wonMade()
		{
			this.aMadeWon++;
		}
		public void lostMade()
		{
			this.aMadeLost++;
		}
		
		public void wonGame()
		{
			this.aGamesWon++;
		}
		public void lostGame()
		{
			this.aGamesLost++;
		}
		
		public int[] cumExport()
		{
			int[] cumExport = {this.aTricksWon, this.aTricksLost, this.aContractWon, this.aContractLost,
					this.aMadeWon, this.aMadeLost, this.aGamesWon, this.aGamesLost, this.aScore};
			return cumExport;
		}
		
		public String stats()
		{
			DecimalFormat df = new DecimalFormat("##.#");
			DecimalFormat df2 = new DecimalFormat("#.##");
			
			String lReturn = this.aName + "\t";
			double tricksTotal = this.aTricksWon + this.aTricksLost;
			double tricksPer = ((double) this.aTricksWon) * PERCENT / tricksTotal;
			double contTotal = this.aContractWon + this.aContractLost;
			double contPer = ((double) this.aContractWon)  * PERCENT / contTotal;
			double madeTotal = this.aMadeWon + this.aMadeLost;
			double madePer = ((double) this.aMadeWon) * PERCENT / madeTotal;
			if(madeTotal == 0)
			{
				madePer = 0;
			}
			double gameTotal = this.aGamesWon + this.aGamesLost;
			double gamePer = ((double) this.aGamesWon) * PERCENT / gameTotal;
			double scoreIndex = ((double) this.aScore) / ( aGameSum * FIVE_HUNDRED);
			lReturn += df.format(tricksPer) + "%\t" +
					df.format(contPer) + "%\t" +
					df.format(madePer) + "%\t" +
					df.format(gamePer) + "%\t" +
					df2.format(scoreIndex) + "\n";
			return lReturn;
		}
		
		public void score(int pScore)
		{
			this.aScore += pScore;
		}
		
		public void cumImport(PlayerStatistics pImportee)
		{
			int [] cumImport = pImportee.cumExport();
			this.aTricksWon += cumImport[0];
			this.aTricksLost += cumImport[ONE];
			this.aContractWon += cumImport[TWO];
			this.aContractLost += cumImport[THREE];
			this.aMadeWon += cumImport[FOUR];
			this.aMadeLost += cumImport[FIVE];
			this.aGamesWon += cumImport[SIX];
			this.aGamesLost += cumImport[SEVEN];
			this.aScore += cumImport[EIGHT];
		}
	}
	
	/**
	 * GameStatistic Constructor | Initializes aLogger.
	 */
	public GameStatistics()
	{
		System.setProperty("org.slf4j.simpleaLogger.defaultaLog", "info");
		aLog = LoggerFactory.getLogger(GameStatistics.class);
		aFile = new File(System.getProperty("user.home") + "/" + "transcript.log");
		resetFileWriter();
	}
	
	private void resetFileWriter()
	{
		if(aFileWriter != null)
		{
			try
			{
				aFileWriter.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			aFileWriter = new FileWriter(aFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// Switches over current stats to cumulative stats
	private void cumSync()
	{
		//Iterate over the current player's stats
		Set<String> keysSet = this.aCurPlayerData.keySet();
		Iterator<String> keyIter = keysSet.iterator();
		while(keyIter.hasNext())
		{
			String key = keyIter.next();
			
			//If there didn't exist cumulative stats for that player,
			//then put them in
			if(aCumPlayerData.get(key) == null)
			{
				aCumPlayerData.put(key, this.aCurPlayerData.get(key));
			}
			//Else, add the cumulative and current stats for that player.
			else
			{
				aCumPlayerData.get(key).cumImport(this.aCurPlayerData.get(key));
			}
		}
		this.aCurPlayerData = new HashMap<String, PlayerStatistics>();
		// ResetFileWriter to push all buffered output to file
		//resetFileWriter();
	}
	
	// Adds a player to the current game stats
	private void player(String pName)
	{		
		// If this player didn't exist, add it.
		if(this.aCurPlayerData.get(pName) == null)
		{
			this.aCurPlayerData.put(pName, new GameStatistics.PlayerStatistics(pName));
		}
		// Make sure it is set up properly by resetting it.
		this.aCurPlayerData.get(pName).reset();
	}
	
	/**
	 *  Inits the stats of the players for the game.
	 * @param pDealer Name of the initial Dealer
	 * @param pPlayerB Name of the second Player
	 * @param pPlayerC Name of the third Player
	 * @param pPlayerD Name of the fourth Player
	 */
	public void initGame(String pDealer, String pPlayerB, String pPlayerC, String pPlayerD)
	{
		aGameSum++;
		this.player(pDealer);
		this.player(pPlayerB);
		this.player(pPlayerC);
		this.player(pPlayerD);
	}
	
	/**
	 *  Sets Game winning and losing stats, and synchronizes cumulative and current stats.
	 *  Last Expected function to be ran in this instance's life.
	 * @param pWinnerA First winning player name
	 * @param pWinnerB Second winning player name
	 * @param pLoserA First loser player name
	 * @param pLoserB Second loser player name
	 */
	public void endGame(String pWinnerA, String pWinnerB, String pLoserA, String pLoserB)
	{
		this.aCurPlayerData.get(pWinnerA).wonGame();
		this.aCurPlayerData.get(pWinnerB).wonGame();
		this.aCurPlayerData.get(pLoserA).lostGame();
		this.aCurPlayerData.get(pLoserB).lostGame();
		this.cumSync();
		try
		{
			this.aFileWriter.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 *  Adds a score to a player's stats.
	 * @param pPlayer Player's name
	 * @param pScore The score value
	 */
	public void score(String pPlayer, int pScore)
	{
		this.aCurPlayerData.get(pPlayer).score(pScore);
	}
	
	/**
	 * Adds a score to 2 players' stats.
	 * @param pPlayerA First player's name
	 * @param pPlayerB Second player's name
	 * @param pScore The score value
	 */
	public void score(String pPlayerA, String pPlayerB, int pScore)
	{
		this.aCurPlayerData.get(pPlayerA).score(pScore);
		this.aCurPlayerData.get(pPlayerB).score(pScore);
	}
	
	/**
	 * Register statistics for a complete bidding: a final contract.
	 * @param pWinnerA first winning player
	 * @param pWinnerB second winning player
	 * @param pLoserA other player
	 * @param pLoserB other player
	 */
	public void contract(String pWinnerA, String pWinnerB, String pLoserA, String pLoserB)
	{
		this.aCurPlayerData.get(pWinnerA).wonContract();
		this.aCurPlayerData.get(pWinnerB).wonContract();
		this.aCurPlayerData.get(pLoserA).lostContract();
		this.aCurPlayerData.get(pLoserB).lostContract();
	}
	
	/**
	 * Register statistics for a complete contract.
	 * @param pWinnerA first winning player
	 * @param pWinnerB second winning player
	 * @param pMade was the contract made or failed
	 */
	public void contractComplete(String pWinnerA, String pWinnerB, boolean pMade)
	{
		if(pMade)
		{
			this.aCurPlayerData.get(pWinnerA).wonMade();
			this.aCurPlayerData.get(pWinnerB).wonMade();
		}
		else
		{
			this.aCurPlayerData.get(pWinnerA).lostMade();
			this.aCurPlayerData.get(pWinnerB).lostMade();
		}
	}
	
	/**
	 * Register statistics for a complete trick.
	 * @param pWinnerA Winner player
	 * @param pLoserA other player
	 * @param pLoserB other player
	 * @param pLoserC other player
	 */
	public void trickComplete(String pWinnerA, String pLoserA, String pLoserB, String pLoserC)
	{
		this.aCurPlayerData.get(pWinnerA).wonTrick();
		this.aCurPlayerData.get(pLoserA).lostTrick();
		this.aCurPlayerData.get(pLoserB).lostTrick();
		this.aCurPlayerData.get(pLoserC).lostTrick();
	}
	
	/**
	 * Prints out statistics for all past games.
	 */
	public static void printStatistics()
	{
		//this.cumSync();
		String lReturn = "";
		lReturn += "Game played : " + aGameSum + "\n\n";
		lReturn += "\t\t\tTrick \tCont \tMade \tGame \tScore\n";
		
		//Iterate over the players
		//Set<String> keysSet = this.aCumPlayerData.keySet();
		Set<String> keysSet = aCumPlayerData.keySet();
		Iterator<String> keyIter = keysSet.iterator();
		while(keyIter.hasNext())
		{
			String key = keyIter.next();
			//lReturn += this.aCumPlayerData.get(key).stats();
			lReturn += "\t" + aCumPlayerData.get(key).stats();
		}
		
		System.out.println(lReturn);
	}
	
	/**
	 * Returns a string with the correct trick number for logging purposes.
	 * @return a string with the correct trick number for logging purposes
	 */
	public String logTrick()
	{
		aTrickNum++;
		String lText = "---- TRICK " + aTrickNum + " ----";
		if(aTrickNum == NUMBER_TRICKS)
		{
			aTrickNum = 0;
		}
		return lText;
	}
	
	/**
	 * java.util.Observer.update .
	 * @param pGame Game object
	 * @param pResponse String what to log
	 */
	public void update(Observable pGame, Object pResponse)
	{
		//this.log((String)pResponse);
		if(pResponse == null)
		{
			this.process((GameEngine) pGame);
		}
	}

	private void log(String pResponse)
	{
		if(aLoggingOn)
		{
			if(aShowProgress)
			{
				this.pushToLogSystems(aGameSum + " - \t"+ pResponse);
			}
			else
			{
				this.pushToLogSystems(pResponse);
			}
		}
		else
		{
			if(aShowProgress && aGameSum > aPreviousGameSum)
			{
				System.out.println("Game # : " + aGameSum);
				aPreviousGameSum = aGameSum;
			}
		}
	}
	
	private void pushToLogSystems(String pStr)
	{
		aLog.info(pStr);
		try
		{
			aFileWriter.write(pStr + "\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets logging on or off.
	 * @param pState True for on
	 */
	public static void setLoggingTo(boolean pState)
	{
		aLoggingOn = pState;
	}
	
	/**
	 * Sets whether or not the game number is displayed in the logging.
	 * @param pState True for on
	 */
	public static void setShowProgressTo(boolean pState)
	{
		aShowProgress = pState;
	}
	
	// ####-####-#### - V2 - ####-####-####
	
	private void process(GameEngine pGame)
	{
		GameEngine lGame = pGame;
		switch(lGame.getCurrentState())
		{
			
			case GAME_NEW:
				this.log(GameStatistics.GAME_INITIALIZED_BY + lGame.getPlayerList()[0].getName());
				this.log(GameStatistics.NEW_GAME);
				this.initGame(
						lGame.getPlayerList()[0].getName(), lGame.getPlayerList()[1].getName(),
						lGame.getPlayerList()[2].getName(), lGame.getPlayerList()[3].getName());
				break;
				
			case CARDS_DEALT:
				this.log(GameStatistics.NEW_DEAL);
				this.log(GameStatistics.CARDS_DEALT);
				for(Player p: lGame.getPlayerList())
				{
					this.log(p.getName() + GameStatistics.HAS_CARDS + p.getHand().toShortString());
				}
				this.log(GameStatistics.WIDOW_HAS_CARDS + lGame.getWidow().toShortString());
				break;
				
			case PLAYER_BIDS:
				this.log(
					lGame.getStateRelativePlayer().getName() + GameStatistics.HAS_CARDS +
					lGame.getStateRelativePlayer().getHand().toShortString() + GameStatistics.BIDS +
					lGame.getStateRelativeBid().toString());
				break;
				
			case PLAYER_GETS_CONTRACT:
				this.log(
					lGame.getStateRelativePlayer().getName() + GameStatistics.HAS_CONTRACT +
					lGame.getStateRelativeBid().toString());
				this.contract(
					lGame.getStateRelativePlayer().getName(),
					lGame.getStateRelativePlayer().getNextPlayer().getNextPlayer().getName(),
					lGame.getStateRelativePlayer().getNextPlayer().getName(),
					lGame.getStateRelativePlayer().getNextPlayer().getNextPlayer().getNextPlayer().getName());
				break;
				
			case GAME_WIDOW_EXCHANGE_DONE:
				this.log(
					lGame.getStateRelativePlayer().getName() + GameStatistics.DISCARDS_CARDS +
					lGame.getStateRelativeCardList().toShortString());
				break;
				
			case GAME_TRICK:
				this.log(this.logTrick());
				break;
				
			case CARDS_PLAY:
				this.log(
					lGame.getStateRelativePlayer().getName() + GameStatistics.HAS_CARDS +
					lGame.getStateRelativePlayer().getHand().toShortString() + GameStatistics.PLAYS +
					lGame.getStateRelativeCard().toString());
				break;
				
			case GAME_TRICK_STATS:
				this.log(lGame.getStateRelativePlayer().getName() + GameStatistics.WINS_TRICK);
				this.trickComplete(
					lGame.getStateRelativePlayer().getName(),
					lGame.getStateRelativePlayer().getNextPlayer().getName(),
					lGame.getStateRelativePlayer().getNextPlayer().getNextPlayer().getName(),
					lGame.getStateRelativePlayer().getNextPlayer().getNextPlayer().getNextPlayer().getName());
				break;
				
			case GAME_SCORE:
				this.log(
					lGame.getStateRelativePlayer().getName() +
					GameStatistics.HAS_CONTRACT +
					lGame.getStateRelativeBid().toString());
				this.log(
					lGame.getTeamList()[0].toString() +
					GameStatistics.WON +
					lGame.getTeamList()[0].computeTricksWon() +
					GameStatistics.TRICKS);
				this.log(
					lGame.getTeamList()[1].toString() +
					GameStatistics.WON +
					lGame.getTeamList()[1].computeTricksWon() +
					GameStatistics.TRICKS);
				break;
				
			case TEAM_MAKES_CONTRACT:
				this.log(lGame.getStateRelativeTeam().toString() + GameStatistics.MAKE_CONTRACT);
				this.log(
						GameStatistics.CONTRACTORS_SCORE + lGame.getStateRelativeInt() +
						GameStatistics.TOTAL_SCORE + lGame.getStateRelativeTeam().getScore());
				this.contractComplete(
						lGame.getStateRelativeTeam().getPlayers()[0].getName(),
						lGame.getStateRelativeTeam().getPlayers()[1].getName(), true);
				this.score(
						lGame.getStateRelativeTeam().getPlayers()[0].getName(),
						lGame.getStateRelativeTeam().getPlayers()[1].getName(), lGame.getStateRelativeInt());
				break;
				
			case TEAM_LOSES_CONTRACT:
				this.log(lGame.getStateRelativeTeam().toString() + GameStatistics.LOSE_CONTRACT);
				this.log(
						GameStatistics.CONTRACTORS_SCORE + "-" + lGame.getStateRelativeInt() +
						GameStatistics.TOTAL_SCORE + lGame.getStateRelativeTeam().getScore());
				this.contractComplete(
						lGame.getStateRelativeTeam().getPlayers()[0].getName(),
						lGame.getStateRelativeTeam().getPlayers()[1].getName(), false);
				this.score(
						lGame.getStateRelativeTeam().getPlayers()[0].getName(),
						lGame.getStateRelativeTeam().getPlayers()[1].getName(), lGame.getStateRelativeInt()*(-1));
				break;
				
			case TEAM_DEFENDERS_SCORE:
				this.log(
				GameStatistics.DEFENDERS_SCORE + lGame.getStateRelativeInt() +
				GameStatistics.TOTAL_SCORE + lGame.getStateRelativeTeam().getScore());
				this.score(
						lGame.getStateRelativeTeam().getPlayers()[0].getName(),
						lGame.getStateRelativeTeam().getPlayers()[1].getName(), lGame.getStateRelativeInt());
				break;
				
			case GAME_END:
				Team team2;
				if(lGame.getTeamList()[0] == lGame.getStateRelativeTeam())
				{ team2 = lGame.getTeamList()[1]; }
				else 
				{ team2 = lGame.getTeamList()[0]; }
				this.endGame(
				lGame.getStateRelativeTeam().getPlayers()[0].getName(),
				lGame.getStateRelativeTeam().getPlayers()[1].getName(),
				team2.getPlayers()[0].getName(),
				team2.getPlayers()[1].getName());
				break;
				
			case CARDS_UPDATE:
			default:
				break;
		}
	}
	
	/**
	 * 
	 */
	public void resetStatistics()
	{
		aCumPlayerData = new HashMap<String, PlayerStatistics>();
	}
	
	/**
	 * java.util.Observer.update .
	 * @param pGame Game object
	 */
	public void update(Observable pGame)
	{
		this.process((GameEngine) pGame);
	}
	
}
