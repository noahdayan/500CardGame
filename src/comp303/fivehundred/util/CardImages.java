package comp303.fivehundred.util;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * A class to store and manage images of the 52 cards.
 */
public final class CardImages 
{
	private static final String IMAGE_LOCATION = "images/";
	private static final String IMAGE_SUFFIX = ".gif";
	private static final String[] RANK_CODES = {"4", "5", "6", "7", "8", "9", "t", "j", "q", "k", "a" };
	private static final String[] SUIT_CODES = {"s", "c", "d", "h"};	
	private static final String[] JOKER_CODES = {"joker-low", "joker-high"};
	
	private static Map<String, ImageIcon> aCards = new HashMap<String, ImageIcon>();
	
	private CardImages()
	{}
	
	/**
	 * Return the image of a card.
	 * @param pCard the target card
	 * @return An icon representing the chosen card.
	 */
	public static ImageIcon getCard( Card pCard )
	{
		return getCard( getCode( pCard ) );
	}
	
	/**
	 * Return an image of the back of a card.
	 * @return An icon representing the back of a card.
	 */
	public static ImageIcon getBack()
	{
		return getCard( "b" );
	}
	
	/**
	 * 
	 * @return blamk image.
	 */
	public static ImageIcon getBlank()
	{
		return new ImageIcon(CardImages.class.getClassLoader().getResource( IMAGE_LOCATION + "blank" + IMAGE_SUFFIX ));
	}
	
	private static String getCode( Card pCard )
	{
		if( pCard.isJoker() )
		{
			if( pCard.getJokerValue() == Card.Joker.LOW)
			{
				return JOKER_CODES[0];
			}
			else
			{
				return JOKER_CODES[1];
			}
		}
		else
		{
			return RANK_CODES[ pCard.getRank().ordinal() ] + SUIT_CODES[ pCard.getSuit().ordinal() ];		
		}
	}
	
	private static ImageIcon getCard( String pCode )
	{
		ImageIcon lIcon = (ImageIcon) aCards.get( pCode );
		if( lIcon == null )
		{
			lIcon = new ImageIcon(CardImages.class.getClassLoader().getResource( IMAGE_LOCATION + pCode + IMAGE_SUFFIX ));
			aCards.put( pCode, lIcon );
		}
		return lIcon;
	}
}
