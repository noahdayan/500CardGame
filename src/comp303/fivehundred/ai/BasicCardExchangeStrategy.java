package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Suit;

/**
 * 
 * @author Noah Dayan
 * 
 * Discards the six lowest non-trump cards.
 *
 */
public class BasicCardExchangeStrategy implements ICardExchangeStrategy
{
	private static final int NUMBER_CARDS_DISCARD = 6;

	@Override
	public CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand)
	{
		Suit aTrump = Bid.max(pBids).getSuit();
		CardList cardsToDiscard = new CardList();
		Hand tempHand = pHand.clone();
		Card cardToDiscard;
		
		for(int i = 0; i < NUMBER_CARDS_DISCARD; i++)
		{
			cardToDiscard = tempHand.selectLowest(aTrump);
			cardsToDiscard.add(cardToDiscard);
			tempHand.remove(cardToDiscard);
		}
		
		return cardsToDiscard;
	}

}
