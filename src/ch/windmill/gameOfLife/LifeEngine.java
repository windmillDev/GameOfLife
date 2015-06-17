package ch.windmill.gameOfLife;

import java.util.ArrayList;

/**
 * This class provides the engine to evolve cells. A life engine needs rules to decide which cells
 * will survive and which cells will be revived. All cells who doesnt pass the check will be dead in the
 * next generation.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class LifeEngine {
    private RuleSet rules;
    
    /**
     * Creates a new life engine object. This constructor invokes the main construchtor with the default 
     * parameter <code>RuleSet.CORNWAY</code>.
     */
    public LifeEngine() {
        this(RuleSet.CONWAY);
    }
    
    /**
     * Creates a new life engine object.
     * @param rules Set of rules to define the evolve process.
     */
    public LifeEngine(final RuleSet rules) {
        this.rules = rules;
    }
    
    /**
     * 
     * @return Get the rules.
     */
    public RuleSet getRules() {
        return rules;
    }
    
    /**
     * Set a new ruleset.
     * @param rules The reference to the ruleset.
     */
    public void setRules(RuleSet rules) {
        this.rules = rules;
    }
    
    /**
     * Calculate a new generation of cells. Every cell in the current generation will be checked. This method
     * write the results into an boolean array. True means that the cell on this position is alive. Otherwise
     * the cell is dead.
     * @param map The current generation.
     * @return The next generation.
     */
    public boolean[][] evolve(final Cell[][] map) {
        boolean[][] newGen = new boolean[map.length][map[0].length];
        boolean right, left;
        int countAlive, k;
        
        for(int i = 0; i < map.length; i++) {                              // check the current generation (X axis)
            right = false;
            left = false;
            if(i > 0 && i < map.length-1) {
                right = true;
                left = true;
            } else if(i == 0) {
                right = true;
            }  else if(i == map.length -1) {
                left = true;
            }
            
            for(int j = 0; j < map[0].length; j++) {                       // check the current generation (Y axis)
                newGen[i][j] = false;
                countAlive = countAliveNeighbours(map, i, j, right, left); // get the number of alive neighbours
                
                if(map[i][j].isAlive()) {                                  // the current cell is alive
                    for(k = 0; k < rules.getRemain().length; k++) {
                        if(countAlive == rules.getRemain()[k]) {
                            newGen[i][j] = true;
                            break;
                        }
                    }
                } else {                                                   // the current cell is dead
                    for(k = 0; k < rules.getBirth().length; k++) {
                        if(countAlive == rules.getBirth()[k]) {
                            newGen[i][j] = true;
                            break;
                        }
                    }
                }
            }
        }
        
        return newGen;
    }
    
    /**
     * Get the references of all neighbour cells.
     * @param map The current generation.
     * @param x The current x position.
     * @param y The current y position.
     * @return Arraylist with references of the neighbours.
     */
    private ArrayList<Cell> getNeighbours(final Cell[][] map, final int x, final int y) {
        ArrayList<Cell> n = new ArrayList<>();
        boolean up = false;
        boolean down = false;
        
        if(y > 0 && y < map[0].length-1) {
            n.add(map[x][y+1]); // up
            n.add(map[x][y-1]); // down
            up = down = true;
        } else if(y == 0) {
            n.add(map[x][y+1]); // up
            up = true;
        } else if(y == map[0].length -1) {
            n.add(map[x][y-1]); // down
            down = true;
        }
        
        if(x > 0 && x < map.length-1) {
            n.add(map[x+1][y]); // right
            n.add(map[x-1][y]); // left
            if(up) {
                n.add(map[x+1][y+1]); // up - right
                n.add(map[x-1][y+1]); // up - left
            }
            if(down) {
                n.add(map[x+1][y-1]); // down - right
                n.add(map[x-1][y-1]); // down - left
            }
        } else if(x == 0) {
            n.add(map[x+1][y]); // right
            if(up) {
                n.add(map[x+1][y+1]); // up - right
            }
            if(down) {
                n.add(map[x+1][y-1]); // down - right
            }
        } else if(x == map.length -1) {
            n.add(map[x-1][y]); // left
            if(up) {
                n.add(map[x-1][y+1]); // up - left
            }
            if(down) {
                n.add(map[x-1][y-1]); // down - left
            }
        }
        
        return n;
    }
    
    /**
     * Count all living neighbour cells. The algorithm checks every cell in the neighbourhood. The following
     * graphic illustrates this calculation. The cell in the middle is the current cell to check.
     * <br>
     * |D|A|D|<br>
     * |D|C|D|<br>
     * |A|D|D|
     * <br>
     * 
     * @param map The current generation.
     * @param x The current x position.
     * @param y The current y position.
     * @param right If there are cells right of this cell.
     * @param left If there are cells left of this cell.
     * @return Number of living cells in the neighbourhood.
     */
    private int countAliveNeighbours(final Cell[][] map, final int x, final int y, final boolean right,
            final boolean left) {
        int alive = 0;
        ArrayList<Cell> n = new ArrayList<>();
        
        if(y > 0 && y < map[0].length-1) {
            n.add(map[x][y+1]);         // up
            n.add(map[x][y-1]);         // down
            if(right) {
                n.add(map[x+1][y]);     // right
                n.add(map[x+1][y+1]);   // up - right
                n.add(map[x+1][y-1]);   // down - right
            }
            if(left) {
                n.add(map[x-1][y]);     // left
                n.add(map[x-1][y+1]);   // up - left
                n.add(map[x-1][y-1]);   // down - left
            }
        } else if(y == 0) {
            n.add(map[x][y+1]);         // up
            if(right) {
                n.add(map[x+1][y]);     // right
                n.add(map[x+1][y+1]);   // up - right
            }
            if(left) {
                n.add(map[x-1][y]);     // left
                n.add(map[x-1][y+1]);   // up - left
            }
        } else if(y == map[0].length -1) {
            n.add(map[x][y-1]);         // down
            if(right) {
                n.add(map[x+1][y]);     // right
                n.add(map[x+1][y-1]);   // down - right
            }
            if(left) {
                n.add(map[x-1][y]);     // left
                n.add(map[x-1][y-1]);   // down - left
            }
        }
        
        for(Cell c : n) {               // check each cell if its alive
            if(c.isAlive()) {
                alive++;
            }
        }
        
        return alive;
    }
}
