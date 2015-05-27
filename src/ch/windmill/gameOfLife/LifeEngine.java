package ch.windmill.gameOfLife;

import java.util.Arrays;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class LifeEngine {
    private RuleSet rules;
    
    /**
     * 
     */
    public LifeEngine() {
        this(RuleSet.CONWAY);
    }
    
    /**
     * 
     * @param rules 
     */
    public LifeEngine(final RuleSet rules) {
        this.rules = rules;
    }
    
    /**
     * 
     * @return 
     */
    public RuleSet getRules() {
        return rules;
    }
    
    /**
     * 
     * @param rules 
     */
    public void setRules(RuleSet rules) {
        this.rules = rules;
    }
    
    public void evolve(final Cell[][] map) {
        Cell[][] newGeneration = new Cell[map.length][map[0].length];
        int tmpX, tmpY, countAlive = 0;
        int[] neighbours = new int[8];
        boolean right, left, up, down;
        
        map[0][0].setAlive();
        
        for(int i = 0; i < map.length; i++) {
            /**if(i > 0) {                 // left
                left = true;
            } else {
                left = false;
            }
            
            if(i != (map.length -1)) {  // right
                right = true;
            } else {
                right = false;
            }*/
            
            for(int j = 0; j < map[0].length; j++) {
                
                /**if(j > 0) {
                    down = true;
                    if(map[i][j-1].isAlive()) {
                        countAlive++;
                    }
                } else {
                    down = false;
                }
                
                if(j < (map[0].length -1)) {
                    up = true;
                    if(map[i][j+1].isAlive()) {
                        countAlive++;
                    }
                } else {
                    up = false;
                }
                
                if(left) {
                    if(map[i-1][j].isAlive()) {
                        countAlive++;
                    }
                    if(up) {
                       if(map[i-1][j+1].isAlive()) {
                           countAlive++;
                       } 
                    }
                    if(down) {
                        if(map[i-1][j-1].isAlive()) {
                           countAlive++;
                       } 
                    }
                }
                
                if(right) {
                    if(map[i+1][j].isAlive()) {
                        countAlive++;
                    }
                    if(up) {
                       if(map[i+1][j+1].isAlive()) {
                           countAlive++;
                       } 
                    }
                    if(down) {
                        if(map[i+1][j-1].isAlive()) {
                           countAlive++;
                       } 
                    }
                }*/
            }
        }
    }
    
    private int[] getNeighbours(final int[][] map, final int x, final int y) {
        int[] neighbours = new int[8];
        
        if(y > 0 && y < map[0].length) {
            
        } else if(y == 0) {
            
        } else if(y == map[0].length -1) {
            
        }
        
        return neighbours;
    }
}
