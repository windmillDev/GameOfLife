package ch.windmill.gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * This class represents a single cell in the game of life. A cell can be alive or dead. It has a 
 * <code>swing.JPanel</code> object to show the state of the cell in a swing ui.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Cell {
    private final static Color COLORDEAD = Color.black;
    private final static Color COLORALIVE = Color.YELLOW;
    
    private boolean isAlive;
    private JPanel panel;
    
    /**
     * Creates a new cell object. This constructor invokes the main constructor <code>Cell(false, 5)</code>.
     */
    public Cell() {
        this(false, 5);
    }
    
    /**
     * Creates a new cell object.
     * @param isAlive The living state of the cell.
     * @param panelSize The size in pixels for the panel axis.
     */
    public Cell(final boolean isAlive, final int panelSize) {
        this.isAlive = isAlive;
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(panelSize, panelSize));
        panel.setBackground(COLORDEAD);
        
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
     * @return The reference to the jpanel object.
     */
    public JPanel getPanel() {
        return panel;
    }
    
    /**
     * Change the living state of the cell. This method changes the background color of the jpanel.
     * @param isAlive True to set the cell alive, false to set it dead.
     */
    public void setAlive(final boolean isAlive) {
        this.isAlive = isAlive;
        
        if(isAlive) {
            panel.setBackground(COLORALIVE);
        } else {
            panel.setBackground(COLORDEAD);
        }
    }
    
    /**
     * Set the jpanel for the cell.
     * @param panel The reference to a jpanel object.
     */
    public void setPanel(final JPanel panel) {
        this.panel = panel;
    }
}
