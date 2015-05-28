package ch.windmill.gameOfLife;

import java.util.ArrayList;

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
    
    public boolean[][] evolve(final Cell[][] map) {
        boolean[][] newGen = new boolean[map.length][map[0].length];
        int countAlive = 0;
        
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                newGen[i][j] = false;
                countAlive = 0;
                
                ArrayList<Cell> n = getNeighbours(map, i, j);
                for(Cell c : n) {
                    if(c.isAlive()) {
                        countAlive++;
                    }
                }
                
                if(map[i][j].isAlive()) {
                    for(int k = 0; k < rules.getRemain().length; k++) {
                        if(countAlive == rules.getRemain()[k]) {
                            newGen[i][j] = true;
                            System.out.println("remain "+i+","+j+" "+countAlive);
                            break;
                        }
                    }
                } else {
                    for(int k = 0; k < rules.getBirth().length; k++) {
                        if(countAlive == rules.getBirth()[k]) {
                            newGen[i][j] = true;
                            System.out.println("birth of a cell "+i+","+j+" "+countAlive);
                            break;
                        }
                    }
                }
            }
        }
        
        return newGen;
    }
    
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
}
