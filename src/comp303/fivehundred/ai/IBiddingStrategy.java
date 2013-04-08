package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;

/**
 * Specifies the bid selection behavior. All parameters passed to methods
 * of this class should be cloned or immutable because this functionality can be obtained
 * from various sources.
 */
public interface IBiddingStrategy
{
	/**
	 * Produces a valid bid, i.e., a between 6 and 10 for any suit or
	 * no trump, that is higher that the last bid.
	 * @param pPreviousBids All the previous bids for this hand, in order. The
	 * size of the array is the number of bids already entered (between 0 and 3).
	 * @param pHand The cards in the hand of the player entering the bid.
	 * @return A valid bid (higher than the last bid, or pass).
	 * @pre pPreviousBids.length <= 3
	 * @pre pHand.size() == 10
	 */
	Bid selectBid(Bid[] pPreviousBids, Hand pHand);
}
