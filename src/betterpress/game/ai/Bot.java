/*
 * Copyright (c) 2013 Elder Research, Inc.
 * All rights reserved. 
 */
package betterpress.game.ai;

import betterpress.game.letterpress.Board;
import betterpress.game.letterpress.GameContext;

/**
 * This class implements a bot that can play Letterpress.
 * 
 * @author William Proffitt
 * @since Aug 2, 2013
 */
public class Bot implements Player {

	private GameContext game;
	private Board board;

	public Bot(GameContext game, Board board) {
		this.board = board;
		this.game = game;
	}

	public int[][] provideMove(char turn) {
		return chooseMove(turn);
	}

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

	private int count(char[][] board, char target) {
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

	private int[][] chooseMove(char color) {
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

	private char[][] simulateMove(int[][] move, char color) {		
		char[][] resultingBoard;
		resultingBoard = Board.colorTiles(board.getColorBoard(), move, color);
		return resultingBoard;

	}

	private boolean weWin(char[][] board, char color) {
		int nulls = count(board, ' ');
		
		if (nulls == 0) {
			int reds = count(board, color) + count(board, Character.toUpperCase(color));
			if (reds > 12) {
				return true;
			}
		}
		return false;
	}

	private int totalDarkMyColor(char[][] targetBoard, char color) {
		char target = Character.toUpperCase(color);
		int score = count(targetBoard, target);
		return score;
	}

}
