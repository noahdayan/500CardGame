package comp303.fivehundred.util;

/**
 * Represents a bug related to the misuse of a utility class.
 */
@SuppressWarnings("serial")
public class GameUtilException extends RuntimeException
{
	/**
	 * @param pMessage Message
	 * @param pException Exception
	 */
	public GameUtilException(String pMessage, Throwable pException)
	{
		super(pMessage, pException);
	}

	/**
	 * @param pMessage Message
	 */
	public GameUtilException(String pMessage)
	{
		super(pMessage);
	}

	/**
	 * @param pException Exception
	 */
	public GameUtilException(Throwable pException)
	{
		super(pException);
	}

}
