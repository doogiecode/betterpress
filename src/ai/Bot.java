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
        Set<int[][]> playableWords = board.getPlayableWords();
        System.out.println("[Bot] Number of playable words: " + playableWords.size());
        
        Random random = new Random();
        int y = getRandomIntInRange(0, 4);
        int x = getRandomIntInRange(0, 4);
        
        Iterator<int[][]> i = board.getPlayableWords().iterator();
        
        int[][] wordToPlay = {{y}, {x}};
        String wordLiteral = board.whatWordDoesThisPlayMake(wordToPlay);
        System.out.println("[Bot] I might play: " + wordLiteral);
        
        return wordToPlay;
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

}
