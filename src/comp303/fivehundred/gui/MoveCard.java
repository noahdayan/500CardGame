package comp303.fivehundred.gui;

public class MoveCard extends Thread
{
	private DisplayableLabelledCard CARD;
	private int X;
	private int Y;
	
	public MoveCard(DisplayableLabelledCard pCard, int pX, int pY)
	{
		this.CARD = pCard;
		this.X = pX;
		this.Y = pY;
	}
	
	public void run()
	{
		CARD.moveCard(this.X, this.Y);
	}
}
