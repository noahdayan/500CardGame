package comp303.fivehundred.engine;

import java.io.IOException;

import comp303.fivehundred.engine.GameEngine.mode;

/**
 * 
 * Driver class.
 *
 */
public final class Driver
{
	private static final int NUMBER_TRICKS = 10;
	private static final int NUMBER_TESTS = 10000;
	
	private Driver() {}
	
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
		GameStatistics.setLoggingTo(false);
		
		//To show the game currently being played, change the value of the boolean just below.
		// True for showing the number, False for not showing the number.
		GameStatistics.setShowProgressTo(true);
		
		//To allow humans to play, change the boolean just below to TRUE.
		//Setting it to false will prevent the game from prompting the user for anything, ideal for testing.
		boolean lHumansCanPlay = false;
		
		GameEngine game = new GameEngine(mode.CONSOLE);
		game.setHumansAllowed(lHumansCanPlay);
		
		for(int i = 0; i < NUMBER_TESTS; i++)
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
