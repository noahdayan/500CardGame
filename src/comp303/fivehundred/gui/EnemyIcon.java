package comp303.fivehundred.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import comp303.fivehundred.util.CardImages;

public class EnemyIcon extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//107
	//124
	public EnemyIcon()
	{
		super(new ImageIcon(CardImages.class.getClassLoader().getResource("images/enemy.gif")));
	}
	
}
