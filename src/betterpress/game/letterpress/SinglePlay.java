package betterpress.game.letterpress;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import betterpress.game.ai.Player;
import betterpress.game.ai.bots.IntelligentBot;

public class SinglePlay {
	
	private static final String filename = "/resources/boardInput.txt";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
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

		GameContext game = new GameContext(boardReader);
		
		System.out.print("Current ");
		Board board = game.getBoard(); 
		System.out.println(board.betterPrint());
		
		Player single = new IntelligentBot(game, board);
		int[][] move = single.provideMove('b');
		String word = board.whatWordDoesThisPlayMake(move);
		System.out.println("Bot's suggestion is: " + word);
		for (int i=0; i<word.length(); i++) {
			System.out.print("[" + move[i][0] + "," + move[i][1]+ "], ");
		}
		System.out.println();
		
		board.setColorBoard(Board.colorTiles(board.getColorBoard(), move, 'b'));
		
		System.out.print("Final ");
		System.out.println(board.betterPrint());
	}

}
