package ai;

import game.Board;

import java.util.HashSet;
import java.util.Set;

public class WordGetter {
	public static Set<int[][]> getPlays(char[][] letterBoard, Set<String> dict) {
	    System.out.println("[WordGetter] Number of words in dictionary: " + dict.size());
		Set<int[][]> plays = new HashSet<int[][]>();
		
		// Iterate through the dictionary, add all the ways to make all the words in it
		for (String word : dict) {
			
			Set<int[][]> ways = allWaysToMake(letterBoard, word, 0,
					new int[word.length()][2]);
			plays.addAll(ways);
		}
		
		return plays;
	}

	// index is the letter this iteration needs to fill in
	// play[][] will eventually be the word we play, after it gets filled in at index
	private static Set<int[][]> allWaysToMake(char[][] letterBoard, String word,
			int index, int[][] play) {
		
		
		// If we've already filled
		if (index == word.length()) {
			HashSet<int[][]> singlePlay = new HashSet<int[][]>();
			singlePlay.add(play);
			return singlePlay;
		}

		// Which character are we looking for a copy of in the letterboard?
		char[] wordarray = word.toCharArray();
		char next = wordarray[index];
		char[][] copyBoard;
		int[][] updatedPlay = play;
		Set<int[][]> plays = new HashSet<int[][]>();

		// Look through letterboard for the letter we want
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 5; ++j) {
				
				// every time we find it, recursively call this method again.
				// System.out.println("[WordGetter] Checking if " + next + " matches " + letterBoard[i][j]);
				if (letterBoard[i][j] == next) {
					// System.out.println("[WordGetter] I found a match for " + next);
					copyBoard = Board.deepCopy5x5Array(letterBoard);
					copyBoard[i][j] = ' ';
					updatedPlay[index][0] = i;

					updatedPlay[index][1] = j;
					// add everything our submethods find to the set of plays
					plays.addAll(allWaysToMake(Board.deepCopy5x5Array(copyBoard), word, index + 1,
							updatedPlay));
					copyBoard[i][j] = letterBoard[i][j];
				}

			}
		}
		// System.out.println("[WordGetter] Done looking for " + next);
		// Return the result, as summed up abovee
		return plays;
	}
}
