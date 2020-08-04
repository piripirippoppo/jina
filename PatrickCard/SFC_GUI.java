package p6Coding;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.*;


// the "view"
public class SFC_GUI extends JPanel {

	private static final long serialVersionUID = 20160613L;
	
	
	static final int TABLE_PADDING = 25;
	static final int CARD_WIDTH = 71;
	static final int CARD_HEIGHT = 96;
	static final int CARD_PADDING = 15;
	static final int PLAYER_PADDING = 50;
	static final int VERTICAL_TEXT_OFFSET = 15;
	static final int X_OFFSET = 
			CARD_WIDTH * 2 
			+ CARD_PADDING * 2 
			+ PLAYER_PADDING;
	static final int Y_OFFSET = 
			CARD_HEIGHT 
			+ VERTICAL_TEXT_OFFSET 
			+ CARD_PADDING 
			+ PLAYER_PADDING;
	static final int TABLE_HEIGHT = 
			2 * TABLE_PADDING 
			//+ 3 * Y_OFFSET 
			- PLAYER_PADDING 
			- CARD_PADDING 
			+ CARD_HEIGHT 
			+ 2 * TABLE_PADDING;
	
	static final int DECK_CARD_OFFSET = 15;
	static final int TABLE_WIDTH = 
			45 * DECK_CARD_OFFSET + CARD_WIDTH + TABLE_PADDING;
	static final int COMMUNITY_OFFSET = 
			TABLE_WIDTH/2 - (CARD_WIDTH * 5) / 2 - 2 * CARD_PADDING;

	

	Deck deck = new Deck();
	
	
	JButton dealButton = new JButton("Deal One Card");
	JButton shuffle = new JButton("Shuffle");
	JLabel cutInstructions = new JLabel("Click on a card to cut the deck at that position.");

	Dimension size;

		
	private class DealButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			deck.deal(1);
			updateDisplay(0);
		}
	}
	
	private class ShuffleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			deck.shuffle();
			updateDisplay(0);
		}
	}
	
	public class MyMouseListener implements MouseListener{
		public void mousePressed(MouseEvent e) {
			int x = e.getX(), y = e.getY();
			if ((y > TABLE_PADDING && y < TABLE_PADDING + CARD_HEIGHT) &&
					x > TABLE_PADDING && x < TABLE_PADDING + (deck.getNumCards() - 1)* DECK_CARD_OFFSET + CARD_WIDTH) {
				int card = (x - TABLE_PADDING) / DECK_CARD_OFFSET;
				if (card > deck.getNumCards() - 1) {
					card = deck.getNumCards() - 1;
				}
				deck.cut(card);
			}
			updateDisplay(0);
		}
		
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
	}
	
	
	
	@SuppressWarnings("deprecation")
	public SFC_GUI() {
		dealButton.addActionListener(new DealButtonListener());
		shuffle.addActionListener(new ShuffleListener());

		size = new Dimension(TABLE_WIDTH, TABLE_HEIGHT);

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));  // left to right
		setBackground(new Color(40, 180, 40));   // looks like green felt table!
		add(Box.createHorizontalStrut(20));

		// Create a top-level window
		JFrame frame = new JFrame("Starfish Card Deck Simulator");
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel combo = new JPanel();
		combo.setLayout(new java.awt.BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(40, 180, 40));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		topPanel.add(shuffle);
		topPanel.add(dealButton);
		topPanel.add(Box.createHorizontalStrut(20));
		topPanel.add(cutInstructions);
		topPanel.add(Box.createHorizontalStrut(20));

		this.setSize(TABLE_WIDTH, TABLE_HEIGHT);
		this.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		this.setMinimumSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		this.setBounds(new java.awt.Rectangle(TABLE_WIDTH, TABLE_HEIGHT));
		MyMouseListener mouseListener = new MyMouseListener();
		this.addMouseListener(mouseListener);
		combo.add(topPanel, java.awt.BorderLayout.NORTH);
		combo.add(this, java.awt.BorderLayout.SOUTH);
		frame.setContentPane(combo);
		frame.pack();
		frame.show();
	}

	/*
	 * redraws the panel, then waits a specified period
	 */
	public void updateDisplay(int milliseconds) {
		repaint();
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {}
	}

	/*
	 * Tells Java how big frame should be.
	 */
	public Dimension getPreferredSize() {
		return size;
	}

	/*
	 * Called by the system to display the current state. YOU SHOULD NEVER
	 * CALL THIS METHOD!  The system will call it automatically any time the
	 * view needs to be re-drawn.  (This is an example of a "call-back" method.)
	 */
	public void paint(Graphics g) {
		super.paint(g);   // ensures button is drawn
	
		Card[] deckCards = new Card[deck.getNumCards()];
		for (int i = 0; i < deckCards.length; i++) {
			deckCards[i] = deck.getCardAt(i);
		}
		
		/* Draw cards in the deck */
		for (int i = 0; i < deckCards.length; i++) {
			drawCard(g, deckCards[i], TABLE_PADDING + DECK_CARD_OFFSET * i, TABLE_PADDING);
		}
	}

	/* 
	 * Draws card c within graphical context g at coordinates (x, y).
	 * If c == null, the card appears face-down
	 */
	private void drawCard(Graphics g, Card c, int x, int y) {
		String cardName;
		if (c == null) {
			cardName = "images/b1fv.gif";  // face-down
		} else {
			cardName = c.getImageFileName();
		}
		g.drawImage(ImageLoader.getImage(cardName), x, y, this);
	}
} 