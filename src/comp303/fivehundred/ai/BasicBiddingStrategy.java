package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import comp303.fivehundred.model.ModelException;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Suit;

import java.util.Iterator;

/**
 * 
 * @author Noah Dayan
 *
 * Supports a point-based bidding strategy. This strategy originated from 
 * strategy systems developed for Bridge and relies on two major concepts: 
 * biddable suits, and point counts. A biddable suit is a suit that is long 
 * enough and strong enough to make sense as a contract. The idea is to 
 * associate each of the strongest cards in a suit with a point count 
 * (e.g., high joker = 4, low joker = 3, and so on down to queen = 1 and 
 * everything else, 0).
 *
 */
public class BasicBiddingStrategy implements IBiddingStrategy
{
	private static final int HAND_SIZE = 10;
	private static final int BIDDABLE_POINTS = 7;
	private static final int BIDDABLE_CARDS = 4;
	private static final int LONG_SUIT = 5;
	private static final int PARTNER_BID = 5;
	private static final int OPPONENT_BID = 5;
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
	private static final int NINE_TRICKS = 9;
	private static final int TEN_TRICKS = 10;
	private static final int SEVEN_THRESHOLD = 15;
	private static final int EIGHT_THRESHOLD = 20;
	private static final int NINE_THRESHOLD = 23;
	private static final int TEN_THRESHOLD = 25;
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
		
		if(pPreviousBids.length >= 2)
		{
			try
			{
				if(pPreviousBids[pPreviousBids.length - 2].getSuit() != null)
				{
					switch(pPreviousBids[pPreviousBids.length - 2].getSuit())
					{
					case SPADES:
						spadesPoints += PARTNER_BID;
						break;
					case CLUBS:
						clubsPoints += PARTNER_BID;
						break;
					case DIAMONDS:
						diamondsPoints += PARTNER_BID;
						break;
					case HEARTS:
						heartsPoints += PARTNER_BID;
						break;
					default:
						break;
					}
				}
			}
			catch(ModelException e) {}
		}
		if(pPreviousBids.length > 0)
		{
			if(pPreviousBids.length == 3)
			{
				try
				{
					if(pPreviousBids[0].getSuit() != null)
					{
						switch(pPreviousBids[0].getSuit())
						{
						case SPADES:
							spadesPoints -= OPPONENT_BID;
							break;
						case CLUBS:
							clubsPoints -= OPPONENT_BID;
							break;
						case DIAMONDS:
							diamondsPoints -= OPPONENT_BID;
							break;
						case HEARTS:
							heartsPoints -= OPPONENT_BID;
							break;
						default:
							break;
						}
					}
				}
				catch(ModelException e) {}
			}
			try
			{
				if(pPreviousBids[pPreviousBids.length - 1].getSuit() != null)
				{
					switch(pPreviousBids[pPreviousBids.length - 1].getSuit())
					{
					case SPADES:
						spadesPoints -= OPPONENT_BID;
						break;
					case CLUBS:
						clubsPoints -= OPPONENT_BID;
						break;
					case DIAMONDS:
						diamondsPoints -= OPPONENT_BID;
						break;
					case HEARTS:
						heartsPoints -= OPPONENT_BID;
						break;
					default:
						break;
					}
				}
			}
			catch(ModelException e) {}
		}
		
		Suit bidSuit = highestSuit(spadesPoints, clubsPoints, diamondsPoints, heartsPoints);
		int bidPoints = highestPoints(spadesPoints, clubsPoints, diamondsPoints, heartsPoints);
		
		int bidStrength = determineStrength(bidPoints);
		
		return determineBid(noTrumpBid, bidStrength, bidSuit, pPreviousBids, spadesBiddable, clubsBiddable, diamondsBiddable, heartsBiddable);
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
	
	private static int determineStrength(int pPoints)
	{
		if(pPoints > TEN_THRESHOLD)
		{
			return TEN_TRICKS;
		}
		else if(pPoints > NINE_THRESHOLD)
		{
			return NINE_TRICKS;
		}
		else if(pPoints > EIGHT_THRESHOLD)
		{
			return EIGHT_TRICKS;
		}
		else if(pPoints > SEVEN_THRESHOLD)
		{
			return SEVEN_TRICKS;
		}
		return SIX_TRICKS;
	}
	
	private static Bid determineBid(boolean pNoTrump, int pStrength, Suit pSuit, Bid[] pBids, boolean pS, boolean pC, boolean pD, boolean pH)
	{
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
