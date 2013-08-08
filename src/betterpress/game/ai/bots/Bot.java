/*
 * Copyright (c) 2013 Elder Research, Inc.
 * All rights reserved. 
 */
package betterpress.game.ai.bots;

import betterpress.game.ai.Player;
import betterpress.game.letterpress.Board;
import betterpress.game.letterpress.GameContext;

/**
 * This class implements a bot that can play Letterpress.
 * 
 * @author William Proffitt
 * @since Aug 2, 2013
 */
public abstract class Bot implements Player {

	protected GameContext game;
	protected Board board;

	public Bot(GameContext game, Board board) {
		this.board = board;
		this.game = game;
	}

	public int[][] provideMove(char turn) {
		return chooseMove(turn);
	}
	
	protected abstract int[][] chooseMove(char color);

	@Deprecated
	public int getRandomIntInRange(int lowerBound, int upperBound) {
		if (lowerBound > upperBound) {
			int tempLowerBound = lowerBound;
			int tempUpperBound = upperBound;
			lowerBound = tempUpperBound;
			upperBound = tempLowerBound;
		}

		upperBound++;
		int differenceInBounds = upperBound - lowerBound;
		return (int) (Math.floor(Math.random() * differenceInBounds) + lowerBound);
	}

	protected int count(char[][] board, char target) {
		int count = 0;
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 5; ++j) {
				if (target == board[i][j]) {
					++count;
				}
			}
		}
		return count;
	}

	protected char[][] simulateMove(int[][] move, char color) {		
		char[][] resultingBoard;
		resultingBoard = Board.colorTiles(board.getColorBoard(), move, color);
		return resultingBoard;

	}

	protected boolean weWin(char[][] board, char color) {
		int nulls = count(board, ' ');
		
		if (nulls == 0) {
			int reds = count(board, color) + count(board, Character.toUpperCase(color));
			if (reds > 12) {
				return true;
			}
		}
		return false;
	}

	protected int totalDarkMyColor(char[][] targetBoard, char color) {
		char target = Character.toUpperCase(color);
		int score = count(targetBoard, target);
		return score;
	}

}
