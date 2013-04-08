package comp303.fivehundred.ai;

import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.Trick;
import comp303.fivehundred.util.Card;

/**
 * Behavior to decide which card to play.
 */
public interface IPlayingStrategy
{
	/**
	 * Selects a card to be played by the player. This method should have no
	 * side effect, i.e., it should not remove any cards from Hand.
	 * @param pTrick Cards played so far in the trick. Note that the 
	 * number of cards in pTrick determines the playing order of the player. For 
	 * example, if pTrick.size() == 0, the player leads. If pTrick.size() == 1, he plays
	 * second, etc.
	 * @param pHand The hand of the player to play.
	 * @return One of the cards in pHand. The card must be a legal play, that is, follow suit
	 * if possible.
	 * @pre pTrick != null
	 * @pre pHand != null
	 */
	Card play(Trick pTrick, Hand pHand);
}
