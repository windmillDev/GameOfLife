package ch.windmill.gameOfLife;

import ch.windmill.gameOfLife.ui.Board;

/**
 * Lauchner for the game of live application.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Launcher {
    
    /**
     * Creates a new board instance and start the game.
     * @param args Console line arguments.
     */
    public static void main(String[] args) {
        new Board();
    }
}
