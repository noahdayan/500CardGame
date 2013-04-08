package comp303.fivehundred.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import comp303.fivehundred.engine.GameEngine;
import comp303.fivehundred.engine.Player.Strategy;
import comp303.fivehundred.model.Bid;
import comp303.fivehundred.util.Card;
import comp303.fivehundred.util.CardList;
import comp303.fivehundred.util.Card.Suit;

public class GUI implements Observer
{
	private static JFrame FRAME;
	private static Dimension FRAME_SIZE = new Dimension(1000,600);
	
	private static Color BG_COLOR = new Color(20,100,30);
	private static Color PANEL_COLOR = new Color(51, 130, 51);
	private static JPanel CONTENT;
	private static ScorePanel SCORE_PANEL = new ScorePanel();
	private static JPanel BUTTON_PANEL;
		private static JButton BUTTON_PLAY;
		private static JButton BUTTON_AUTOPLAY;
		private static JButton BUTTON_EXCHANGE;
		private static JButton BUTTON_AI_1;
		private static JButton BUTTON_AI_2;
		private static JButton BUTTON_AI_3;
		private static JComboBox COMBO_AI_1;
		private static JComboBox COMBO_AI_2;
		private static JComboBox COMBO_AI_3;
		private static JButton BUTTON_CLEAR_STATS;
		private static JButton BUTTON_PLAY_NEXT;
	private static BidPanel BID_PANEL = new BidPanel();
	//private static DisplayableLabelledCard[] PLAYER_CARDS_SOUTH;
	private static LinkedList<DisplayableLabelledCard> PLAYER_CARDS_SOUTH;
	private static DisplayableLabelledCard PLAYER_CARD_SOUTH_SELECTED;
	
	private static EnemyIcon P1;
	private static PlayerIcon P2;
	private static EnemyIcon P3;
	
	private static Point P0_INPUT;
	private static Point P1_INPUT;
	private static Point P2_INPUT;
	private static Point P3_INPUT;
	
	private static DisplayableLabelledCard P1_CARD;
	private static DisplayableLabelledCard P2_CARD;
	private static DisplayableLabelledCard P3_CARD;
	
	private static LinkedList<DisplayableLabelledCard> LIST_CARDS_IN;
	
	private static CardList PLAYABLE_CARDS_SOUTH;
	private static CardList PLAYER_CARDS_EXCHANGE;
	private static int PLAYER_CARDS_EXCHANGED;
	
	private static MessagePanel MESSAGE_PANEL;
	private static JButton END_ON_NEXT_END;
	private static boolean WANT_TO_END = false;
	
	public static boolean getWantToEnd()
	{
		return WANT_TO_END;
	}
	
	
	
	public static void setupBase()
	{
		FRAME = new JFrame();
		CONTENT = new JPanel();
		CONTENT.setLayout(null);
		FRAME.setContentPane(CONTENT);
		FRAME.setResizable(false);
		CONTENT.add(SCORE_PANEL.getPanel());
		SCORE_PANEL.initSP();
		SCORE_PANEL.getPanel().setBounds(5, 5, (int)SCORE_PANEL.getPanel().getPreferredSize().getWidth(), (int)SCORE_PANEL.getPanel().getPreferredSize().getHeight());
		CONTENT.setPreferredSize(FRAME_SIZE);
		CONTENT.setBackground(BG_COLOR);
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.pack();
		FRAME.setVisible(true);
		
		FRAME.add(BID_PANEL.getPanel());
		BID_PANEL.getPanel().setBounds(400, 300, (int)BID_PANEL.getPanel().getPreferredSize().getWidth(), (int)BID_PANEL.getPanel().getPreferredSize().getHeight());
		BID_PANEL.initPanel();
		BID_PANEL.getPanel().setVisible(false);
		
		LIST_CARDS_IN = new LinkedList<DisplayableLabelledCard>();
		
		P0_INPUT = new Point(FRAME.getWidth()/2 - (73/2), 353);
		P2_INPUT = new Point(FRAME.getWidth()/2 - (73/2), 251);
		P1_INPUT = new Point(FRAME.getWidth()/2 - (73/2) - (73+5), 304);
		P3_INPUT = new Point(FRAME.getWidth()/2 - (73/2) + (73+5), 304);
		
		MESSAGE_PANEL = new MessagePanel();
		FRAME.add(MESSAGE_PANEL.getPanel());
		MESSAGE_PANEL.initPanel();
		MESSAGE_PANEL.getPanel().setBounds(5, 102, (int)MESSAGE_PANEL.getPanel().getPreferredSize().getWidth(), (int)MESSAGE_PANEL.getPanel().getPreferredSize().getHeight());
	}
	
	public static void setupPlayerIcons()
	{
		P1 = new EnemyIcon();
		CONTENT.add(P1);
		P1.setBounds(275, 5, 73, 97);
		
		P2 = new PlayerIcon();
		CONTENT.add(P2);
		P2.setBounds(467, 5, 73, 97);
		
		P3 = new EnemyIcon();
		CONTENT.add(P3);
		P3.setBounds(650, 5, 73, 97);
	}
	
	public static void setupButtons()
	{
		BUTTON_PANEL = new JPanel();
		FRAME.add(BUTTON_PANEL);
		//BUTTON_PANEL.setBounds((int)(FRAME_SIZE.getWidth()-272), 396, 200, 97);
		BUTTON_PANEL.setBounds(FRAME.getWidth()-210, 5, 200, 252);
		BUTTON_PANEL.setBackground(PANEL_COLOR);
		BUTTON_PANEL.setVisible(true);
		BUTTON_PANEL.setLayout(null);
		
		BUTTON_PLAY = new JButton("Play");
		BUTTON_PANEL.add(BUTTON_PLAY);
		BUTTON_PLAY.setBounds(6, 6, 188, 25);
		BUTTON_PLAY.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						boolean lContains = false;
						
						if(PLAYER_CARD_SOUTH_SELECTED == null)
						{
							JOptionPane.showMessageDialog(null, "Please select a card", "Error", JOptionPane.PLAIN_MESSAGE);
						}
						else
						{
							for(Card c: PLAYABLE_CARDS_SOUTH)
							{
								if(c == PLAYER_CARD_SOUTH_SELECTED.getCard()) lContains = true;
							}
							if(lContains)
							{
								disablePlayButton();
								disableAutoPlayButton();
								Card lCard = PLAYER_CARD_SOUTH_SELECTED.getCard();
								LIST_CARDS_IN.add(PLAYER_CARD_SOUTH_SELECTED);
								(new MoveCard(PLAYER_CARD_SOUTH_SELECTED, (int)P0_INPUT.getX(), (int)P0_INPUT.getY())).start();
								PLAYER_CARD_SOUTH_SELECTED = null;
								aEngine.setGUIPlayerCard(lCard);
								PLAYABLE_CARDS_SOUTH = null;
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Please select a playable card", "Error", JOptionPane.PLAIN_MESSAGE);
							}
							
						}
					}
			});
		
		
		BUTTON_AUTOPLAY = new JButton("Auto-Play");
		BUTTON_PANEL.add(BUTTON_AUTOPLAY);
		BUTTON_AUTOPLAY.setBounds(6, 37, 188, 25);
		BUTTON_AUTOPLAY.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						disablePlayButton();
						disableAutoPlayButton();
						Card lCard = aEngine.getStateRelativeCard();
						for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
						{
							if(dlc.getCard() == lCard)
							{
								PLAYER_CARD_SOUTH_SELECTED = dlc;
								PLAYER_CARD_SOUTH_SELECTED.setClicked();
								break;
							}
						}
						(new MoveCard(PLAYER_CARD_SOUTH_SELECTED, (int)P0_INPUT.getX(), (int)P0_INPUT.getY())).start();
						LIST_CARDS_IN.add(PLAYER_CARD_SOUTH_SELECTED);
						PLAYER_CARD_SOUTH_SELECTED = null;
						aEngine.setGUIPlayerCardAuto();
					}
			});
		
		BUTTON_EXCHANGE= new JButton("Exchange");
		BUTTON_PANEL.add(BUTTON_EXCHANGE);
		BUTTON_EXCHANGE.setBounds(6, 68, 188, 25);
		BUTTON_EXCHANGE.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						if(PLAYER_CARD_SOUTH_SELECTED == null)
						{
							JOptionPane.showMessageDialog(null, "Please select a card", "Error", JOptionPane.PLAIN_MESSAGE);
						}
						else
						{
							PLAYER_CARDS_EXCHANGED++;
							Card lCard = PLAYER_CARD_SOUTH_SELECTED.getCard();
							PLAYER_CARDS_EXCHANGE.add(lCard);
							(new MoveCard(PLAYER_CARD_SOUTH_SELECTED, (int)P0_INPUT.getX(), (int)P0_INPUT.getY())).start();
							PLAYER_CARD_SOUTH_SELECTED = null;
							removeCard(lCard);
							if(PLAYER_CARDS_EXCHANGED >= 6)
							{
								disableExchangeButton();
								CardList playerCards = new CardList();
								for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
								{
									playerCards.add(dlc.getCard());
								}
								for(Card c: PLAYER_CARDS_EXCHANGE)
								{
									playerCards.remove(c);
								}
								for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
								{
									removeCard(dlc.getCard());
								}
								aEngine.setGUIPlayerExchange(PLAYER_CARDS_EXCHANGE);
								System.out.println(playerCards.size());
								putCardsSouth(playerCards);
							}
						}
					}
			});
		
		
		String[] AI_1 = {"AI 1 Random", "AI 1 Basic", "AI 1 Advanced"};
		String[] AI_2 = {"AI 2 Random", "AI 2 Basic", "AI 2 Advanced"};
		String[] AI_3 = {"AI 3 Random", "AI 3 Basic", "AI 3 Advanced"};
		
		COMBO_AI_1 = new JComboBox(AI_1);
		BUTTON_PANEL.add(COMBO_AI_1);
		COMBO_AI_1.setBounds(5, 102, 120, 20);
		
		COMBO_AI_2 = new JComboBox(AI_2);
		BUTTON_PANEL.add(COMBO_AI_2);
		COMBO_AI_2.setBounds(5, 127, 120, 20);
		
		COMBO_AI_3 = new JComboBox(AI_3);
		BUTTON_PANEL.add(COMBO_AI_3);
		COMBO_AI_3.setBounds(5, 152, 120, 20);
		
		BUTTON_AI_1 = new JButton("Set");
		BUTTON_PANEL.add(BUTTON_AI_1);
		BUTTON_AI_1.setBounds(140, 102, 54, 20);
		BUTTON_AI_1.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						aEngine.setPlayerStrategy(COMBO_AI_1.getSelectedIndex(), 1);
					}
			});
		
		BUTTON_AI_2 = new JButton("Set");
		BUTTON_PANEL.add(BUTTON_AI_2);
		BUTTON_AI_2.setBounds(140, 127, 54, 20);
		BUTTON_AI_2.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						aEngine.setPlayerStrategy(COMBO_AI_2.getSelectedIndex(), 2);
					}
			});
		
		BUTTON_AI_3 = new JButton("Set");
		BUTTON_PANEL.add(BUTTON_AI_3);
		BUTTON_AI_3.setBounds(140, 152, 54, 20);
		BUTTON_AI_3.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						aEngine.setPlayerStrategy(COMBO_AI_3.getSelectedIndex(), 3);
					}
			});
		
		BUTTON_CLEAR_STATS = new JButton("Clear Statistics");
		BUTTON_PANEL.add(BUTTON_CLEAR_STATS);
		BUTTON_CLEAR_STATS.setBounds(5, 177, 190, 20);
		BUTTON_CLEAR_STATS.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						aEngine.resetStatistics();
						BUTTON_CLEAR_STATS.setEnabled(false);
						MESSAGE_PANEL.pushMessage("Statistics Cleared.");
					}
			});
		
		BUTTON_PLAY_NEXT = new JButton("New Game");
		BUTTON_PANEL.add(BUTTON_PLAY_NEXT);
		BUTTON_PLAY_NEXT.setBounds(5, 202, 190, 20);
		BUTTON_PLAY_NEXT.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						BUTTON_PLAY_NEXT.setEnabled(false);
						BUTTON_AI_1.setEnabled(false);
						BUTTON_AI_2.setEnabled(false);
						BUTTON_AI_3.setEnabled(false);
						BUTTON_CLEAR_STATS.setEnabled(false);
						aEngine.setGUIGameStart();
						BUTTON_CLEAR_STATS.setEnabled(false);
					}
			});
		
		disablePlayButton();
		disableAutoPlayButton();
		disableExchangeButton();
		
		BID_PANEL.getBidButton().addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						BID_PANEL.getPanel().setVisible(false);
						aEngine.setGUIPlayerBid(BID_PANEL.getBid());
					}
			});
		
		BID_PANEL.getPassButton().addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						BID_PANEL.getPanel().setVisible(false);
						aEngine.setGUIPlayerBid(new Bid());
					}
			});
		
		END_ON_NEXT_END = new JButton("Quit at end of game");
		BUTTON_PANEL.add(END_ON_NEXT_END);
		END_ON_NEXT_END.setBounds(5, 227, 190, 20);
		END_ON_NEXT_END.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e)
					{
						WANT_TO_END = true;
						END_ON_NEXT_END.setEnabled(false);
					}
			});
		
		
		BUTTON_PANEL.repaint();
		BUTTON_PANEL.validate();
		BUTTON_PANEL.repaint();
	}
	
	public static void updateCardsSouth()
	{
		for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
		{
			removeCard(dlc.getCard());
		}
		int counter = 0;
		for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
		{
			CONTENT.add(dlc.getLabel());
			dlc.getLabel().setBounds(getBoundsCardsSouth(counter));
			dlc.getLabel().setVisible(true);
			CONTENT.repaint();
			counter++;
		}
	}
	
	public static void unclickCardsSouth()
	{
		for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
		{
			if(dlc != null) dlc.setBorderUnClicked();
		}
	}
	
	public static void updateCardP1(Card pCard)
	{
		P1_CARD = new DisplayableLabelledCard(pCard, false);
		P1_CARD.flipCard();
		FRAME.add(P1_CARD.getLabel());
		P1_CARD.setBounds(P1.getX(), P1.getY(), 73, 97);
		try{Thread.sleep(1000);}catch (InterruptedException e){}
		(new MoveCard(P1_CARD,(int)P1_INPUT.getX(), (int)P1_INPUT.getY())).start();
		try{Thread.sleep(1000);}catch (InterruptedException e){}
		P1_CARD.flipCard();
		LIST_CARDS_IN.add(P1_CARD);
	}
	
	public static void updateCardP2(Card pCard)
	{
		P2_CARD = new DisplayableLabelledCard(pCard, false);
		P2_CARD.flipCard();
		FRAME.add(P2_CARD.getLabel());
		P2_CARD.setBounds(P2.getX(), P2.getY(), 73, 97);
		try{Thread.sleep(1000);}catch (InterruptedException e){}
		(new MoveCard(P2_CARD,(int)P2_INPUT.getX(), (int)P2_INPUT.getY())).start();
		try{Thread.sleep(1000);}catch (InterruptedException e){}
		P2_CARD.flipCard();
		LIST_CARDS_IN.add(P2_CARD);
	}
	
	public static void updateCardP3(Card pCard)
	{
		P3_CARD = new DisplayableLabelledCard(pCard, false);
		P3_CARD.flipCard();
		FRAME.add(P3_CARD.getLabel());
		P3_CARD.setBounds(P3.getX(), P3.getY(), 73, 97);
		try{Thread.sleep(1000);}catch (InterruptedException e){}
		(new MoveCard(P3_CARD,(int)P3_INPUT.getX(), (int)P3_INPUT.getY())).start();
		try{Thread.sleep(1000);}catch (InterruptedException e){}
		P3_CARD.flipCard();
		LIST_CARDS_IN.add(P3_CARD);
	}
	
	public static void disposeCardsIn()
	{
		for(DisplayableLabelledCard dlc: LIST_CARDS_IN)
		{
			dlc.moveCard(SCORE_PANEL.getPanel().getX(), SCORE_PANEL.getPanel().getX());
			dlc.getLabel().setVisible(false);
			FRAME.remove(dlc.getLabel());
		}
		LIST_CARDS_IN.clear();
	}
	
	public static void setSelectedCardSouth(DisplayableLabelledCard pCard)
	{
		PLAYER_CARD_SOUTH_SELECTED = pCard;
	}
	
	public static Rectangle getBoundsCardsSouth(int pCardIndex)
	{
		int x = 0;
		int y = 0;
		if(pCardIndex<10)
		{
			y = 498;
			x = 125;
			x += pCardIndex*(73+2);
		}
		else
		{
			y = 396;
			x = 275;
			x+= (pCardIndex-10)*(73+2);
		}
		return new Rectangle(x, y, 73, 97);
	}
	
	public static void putCardsSouth(CardList pCL)
	{
		//PLAYER_CARDS_SOUTH = new DisplayableLabelledCard[pCL.size()];
		PLAYER_CARDS_SOUTH = new LinkedList<DisplayableLabelledCard>();
		int counter = 0;
		for(Card c: pCL)
		{
			DisplayableLabelledCard t = new DisplayableLabelledCard(c, true);
			PLAYER_CARDS_SOUTH.add(t);
			CONTENT.add(t.getLabel());
			t.getLabel().setBounds(getBoundsCardsSouth(counter));
			t.getLabel().setVisible(true);
			counter++;
		}
	}
	
	public static void displayWidow(CardList pCL)
	{
		int counter = 10;
		for(Card c: pCL)
		{
			DisplayableLabelledCard t = new DisplayableLabelledCard(c, true);
			PLAYER_CARDS_SOUTH.add(t);
			CONTENT.add(t.getLabel());
			t.getLabel().setBounds(getBoundsCardsSouth(counter));
			t.getLabel().setVisible(true);
			counter++;
		}
	}
	
	public static void themain(String[] args)
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		setupBase();
		setupButtons();
		setupPlayerIcons();
		//disablePlayButton();
		//disableAutoPlayButton();
		//updateCardP1(AllCards.a4C);
		//updateCardP2(AllCards.a4C);
		//updateCardP3(AllCards.a4C);
		//enablePlayButton();
		//enableAutoPlayButton();
		//disposeCardsIn();
		//askPlayerForBid();		
	}
	
	public static void removeCard(Card pCard)
	{
		for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
		{
			if(dlc.getCard() == pCard)
			{
				dlc.getLabel().setVisible(false);
				CONTENT.remove(dlc.getLabel());
				CONTENT.repaint();
			}
		}
	}
	
	public static void enablePlayButton()
	{
		BUTTON_PLAY.setEnabled(true);
	}
	public static void disablePlayButton()
	{
		BUTTON_PLAY.setEnabled(false);
	}
	public static void enableAutoPlayButton()
	{
		BUTTON_AUTOPLAY.setEnabled(true);
	}
	public static void disableAutoPlayButton()
	{
		BUTTON_AUTOPLAY.setEnabled(false);
	}
	public static void enableExchangeButton()
	{
		BUTTON_EXCHANGE.setEnabled(true);
	}
	public static void disableExchangeButton()
	{
		BUTTON_EXCHANGE.setEnabled(false);
	}
	
	public static Card askPlayerForCard(CardList pList)
	{
		LinkedList<String> cards = new LinkedList<String>();
		for(Card dlc: pList)
		{
				cards.add(dlc.toShortString());
		}
		Object[] cardsA = cards.toArray();
		String cardN = (String) JOptionPane.showInputDialog(null,
				"Select a card, cancelling the dialog will cause autoplay",
				"Play a Trick",
				JOptionPane.PLAIN_MESSAGE,
				null,
				cardsA,
				cardsA[0].toString());
		
		if(cardN == null)
		{
			Card ai = aEngine.getStateRelativeCard();
			for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
			{
				if(dlc.getCard().equals(ai))
				{
					dlc.moveCard((int)P0_INPUT.getX(), (int)P0_INPUT.getY());
					LIST_CARDS_IN.add(dlc);
					return ai;
				}
			}
		}
		
		int i;
		for(i = 0; i < cardsA.length; i++)
		{
			if(cardN.equals(cardsA[i])) {
				break;
			}
		}

		Card t = pList.get(i);
		
		int j = 0;
		
		for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
		{
			if(dlc.getCard().equals(t))
			{
				break;
			}
			j++;
		}
		
		
		PLAYER_CARDS_SOUTH.get(j).moveCard((int)P0_INPUT.getX(), (int)P0_INPUT.getY());
		
		LIST_CARDS_IN.add(PLAYER_CARDS_SOUTH.get(j));
		PLAYER_CARDS_SOUTH.remove(PLAYER_CARDS_SOUTH.get(j));
		return t;
	}
	
	public static Card askPlayerForExchange()
	{
		LinkedList<String> cards = new LinkedList<String>();
		for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
		{
				cards.add(dlc.getCard().toShortString());
		}
		Object[] cardsA = cards.toArray();
		String cardN = (String) JOptionPane.showInputDialog(null,
				"Select a card to exchange",
				"Exchange",
				JOptionPane.PLAIN_MESSAGE,
				null,
				cardsA,
				cardsA[0].toString());
		
		if(cardN == null) cardN = (String) cardsA[0];
		
		int cardI = 0;
		for(int i = 0; i < cardsA.length; i++)
		{
			if(cardN.equals(cardsA[i])) {
				cardI = i;
				break;
			}
		}
		
		removeCard(PLAYER_CARDS_SOUTH.get(cardI).getCard());
		
		LIST_CARDS_IN.add(PLAYER_CARDS_SOUTH.get(cardI));
		Card t = PLAYER_CARDS_SOUTH.get(cardI).getCard();
		PLAYER_CARDS_SOUTH.remove(PLAYER_CARDS_SOUTH.get(cardI));
		return t;
	}
	
	public static Bid askPlayerForBid()
	{
		String rank[] = { "Pass","6","7","8","9","10"};
		String suit[] = { "Spades", "Clubs", "Diamonds", "Hearts", "No Trumps"};
		int Rank = JOptionPane.showOptionDialog(null,
										"Enter a rank for your bid.",
										"Bid",
										JOptionPane.OK_OPTION,
										JOptionPane.PLAIN_MESSAGE,
										null,
										rank,
										"Pass");
		if(Rank == 0)
		{
			return new Bid();
		}
		else
		{
			Rank+=5;
			int SuitD = JOptionPane.showOptionDialog(null,
					"Enter a suit for your bid.",
					"Bid",
					JOptionPane.OK_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null,
					suit,
					"No Trumps");
			if(SuitD == 0) return new Bid(Rank, Suit.SPADES);
			else if(SuitD == 1) return new Bid(Rank, Suit.CLUBS);
			else if(SuitD == 2) return new Bid(Rank, Suit.DIAMONDS);
			else if(SuitD == 3) return new Bid(Rank, Suit.HEARTS);
			else return new Bid(Rank, null);
		}
		
	}

	private static GameEngine aEngine;

	@Override
	public void update(Observable pEngine, Object pUseless)
	{
		aEngine = (GameEngine) pEngine;
		GameEngine lEngine = aEngine;
		switch(lEngine.getCurrentState()){
		case CARDS_DEALT:
			// Means cards have been dealt. U can access each player's cards by calling functions on lEngine
			// Bidding hasn't started yet
			SCORE_PANEL.setTeam0Tricks(0);
			SCORE_PANEL.setTeam1Tricks(0);
			clearCardsSouth(); // ADDED BY ELIOT for bug :
			// When everyone passes, cards are not removed and updated with the new ones. This should fix it.
			putCardsSouth(lEngine.getStateRelativeCardList());
			break;
		case CARDS_PLAY:
			// Means the stateRelativeCard has been played by the StateRelativePlayer
			int player_play = lEngine.getStateRelativePlayerIndex();
			Card card = lEngine.getStateRelativeCard();
			if(player_play == 1) updateCardP1(card);
			else if(player_play == 2) updateCardP2(card);
			else if(player_play == 3) updateCardP3(card);
			break;
		case CARDS_UPDATE:
			// Means that the StateRelativePlayer 's hand is the stateRelativeCardList cardlist
			break;
		case GAME_CONSTRUCTED:
			// Means the gameEngine has been constructed
			break;
		case GAME_END:
			// Means a game has been ended and scores have been calculated
			BUTTON_CLEAR_STATS.setEnabled(true);
			break;
		case GAME_NEW:
			// Means a new game has been initiated, players have been set
			SCORE_PANEL.setTeam0Score(0);
			SCORE_PANEL.setTeam1Score(0);
			MESSAGE_PANEL.clearMessage();
			break;
		case GAME_SCORE:
			// Means In computeScore stage
			MESSAGE_PANEL.clearMessage();
			MESSAGE_PANEL.pushMessage(
					"Contract was " + lEngine.getStateRelativeBid().toString() +
					" for team " + lEngine.getStateRelativeTeamIndex() );
			break;
		case GAME_TRICK:
			// Means In trick stage
			break;
		case GAME_TRICK_STATS:
			// Means that the trick has been completed and the stateRelativePlayer is the trick winner
			MESSAGE_PANEL.pushMessage(
					lEngine.getStateRelativePlayer().getShortName() + " in team " + 
					lEngine.getStateRelativeTeamIndex() + " wins with " +
					lEngine.getStateRelativeCard().toString() + "." );
			try{Thread.currentThread();
			Thread.sleep(1000);}catch (InterruptedException e){}
			disposeCardsIn();
			int team_trick_winner = lEngine.getStateRelativeTeamIndex();
			if(team_trick_winner == 0)
				SCORE_PANEL.setTeam0Tricks(SCORE_PANEL.getTeam0Tricks() + 1);
			if(team_trick_winner == 1)
				SCORE_PANEL.setTeam1Tricks(SCORE_PANEL.getTeam1Tricks() + 1);
			break;
		case GAME_WIDOW_EXCHANGE:
			// Means in window exchange stage
			break;
		case GAME_WIDOW_EXCHANGE_DONE:
			// Means widow has been exchanged (widow is now empty and the 6 cards have been discarded)
			break;
		case PLAYER_BIDS:
			// Means the stateRelativePlayer has bid the stateRelativeBid
			MESSAGE_PANEL.pushMessage(
					lEngine.getStateRelativePlayer().getShortName() + " of team " + 
					lEngine.getStateRelativeTeamIndex() + " bids " +
					lEngine.getStateRelativeBid().toString() + "." );
			break;
		case PLAYER_GETS_CONTRACT:
			// Means that the stateRelativePlayer won the bidding with the stateRelativeBid
			MESSAGE_PANEL.clearMessage();
			MESSAGE_PANEL.pushMessage(
					lEngine.getStateRelativePlayer().getShortName() + " in team " + 
					lEngine.getStateRelativeTeamIndex() + " gets contract " +
					lEngine.getStateRelativeBid().toString() + "." );
			if(!lEngine.getStateRelativeBid().isPass())
			{
				SCORE_PANEL.setBid(lEngine.getStateRelativeBid());
			}
			
			if(lEngine.getStateRelativeTeamIndex() == 0)
			{
				SCORE_PANEL.setTeam0BidWinner();
			}
			if(lEngine.getStateRelativeTeamIndex() == 1)
			{
				SCORE_PANEL.setTeam1BidWinner();
			}
			
			if(lEngine.getStateRelativePlayerIndex()==0)
			{
				displayWidow(lEngine.getWidow());
			}
			break;
		case TEAM_DEFENDERS_SCORE:
			// Means the stateRelativeInt is the score won by the defenders of this contract
			MESSAGE_PANEL.pushMessage(
					"Team " + lEngine.getStateRelativeTeamIndex() + " defends with " + 
					lEngine.getStateRelativeInt() + " points." );
			int team_def = lEngine.getStateRelativeTeamIndex();
			if(team_def == 0)
				SCORE_PANEL.setTeam0Score(SCORE_PANEL.getTeam0Score() + lEngine.getStateRelativeInt());
			if(team_def == 1)
				SCORE_PANEL.setTeam1Score(SCORE_PANEL.getTeam1Score() + lEngine.getStateRelativeInt());
			break;
		case TEAM_LOSES_CONTRACT:
			// Means the stateRelativeInt is the score lost by the contractors
			MESSAGE_PANEL.pushMessage(
					"Team " + lEngine.getStateRelativeTeamIndex() + " loses contract of " + 
					lEngine.getStateRelativeInt() + " points." );
			int team_lose = lEngine.getStateRelativeTeamIndex();
			if(team_lose == 0)
				SCORE_PANEL.setTeam0Score(SCORE_PANEL.getTeam0Score() - lEngine.getStateRelativeInt());
			if(team_lose == 1)
				SCORE_PANEL.setTeam1Score(SCORE_PANEL.getTeam1Score() - lEngine.getStateRelativeInt());
			break;
		case TEAM_MAKES_CONTRACT:
			// Means the stateRelativeInt is the score won by the contractors
			MESSAGE_PANEL.pushMessage(
					"Team " + lEngine.getStateRelativeTeamIndex() + " makes contract of " + 
					lEngine.getStateRelativeInt() + " points." );
			int team_win = lEngine.getStateRelativeTeamIndex();
			if(team_win == 0)
				SCORE_PANEL.setTeam0Score(SCORE_PANEL.getTeam0Score() + lEngine.getStateRelativeInt());
			if(team_win == 1)
				SCORE_PANEL.setTeam1Score(SCORE_PANEL.getTeam1Score() + lEngine.getStateRelativeInt());
			break;
		case GUI_WAITING_BID:
			//Bid wait_bid = askPlayerForBid();
			//lEngine.setGUIPlayerBid(wait_bid);
			BID_PANEL.getPanel().setVisible(true);
			break;
		case GUI_WAITING_EXCHANGE:
			PLAYER_CARDS_EXCHANGE = new CardList();
			PLAYER_CARDS_EXCHANGED = 0;
			enableExchangeButton();
			break;
		case GUI_WAITING_PLAYCARD:
			PLAYABLE_CARDS_SOUTH = lEngine.getStateRelativeCardList();
			enablePlayButton();
			enableAutoPlayButton();
			break;
		case GUI_WAITING_GAMESTART:
			BUTTON_PLAY_NEXT.setEnabled(true);
			BUTTON_AI_1.setEnabled(true);
			BUTTON_AI_2.setEnabled(true);
			BUTTON_AI_3.setEnabled(true);
			BUTTON_CLEAR_STATS.setEnabled(true);
			break;
		case GAME_AI_CHANGE:
			MESSAGE_PANEL.pushMessage(
					"AI: " + getNameFromStrat(lEngine.getStateRelativeStrategy()) +
					" strategy for AI" + lEngine.getStateRelativeInt() + "." );
		default:
			break;
		}
	}
	
	private String getNameFromStrat(Strategy pStrat)
	{
		switch(pStrat)
		{
		case RANDOM:
			return "Random";
		case BASIC:
			return "Basic";
		case ADVANCED:
			return "Advanced";
		default:
			return "Error";
		}
	}
	
	private void clearCardsSouth()
	{
		if(PLAYER_CARDS_SOUTH != null)
		{
			for(DisplayableLabelledCard dlc: PLAYER_CARDS_SOUTH)
			{
				removeCard(dlc.getCard());
			}
		}
	}
	
}
