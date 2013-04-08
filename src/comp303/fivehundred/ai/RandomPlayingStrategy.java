package comp303.fivehundred.ai;

import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.Card.Suit;

/**
 * If leading, picks a card at random except a joker if the contract is in no trump.
 * If not leading and the hand contains cards that can follow suit, pick a suit-following 
 * card at random. If not leading and the hand does not contain cards that can follow suit,
 * pick a card at random (including trumps, if available).
 * 
 * @author Noah Dayan
 */
public class RandomPlayingStrategy implements IPlayingStrategy
{
	@Override
	public Card play(Trick pTrick, Hand pHand)
	{
		assert pTrick != null;
		assert pHand != null;
		
		Suit trumpSuit = pTrick.getTrumpSuit();
		
		if(pTrick.size() == 0)
		{
			if(trumpSuit == null)
			{
				if(pHand.size() > 1 && pHand.getJokers().size() < 2)
				{
					return pHand.getNonJokers().random();
				}
			}
			return pHand.random();
		}
		else
		{
			Suit suitLed = pTrick.getSuitLed();
			if(pHand.numberOfCards(suitLed, trumpSuit) > 0)
			{
				return pHand.playableCards(suitLed, trumpSuit).random();
			}
			return pHand.random();
		}
	}
}
