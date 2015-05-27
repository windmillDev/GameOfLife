package ch.windmill.gameOfLife;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class World {
    private Cell[][] map;
    
    /**
     * 
     * @param axisX
     * @param axisY
     */
    public World(final int axisX, final int axisY) {
        map = new Cell[axisX][axisY];
        initializeMap(axisX, axisY);
    }
    
    /**
     * 
     * @return 
     */
    public Cell[][] getMap() {
        return map;
    }
    
    /**
     * 
     * @param map 
     */
    public void setMap(Cell[][] map) {
        this.map = map;
    }
    
    public void startEngine() {
        LifeEngine engine = new LifeEngine();
        engine.evolve(map);
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
    }
}
