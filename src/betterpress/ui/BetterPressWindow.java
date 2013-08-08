package betterpress.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BetterPressWindow extends JFrame {
	
	private JPanel display;
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
		JScrollPane scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(200, 400));
		
		getContentPane().add(scrollPane, BorderLayout.EAST);
	}
	
	public void setBoardDisplay(JPanel boardDisplay) {
		display = boardDisplay;
		getContentPane().add(display, BorderLayout.WEST);
	}
	
	public void printToTextArea(String s) {
		textArea.append(s + "\n");
		repaint();
	}
	
}
