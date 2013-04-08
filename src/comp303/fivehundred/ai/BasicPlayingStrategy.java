package comp303.fivehundred.ai;

import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.AllCards;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.BySuitComparator;
import comp303.fivehundred.util.Card.BySuitNoTrumpComparator;
import comp303.fivehundred.util.Card.Suit;
import comp303.fivehundred.util.CardList;

import java.util.Collections;
import java.util.Iterator;

/**
 * 
 * @author Noah Dayan
 *
 * Plays as follows: If leading, picks a card at random except jokers 
 * if playing in no trump. If following, choose the lowest card that can 
 * follow suit and win. If no card can follow suit and win, picks the 
 * lowest card that can follow suit. If no card can follow suit, picks 
 * the lowest trump card that can win. If there are no trump card or the 
 * trump cards can't win (because the trick was already trumped), then 
 * picks the lowest card. If a joker was led, dump the lowest card unless 
 * it can be beaten with the high joker according to the rules of the game.
 *
 */
public class BasicPlayingStrategy implements IPlayingStrategy
{

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
					returnCard = pHand.getNonJokers().random();
				}
				else
				{
					returnCard = pHand.random();
				}
			}
			else
			{
				returnCard = pHand.random();
			}
			return returnCard;
		}
		else
		{
			Suit suitLed = pTrick.getSuitLed();
			Card highestCard = pTrick.highest();
			
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
				while(index.hasNext())
				{
					current = index.next();
					if(comparator.compare(current, highestCard) > 0)
					{
						return current;
					}
				}
				return sortedCards.getFirst();
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
						return current;
					}
				}
			}
			return returnCard;
		}
	}

}
