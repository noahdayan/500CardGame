package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;

/**
 * Picks six cards at random.
 * 
 * @author Noah Dayan
 */
public class RandomCardExchangeStrategy implements ICardExchangeStrategy
{
	private static final int NUMBER_CARDS_DISCARD = 6;
	
	@Override
	public CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand)
	{
		CardList cardsToDiscard = new CardList();
		Hand tempHand = pHand.clone();
		Card cardToDiscard;
		
		for(int i = 0; i < NUMBER_CARDS_DISCARD; i++)
		{
			cardToDiscard = tempHand.random();
			cardsToDiscard.add(cardToDiscard);
			tempHand.remove(cardToDiscard);
		}
		
		return cardsToDiscard;
	}

}
