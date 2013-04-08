package comp303.fivehundred.model;

/**
 * Represents a bug related to the misuse of a utility class.
 */
@SuppressWarnings("serial")
public class ModelException extends RuntimeException
{
	/**
	 * @param pMessage Message
	 * @param pException Exception
	 */
	public ModelException(String pMessage, Throwable pException)
	{
		super(pMessage, pException);
	}

	/**
	 * @param pMessage Message
	 */
	public ModelException(String pMessage)
	{
		super(pMessage);
	}

	/**
	 * @param pException Exception
	 */
	public ModelException(Throwable pException)
	{
		super(pException);
	}

}
