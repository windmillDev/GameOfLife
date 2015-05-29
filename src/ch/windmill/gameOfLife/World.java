package ch.windmill.gameOfLife;

/**
 * This class provides the world in a game of life. The world has a 2D array of cells, this is the 
 * organism. It can start the life evolution with a <code>gameOfLife.LifeEngine</code>. The size of the world
 * is defined by the xaxis and yaxis fields.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class World {
    private LifeEngine engine;
    private final Cell[][] map;
    private final int xAxis, yAxis;
    
    /**
     * Creates a new world object. This constructor initialize the 2D cell array and create a cell for each
     * place.
     * @param xAxis The horizontal size.
     * @param yAxis The vertical size.
     */
    public World(final int xAxis, final int yAxis) {
        this(xAxis, yAxis, new LifeEngine());
    }
    
    public World(final int xAxis, final int yAxis, LifeEngine engine) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.engine = engine;
        map = new Cell[xAxis][yAxis];
        
        initializeMap(xAxis, yAxis);
    }
    
    /**
     * 
     * @return The size of the x axis.
     */
    public int getXAxis() {
        return xAxis;
    }
    
    /**
     * 
     * @return The size of the y axis.
     */
    public int getYAxis() {
        return yAxis;
    }
    
    /**
     * 
     * @return Get the reference to the lifeengine object.
     */
    public LifeEngine getEngine() {
        return engine;
    }
    
    /**
     * Get the reference to the cell object with the coordinates x and y.
     * @param x The position in the x axis.
     * @param y The position in the y axis.
     * @return Refernce to the cell object.
     */
    public Cell getCell(final int x, final int y) {
        return map[x][y];
    }
    
    /**
     * 
     * @return The 2D array of cells.
     */
    public Cell[][] getMap() {
        return map;
    }
    
    /**
     * Evolve the current generation of cells.
     */
    public void startEngine() {
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
        map[4][0].setAlive(true);
        map[4][2].setAlive(true);
        map[4][3].setAlive(true);
        map[4][4].setAlive(true);
        map[4][5].setAlive(true);
        map[4][6].setAlive(true);
        map[4][7].setAlive(true);
        map[4][8].setAlive(true);
        map[4][9].setAlive(true);
        map[4][1].setAlive(true);
    }
}
