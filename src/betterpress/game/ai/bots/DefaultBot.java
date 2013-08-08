package betterpress.game.ai.bots;

import betterpress.game.letterpress.Board;
import betterpress.game.letterpress.GameContext;

public class DefaultBot extends Bot {

	public DefaultBot(GameContext game, Board board) {
		super(game, board);
	}

	@Override
	protected int[][] chooseMove(char color) {
		int max = 0;
		int[][] bestmove = new int[0][0];
		char[][] simBoard;
		for (int[][] move : game.getPlayableWords()) {
			if (!game.isValidWord(game.whatWordDoesThisPlayMake(move))) {
				continue;
			}
			simBoard = simulateMove(move, color);
			
			if (weWin(simBoard, color)) {
				bestmove = move;
				break;
			}
			
			int nextWordScore = totalDarkMyColor(simBoard, color);
			if (nextWordScore > max) {
				bestmove = move;
				max = nextWordScore;
			}
		}
		if (bestmove.length == 0) {
			game.print("[BOT]:" + color + "  Error: No Playable Moves");
//			Why is this here? vvvvvvv?
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
		}
		return bestmove;
	}

}
