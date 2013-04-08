package comp303.fivehundred.model;

import java.util.Iterator;

import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Joker;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;

import java.util.Comparator;


/**
 * A card list specialized for handling cards discarded
 * as part of the play of a trick.
 * @author Eliot Hautefeuille
 */ // VERSION 1.11 :: 2012 OCT 1 09:3PM :: Eliot Hautefeuille
public class Trick extends CardList
{	
	// Touched / Unspecified / Helpers
	// Trick private status variables [[
	private Bid aContract;
	// ]] Trick private status variables
	
	/**
	 * Constructs a new empty trick for the specified contract.
	 * @param pContract The contract that this trick is played for.
	 */ // Touched
	public Trick(Bid pContract)
	{
		this.aContract = pContract;
	}
	
	private CardList getJokers()
	{
		Card aCurrent = this.getFirst();
		CardList pJokersList = new CardList();
		Iterator<Card> iter = this.iterator();
		while(iter.hasNext())
		{
	         aCurrent = iter.next();
	         if(aCurrent.isJoker())
	         {
	        	 pJokersList.add(aCurrent);
	         }
		}
		return pJokersList;
	}
	
	// GIVES ALL CARD IN HAND FROM A GIVEN SUIT
	private CardList getInSuitWhenNoTrump(Suit pSuit)
	{
		Card aCurrent = this.getFirst();
		CardList pSuitList = new CardList();
		Iterator<Card> iter = this.iterator();
		while(iter.hasNext())
		{
	         aCurrent = iter.next();
	         if(aCurrent.getSuit() == pSuit)
	         {
	        	 pSuitList.add(aCurrent);
	         }
		}
		return pSuitList;
	}
	
	/**
	 * @return Can be null for no-trump.
	 */ // Touched
	public Suit getTrumpSuit()
	{
		return this.aContract.getSuit();
	}
	
	
	/**
	 * @return The effective suit led.
	 */ // Touched
	public Suit getSuitLed()
	{
		return this.cardLed().getSuit();
	}
	
	/**
	 * @return True if a joker led this trick
	 */ // Touched
	public boolean jokerLed()
	{
		return this.cardLed().isJoker();
	}
	
	/**
	 * @return The card that led this trick
	 * @pre size() > 0
	 */ // Touched
	public Card cardLed()
	{
		assert this.size() > 0;
		return this.getFirst();
	}

	/**
	 * @return Highest card that actually follows suit (or trumps it).
	 * I.e., the card currently winning the trick.
	 * @pre size() > 0
	 */ // Touched
	public Card highest()
	{
		assert this.size() > 0;
		
		// if joker present, return highest of the 2 jokers
		CardList jokersList = this.getJokers();
		if(jokersList.size() == 2)
		{
			if(jokersList.get(0).getJokerValue() == Joker.HIGH)
			{
				return jokersList.get(0);
			}
			else
			{
				return jokersList.get(1);
			}
		}
		else if(jokersList.size() == 1)
		{
			return jokersList.get(0);
		}
		
		//otherwise
		else
		{
			CardList sorted = null;
			Comparator<Card> sorter = null;
			
			//If no trump
			if(this.getTrumpSuit() == null)
			{
				//get all cards from led suit then pick byRank comparator
				sorted = this.getInSuitWhenNoTrump(this.getSuitLed());
				sorter = new Card.ByRankComparator();
			}
			//If trump
			else
			{
				//get all cards then pick Toby's bySuit comparator
				sorted = this.clone();
				sorter = new Card.BySuitComparator(this.getTrumpSuit());
			}
			
			// Sort using previously picked comparator then return
			sorted.sort(sorter);
			return sorted.getFirst();
		}		
	}
	
	/**
	 * @return The index of the card that wins the trick.
	 */ // Touched / 
	public int winnerIndex()
	{
		Card aCurrent = null;
		int index = 0;
		int answer = 0;
		Card aTarget = this.highest();
		Iterator<Card> iter = this.iterator();
		while(iter.hasNext())
		{
	         aCurrent = iter.next();
	         if(aCurrent.equals(aTarget))
	         {
	        	 answer = index;
	        	 break;
	         }
	         index++;
		}
		return answer;
	}
	
}
