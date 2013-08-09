package betterpress.game.letterpress;

import betterpress.ui.BetterPressWindow;
import betterpress.ui.BoardDisplay;

public class Main {
	public static void main(String[] args) {
		BetterPressWindow window = new BetterPressWindow();
		BoardDisplay boardDisplay = new BoardDisplay(5, 5);
		window.setBoardDisplay(boardDisplay);
		window.printToTextArea("Welcome to BetterPress!");
		window.setVisible(true);
		
		GameContext gc = new GameContext(window);
		gc.start();	
	}
}
