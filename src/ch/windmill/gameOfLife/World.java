package ch.windmill.gameOfLife;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class World {
    private Cell[][] map;
    private int xAxis, yAxis;
    
    /**
     * 
     * @param xAxis
     * @param yAxis
     */
    public World(final int xAxis, final int yAxis) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        map = new Cell[xAxis][yAxis];
        
        initializeMap(xAxis, yAxis);
    }

    public int getXAxis() {
        return xAxis;
    }

    public int getYAxis() {
        return yAxis;
    }
    
    public Cell getCell(final int x, final int y) {
        return map[x][y];
    }
    
    /**
     * 
     * @return 
     */
    public Cell[][] getMap() {
        return map;
    }
    
    public void startEngine() {
        LifeEngine engine = new LifeEngine();
        boolean[][] newGen = engine.evolve(map);
        
        for(int i = 0; i < newGen.length; i++) {
            for(int j = 0; j < newGen[0].length; j++) {
                if(newGen[i][j]) {
                    map[i][j].setAlive(true);
                } else {
                    map[i][j].setAlive(false);
                }
            }
        }
    }
    
    public void printOutWorld() {
        for(int j = map.length-1; j >= 0; j--) {
            for(int i = 0; i < map[0].length; i++) {
                System.out.print("|"+map[i][j].isAlive()+"|");
            }
            System.out.println();
        }
    }
    
    /**
     * 
     * @param axisX
     * @param axisY 
     */
    private void initializeMap(final int axisX, final int axisY) {
        for(int i = 0; i < axisX; i++) {
            for(int j = 0; j < axisY; j++) {
                map[i][j] = new Cell();
            }
        }
        
        map[1][1].setAlive(true);
        map[0][1].setAlive(true);
        map[1][0].setAlive(true);
    }
}
