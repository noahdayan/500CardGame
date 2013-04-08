package comp303.fivehundred.ai;

import java.util.Iterator;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Suit;

/**
 * 
 * @author Noah
 *
 * Advanced Bidding Strategy
 * 
 * Bids 6 or 7 of the strongest suit if leading in the team.
 * Bids one higher than the teammate if opponent outbid
 * the same number of tricks and strongest suit is the same as teammate.
 * Passes if the opponent bids higher than or equal to 8
 * to make the opposite team lose points.
 */
public class AdvancedBiddingStrategy implements IBiddingStrategy
{

	private static final int HAND_SIZE = 10;
	private static final int BIDDABLE_POINTS = 7;
	private static final int BIDDABLE_CARDS = 4;
	private static final int LONG_SUIT = 5;
	private static final int NO_TRUMP_POINTS = 50;
	private static final int HAND_RANKS = 11;
	private static final int HIGH_JOKER_HASH = 45;
	private static final int LOW_JOKER_HASH = 44;
	private static final int BOWER_HASH = 7;
	private static final int ACE_HASH = 10;
	private static final int KING_HASH = 9;
	private static final int QUEEN_HASH = 8;
	private static final int SIX_TRICKS = 6;
	private static final int SEVEN_TRICKS = 7;
	private static final int EIGHT_TRICKS = 8;
	private static final int SEVEN_THRESHOLD = 15;
	private static final int HIGH_JOKER_POINTS = 7;
	private static final int LOW_JOKER_POINTS = 6;
	private static final int BOWER_POINTS = 5;

	@Override
	public Bid selectBid(Bid[] pPreviousBids, Hand pHand)
	{
		assert pPreviousBids.length <= 3;
		assert pHand.size() == HAND_SIZE;
		
		int spadesPoints = countPoints(pHand, Suit.SPADES);
		int spadesCards = pHand.numberOfCards(Suit.SPADES, Suit.SPADES);
		boolean spadesBiddable = biddable(spadesPoints, spadesCards);
		
		int clubsPoints = countPoints(pHand, Suit.CLUBS);
		int clubsCards = pHand.numberOfCards(Suit.CLUBS, Suit.CLUBS);
		boolean clubsBiddable = biddable(clubsPoints, clubsCards);
		
		int diamondsPoints = countPoints(pHand, Suit.DIAMONDS);
		int diamondsCards = pHand.numberOfCards(Suit.DIAMONDS, Suit.DIAMONDS);
		boolean diamondsBiddable = biddable(diamondsPoints, diamondsCards);
		
		int heartsPoints = countPoints(pHand, Suit.HEARTS);
		int heartsCards = pHand.numberOfCards(Suit.HEARTS, Suit.HEARTS);
		boolean heartsBiddable = biddable(heartsPoints, heartsCards);
		
		boolean noTrumpBid = noTrumpBiddable(spadesPoints, clubsPoints, diamondsPoints, heartsPoints);
		
		spadesPoints = longSuit(spadesPoints, spadesCards);
		clubsPoints = longSuit(clubsPoints, clubsCards);
		diamondsPoints = longSuit(diamondsPoints, diamondsCards);
		heartsPoints = longSuit(heartsPoints, heartsCards);
		
		Suit bidSuit = highestSuit(spadesPoints, clubsPoints, diamondsPoints, heartsPoints);
		int bidPoints = highestPoints(spadesPoints, clubsPoints, diamondsPoints, heartsPoints);
		
		return determineBid(noTrumpBid, bidPoints, bidSuit, pPreviousBids, spadesBiddable, clubsBiddable, diamondsBiddable, heartsBiddable);
	}

	private static int countPoints(Hand pHand, Suit pSuit)
	{
		int count = 0;
		
		CardList suitCards = pHand.playableCards(pSuit, pSuit);
		Iterator<Card> index = suitCards.iterator();
		Card current = suitCards.getFirst();
		while(index.hasNext())
		{
			current = index.next();
			if(current.hashCode() == HIGH_JOKER_HASH)
			{
				count += HIGH_JOKER_POINTS;
			}
			else if(current.hashCode() == LOW_JOKER_HASH)
			{
				count += LOW_JOKER_POINTS;
			}
			else if(current.getEffectiveSuit(null) == pSuit)
			{
				switch(current.hashCode() - current.getSuit().ordinal()*HAND_RANKS)
				{
				case BOWER_HASH:
					count += BOWER_POINTS;
					break;
				case ACE_HASH:
					count += 4;
					break;
				case KING_HASH:
					count += 3;
					break;
				case QUEEN_HASH:
					count += 2;
					break;
				default:
					count += 1;
					break;
				}
			}
		}
		
		return count;
	}
	
	private static boolean biddable(int pPoints, int pCards)
	{
		if(pPoints >= BIDDABLE_POINTS && pCards >= BIDDABLE_CARDS)
		{
			return true;
		}
		return false;
	}
	
	private static boolean noTrumpBiddable(int pSpades, int pClubs, int pDiamonds, int pHearts)
	{
		if((pSpades + pClubs + pDiamonds + pHearts) >= NO_TRUMP_POINTS)
		{
			return true;
		}
		return false;
	}
	
	private static int longSuit(int pPoints, int pCards)
	{
		if(pCards > LONG_SUIT)
		{
			return pPoints + pCards;
		}
		return pPoints;
	}
	
	private static Suit highestSuit(int pSpades, int pClubs, int pDiamonds, int pHearts)
	{
		Suit bidSuit = Suit.SPADES;
		int bidPoints = pSpades;
		
		if(pClubs > bidPoints)
		{
			bidSuit = Suit.CLUBS;
			bidPoints = pClubs;
		}
		if(pDiamonds > bidPoints)
		{
			bidSuit = Suit.DIAMONDS;
			bidPoints = pDiamonds;
		}
		if(pHearts > bidPoints)
		{
			bidSuit = Suit.HEARTS;
			bidPoints = pHearts;
		}
		return bidSuit;
	}
	
	private static int highestPoints(int pSpades, int pClubs, int pDiamonds, int pHearts)
	{
		int bidPoints = pSpades;
		
		if(pClubs > bidPoints)
		{
			bidPoints = pClubs;
		}
		if(pDiamonds > bidPoints)
		{
			bidPoints = pDiamonds;
		}
		if(pHearts > bidPoints)
		{
			bidPoints = pHearts;
		}
		return bidPoints;
	}
	
	private static Bid determineBid(boolean pNoTrump, int pPoints, Suit pSuit, Bid[] pBids, boolean pS, boolean pC, boolean pD, boolean pH)
	{
		int pStrength = SIX_TRICKS;
		
		if(pBids.length != 0 && !Bid.max(pBids).isPass() && Bid.max(pBids).toIndex() >= EIGHT_TRICKS)
		{
			return new Bid();
		}
		if(pBids.length < 2)
		{
			if(pPoints > SEVEN_THRESHOLD)
			{
				pStrength = SEVEN_TRICKS;
			}
		}
		else
		{
			if(!pBids[pBids.length - 2].isPass() && !pBids[pBids.length - 2].isNoTrump())
			{
				if(pBids[pBids.length - 2].getSuit() == pSuit)
				{
					pStrength = pBids[pBids.length - 2].getTricksBid() + 1;
				}
			}
		}
		
		if(pNoTrump)
		{
			return new Bid(pStrength, null);
		}
		else if(pS || pC || pD || pH)
		{
			Bid highBid = new Bid(pStrength, pSuit);
			if(pBids.length == 0)
			{
				return highBid;
			}
			else if(Bid.max(pBids).isPass() || highBid.toIndex() > Bid.max(pBids).toIndex())
			{
				return highBid;
			}
		}
		
		return new Bid();
	}

}
