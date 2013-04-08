package comp303.fivehundred.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import comp303.fivehundred.model.Bid;
import comp303.fivehundred.util.Card.Suit;

public class BidPanel
{
	private JPanel PANEL;
	private JLabel TITLE;
	private JButton PASS;
	private JButton BID;
	private Color BG = new Color(51,130,51);
	private JComboBox RANK;
	private JComboBox SUIT;
	
	public static void main(String[] args)
	{
		JFrame test = new JFrame();
		BidPanel bp = new BidPanel();
		test.add(bp.getPanel());
		test.setVisible(true);
		test.pack();
		bp.initPanel();
	}
	
	public BidPanel()
	{
		this.PANEL = new JPanel();
		this.PANEL.setPreferredSize(new Dimension(200,150));
		this.PANEL.setLayout(null);
		this.PANEL.setBackground(this.BG);
		
		this.TITLE = new JLabel("Bid:");
		this.PANEL.add(this.TITLE);
		
		this.PASS = new JButton("Pass");
		this.PANEL.add(this.PASS);
		this.BID = new JButton("Bid");
		this.PANEL.add(this.BID);
		
		String[] rankOptions = {"6", "7", "8", "9", "10"};
		String[] suitOptions = {"Spades", "Clubs", "Diamonds", "Hearts", "No Trumps"};
		
		this.RANK = new JComboBox(rankOptions);
		this.PANEL.add(this.RANK);
		this.SUIT = new JComboBox(suitOptions);
		this.PANEL.add(this.SUIT);
	}
	
	public void initPanel()
	{
		this.TITLE.setBounds(10, 0, 100,30);
		this.PASS.setBounds(50,0,75,30);
		this.BID.setBounds(125,0,75,30);
		this.RANK.setBounds(10, 40, 180, 30);
		this.SUIT.setBounds(10, 80, 180, 30);
	}
	
	public JPanel getPanel()
	{
		return this.PANEL;
	}
	
	
	public JButton getBidButton()
	{
		return this.BID;
	}
	
	public JButton getPassButton()
	{
		return this.PASS;
	}
	
	public Bid getBid()
	{
		
		switch(SUIT.getSelectedIndex())
		{
		case 0:
			return new Bid(RANK.getSelectedIndex()+6, Suit.SPADES);
		case 1:
			return new Bid(RANK.getSelectedIndex()+6, Suit.CLUBS);
		case 2:
			return new Bid(RANK.getSelectedIndex()+6, Suit.DIAMONDS);
		case 3:
			return new Bid(RANK.getSelectedIndex()+6, Suit.HEARTS);
		case 4:
			return new Bid(RANK.getSelectedIndex()+6, null);
		default:
			return new Bid();
		}
	}
	
}
