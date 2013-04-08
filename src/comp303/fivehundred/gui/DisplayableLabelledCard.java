package comp303.fivehundred.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardImages;

public class DisplayableLabelledCard implements MouseListener
{

	/**
	 * 
	 */
	private Card CARD;
	private JLabel LABEL;
	private boolean CLICKED;
	private boolean MOUSABLE;
	
	public DisplayableLabelledCard(Card pCard, boolean pMousable)
	{
		this.CARD = pCard;
		this.CLICKED = false;
		this.LABEL = new JLabel(CardImages.getCard(pCard));
		this.LABEL.addMouseListener(this);
		this.MOUSABLE = pMousable;
	}
	
	public boolean getClickedState()
	{
		return this.CLICKED;
	}
	
	public void setBorderClicked()
	{
		if(this.MOUSABLE)
			GUI.setSelectedCardSouth(this);
			GUI.unclickCardsSouth();
			this.CLICKED = true;
			this.LABEL.setBorder(BorderFactory.createMatteBorder( 3, 3, 3, 3, Color.ORANGE));
	}
	
	public void setBorderUnClicked()
	{
		if(this.MOUSABLE)
			this.CLICKED = false;
			this.LABEL.setBorder(BorderFactory.createMatteBorder( 0, 0, 0, 0, Color.cyan));
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(this.MOUSABLE)
			if(this.CLICKED)
			{
				this.setBorderUnClicked();
			}
			else
			{
				this.setBorderClicked();
			}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		if(this.MOUSABLE)
			this.LABEL.setBorder(BorderFactory.createMatteBorder( 1, 1, 1, 1, Color.ORANGE));
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		if(this.MOUSABLE)
			if(this.CLICKED)
			{
				this.LABEL.setBorder(BorderFactory.createMatteBorder( 3, 3, 3, 3, Color.ORANGE));
			}
			else
			{
				this.LABEL.setBorder(BorderFactory.createMatteBorder( 0, 0, 0, 0, Color.ORANGE));
			}
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		//Nothing goes here
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		//Nothing goes here
		
	}
	
	public JLabel getLabel()
	{
		return this.LABEL;
	}
	
	public void setBounds(int pX, int pY, int pCard_Width, int pCard_Height)
	{
		this.LABEL.setBounds(pX, pY, pCard_Width, pCard_Height);
	}

	public Card getCard()
	{
		return this.CARD;
	}
	
	public void setUnClicked()
	{
		if(this.MOUSABLE)
			this.setBorderUnClicked();
	}
	
	public void setClicked()
	{
		if(this.MOUSABLE)
			this.setBorderClicked();
	}
	
	public int getX()
	{
		return this.LABEL.getX();
	}
	
	public int getY()
	{
		return this.LABEL.getY();
	}
	
	public int getWidth()
	{
		return this.LABEL.getWidth();
	}
	
	public int getHeight()
	{
		return this.LABEL.getHeight();
	}
	
	public void moveCard(int pX, int pY)
	{
		int speed = 3000;
		if(System.getProperty("os.name").equals("Mac OS X"))
		{
			speed = 3000000;
		}
		boolean xPlus = false;
		boolean yPlus = false;
		
		if(this.getX() < pX)
		{
			xPlus = true;
		}
		
		if(this.getY() < pY)
		{
			yPlus = true;
		}
		
		for(;;)
		{
			if(System.nanoTime() % speed == 0)
			{
				if(this.getX() != pX)
				{
					if(xPlus)
					{
						this.setBounds(this.getX()+1, this.getY(), this.getWidth(), this.getHeight());
					}
					else
					{
						this.setBounds(this.getX()-1, this.getY(), this.getWidth(), this.getHeight());
					}
				}
				
				if(this.getY() != pY)
				{
					if(yPlus)
					{
						this.setBounds(this.getX(), this.getY()+1, this.getWidth(), this.getHeight());
					}
					else
					{
						this.setBounds(this.getX(), this.getY()-1, this.getWidth(), this.getHeight());
					}
				}
			}
			if(this.getX() == pX && this.getY() == pY)
			{
				break;
			}
		}
	}

	public void flipCard()
	{
		if(this.getLabel().getIcon() == CardImages.getBack())
		{
			this.getLabel().setIcon(CardImages.getCard(this.getCard()));
		}
		else
		{
			this.getLabel().setIcon(CardImages.getBack());
		}
	}
}
