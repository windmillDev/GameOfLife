package ch.windmill.gameOfLife;

/**
 *
 * @author Cyrill Jauner
 */
public class Launcher {
    
    public static void main(String[] args) {
        World w = new World(1000, 1000);
        w.startEngine();
    }
}
