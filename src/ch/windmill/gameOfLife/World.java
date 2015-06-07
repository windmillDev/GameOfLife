package ch.windmill.gameOfLife;

import java.awt.Graphics;
import java.util.Random;

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
    private final int xAxis, yAxis, cellSize;
    
    /**
     * Creates a new world object. This constructor invokes the main constructor with the given parameters
     * and a <code>gameOfLife.LifeEngine</code> reference.
     * @param xAxis The horizontal size.
     * @param yAxis The vertical size.
     */
    public World(final int xAxis, final int yAxis) {
        this(xAxis, yAxis, 10, new LifeEngine());
    }
    
    /**
     * Creates a new world object. This constructor initialize the 2D cell array and create a cell for each
     * place.
     * @param height The horizontal size in pixels.
     * @param width The vertical size in pixels.
     * @param cellSize The size of the cell in pixels.
     * @param engine The engine to evolve cells.
     */
    public World(final int height, final int width, final int cellSize, LifeEngine engine) {
        this.cellSize = cellSize;
        this.engine = engine;
        xAxis = height / cellSize;
        yAxis = width / cellSize;
        
        map = new Cell[xAxis][yAxis];
        initializeMap();
    }
    
    /**
     * Create cell objects and save their references into the 2D cell array.
     */
    private void initializeMap() {
        for(int i = 0; i < xAxis; i++) {
            for(int j = 0; j < yAxis; j++) {
                map[i][j] = new Cell(false, cellSize);
            }
        }
    }
    
    /**
     * The size of the x axis.
     * @return The size of the x axis.
     */
    public int getXAxis() {
        return xAxis;
    }
    
    /**
     * The size of the y axis.
     * @return The size of the y axis.
     */
    public int getYAxis() {
        return yAxis;
    }
    
    /**
     * The size of the cells in pixels.
     * @return The size of the cells in pixels.
     */
    public int getCellSize() {
        return cellSize;
    }
    
    /**
     * Get the reference to the lifeengine object.
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
     * The 2D array of cells.
     * @return The 2D array of cells.
     */
    public Cell[][] getMap() {
        return map;
    }
    
    
    /**
     * Set the rules for the life engine.
     * @param r Rule set.
     */
    public void setEngineRules(final RuleSet r) {
        engine.setRules(r);
    }
    
    /**
     * Evolve the current generation of cells. Start the engine to calculate a new generation.
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
    
    /**
     * Kill every cell of the current generation.
     */
    public void killGeneration() {
        for(int i = 0; i < xAxis; i++) {
            for(int j = 0; j < yAxis; j++) {
                map[i][j].setAlive(false);
            }
        }
    }
    
    /**
     * Create a random generation. The parameter value is the percentage of living cells. This value must be
     * greater than 0.0f and lower or equal than 1.0f.
     * @param percentAlive The percentage of living cells.
     * @throws IllegalArgumentException Illegal percentage.
     */
    public void randomGeneration(final float percentAlive) throws IllegalArgumentException {
        int aliveCells = (int) (xAxis*yAxis*percentAlive);
        Random ran = new Random();
        Cell c = null;
        
        if(percentAlive <= 1.0f || percentAlive > 0.0f) {
            while(aliveCells > 0) {
                if(!(c = getCell((int) (xAxis * ran.nextDouble()), (int) (yAxis * ran.nextDouble()))).isAlive()) {
                    c.setAlive(true);
                    aliveCells--;
                }
            }
        } else {
            throw new IllegalArgumentException("The parameter must be greater than 0.0f and lower or equal 1.0f ");
        }
    }
    
    /**
     * Draw each cell to the given graphics context.
     * @param g The graphic context to draw to.
     */
    public void drawWorld(final Graphics g) {
        for(int i = 0; i < xAxis; i++) {
            for(int j = 0; j < yAxis; j++) {
                map[i][j].draw(g, i*cellSize, j*cellSize);
            }
        }
    }
}
