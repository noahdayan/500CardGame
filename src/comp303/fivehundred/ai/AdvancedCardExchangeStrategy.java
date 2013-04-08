package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Suit;

/**
 * 
 * @author Noah
 *
 * Advanced Card Exchange Strategy
 * 
 * If playing in trump, discards a non-converse suit and
 * then a non-trump suit completely if possible.
 * Otherwise, discards the lowest cards.
 */
public class AdvancedCardExchangeStrategy implements ICardExchangeStrategy
{
	
	private static final int NUMBER_CARDS_DISCARD = 6;
	
	@Override
	public CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand)
	{
		
		Suit aTrump = Bid.max(pBids).getSuit();
		CardList cardsToDiscard = new CardList();
		Hand tempHand = pHand.clone();
		Card cardToDiscard;
		
		Suit[] suits = {Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS}; 
		
		if(aTrump != null)
		{
			for(Suit s: suits)
			{
				if(s != aTrump && s != aTrump.getConverse())
				{
					if(pHand.numberOfCards(s, aTrump) <= NUMBER_CARDS_DISCARD)
					{
						for(int i = 0; i < NUMBER_CARDS_DISCARD; i++)
						{
							if(i < pHand.numberOfCards(s, aTrump))
							{
								cardToDiscard = (new Hand(tempHand.playableCards(s, aTrump))).selectLowest(aTrump);
								cardsToDiscard.add(cardToDiscard);
								tempHand.remove(cardToDiscard);
							}
							else
							{
								cardToDiscard = tempHand.selectLowest(aTrump);
								cardsToDiscard.add(cardToDiscard);
								tempHand.remove(cardToDiscard);
							}
						}
						return cardsToDiscard;
					}
				}
				else if(s != aTrump)
				{
					if(pHand.numberOfCards(s, aTrump) <= NUMBER_CARDS_DISCARD)
					{
						for(int i = 0; i < NUMBER_CARDS_DISCARD; i++)
						{
							if(i < pHand.numberOfCards(s, aTrump))
							{
								cardToDiscard = (new Hand(tempHand.playableCards(s, aTrump))).selectLowest(aTrump);
								cardsToDiscard.add(cardToDiscard);
								tempHand.remove(cardToDiscard);
							}
							else
							{
								cardToDiscard = tempHand.selectLowest(aTrump);
								cardsToDiscard.add(cardToDiscard);
								tempHand.remove(cardToDiscard);
							}
						}
						return cardsToDiscard;
					}
				}
			}
		}
		for(int i = 0; i < NUMBER_CARDS_DISCARD; i++)
		{
			cardToDiscard = tempHand.selectLowest(aTrump);
			cardsToDiscard.add(cardToDiscard);
			tempHand.remove(cardToDiscard);
		}
		
		return cardsToDiscard;
	}
}
