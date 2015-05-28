package ch.windmill.gameOfLife.ui;

import ch.windmill.gameOfLife.World;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author jaunerc
 * @version 1.0.0
 */
public class Board {
    private int xAxis, yAxis;
    private World world;
    private JFrame window;
    
    /**
     * 
     */
    public Board() {
        xAxis = 3;
        yAxis = 3;
        world = new World(xAxis, xAxis);
        
        initializeUI();
        window.setVisible(true);
        
        for(int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                world.startEngine();
                window.repaint();
            } catch (InterruptedException ex) {
                Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * 
     */
    private void initializeUI() {
        GridLayout layout = new GridLayout(xAxis, yAxis);
        window = new JFrame("Game of life");
        
        // frame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(layout);
        
        // add fields
        for(int i = (world.getYAxis()-1); i >= 0; i--) {
            for(int j = 0; j < world.getXAxis(); j++) {
                window.add(world.getCell(i, j).getPanel());
            }
        }
        
        window.pack();
    }
}
