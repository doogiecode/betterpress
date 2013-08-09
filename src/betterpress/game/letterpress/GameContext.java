package betterpress.game.letterpress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import betterpress.game.ai.Player;
import betterpress.game.ai.WordGetter;
import betterpress.game.ai.bots.DefaultBot;
import betterpress.game.ai.bots.IntelligentBot;
import betterpress.ui.BetterPressWindow;
import betterpress.ui.BoardDisplay;

/**
 * 
 * @author <a href="tzakyrie@gmail.com">zab7ge</a>
 * @since Aug 8, 2013
 */
public class GameContext {

	private boolean DEBUG = true; // Switch me to disable most text output
	
	private final String DICTIONARY_FILE = "/resources/en.txt";
	private BetterPressWindow window;
	private BoardDisplay display;

	private Board board;
	private char turn;
	private HashSet<String> usedWords;
	private HashSet<String> dictionary;
	private Player redPlayer;
	private Player bluePlayer;
	private Set<int[][]> playableWords;

	public GameContext(BetterPressWindow window) {
		this.window = window;
		display = window.getBoardDisplay();
	}
	
	public GameContext(BetterPressWindow window, File boardInput) {
		this.window = window;
		display = window.getBoardDisplay();
		// Input board should be formatted as such:
		// abdrw
		// cvqpo
		// lobty
		// macgr
		// boppe
		//
		// rbrr 
		//  brrr
		// rbb b
		// brr  
		// bbrrr
		// 
		// word1
		// word2
		// etc.
		//
		try {
			BufferedReader br = new BufferedReader(new FileReader(boardInput));
			String nextline;
			char[][] letterBoard = new char[5][5];
			char[][] colorBoard = new char[5][5];
			int writeIndex = 0;
			while ((nextline = br.readLine()) != null && writeIndex < 5) {
				letterBoard[writeIndex] = nextline.toCharArray();
				++writeIndex;
			}
			
			writeIndex = 0;
			while ((nextline = br.readLine()) != null && writeIndex < 5) {
				colorBoard[writeIndex] = nextline.toCharArray();
				++writeIndex;
			}
			initializeDictionary();
			this.board = new Board(letterBoard, colorBoard);
			this.playableWords = WordGetter.getPlays(letterBoard, dictionary);
			while ((nextline = br.readLine()) != null) {
				if (dictionary.contains(nextline)) {
					usedWords.add(nextline);
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Board input file not found.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		if (dictionary == null) {
			initializeDictionary();
		}
			
		if (board == null) {
			board = new Board(false);
		}
		

		
		display.setLetterBoard(board.getLetterBoard(), 5, 5);
		display.setColorBoard(board.getColorBoard(), 5, 5);

		this.playableWords = WordGetter.getPlays(board.getLetterBoard(), dictionary);

		this.bluePlayer = new IntelligentBot(this, board);
		this.redPlayer = new IntelligentBot(this, board);

		this.turn = 'b'; // Blue goes first because.
		char w = playOneGame();
		String winner;
		if (w == 'r') {
			winner = "RedPlayer";
		} else if (w == 'b') {
			winner = "BluePlayer";
		} else {
			throw new IllegalStateException(
					"Should not have a winner that is not red or blue player.");
		}
		window.printToTextArea("Winner is " + winner + "!");
	}

	public char playWord(int[][] moves, char playerChar) {

		String word = board.whatWordDoesThisPlayMake(moves);

		if (isValidWord(word)) {
			usedWords.add(word);
			// Update colorboard to represent move
			board.setColorBoard(Board.colorTiles(board.getColorBoard(), moves, turn));
			display.setColorBoard(board.getColorBoard(), 5, 5);

			switchTurns();
			if (DEBUG) {
				window.printToTextArea("player " + turn + " played " + word);
				window.printToTextArea(board.betterPrint());
			}

			return board.checkWinner();
		} else {
			window.printToTextArea("[BOARD] Player " + turn
					+ " made an illegal move, and so loses.");
			switchTurns();
			return turn;
		}
	}

	/**
	 * Build the dictionary.
	 */
	private void initializeDictionary() {
		InputStreamReader isr = new InputStreamReader(getClass()
				.getResourceAsStream(DICTIONARY_FILE));
		this.dictionary = new HashSet<String>(100000);
		this.usedWords = new HashSet<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(isr);

			String nextline = "";

			while ((nextline = br.readLine()) != null) {
				dictionary.add(nextline);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * returns the letter of who wins, or ' ' if no one has won yet.
	 * @return A char representing the winner. ( 'r', 'b', or ' ')
	 */
	public char checkWinner() {
		char boardWinner = board.checkWinner();

		if (boardWinner != ' ') {
			return boardWinner;

		} else if (usedWords.size() == playableWords.size()) {

			return board.boardLeader();
		} else {
			return ' ';
		}
	}

	// returns the char of the winner as soon as someone wins.
	public char playOneGame() {
		char winner;
		while ((winner = promptCurrentPlayer()) == ' ') {
			continue;
		}
		return winner;
	}

	private char promptCurrentPlayer() {
		if (turn == 'r') {
			return playWord(redPlayer.provideMove(turn), turn);
		} else {
			return playWord(bluePlayer.provideMove(turn), turn);
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

	public boolean isValidWord(String word) {
		return (dictionary.contains(word) && !usedWords.contains(word));
	}

	public Set<int[][]> getPlayableWords() {
		return this.playableWords;
	}

	/**
	 * @return the redPlayer
	 */
	public Player getRedPlayer() {
		return redPlayer;
	}

	/**
	 * @param redPlayer
	 *            the redPlayer to set
	 */
	public void setRedPlayer(Player redPlayer) {
		this.redPlayer = redPlayer;
	}

	/**
	 * @return the bluePlayer
	 */
	public Player getBluePlayer() {
		return bluePlayer;
	}

	/**
	 * @param bluePlayer
	 *            the bluePlayer to set
	 */
	public void setBluePlayer(Player bluePlayer) {
		this.bluePlayer = bluePlayer;
	}

	public HashSet<String> getDictionary() {
		return this.dictionary;
	}

	public void setDictionary(HashSet<String> dict) {
		this.dictionary = dict;
	}

	public String whatWordDoesThisPlayMake(int[][] move) {
		return board.whatWordDoesThisPlayMake(move);
	}
	
	public void print(String s) {
		window.printToTextArea(s);
	}
}
