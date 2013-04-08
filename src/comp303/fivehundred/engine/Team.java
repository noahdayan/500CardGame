package comp303.fivehundred.engine;

/**
 * 
 * Team class.
 */
public class Team
{
	private Player [] aPlayers;
	private int aScore;
	
	/**
	 * 
	 * Team constructor.
	 */
	public Team()
	{
		this.aScore = 0;
		aPlayers = new Player[2];
	}
	
	/**
	 * 
	 * this is a sentence.
	 * @param pPlayer the player who needs to be added to the array
	 * @param pIndex the index in the array which the player should be set to
	 */
	public void setPlayer(Player pPlayer, int pIndex)
	{
		this.aPlayers[pIndex] = pPlayer;
	}
	
	/**
	 * 
	 * gets the array of players in the team.
	 * @return the players in this team
	 */
	public Player[] getPlayers()
	{
		return this.aPlayers;
	}
	
	/**
	 * 
	 * gets the score of the team.
	 * @return the score of the team
	 */
	public int getScore()
	{
		return this.aScore;
	}
	
	/**
	 * 
	 * computes how many tricks the team has won.
	 * @return the number of tricks each player has won.
	 */
	public int computeTricksWon()
	{
		int tricksWon = 0;
		for(Player p: this.aPlayers)
		{
			tricksWon += p.getTricksWon();
		}
		return tricksWon;
	}
	
	/**
	 * 
	 * sets the score of the team.
	 * @param pNewScore the new score of the team.
	 */
	public void setScore(int pNewScore)
	{
		this.aScore = pNewScore;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * {@inheritDoc}
	 * Returns the name of the 2 players of the team.
	 */
	public String toString()
	{
		return aPlayers[0].getName() + " and " + aPlayers[1].getName();
	}
}
