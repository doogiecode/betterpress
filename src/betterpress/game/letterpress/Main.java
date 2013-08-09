package betterpress.game.letterpress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import betterpress.ui.BetterPressWindow;
import betterpress.ui.BoardDisplay;

public class Main {
	
	public static final String filename = "/resources/boardInput.txt";
	
	public static void main(String[] args) {
		BetterPressWindow window = new BetterPressWindow();
		BoardDisplay boardDisplay = new BoardDisplay(5, 5);
		window.setBoardDisplay(boardDisplay);
		window.printToTextArea("Welcome to BetterPress!");
		window.setVisible(true);
		
		//======Board File Initialization======
		InputStreamReader boardReader = null;

		if (args.length < 1) {
			InputStream input = SinglePlay.class.getResourceAsStream(filename);
			boardReader = new InputStreamReader(input);
		} else {
			System.out.println("Reading from input file...\n");
			InputStream input = null;
			try {
				input = new FileInputStream(args[0]);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Unable to find file: " + args[0]);
				System.exit(1);
			}
			boardReader = new InputStreamReader(input);
		}
		//======Board File Initialization======
		GameContext gc = new GameContext(window, boardReader);
		gc.start();	
	}
}
