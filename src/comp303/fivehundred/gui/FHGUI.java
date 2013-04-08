package comp303.fivehundred.gui;

import java.io.IOException;

import comp303.fivehundred.engine.GameEngine;
import comp303.fivehundred.engine.GameStatistics;

public class FHGUI
{
	private static final int NUMBER_TRICKS = 10;
	
	private FHGUI() {}
	
	/**
	 * 
	 * @param pArgs main arguments.
	 * @throws IOException throws I/O exception.
	 * 
	 */
	public static void main(String[] pArgs) throws IOException
	{
		run();
	}
	
	/**
	 * 
	 * @throws IOException throws I/O exception.
	 */
	public static void run() throws IOException
	{
		//To turn logging on or off, change the value of the boolean just below.
		// True for logging ON, False for logging OFF.
		GameStatistics.setLoggingTo(true);
		
		//To show the game currently being played, change the value of the boolean just below.
		// True for showing the number, False for not showing the number.
		GameStatistics.setShowProgressTo(false);
		
		GameEngine game = new GameEngine(GameEngine.mode.GUI);
		
		while(game.shouldContinue())
		{
			game.newgame();
			while(game.gameStillOnGoing() )
			{
				game.deal();
				game.bid();
				while( game.allPasses() )
				{
					game.deal();
					game.bid();
				}
				game.exchange();
				for( int lIndex = 0; lIndex < NUMBER_TRICKS; lIndex++ )
				{
					game.playTrick();
				}
				game.computeScore();
			}
		}
		GameStatistics.printStatistics();
	}
}
