/*
 * Copyright (c) 2013 Elder Research, Inc.
 * All rights reserved. 
 */
package ai;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import board.Board;


/**
 * This class implements a bot that can play Letterpress.
 *
 * @author William Proffitt
 * @since Aug 2, 2013
 */
public class Bot implements Player {
    
    private Board board;
    
    public Bot(Board board) {
        this.board = board;
    }
    
    public int[][] provideMove(char[][] letterBoard, char[][] colorBoard, HashSet<String> usedWords, char turn) {
        return chooseMove(turn);
    }
    
    /**
     * 
     * @param lowerBound
     * @param upperBound
     * @return Random integer between lowerBound and upperBound inclusive
     */
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
    	System.out.println("[BOT] I am choosing a move.");
    	int max = 0;
    	int[][] bestmove = new int[0][0];
    	for (int[][] move : board.getPlayableWords()) {
    		int nextWordScore = totalDarkMyColor(move, color);
    		if (nextWordScore > max) {
    			bestmove = move;
    			max = nextWordScore;
    		}
    	}
    	if (bestmove.length == 0) {
    		System.out.println("[BOT] Error: No Playable Moves");
    		// try {Thread.sleep(10000);} catch (InterruptedException e) {}
    	}
    	return bestmove;
    }
    
    private int totalDarkMyColor(int[][] move, char color) {
    	char[][] targetBoard;
    	if (board.isValidWord(board.whatWordDoesThisPlayMake(move))) {
    		targetBoard = Board.colorTiles(board.getColorBoard(), move, color);
    		char target = Character.toUpperCase(color);
    		int score = count(targetBoard, target);
    		return score;
    	} else {
    		return -1; // This word's score is low because it is illegal. Do not play moves with negative score.
    	}
    }

}
