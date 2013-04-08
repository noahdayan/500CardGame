package comp303.fivehundred.util;

import java.util.Collections;
import java.util.Stack;

import comp303.fivehundred.util.Card.Rank;
import comp303.fivehundred.util.Card.Suit;

/**
 * Models a deck of 46 cards. Ace, King, ... down to 4 in all suits, plus two jokers.
 */
public class Deck 
{
	private Stack<Card> aCards;
	
	/**
	 * Creates a new empty deck of cards. To initialize it, call shuffle.
	 */
	public Deck()
	{
		aCards = new Stack<Card>();
		shuffle();
	}
	
	/**
	 * Initialize the deck to a full stack of 46 cards in
	 * random order.
	 */
	public void shuffle()
	{
		aCards.clear();
		for( Suit lSuit : Suit.values() )
		{
            for( Rank lRank : Rank.values() )
            {
                aCards.add( new Card( lRank, lSuit ));
            }
		}
		aCards.add( new Card(Card.Joker.LOW));
		aCards.add( new Card(Card.Joker.HIGH));
		Collections.shuffle( aCards );
	}

	/**
	 * Draws a card from the deck and removes the card from the deck.
	 * @return The card drawn.
	 * @pre initial.size() > 0
	 * @post final.size() == initial.size() - 1
	 */
	public Card draw()
	{
		assert size() > 0;
		return aCards.pop();
	}
	
	/**
	 * Returns the size of the deck.
	 * @return The number of cards in the deck.
	 */
	public int size()
	{
		return aCards.size();
	}
}
