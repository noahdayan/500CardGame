package comp303.fivehundred.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;

/**
 * Additional services to manage a card list that corresponds to
 * the cards in a player's hand.
 * @author Eliot Hautefeuille
 */ // VERSION 1.10 :: 2012 OCT 15 05:45PM :: Eliot Hautefeuille
public class Hand extends CardList
{
	
	/**
	 * 
	 */
	public Hand()
	{

	}
	
	/**
	 * 
	 * @param pList the CardList from which to base the Hand.
	 */
	public Hand(CardList pList)
	{
		for(Card c : pList)
		{
			this.add(c);
		}
	}
	
	/**
	 * @see java.lang.Object#clone()
	 * {@inheritDoc} 
	 */
	@Override
	public Hand clone()
	{
		return (Hand) super.clone();
	}
	
	// GIVES ALL CARD IN HAND FROM A GIVEN SUIT
	private CardList getInSuit(Suit pSuit, Suit pTrump)
	{
		Card lCurrent = this.getFirst();
		CardList lSuitList = new CardList();
		Iterator<Card> iter = this.iterator();
		while(iter.hasNext())
		{
	         lCurrent = iter.next();
	         if(lCurrent.getEffectiveSuit(pTrump) == pSuit)
	         {
	        	 lSuitList.add(lCurrent);
	         }
		}
		return lSuitList;
	}
	
	/**
	 * @param pNoTrump If the contract is in no-trump
	 * @return A list of cards that can be used to lead a trick.
	 */ // TOUCHED
	public CardList canLead(boolean pNoTrump)
	{
		//IF NO TRUMP, NO JOKER, EXCEPT IF NO OTHER CARDS LEFT
		if(pNoTrump)
		{
			if(this.getNonJokers().size() > 0)
			{
				return this.getNonJokers();
			}
			else
			{
				return (CardList) this;
			}
		}
		//OTHERWISE, ALL
		else
		{
			return (CardList) this;
		}
	}
	
	/**
	 * @return The cards that are jokers.
	 */ // Touched
	public CardList getJokers()
	{
		Card lCurrent = this.getFirst();
		CardList lJokersList = new CardList();
		Iterator<Card> iter = this.iterator();
		while(iter.hasNext())
		{
	         lCurrent = iter.next();
	         if(lCurrent.isJoker())
	         {
	        	 lJokersList.add(lCurrent);
	         }
		}
		return lJokersList;
	}
	
	/**
	 * @return The cards that are not jokers.
	 */ // Touched
	public CardList getNonJokers()
	{
		Card lCurrent = null;
		CardList lNonJokersList = new CardList();
		Iterator<Card> iter = this.iterator();
		while(iter.hasNext())
		{
	         lCurrent = iter.next();
	         if(!lCurrent.isJoker())
	         {
	        	 lNonJokersList.add(lCurrent);
	         }
		}
		return lNonJokersList;
	}
	
	/**
	 * Returns all the trump cards in the hand, including jokers.
	 * Takes jack swaps into account.
	 * @param pTrump The trump to check for. Cannot be null.
	 * @return All the trump cards and jokers.
	 * @pre pTrump != null
	 */ // Touched
	public CardList getTrumpCards(Suit pTrump)
	{
		assert pTrump != null;
		Card lCurrent = null;
		CardList pTrumpsList = new CardList();
		Iterator<Card> iter = this.iterator();
		while(iter.hasNext())
		{
	         lCurrent = iter.next();
	         if(lCurrent.getEffectiveSuit(pTrump) == pTrump || lCurrent.isJoker())
	         {
	        	 pTrumpsList.add(lCurrent);
	         }
		}
		return pTrumpsList;
	}
	
	/**
	 * Returns all the cards in the hand that are not trumps or jokers.
	 * Takes jack swaps into account.
	 * @param pTrump The trump to check for. Cannot be null.
	 * @return All the cards in the hand that are not trump cards.
	 * @pre pTrump != null
	 */ // Touched
	public CardList getNonTrumpCards(Suit pTrump)
	{
		assert pTrump != null;
		Card lCurrent = null;
		CardList pNonTrumpsList = new CardList();
		Iterator<Card> iter = this.getNonJokers().iterator();
		while(iter.hasNext())
		{
	         lCurrent = iter.next();
	         if(lCurrent.getEffectiveSuit(pTrump) != pTrump)
	         {
	        	 pNonTrumpsList.add(lCurrent);
	         }
		}
		return pNonTrumpsList;
	}
	
	
	/**
	 * Selects the least valuable card in the hand, if pTrump is the trump.
	 * @param pTrump The trump suit. Can be null to indicate no-trump.
	 * @return The least valuable card in the hand.
	 */ // Touched 
	public Card selectLowest(Suit pTrump)
	{
		CardList lFiltered = this.clone();
		
		//If there are jokers and there are other cards left, take the jokers away
		CardList lJokersList = this.getJokers();
		CardList lNonJokersList = this.getNonJokers();
		if(lJokersList.size() > 0 && lNonJokersList.size() > 0)
		{
			lFiltered = lNonJokersList;
		}
			
		//If there is a trump and there are other cards left, take the trumps away
		if(pTrump != null)
		{
			CardList lTrumpsList = this.getTrumpCards(pTrump);
			CardList lNonTrumpsList = this.getNonTrumpCards(pTrump);
			if(lTrumpsList.size() > 0 && lNonTrumpsList.size() > 0)
			{
				lFiltered = lNonTrumpsList;
			}
		}

		//Sort inverse by Rank and return
		Comparator<Card> lSorter = new Card.ByRankComparator();
		lFiltered.sort(Collections.reverseOrder(lSorter));
		return lFiltered.getFirst();
	}
	
	/**
	 * @param pLed The suit led.
	 * @param pTrump Can be null for no-trump
	 * @return All cards that can legally be played given a lead and a trump.
	 */ // Touched
	public CardList playableCards( Suit pLed, Suit pTrump )
	{		
		//Check for cards in the lead suit (and include Jokers)
		if(this.numberOfCards(pLed, pTrump) > 0)
		{
			//Get cards in suit
			CardList lPlayableCards = this.getInSuit(pLed, pTrump);
			//Add the Jokers
			if(this.getJokers().size() > 0)
			{
				CardList lJokersList = this.getJokers();
				Iterator<Card> iter = lJokersList.iterator();
				while(iter.hasNext())
				{
			         lPlayableCards.add(iter.next());
				}	
			}
			//Return the cards
			return lPlayableCards;
		}
		//Otherwise return all cards
		else
		{
			return (CardList) this;
		}

	}
	
	/**
	 * Returns the number of cards of a certain suit 
	 * in the hand, taking jack swaps into account.
	 * Excludes jokers.
	 * @param pSuit Cannot be null.
	 * @param pTrump Cannot be null.
	 * @return pSuit Can be null.
	 */ // Touched
	public int numberOfCards(Suit pSuit, Suit pTrump)
	{
		Card lCurrent = null;
		int lNumber = 0;
		Iterator<Card> iter = this.getNonJokers().iterator();
		while(iter.hasNext())
		{
	         lCurrent = iter.next();
	         if(lCurrent.getEffectiveSuit(pTrump) == pSuit )
	         {
	        	 lNumber++;
	         }
		}
		return lNumber;

	}
}
