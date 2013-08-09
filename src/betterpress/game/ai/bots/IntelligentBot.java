package betterpress.game.ai.bots;

import betterpress.game.letterpress.Board;
import betterpress.game.letterpress.GameContext;

public class IntelligentBot extends Bot {

	public IntelligentBot(GameContext game, Board board) {
		super(game, board);
	}

	@Override
	protected int[][] chooseMove(char color) {
		double max = 0;
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
			char otherColor;
			if (color == 'r') {
				otherColor = 'b';
			} else {
				otherColor = 'r';
			}
			int darkMyColor = count(simBoard, Character.toUpperCase(color));
			int lightMyColor = count(simBoard, Character.toLowerCase(color));
			int lightOtherColor = count(simBoard, otherColor);
			double nextWordScore = 10*darkMyColor + lightMyColor + 0.1*lightOtherColor;
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
