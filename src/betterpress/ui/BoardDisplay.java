package betterpress.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Draws the BetterPress board.
 * 
 * @author <a href="tzakyrie@gmail.com">zab7ge</a>
 * @since Aug 8, 2013
 */
public class BoardDisplay extends JPanel {

	private int CELLSIZE = 40;
	private int CORNER_OFFSET = 50;
	private Color LIGHT_RED = new Color(255, 127, 127);
	private Color LIGHT_BLUE = new Color(127, 127, 255);
	
	private JPanel boardPanel;
	private JLabel[][] board;
	private int boardWidth, boardHeight;
	
	private JPanel playerWordPanel;
	private JLabel[] playedWord;
	
	public BoardDisplay(int boardWidth, int boardHeight) {	
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 3;
		
		boardPanel = new JPanel(new GridLayout(boardWidth, boardHeight));
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		setPreferredSize(new Dimension(600, 600));
		boardPanel.setPreferredSize(new Dimension(300, 300));
		
		board = new JLabel[boardWidth][boardHeight];
		for (int i=0; i<boardHeight; i++) {
			for (int j=0; j<boardWidth; j++) {
				board[i][j] = new JLabel("_");
				board[i][j].setHorizontalAlignment(JLabel.CENTER);
				board[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				board[i][j].setOpaque(true);
				boardPanel.add(board[i][j]);
			}
		}
		
		this.add(boardPanel, gbc);
		
		
		
	}

	public void setLetterBoard(char[][] letterboard) {
		for (int i=0; i<boardHeight; i++) {
			for (int j=0; j<boardWidth; j++) {
				board[i][j].setText(Character.toString(Character.toUpperCase(letterboard[i][j])));
			}
		}
	}
	
	public void setColorBoard(char[][] colorboard) {
		for (int i=0; i<boardHeight; i++) {
			for (int j=0; j<boardWidth; j++) {
				Color c;
				switch (colorboard[i][j]) {
				case 'r':
					c = LIGHT_RED;
					break;
				case 'R':
					c = Color.RED;
					break;
				case 'b':
					c = LIGHT_BLUE;
					break;
				case 'B':
					c = Color.BLUE;
					break;
				default:
					c = Color.WHITE;
					break;
				}
				board[i][j].setBackground(c);
			}
		}
		repaint();
	}
	
}

