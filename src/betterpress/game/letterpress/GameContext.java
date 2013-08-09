package betterpress.game.letterpress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import betterpress.game.ai.Player;
import betterpress.game.ai.WordGetter;
import betterpress.game.ai.bots.DefaultBot;
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

	public void start() {
		initializeDictionary();
		board = new Board(true, false);

		display.setLetterBoard(board.getLetterBoard());
		display.setColorBoard(board.getColorBoard());

		this.playableWords = WordGetter.getPlays(board.getLetterBoard(), dictionary);

		this.bluePlayer = new DefaultBot(this, board);
		this.redPlayer = new DefaultBot(this, board);

		// will currently fail because the Player objects are never initialized
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
			// Don't allow any substring words either
			removeSubPlayedWords(word);
			// Update colorboard to represent move
			board.setColorBoard(Board.colorTiles(board.getColorBoard(), moves, turn));
			display.setColorBoard(board.getColorBoard());

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
	
	private void removeSubPlayedWords(String word) {
		for (int i=2; i < word.length()-1; i++) {
			usedWords.add(word.substring(0, i));
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
