package betterpress.game.letterpress;

import java.io.File;

import betterpress.ui.BetterPressWindow;
import betterpress.ui.BoardDisplay;

public class Main {
	public static void main(String[] args) {
		BetterPressWindow window = new BetterPressWindow();
		BoardDisplay boardDisplay = new BoardDisplay();
		window.setBoardDisplay(boardDisplay);
		window.printToTextArea("Welcome to BetterPress!");
		window.setVisible(true);
		
		File boardInput = new File("/Users/rbooth/Documents/boardInput.txt");
		GameContext gc = new GameContext(window, boardInput);
		gc.start();	
	}
}
