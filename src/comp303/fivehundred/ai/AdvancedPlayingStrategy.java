package comp303.fivehundred.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Observer;
import java.util.Observable;

import comp303.fivehundred.engine.GameEngine;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.BySuitComparator;
import comp303.fivehundred.util.Card.BySuitNoTrumpComparator;
import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;

/**
 * 
 * @author Noah
 *
 * Advanced Playing Strategy
 * 
 * Calculates the number of cards that can beat a certain card
 * from the hand by memorizing all the cards previously played.
 * Plays the least beatable card when leading.
 * Plays lowest card if teammate is winning the trick.
 */
public class AdvancedPlayingStrategy implements IPlayingStrategy, Observer
{
	private CardList aPlayedCards = new CardList();
	private int aIndex = -1;
	
	/**
	 * Constructor.
	 */
	public AdvancedPlayingStrategy()
	{
		reset();
	}

	@Override
	public Card play(Trick pTrick, Hand pHand)
	{
		assert pTrick != null;
		assert pHand != null;
		
		Suit trumpSuit = pTrick.getTrumpSuit();
		BySuitComparator comparator = new BySuitComparator(trumpSuit);
		BySuitNoTrumpComparator nComparator = new BySuitNoTrumpComparator();
		Card returnCard = pHand.selectLowest(trumpSuit);
		
		if(pTrick.size() == 0)
		{
			if(trumpSuit == null)
			{
				if(pHand.getJokers().size() != pHand.size())
				{
					CardList sortedCards = pHand.getNonJokers().sort(Collections.reverseOrder(new Card.ByRankComparator()));
					Iterator<Card> index = sortedCards.iterator();
					Card current = sortedCards.getFirst();
					int rank = numberBeat(current, aPlayedCards, nComparator);
					returnCard = current;
					while(index.hasNext())
					{
						current = index.next();
						int beat = numberBeat(current, aPlayedCards, nComparator);
						if(rank > beat)
						{
							rank = beat;
							returnCard = current;
						}
					}
				}
				else
				{
					returnCard = pHand.random();
				}
			}
			else
			{
				if(pHand.getNonTrumpCards(trumpSuit).size() > 0)
				{
					CardList sortedCards = pHand.getNonTrumpCards(trumpSuit).sort(Collections.reverseOrder(new Card.ByRankComparator()));
					Iterator<Card> index = sortedCards.iterator();
					Card current = sortedCards.getFirst();
					int rank = numberBeat(current, aPlayedCards, comparator);
					returnCard = current;
					while(index.hasNext())
					{
						current = index.next();
						int beat = numberBeat(current, aPlayedCards, comparator);
						if(rank > beat)
						{
							rank = beat;
							returnCard = current;
						}
					}
				}
				else
				{
					CardList sortedCards = pHand.sort(Collections.reverseOrder(new Card.ByRankComparator()));
					Iterator<Card> index = sortedCards.iterator();
					Card current = sortedCards.getFirst();
					int rank = numberBeat(current, aPlayedCards, comparator);
					returnCard = current;
					while(index.hasNext())
					{
						current = index.next();
						int beat = numberBeat(current, aPlayedCards, comparator);
						if(rank > beat)
						{
							rank = beat;
							returnCard = current;
						}
					}
				}
			}
			return returnCard;
		}
		else
		{
			Suit suitLed = pTrick.getSuitLed();
			Card highestCard = pTrick.highest();
			
			if(pTrick.size() > 2 && highestCard == pTrick.get(pTrick.size() - 2))
			{	
				if(trumpSuit != null)
				{
					if(numberBeat(highestCard, aPlayedCards, comparator) == 0)
					{
						return returnCard;
					}
				}
				else
				{
					if(numberBeat(highestCard, aPlayedCards, nComparator) == 0)
					{
						return returnCard;
					}
				}
			}
			
			if(highestCard == AllCards.aLJo)
			{
				if(pHand.getJokers().size() > 0)
				{
					returnCard = pHand.getJokers().getFirst();
				}
			}
			else if(pHand.numberOfCards(suitLed, trumpSuit) > 0)
			{
				CardList sortedCards = pHand.playableCards(suitLed, trumpSuit).sort(Collections.reverseOrder(new Card.ByRankComparator()));
				Iterator<Card> index = sortedCards.iterator();
				Card current = sortedCards.getFirst();
				returnCard = current;
				while(index.hasNext())
				{
					current = index.next();
					if(comparator.compare(current, highestCard) > 0)
					{
						returnCard = current;
						break;
					}
				}
				return returnCard;
			}
			else if(pHand.numberOfCards(trumpSuit, trumpSuit) > 0)
			{
				CardList sortedCards = pHand.playableCards(trumpSuit, trumpSuit).sort(Collections.reverseOrder(new Card.ByRankComparator()));
				Iterator<Card> index = sortedCards.iterator();
				Card current = sortedCards.getFirst();
				while(index.hasNext())
				{
					current = index.next();
					if(nComparator.compare(current, highestCard) > 0)
					{
						returnCard = current;
						break;
					}
				}
			}
			return returnCard;
		}
	}

	/**
	 * 
	 * @param pIndex player Index.
	 */
	public void setIndex(int pIndex)
	{
		this.aIndex = pIndex;
	}

	private static int numberBeat(Card pCard, CardList pPlayedCards, Comparator<Card> pComparator)
	{
		int counter = 0;
		for(Card c: pPlayedCards)
		{
			if(pComparator.compare(c, pCard) > 0)
			{
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * java.util.Observer.update .
	 * @param pGame Game object
	 * @param pResponse Card played
	 */
	public void update(Observable pGame, Object pResponse)
	{
		GameEngine engine = (GameEngine) pGame;
		
		switch(engine.getCurrentState())
		{
		case CARDS_DEALT:
			reset();
			CardList cards = engine.getPlayerList()[aIndex].getHand();
			for(Card c: cards)
			{
				aPlayedCards.remove(c);
			}
			break;
		case PLAYER_GETS_CONTRACT:
			if(engine.getPlayerList()[aIndex] == engine.getStateRelativePlayer())
			{
				CardList widow = engine.getWidow();
				for(Card c: widow)
				{
					aPlayedCards.remove(c);
				}
			}
			break;
		case CARDS_PLAY:
			aPlayedCards.remove(engine.getStateRelativeCard());
			break;
		default:
			break;
		}
	}
	
	private void reset()
	{
		aPlayedCards = new CardList();
		for( Suit lSuit : Suit.values() )
		{
            for( Rank lRank : Rank.values() )
            {
                aPlayedCards.add( new Card( lRank, lSuit ));
            }
		}
		aPlayedCards.add( new Card(Card.Joker.LOW));
		aPlayedCards.add( new Card(Card.Joker.HIGH));
	}
}
