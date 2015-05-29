package ch.windmill.gameOfLife.ui;

import ch.windmill.gameOfLife.World;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Board implements MouseListener{
    private int xAxis, yAxis;
    private World world;
    private JFrame window;
    
    /**
     * 
     */
    public Board() {
        xAxis = 10;
        yAxis = 10;
        world = new World(xAxis, xAxis);
        
        initializeUI();
        window.setVisible(true);
        
        while(true) {
            try {
                Thread.sleep(500);
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
        JPanel panelFields = new JPanel(new GridLayout(xAxis, yAxis));
        window = new JFrame("Game of life");
        
        // panel
        panelFields.setPreferredSize(new Dimension(200, 200));
        
        // frame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());
        window.add(panelFields);
        
        // add all fields to the frame
        for(int i = (world.getYAxis()-1); i >= 0; i--) {
            for(int j = 0; j < world.getXAxis(); j++) {
                world.getCell(i, j).getPanel().addMouseListener(this);  // add listener
                panelFields.add(world.getCell(i, j).getPanel());             // add panel to the frame
            }
        }
        
        window.pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for(int i = 0; i < world.getXAxis(); i++) {
            for(int j = 0; j < world.getYAxis(); j++) {
                if(e.getSource().equals(world.getCell(i, j).getPanel())) {
                    world.getCell(i, j).setAlive(true);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        for(int i = 0; i < world.getXAxis(); i++) {
            for(int j = 0; j < world.getYAxis(); j++) {
                if(e.getSource().equals(world.getCell(i, j))) {
                    world.getCell(i, j).setAlive(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
