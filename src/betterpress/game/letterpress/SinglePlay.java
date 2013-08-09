package betterpress.game.letterpress;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import betterpress.game.ai.Player;
import betterpress.game.ai.bots.IntelligentBot;

public class SinglePlay {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		URL url = SinglePlay.class.getResource("/resources/boardInput.txt");
		File boardFile = null;
		try {
			boardFile = new File(url.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GameContext game = new GameContext(boardFile);
		
		System.out.println("Current Board State: ");
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
		
		System.out.println("Final Board State: ");
		System.out.println(board.betterPrint());
	}

}
