package betterpress.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Window to display BetterPress Visualization.
 * 
 * @author <a href="tzakyrie@gmail.com">zab7ge</a>
 * @since Aug 8, 2013
 */
public class BetterPressWindow extends JFrame {
	
	private BoardDisplay display;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	
	public BetterPressWindow() {
		setTitle("BetterPress");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(200, 400));
		
		
		getContentPane().add(scrollPane, BorderLayout.EAST);
	}
	
	public void setBoardDisplay(BoardDisplay boardDisplay) {
		display = boardDisplay;
		getContentPane().add(display, BorderLayout.WEST);
	}
	
	public BoardDisplay getBoardDisplay() {
		return display;
	}
	
	public void printToTextArea(String s) {
		textArea.append(s + "\n\n");
		scrollPane.getVerticalScrollBar().setValue(
				scrollPane.getVerticalScrollBar().getMaximum());
		repaint();
	}
	
}
