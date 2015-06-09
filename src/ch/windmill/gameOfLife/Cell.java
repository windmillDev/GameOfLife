package ch.windmill.gameOfLife;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents a single cell in the game of life. A cell can be alive or dead.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Cell {
    public final static Color COLORDEAD = Color.yellow;
    public final static Color COLORALIVE = Color.black;
    
    private boolean isAlive;
    private int size;
    
    
    /**
     * Creates a new cell object. This constructor invokes the main constructor <code>Cell(false, 5)</code>.
     */
    public Cell() {
        this(false, 5);
    }
    
    /**
     * Creates a new cell object.
     * @param isAlive The living state of the cell.
     * @param size The size in pixels.
     */
    public Cell(final boolean isAlive, final int size) {
        this.isAlive = isAlive;
        this.size = size;
    }
    
    /**
     * 
     * @return True if this cell is alive, otherwise false.
     */
    public boolean isAlive() {
        return isAlive;
    }
    
    /**
     * 
     * @return The size in pixels.
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Change the living state of the cell.
     * @param isAlive True to set the cell alive, false to set it dead.
     */
    public void setAlive(final boolean isAlive) {
        this.isAlive = isAlive;
    }
    
    /**
     * Draw a rectangle with the correct live state colour. This method uses the given graphics to
     * draw a rectangle on the given position. If this cell is alive, the method uses the COLORALIVE value
     * otherwise the COLORDEAD value. The size of the rectangle is the value of the field size.
     * @param g The graphic context to draw to.
     * @param x The x position.
     * @param y The y position.
     */
    public void draw(Graphics g, final int x, final int y) {
        if(isAlive) {
            g.setColor(COLORALIVE);
        } else {
            g.setColor(COLORDEAD);
        }
        g.fillRect(x, y, size-1, size-1);
    }
}
