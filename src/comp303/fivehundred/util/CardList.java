package comp303.fivehundred.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A mutable list of cards. Does not support duplicates.
 * The cards are maintained in the order added.
 * @author toby welch-richards
 */
public class CardList implements Iterable<Card>, Cloneable
{
	private List<Card> aList;
	/**
	 * Creates a new, empty card list.
	 */
	public CardList()
	{
		this.aList = new ArrayList<Card>();
	}
	
	/**
	 * Adds a card if it is not
	 * already in the list. Has no effect if the card
	 * is already in the list.
	 * @param pCard The card to add.
	 * @pre pCard != null
	 */
	public void add(Card pCard)
	{
		this.aList.add(pCard);
	}
	
	/**
	 * @return The number of cards in the list.
	 */
	public int size()
	{
		return this.aList.size();
	}
	
	/**
	 * @return The first card in the list, according to whatever
	 * order is currently being used. 
	 * @throws GameUtilException if the list is empty.
	 */
	public Card getFirst()
	{
		if(this.aList.size()==0)
		{
			throw new GameUtilException("Empty List");
		}
		else
		{
			return this.aList.get(0);
		}
	}
	
	
	/**
	 * @return The last card in the list, according to whatever
	 * order is currently being used. 
	 * @throws GameUtilException If the list is empty.
	 */
	public Card getLast()
	{
		if(this.aList.size()==0)
		{
			throw new GameUtilException("Empty List");
		}
		else
		{
			int last = this.aList.size()-1;
			return this.aList.get(last);
		}
	}
	
	/**
	 * @param pIndex index of card in list
	 * @return The index card in the list, according to whatever
	 * order is currently being used. 
	 * @throws GameUtilException If the list is empty or
	 * if the index supplied is greater than the size.
	 */
	public Card get(int pIndex)
	{
		if(this.aList.size()==0 || pIndex >= this.aList.size())
		{
			throw new GameUtilException("Empty List");
		}
		else
		{
			return this.aList.get(pIndex);
		}
	}
	
	/**
	 * Removes a card from the list. Has no effect if the card is
	 * not in the list.
	 * @param pCard The card to remove. 
	 * @pre pCard != null;
	 */
	public void remove(Card pCard)
	{
		this.aList.remove(pCard);
	}
	
	/**
	 * @see java.lang.Object#clone()
	 * {@inheritDoc}
	 */
	public CardList clone()
	{
		if(this.size() == 0)
		{
			return new CardList();
		}
		
		
		try
		{
			ArrayList<Card> copiedList = new ArrayList<Card>();
			CardList copy = (CardList) super.clone();
			Iterator<Card> index = this.iterator();
			
			Card current = this.getFirst();
			
			while(index.hasNext())
			{
		         current = index.next();
		         copiedList.add(current);
			}
			this.aList = copiedList;
			return copy;
		}
		catch(CloneNotSupportedException e)
		{
			return null;
		}
	}
	
	/**
	 * @see java.lang.Iterable#iterator()
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<Card> iterator()
	{
		return this.aList.iterator();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * {@inheritDoc}
	 */
	public String toString()
	{
		return this.aList.toString();
	}
	
	/**
	 * To short string for the cardList.
	 * @return A shortString version of the toString
	 */
	public String toShortString()
	{
		String lReturn = "[";
		Iterator<Card> iter = this.iterator();
		while(iter.hasNext())
		{
	         lReturn += iter.next().toShortString() + ",";
		}
		lReturn = lReturn.substring(0, lReturn.length() - 1) + "]";
		return lReturn;
	}
	
	/**
	 * @pre aCards.size() > 0
	 * @return A randomly-chosen card from the set. 
	 */
	public Card random()
	{
		assert this.size() > 0;
		Random generator = new Random();
		int index = generator.nextInt(this.aList.size());
		return this.aList.get(index);
	}
	
	/**
	 * Returns another CardList, sorted as desired. This
	 * method has no side effects.
	 * @param pComparator Defines the sorting order.
	 * @return the sorted CardList
	 * @pre pComparator != null
	 */
	public CardList sort(Comparator<Card> pComparator)
	{
		/*
		//bubble sort
		CardList newList = this.clone();
		Comparator<Card> comp = pComparator;
		int sizeOfList = newList.aList.size();
		for(int i = 0; i < sizeOfList; i++)
		{
			for(int j = 0; j<sizeOfList - 1; j++)
			{
				if(comp.compare(this.get(j), this.get(j+1))<1)
				{
					Card temp = this.get(j);
					this.aList.set(j, this.aList.get(j+1));
					this.aList.set(j+1, temp);
				}
			}
		}
		return newList;*/
		Collections.sort(aList, Collections.reverseOrder(pComparator));
		return this;
	}
}
