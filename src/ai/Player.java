package ai;

import java.util.HashSet;

public interface Player {
	public int[][] provideMove(char[][] letterBoard, char[][] colorBoard, HashSet<String> usedWords, char turn);
}