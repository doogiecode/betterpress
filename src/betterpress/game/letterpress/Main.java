package betterpress.game.letterpress;

import javax.swing.JPanel;

import betterpress.ui.BetterPressWindow;
import betterpress.ui.BoardDisplay;

public class Main {
	public static void main(String[] args) {
		BetterPressWindow window = new BetterPressWindow();
		BoardDisplay boardDisplay = new BoardDisplay();
		window.setBoardDisplay(boardDisplay);
		window.printToTextArea("Welcome to BetterPress!\n");
		window.setVisible(true);
		
		GameContext gc = new GameContext(boardDisplay);
		gc.start();
		
	}
}
