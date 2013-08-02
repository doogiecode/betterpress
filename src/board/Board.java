package board;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import AI.Player;
import AI.WordGetter;

public class Board {
	private static char[][] letterBoard;
	public char[][] colorBoard;
	
	// used for generating random boards
	Random random;
	
	// all words that are allowed, initialized from dictfile
	HashSet<String> dict;
	File dictfile = new File("/usr/share/dict/words");
	
	// words that have been played already
	HashSet<String> used;
	int pass = 0;

	// The two players, whether Bot or Human objects.
	private Player redPlayer;
	private Player bluePlayer;

	// Letter representing whose turn it is
	private char turn;

	public void printLetters() {
		for (char[] row : letterBoard) {
			for (char letter : row) {
				System.out.print(letter + " ");
			}
			System.out.println();
		}
	}
	
	public void printColors() {
		for (char[] row : colorBoard) {
			for (char letter : row) {
				System.out.print(letter + " ");
			}
			System.out.println();
		}
	}

	// updates the board to reflect moves, if legal. else, prompts current player.
	private char playWord(int[][] moves, char playerChar) {
		if (moves.length == 0) {
			++pass;
		} else {
			pass = 0;
		}
		char[] word = new char[moves.length];
		for (int i = 0; i < moves.length; ++i) {
			int[] loc = moves[i];
			word[i] = letterBoard[loc[0]][loc[1]];
		}

		if (dict.contains(new String(word)) && !used.contains(word)) {
			used.add(new String(word));
			// Update colorboard to represent move
			for (int[] loc : moves) {
				char target = colorBoard[loc[0]][loc[1]];
				if (Character.isLowerCase(target)) {
					colorBoard[loc[0]][loc[1]] = playerChar;
				}
			}
			
			updateLocked();
			switchTurns();
			return checkWinner();
		} else {
			return promptCurrentPlayer();
		}
	}

	// super simple. changes whose turn it is.
	private void switchTurns() {
		if (turn == 'r') {
			turn = 'b';
		} else {
			turn = 'r';
		}
	}

	// tell whoever's turn it is to provide a move, then plays that move.
	// char returned is the winner, as determined by checkWinner. Will be ' ' until the game is over.
	private char promptCurrentPlayer() {
		if (turn == 'r') {
			return playWord(redPlayer.provideMove(letterBoard, colorBoard, used, turn), turn);
		} else {
			return playWord(bluePlayer.provideMove(letterBoard, colorBoard, used, turn), turn);
		}
	}
	
	// Constructor. Random characters, no players constructed yet. Blue goes first.
	// Dictionary is read in in this constructor, exception currently unhandled.
	public Board() {
		random = new Random();
		
		HashSet<String> dict = new HashSet<String>(100000);
		HashSet<String> used = new HashSet<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(dictfile));

			String nextline = "";

			while ((nextline = br.readLine()) != null) {
				dict.add(nextline);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Initialize with random
		letterBoard = new char[5][5];
		colorBoard = new char[5][5];
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 5; ++j) {
				char fill = ((char) (random.nextInt(26) + 'a'));
				letterBoard[i][j] = fill;
				colorBoard[i][j] = random.nextBoolean() ? 'r' : 'b';
			}
		}

		// Blue goes first because I said so
		turn = 'b';
	}
	
	// returns the letter of who wins, or ' ' if no one has won yet.
	private char checkWinner() {
		int blue = 0;
		int red = 0;
		for (char[] row : colorBoard) {
			for (char color : row) {
				if (color == 'r' || color == 'R') {
					++red;
				}
				if (color == 'b' || color == 'B') {
					++blue;
				}
			}
		}
		if (blue + red == 25) {
			if (red > blue) {
				return 'r';
			} else {
				return 'b';
			}
		} else {
			return ' ';
		}
	}

	// This function is ugly. Do not look at it.
	private void updateLocked() {
		char[][] newColorBoard = new char[5][5];
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 5; ++j) {
				// check whether [i][j] should be locked
				
				// assume locked
				newColorBoard[i][j] = Character.toUpperCase(colorBoard[i][j]);

				// for all 5 surrounding squares...
				for (int x = -1; x < 2; ++x) {
					// (but skip null pointer exceptions)
					if (i + x < 0 || i + x > 4) {
						continue;
					}
					for (int y = -1; y < 2; ++y) {
						// (but skip null pointer exceptions)
						if (j + y < 0 || j + y > 4) {
							continue;
						}

						// Also skip the corners.
						if (x * y != 0) {
							continue;
						}
						// if any don't match, don't lock it
						if (colorBoard[i][j] != Character
								.toLowerCase(colorBoard[i + x][j + y])) {
							newColorBoard[i][j] = colorBoard[i][j];
						}
					}
				}
			}
		}
		colorBoard = newColorBoard;
	}
	
	// Stores the boardstate, plays move, returns current boardstate, reverts
	public char[][] hypotheticalMove(int[][] move, char color) {
		char[][] storedColorBoard = colorBoard;
		char[][] returnBoard;
		HashSet<String> storedUsed = used;
		playWord(move, color);
		returnBoard = colorBoard;
		colorBoard = storedColorBoard;
		turn = color;
		used = storedUsed;
		return returnBoard;
		
	}

	// returns the char of the winner as soon as someone wins.
	public char playOneGame() {
		char winner;
		while ((winner = promptCurrentPlayer()) == ' ');
		return winner;
	}

	public static void main(String[] args) {
		Board board = new Board();
		board.playOneGame();

	}
}
