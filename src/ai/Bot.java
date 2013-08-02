/*
 * Copyright (c) 2013 Elder Research, Inc.
 * All rights reserved. 
 */
package ai;

import java.util.HashSet;


/**
 * This class implements a bot that can play Letterpress.
 *
 * @author William Proffitt
 * @since Aug 2, 2013
 */
public class Bot implements Player {
    
    public int[][] provideMove(char[][] letterBoard, char[][] colorBoard, HashSet<String> usedWords, char turn) {
        int[][] wordToPlay = {{0}, {0}};
        return wordToPlay;
    }

}
