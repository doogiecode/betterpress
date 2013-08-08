package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ai.Bot;
import ai.Player;
import ai.WordGetter;

public class GameContext {
	private Board board;
	private char turn;
	private HashSet<String> usedWords;
	private HashSet<String> dictionary;
	private Player redPlayer;
	private Player bluePlayer;
	private Random random;
	private Set<int[][]> playableWords;
	
	public GameContext() {
		
	}
	
	public void start() {
		initializeDictionary();
		Board board = new Board(true, false);
		this.playableWords = WordGetter.getPlays(board.getLetterBoard(), dictionary);
		this.board = board;

		Bot bluePlayer = new Bot(this, this.board);
		Bot redPlayer = new Bot(this, this.board);
		this.bluePlayer = bluePlayer;
		this.redPlayer = redPlayer;

		// will currently fail because the Player objects are never initialized
		System.out.println(playOneGame());

	}
	
	public char playWord(int[][] moves, char playerChar) {

		String word = board.whatWordDoesThisPlayMake(moves);

		if (isValidWord(word)) {
			usedWords.add(word);
			// Update colorboard to represent move

			board.setColorBoard(board.colorTiles(board.getColorBoard(), moves, turn));

			switchTurns();
			if (false) {
				System.out.println("player " + turn + " played " + word);
				board.betterPrint();
			}

			return board.checkWinner();
		} else {
			System.out.println("[BOARD] Player " + turn
					+ " made an illegal move, and so loses.");
			switchTurns();
			return turn;
		}
	}
	
	private void initializeDictionary() {
		InputStreamReader isr = new InputStreamReader(getClass()
				.getResourceAsStream("/resources/en.txt"));
		random = new Random();
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
	
	// returns the letter of who wins, or ' ' if no one has won yet.
		char checkWinner() {
			
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
		while ((winner = promptCurrentPlayer()) == ' ')
			;
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
}
