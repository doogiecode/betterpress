/*
 * Copyright (c) 2013 Elder Research, Inc.
 * All rights reserved. 
 */
package ai;

import java.util.HashSet;
import java.util.Iterator;
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
        
        
        Iterator<int[][]> i = board.getPlayableWords().iterator();
        
        int[][] wordToPlay = i.next(); 
       
        return wordToPlay;
    }

}
