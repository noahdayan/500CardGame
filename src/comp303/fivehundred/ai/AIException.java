package comp303.fivehundred.ai;

/**
 * Represents a bug related to the misuse of an AI class.
 */
@SuppressWarnings("serial")
public class AIException extends RuntimeException
{
	/**
	 * Constructor.
	 * @param pMessage The message.
	 * @param pException The Exception.
	 */
	public AIException(String pMessage, Throwable pException)
	{
		super(pMessage, pException);
	}

	/**
	 * Constructor.
	 * @param pMessage The message.
	 */
	public AIException(String pMessage)
	{
		super(pMessage);
	}

	/**
	 * Constructor.
	 * @param pException The exception.
	 */
	public AIException(Throwable pException)
	{
		super(pException);
	}

}
