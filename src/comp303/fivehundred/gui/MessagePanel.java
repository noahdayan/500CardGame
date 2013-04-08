package comp303.fivehundred.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessagePanel
{
	
	private JPanel MESSAGEPANEL;
	private JLabel LINE_0;
	private JLabel LINE_1;
	private JLabel LINE_2;
	private JLabel LINE_3;
	private static Color BG = new Color(51,130,51);
	
	public MessagePanel()
	{
		this.MESSAGEPANEL = new JPanel();
		this.LINE_0 = new JLabel("");
		this.LINE_1 = new JLabel("");
		this.LINE_2 = new JLabel("");
		this.LINE_3 = new JLabel("");
		this.MESSAGEPANEL.setPreferredSize(new Dimension(200,110));
		this.MESSAGEPANEL.setBackground(BG);
	}
	
	public void pushMessage(String pMessage)
	{
		this.LINE_3.setText(this.LINE_2.getText());
		this.LINE_2.setText(this.LINE_1.getText());
		this.LINE_1.setText(this.LINE_0.getText());
		this.LINE_0.setText(pMessage);
	}
	
	public void initPanel()
	{
		this.LINE_0.setFont(new Font(this.LINE_0.getName(), Font.PLAIN, 9));
		this.LINE_1.setFont(new Font(this.LINE_0.getName(), Font.PLAIN, 9));
		this.LINE_2.setFont(new Font(this.LINE_0.getName(), Font.PLAIN, 9));
		this.LINE_3.setFont(new Font(this.LINE_0.getName(), Font.PLAIN, 9));
		
		this.MESSAGEPANEL.setLayout(null);
		this.MESSAGEPANEL.add(this.LINE_0);
		this.LINE_0.setBounds(5, 5, 190, 20);
		this.LINE_0.setForeground(Color.WHITE);
		this.MESSAGEPANEL.add(this.LINE_1);
		this.LINE_1.setBounds(5, 30, 190, 20);
		this.LINE_1.setForeground(Color.WHITE);
		this.MESSAGEPANEL.add(this.LINE_2);
		this.LINE_2.setBounds(5, 55, 190, 20);
		this.LINE_2.setForeground(Color.WHITE);
		this.MESSAGEPANEL.add(this.LINE_3);
		this.LINE_3.setBounds(5, 80, 190, 20);
		this.LINE_3.setForeground(Color.WHITE);
	}
	
	public void clearMessage()
	{
		this.LINE_0.setText("");
		this.LINE_1.setText("");
		this.LINE_2.setText("");
		this.LINE_3.setText("");
	}
	
	public JPanel getPanel()
	{
		return this.MESSAGEPANEL;
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MessagePanel mp = new MessagePanel();
		frame.add(mp.getPanel());
		mp.initPanel();
		frame.setVisible(true);
		frame.pack();
		try
		{
			Thread.currentThread();
			Thread.sleep(1000);
			mp.pushMessage("new line");
			Thread.currentThread();
			Thread.sleep(1000);
			mp.pushMessage("new line2");
			Thread.currentThread();
			Thread.sleep(1000);
			mp.pushMessage("new line3");
			Thread.currentThread();
			Thread.sleep(1000);
			mp.pushMessage("new line4");
			Thread.currentThread();
			Thread.sleep(1000);
			mp.pushMessage("new line5");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
}
