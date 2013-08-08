package betterpress.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardDisplay extends JPanel {

	private int CELLSIZE = 20;
	private int CORNER_OFFSET = 25;
	private Color LIGHT_RED = new Color(255, 127, 127);
	private Color LIGHT_BLUE = new Color(127, 127, 255);
	
	private JLabel[][] board;
	
	public BoardDisplay() {	
		setPreferredSize(new Dimension(300, 300));
		setLayout(new GridLayout(5, 5));
		board = new JLabel[5][5];
	}

	public void setLetterBoard(char[][] letterboard, int width, int height) {
		if (board != null) { return; }
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				board[i][j] = new JLabel(Character.toString(Character.toUpperCase(letterboard[i][j])));
				board[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				board[i][j].setSize(new Dimension(CELLSIZE, CELLSIZE));
				add(board[i][j]);
			}
		}
	}
	
	public void setColorBoard(char[][] colorboard, int width, int height) {
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
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
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
}

