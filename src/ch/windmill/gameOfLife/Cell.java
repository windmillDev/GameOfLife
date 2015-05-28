package ch.windmill.gameOfLife;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Cell {
    private boolean isAlive;
    private JPanel panel;
    
    /**
     * 
     */
    public Cell() {
        this(false);
    }
    
    public Cell(final boolean isAlive) {
        this.isAlive = isAlive;
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(10, 10));
        panel.setBackground(Color.red);
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public JPanel getPanel() {
        return panel;
    }
    
    public void setAlive(final boolean isAlive) {
        this.isAlive = isAlive;
        
        if(isAlive) {
            panel.setBackground(Color.green);
        } else {
            panel.setBackground(Color.red);
        }
    }
    
    public void setField(final JPanel panel) {
        this.panel = panel;
    }
}
