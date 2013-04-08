package comp303.fivehundred.ai;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.model.Hand;
import java.util.Random;

/**
 * Enters a valid but random bid. Passes a configurable number of times.
 * If the robot does not pass, it uses a universal probability
 * distribution across all bids permitted.
 * 
 * @author Noah Dayan
 */
public class RandomBiddingStrategy implements IBiddingStrategy
{
	private static final int HAND_SIZE = 10;
	private static final int MAX_PERCENTAGE = 100;
	private static final int HALF_PERCENTAGE = 50;
	private static final int MAX_BID = 24;
	
	private int aPassFrequency = 0;
	
	/**
	 * Builds a robot that passes 50% of the time and bids randomly otherwise.
	 */
	public RandomBiddingStrategy()
	{
		this.aPassFrequency = HALF_PERCENTAGE;
	}
	
	/** 
	 * Builds a robot that passes the specified percentage number of the time.
	 * @param pPassFrequency A percentage point (e.g., 50 for 50%) of the time the robot 
	 * will pass. Must be between 0 and 100 inclusive. 
	 * Whether the robot passes is determined at random.
	 */
	public RandomBiddingStrategy(int pPassFrequency)
	{
		assert pPassFrequency >= 0 && pPassFrequency <= MAX_PERCENTAGE;
		
		this.aPassFrequency = pPassFrequency;
	}
	
	@Override
	public Bid selectBid(Bid[] pPreviousBids, Hand pHand)
	{
		assert pPreviousBids.length <= 3;
		assert pHand.size() == HAND_SIZE;
		
		Random generator = new Random();
		Bid[] previousBids = pPreviousBids.clone();
		
		if(previousBids.length == 0 || Bid.max(previousBids).isPass())
		{
			if(generator.nextInt(MAX_PERCENTAGE) >= this.aPassFrequency)
			{
				return new Bid(generator.nextInt(MAX_BID + 1));
			}
			return new Bid();
		}
		else
		{
			int maxPrevBid = Bid.max(previousBids).toIndex();
			if(maxPrevBid == MAX_BID)
			{
				return new Bid();
			}
			else if(generator.nextInt(MAX_PERCENTAGE) >= this.aPassFrequency)
			{
				return new Bid(maxPrevBid + generator.nextInt(MAX_BID - maxPrevBid) + 1);
			}
			return new Bid();
		}
	}

}
