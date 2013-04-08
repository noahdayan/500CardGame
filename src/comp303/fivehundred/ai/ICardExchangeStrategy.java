package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.CardList;

/**
 * Defines the behavior of exchanging cards in the hand with 
 * cards in the widow. 
 */
public interface ICardExchangeStrategy
{
	/**
	 * Select exactly 6 cards from the 16 cards in pHand (basic deal plus widow),
	 * to remove from the hand.
	 * @param pBids The bids for this round. An array of 4 elements.
	 * @param pIndex The index of the player in this round. Between 0 and 3 incl.
	 * @param pHand The hand of cards for this player, with the widow added in.
	 * @return Six cards to remove from the hand.
	 * @pre A least one bid in pBids is non-passing.
	 * @pre pBids.length == 4
	 * @pre pIndex >= 0 && pIndex <= 3
	 * @pre pHand.size() == 16
	 */
	CardList selectCardsToDiscard(Bid[] pBids, int pIndex, Hand pHand);
}