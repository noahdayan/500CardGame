package comp303.fivehundred.model;

import comp303.fivehundred.util.Card.Suit;

/**
 * Represents a bid or a contract for a player. Immutable.
 * @author Eliot Hautefeuille
 */ // VERSION 1.8 :: 2012 OCT 15 05:45PM :: Eliot Hautefeuille
public class Bid implements Comparable<Bid>
{
	// Touched / Unspecified / Helpers
	// Bid private status variables [[
	private static final int INDEX_MAX = 24;
	private static final int TRICKS_MIN = 6;
	private static final int TRICKS_MAX = 10;
	private static final int SUITS_MAX = 5;
	private static final int SUITS_MAX_ORD = 4;
	private static final int SCORE_BASE = 40;
	private static final int SCORE_INCREMENT = 20;
	private int aTricks;
	private Suit aSuit;
	// ]] Bid private status variables
	
	/**
	 * Constructs a new standard bid (not a pass) in a trump.
	 * @param pTricks Number of tricks bid. Must be between 6 and 10 inclusive.
	 * @param pSuit Suit bid. 
	 * @pre pTricks >= 6 && pTricks <= 10
	 */ // Touched / Done / Contract checked
	public Bid(int pTricks, Suit pSuit)
	{
		assert pTricks >= TRICKS_MIN && pTricks <= TRICKS_MAX;
		this.aTricks = pTricks;
		this.aSuit = pSuit;
	}
	
	/**
	 * Constructs a new passing bid.
	 */ // Touched / Done / No contract
	public Bid()
	{
		this.aSuit = null;
		this.aTricks = -1;
	}
	
	/**
	 * Creates a bid from an index value between 0 and 24 representing all possible
	 * bids in order of strength.
	 * @param pIndex 0 is the weakest bid (6 spades), 24 is the highest (10 no trump),
	 * and everything in between.
	 * @pre pIndex >= 0 && pIndex <= 24
	 */ // Touched / Done / Contract checked
	public Bid(int pIndex)
	{
		assert pIndex >= 0 && pIndex <= INDEX_MAX;
		this.aTricks = ( pIndex / SUITS_MAX ) + TRICKS_MIN;
		switch( pIndex % SUITS_MAX )
		{
			case 0: this.aSuit = Suit.SPADES; break;
			case 1: this.aSuit = Suit.CLUBS; break;
			case 2: this.aSuit = Suit.DIAMONDS; break;
			case 3: this.aSuit = Suit.HEARTS; break;
			default: this.aSuit = null;
		}
	}
	
	/**
	 * @return The suit the bid is in, or null if it is in no-trump.
	 * @throws ModelException if the bid is a pass.
	 */ // Touched / Done / Contract checked ||||| EXCEPTION
	public Suit getSuit()
	{
		if(this.aTricks < 0)
		{
			throw new ModelException("This bid is a pass.");
		}
		else
		{
			return this.aSuit;
		}
	}
	
	/**
	 * @return The number of tricks bid.
	 * @throws ModelException if the bid is a pass.
	 */ // Touched / Done / Contract checked ||||| EXCEPTION
	public int getTricksBid()
	{
		if(this.aTricks < 0)
		{
			throw new ModelException("This bid is a pass.");
		}
		else
		{
			return this.aTricks;
		}
	}
	
	/**
	 * @return True if this is a passing bid.
	 */ // Touched / Done / No contract
	public boolean isPass()
	{
		return this.aTricks < 0;
	}
	
	/**
	 * @return True if the bid is in no trump.
	 */ // Touched / Done / No contract
	public boolean isNoTrump()
	{
		return this.aSuit == null;
	}

	// Touched / Done / No Contract
	@Override
	public int compareTo(Bid pBid)
	{
		return  this.hashCode() - pBid.hashCode();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * {@inheritDoc}
	 */ // Touched / Done / No Contract
	@Override
	public String toString()
	{
		if(this.isPass())
		{
			return "PASS";
		}
		if(!this.isNoTrump())
		{
			return this.aTricks + " " + this.aSuit.toString();
		}
		else
		{
			return this.aTricks + " NO TRUMP";
		}
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#equals(java.lang.Object)
	 */ // Touched / Done / No Contract
	@Override
	public boolean equals(Object pBid)
	{
		if(pBid == null)
		{
			return false;
		}
		return this.compareTo((Bid) pBid) == 0;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 * {@inheritDoc}
	 */ // Touched / Done / No Contract
	@Override
	public int hashCode()
	{
		if(this.isPass())
		{
			return -1;
		}
		else
		{
			return this.toIndex();
		}
		
	}

	/**
	 * Converts this bid to an index in the 0-24 range.
	 * @return 0 for a bid of 6 spades, 24 for a bid of 10 no-trump,
	 * and everything in between.
	 * @throws ModelException if this is a passing bid.
	 */ // Touched / Done / Contract checked ||||| EXCEPTION
	public int toIndex()
	{
		if(this.isPass())
		{
			throw new ModelException("This bid is a pass.");
		}
		else
		{
			int lSuitDefer = 0;
			if(aSuit == null)
			{
				lSuitDefer = SUITS_MAX_ORD;
			}
			else
			{
				lSuitDefer = this.aSuit.ordinal();
			}
			return ((this.aTricks - TRICKS_MIN) * SUITS_MAX) + lSuitDefer;
		}
	}
	
	/**
	 * Returns the highest bid in pBids. If they are all passing
	 * bids, returns pass.
	 * @param pBids The bids to compare.
	 * @return the highest bid.
	 */ // Touched / Done / Contract checked
	public static Bid max(Bid[] pBids)
	{
		int curIndex = 0;
		int maxIndex = -1;
		Bid maxBid = pBids[0];
		for(int i = 0; i < pBids.length; i++)
		{
			try
			{
				curIndex = pBids[i].toIndex();
				if( curIndex > maxIndex )
				{
					maxIndex = curIndex;
					maxBid = pBids[i];
				}
			}
			catch (ModelException e)
			{}
		}
		return maxBid;
	}
	
	/**
	 * @return The score associated with this bid.
	 * @throws ModelException if the bid is a pass. ||||| EXCEPTION
	 */ // Touched / Done / Contract Checked
	public int getScore()
	{
		if(this.aTricks < 0)
		{
			throw new ModelException("This bid is a pass.");
		}
		else
		{
			return SCORE_BASE + SCORE_INCREMENT * this.toIndex();
		}
	}
}
