package comp303.fivehundred.engine;

import comp303.fivehundred.ai.AdvancedBiddingStrategy;
import comp303.fivehundred.ai.AdvancedCardExchangeStrategy;
import comp303.fivehundred.ai.AdvancedPlayingStrategy;
import comp303.fivehundred.ai.BasicBiddingStrategy;
import comp303.fivehundred.ai.BasicCardExchangeStrategy;
import comp303.fivehundred.ai.BasicPlayingStrategy;
import comp303.fivehundred.ai.IBiddingStrategy;
import comp303.fivehundred.ai.ICardExchangeStrategy;
import comp303.fivehundred.ai.IPlayingStrategy;
import comp303.fivehundred.ai.RandomBiddingStrategy;
import comp303.fivehundred.ai.RandomCardExchangeStrategy;
import comp303.fivehundred.ai.RandomPlayingStrategy;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;

/**
 * 
 * Player class.
 */
public class Player
{
	/**
	 * 
	 * strategy enum.
	 * easy way to switch between player strategy types
	 */
	public enum Strategy
		{PLAYER, BASIC, RANDOM, ADVANCED, GUI};
	private String aName;
	private String aShortName;
	private Hand aHand;
	private Strategy aStrategy;
	private IBiddingStrategy aObjectBiddingStrategy;
	private IPlayingStrategy aObjectPlayingStrategy;
	private ICardExchangeStrategy aObjectExchangeStrategy;
	private AdvancedPlayingStrategy aAdvancedPlayingStrategy = null;
	private int aTricksWon;
	private Team aTeam;
	private Player aNextPlayer;
	private int aIndex;
	
	/**
	 * 
	 * Player constructor.
	 * @param pName the name of the player
	 * @param pStrat the strategy of the player (enum)
	 * @param pTeam the team the player is on
	 */
	public Player(String pName, Strategy pStrat, Team pTeam)
	{
		this.aName = pName;
		
		this.aHand = new Hand();
		this.aStrategy = pStrat;
		switch(pStrat)
		{
		case BASIC:
			aObjectBiddingStrategy = new BasicBiddingStrategy();
			aObjectPlayingStrategy = new BasicPlayingStrategy();
			aObjectExchangeStrategy = new BasicCardExchangeStrategy();
			break;
		case RANDOM:
			aObjectBiddingStrategy = new RandomBiddingStrategy();
			aObjectPlayingStrategy = new RandomPlayingStrategy();
			aObjectExchangeStrategy = new RandomCardExchangeStrategy();
			break;
		case GUI:
		case ADVANCED:
			aObjectBiddingStrategy = new AdvancedBiddingStrategy();
			aObjectPlayingStrategy = new AdvancedPlayingStrategy();
			aObjectExchangeStrategy = new AdvancedCardExchangeStrategy();
			aAdvancedPlayingStrategy = new AdvancedPlayingStrategy();
			aObjectPlayingStrategy = aAdvancedPlayingStrategy;
			break;
		default:
			break;
		}
		this.aTricksWon = 0;
		this.aTeam  = pTeam;
	}
	
	/**
	 * 
	 * @param pIndex Player Index
	 */
	public void setIndex(int pIndex)
	{
		this.aIndex = pIndex;
		if(this.aAdvancedPlayingStrategy != null)
		{
			this.aAdvancedPlayingStrategy.setIndex(pIndex);
		}
	}
	
	/**
	 * 
	 * @return the Player Index
	 */
	public int getIndex()
	{
		return this.aIndex;
	}
	
	/**
	 * 
	 * get the team the player is on.
	 * @return the team the player is on.
	 */
	public Team getTeam()
	{
		return this.aTeam;
	}
	
	/**
	 * 
	 * Player hand getter.
	 * @return the hand of the player
	 */
	public Hand getHand()
	{
		return this.aHand;
	}
	
	/**
	 * 
	 * gets the name of the player.
	 * @return string name of player
	 */
	public String getName()
	{
		return this.aName;
	}
	
	/**
	 * 
	 * sets the name of the player.
	 * @param pName the new name of the player
	 */
	public void setName(String pName)
	{
		this.aName = pName;
	}
	
	/**
	 * 
	 * adds the card to the players hand.
	 * @param pCard the card you want to add
	 */
	public void addCard(Card pCard)
	{
		this.aHand.add(pCard);
	}
	
	/**
	 * 
	 * removes the supplied card from the players hand.
	 * @param pCard the card you want to remove
	 */
	public void removeCard(Card pCard)
	{
		this.aHand.remove(pCard);
	}
	
	/**
	 * 
	 * gets the first card in the players hand.
	 * @return the first card in a players hand
	 */
	public Card getFirstCard()
	{
		return this.aHand.getFirst();
	}
	
	/**
	 * 
	 * gets the last card in the players hand.
	 * @return the last card in the players hand.
	 */
	public Card getLastCard()
	{
		return this.aHand.getLast();
	}
	
	/**
	 * 
	 * gets the strategy type (enum) of the player.
	 * @return strategy of the player
	 */
	public Strategy getStrat()
	{
		return this.aStrategy;
	}
	
	/**
	 * 
	 * gets object bidding strategy of the player.
	 * @return object bidding strategy of the player
	 */
	public IBiddingStrategy getObjectBiddingStrat()
	{
		return this.aObjectBiddingStrategy;
	}
	
	/**
	 * 
	 * gets object playing strategy of the player.
	 * @return object playing strategy
	 */
	public IPlayingStrategy getObjectPlayingStrat()
	{
		return this.aObjectPlayingStrategy;
	}
	
	/**
	 * 
	 * gets object playing strategy of the player.
	 * @return object playing strategy
	 */
	public ICardExchangeStrategy getObjectExchangeStrat()
	{
		return this.aObjectExchangeStrategy;
	}
	
	/**
	 * 
	 * increments the players trick count by one.
	 */
	public void incTricks()
	{
		this.aTricksWon++;
	}
	
	/**
	 * 
	 * gets the number of tricks won by the player.
	 * @return the number of tricks won by the player.
	 */
	public int getTricksWon()
	{
		return this.aTricksWon;
	}
	
	/**
	 * 
	 * resets the playerstrick count to zero.
	 */
	public void setTricksToZero()
	{
		this.aTricksWon = 0;
	}
	
	/**
	 * 
	 * get the player who comes after this one in the order of play.
	 * @return the next player in the playing order
	 */
	public Player getNextPlayer()
	{
		return this.aNextPlayer;
	}
	
	/**
	 * 
	 * sets the player who comes after this one in the order of playing.
	 * @param pPlayer the next player you wantto set in the order of playing
	 */
	public void setNextPlayer(Player pPlayer)
	{
		this.aNextPlayer = pPlayer;
	}
	
	/**
	 * 
	 * removes all the cards in the players hand.
	 */
	public void removeAllCards()
	{
		this.aHand = new Hand();
	}
	
	/**
	 * 
	 * returns the partner of the player.
	 * @return the partner
	 */
	public Player getPartner()
	{
		return this.getNextPlayer().getNextPlayer();
	}

	/**
	 * @return the aObjectExchangeStrategy
	 */
	public ICardExchangeStrategy getObjectExchangeStrategy()
	{
		return aObjectExchangeStrategy;
	}
	
	/**
	 * 
	 * @return the Advanced Bidding strategy
	 */
	public AdvancedPlayingStrategy getAdvPlayStrat()
	{
		return this.aAdvancedPlayingStrategy;
	}

	/**
	 * @return the aShortName
	 */
	public String getShortName()
	{
		return aShortName;
	}

	/**
	 * @param pShortName the aShortName to set
	 */
	public void setShortName(String pShortName)
	{
		this.aShortName = pShortName;
	}
}