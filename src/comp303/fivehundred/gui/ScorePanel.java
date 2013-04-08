package comp303.fivehundred.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import comp303.fivehundred.model.Bid;

public class ScorePanel
{
	
	private JPanel SCOREBOARD;
	private final String[] HEADERS = { "", "", ""}; //HEADERS ARE UGLY
	private String[][] DATA = {
								{"", "Team 0", "Team 1"},
								{"Tricks", "", ""},
								{"", "", ""},
								{"Points", "", ""},
								{"", "", ""},
								{"Bid", "", ""}
							};
	
	private JScrollPane SCROLLPANE;
	private JTable TABLE;
	
	public ScorePanel()
	{
		this.SCOREBOARD = new JPanel();
		this.TABLE = new JTable(this.DATA, this.HEADERS);
		this.TABLE.setTableHeader(null);
		this.SCROLLPANE = new JScrollPane(this.TABLE);
	}
	
	public void initSP()
	{
		this.SCOREBOARD.setPreferredSize(new Dimension(200,97));
		this.SCOREBOARD.setLayout(null);
		this.SCOREBOARD.add(this.SCROLLPANE);
		this.SCROLLPANE.setBounds(0,0, 200, 100);
		this.TABLE.setFillsViewportHeight(true);
		this.SCOREBOARD.setVisible(true);
		this.TABLE.setEnabled(false);
		this.TABLE.setPreferredSize(new Dimension(200, 97));
		this.SCOREBOARD.setPreferredSize(new Dimension(200, 97));
		this.SCOREBOARD.setBackground(new Color(51 ,130, 51));
		this.SCROLLPANE.setBackground(this.SCOREBOARD.getBackground());
		this.TABLE.setBackground(this.SCOREBOARD.getBackground());
		this.SCROLLPANE.setBorder(BorderFactory.createMatteBorder( 1, 1, 1, 1, new Color(51, 130, 51)));
		this.TABLE.setShowGrid(false);
		this.TABLE.setForeground(Color.WHITE);
		this.SCROLLPANE.setPreferredSize(this.TABLE.getPreferredSize());
		this.SCROLLPANE.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.setTeam0Score(0);
		this.setTeam1Score(0);
		this.setTeam0Tricks(0);
		this.setTeam1Tricks(0);
	}
	
	public int getTeam0Tricks()
	{
		if(this.DATA[1][1]=="") return 0;
		return Integer.parseInt(this.DATA[1][1]);
	}
	
	public int getTeam1Tricks()
	{
		if(this.DATA[1][2]=="") return 0;
		return Integer.parseInt(this.DATA[1][2]);
	}
	
	public int getTeam0Score()
	{
		if(this.DATA[3][1]=="") return 0;
		return Integer.parseInt(this.DATA[3][1]);
	}
	
	public int getTeam1Score()
	{
		if(this.DATA[3][2]=="") return 0;
		return Integer.parseInt(this.DATA[3][2]);
	}
	
	public void setTeam0Tricks(int pTricks)
	{
		this.DATA[1][1] = "" + pTricks;
		this.SCOREBOARD.repaint();
	}
	
	public void setTeam1Tricks(int pTricks)
	{
		this.DATA[1][2] = "" + pTricks;
		this.SCOREBOARD.repaint();
	}
	
	public void setTeam0Score(int pScore)
	{
		this.DATA[3][1] = "" + pScore;
		this.SCOREBOARD.repaint();
	}
	
	public void setTeam1Score(int pScore)
	{
		this.DATA[3][2] = "" + pScore;
		this.SCOREBOARD.repaint();
	}
	
	public void setBid(Bid pBid)
	{
		if(pBid.getSuit() == null)
		{
			this.DATA[5][1] = "" + (int)pBid.getTricksBid();
			this.DATA[5][2] = "No Trump";
		}
		else
		{
			this.DATA[5][1] = "" + (int)pBid.getTricksBid();
			this.DATA[5][2] = pBid.getSuit().toString();
		}
		this.SCOREBOARD.repaint();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ScorePanel panel = new ScorePanel();
		frame.add(panel.SCOREBOARD);
		panel.initSP();
		frame.setVisible(true);
		frame.pack();
	}
	
	public void setTeam0BidWinner()
	{
		this.DATA[2][1] = "Bid winner";
		this.DATA[2][2] = "";
		this.SCOREBOARD.repaint();
	}
	
	public void setTeam1BidWinner()
	{
		this.DATA[2][1] = "";
		this.DATA[2][2] = "Bid winner";
		this.SCOREBOARD.repaint();
	}
	
	public JPanel getPanel()
	{
		return this.SCOREBOARD;
	}
}
