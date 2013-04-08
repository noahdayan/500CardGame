package comp303.fivehundred.util;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * GUI application to demonstrate the card utilities at work.
 * You do not need to understand how this code works: it will be explained 
 * later in the course.
 */
@SuppressWarnings("serial")
public class CardDemoApplet extends Applet implements ActionListener
{
	private static final int MARGIN = 10;
	private static final int WIDTH = 180;
	private static final int HEIGHT = 200;
	
	private Deck aDeck = new Deck();
	private JLabel aImage = new JLabel();
	private JLabel aText = new JLabel();
	private JButton aButton = new JButton("Next");
	
	/**
	 * @see java.applet.Applet#init()
	 */
	public void init()
	{
		aDeck.shuffle();
		aImage.setIcon(CardImages.getBack());
		aText.setText("Click the button to start");
		aImage.setHorizontalAlignment(JLabel.CENTER);
		aText.setHorizontalAlignment(JLabel.CENTER);
		setLayout(new BorderLayout(MARGIN, MARGIN));
		add(aText, BorderLayout.NORTH);
		add(aImage, BorderLayout.CENTER);
		add(aButton, BorderLayout.SOUTH);
		aButton.addActionListener(this);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent pEvent)
	{
		if( aDeck.size() == 0 )
		{
			aDeck.shuffle();
		}
		Card lCard = aDeck.draw();
		aImage.setIcon(CardImages.getCard(lCard));
		aText.setText(lCard.toString());
	}
}