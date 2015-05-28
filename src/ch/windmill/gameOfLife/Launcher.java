package ch.windmill.gameOfLife;

import ch.windmill.gameOfLife.ui.Board;

/**
 *
 * @author Cyrill Jauner
 */
public class Launcher {
    
    public static void main(String[] args) {
        /**World w = new World(3, 3);
        w.printOutWorld();
        long s = System.currentTimeMillis();
        w.startEngine();
        long e = System.currentTimeMillis();
        
        //System.out.println("calc time "+ (e-s)+" milliseconds.");
        w.printOutWorld();*/
        new Board();
    }
}
