package ch.windmill.gameOfLife.ui;

import ch.windmill.gameOfLife.World;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class MainWindow implements MouseListener{
    private final int xAxis, yAxis;
    private final World world;
    private Thread evolveThread;
    private JFrame window;
    
    /**
     * Main method to start the programm.
     * @param args Console line arguments.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new MainWindow();
    }
    
    /**
     * 
     */
    public MainWindow() {
        xAxis = 100;
        yAxis = 100;
        world = new World(xAxis, xAxis);
        
        initializeUI();
        window.setVisible(true);
        evolveThread = getEvolveThread();
    }
    
    /**
     * 
     */
    private void initializeUI() {
        JPanel panelFields = new JPanel(new GridLayout(xAxis, yAxis));
        JPanel panelControl = new JPanel(new FlowLayout());
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        JTextPane paneState = new JTextPane();
        JButton btnStart = new JButton("Start");
        JButton btnStop = new JButton("Stop");
        JButton btnRandom = new JButton("Random");
        JButton btnClear = new JButton("Clear");
        window = new JFrame("Game of life");
        
        separator.setPreferredSize(new Dimension(140, 2));
        separator.setBorder(BorderFactory.createLineBorder(Color.black));
        
        addStartAction(btnStart, paneState);                                // buttons
        addStopAction(btnStop, paneState);
        addRandomPatternAction(btnRandom);
        
        changeStateText(false, paneState);                                  // text
        paneState.setEditable(false);
        
        panelFields.setPreferredSize(new Dimension(400, 400));              // panels
        panelControl.setPreferredSize(new Dimension(150, 100));
        panelControl.setBorder(BorderFactory.createLineBorder(Color.black));
        panelControl.add(paneState);
        panelControl.add(btnStart);
        panelControl.add(btnStop);
        panelControl.add(separator);
        panelControl.add(btnRandom);
        panelControl.add(btnClear);
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);              // frame
        window.setLayout(new BorderLayout());
        window.add(panelFields, BorderLayout.CENTER);
        window.add(panelControl, BorderLayout.EAST);
        
        for(int i = (world.getYAxis()-1); i >= 0; i--) {                    // add all fields to the frame
            for(int j = 0; j < world.getXAxis(); j++) {
                world.getCell(i, j).getPanel().addMouseListener(this);      // add listener
                panelFields.add(world.getCell(i, j).getPanel());            // add panel to the frame
            }
        }
        
        window.pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(int i = 0; i < world.getXAxis(); i++) {
            for(int j = 0; j < world.getYAxis(); j++) {
                if(e.getSource().equals(world.getCell(i, j).getPanel())) {
                    world.getCell(i, j).setAlive(true);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
    
    private void changeStateText(final boolean running, final JTextPane tp) {
        if(running) {
            tp.setText("Current state: "+ProcessState.RUNNING.getText());
        } else {
            tp.setText("Current state: "+ProcessState.STOPPED.getText());
        }
    }
    
    private void addStartAction(final JButton b, final JTextPane tp) {
        b.addActionListener((ActionEvent e) -> {
            if(evolveThread == null) {
                evolveThread = getEvolveThread();
            }
            evolveThread.start();
            changeStateText(true, tp);
        });
    }
    
    private void addStopAction(final JButton b, final JTextPane tp) {
        b.addActionListener((ActionEvent e) -> {
            evolveThread = null;
            changeStateText(false, tp);
        });
    }
    
    private void addRandomPatternAction(final JButton b) {
        b.addActionListener((ActionEvent e) -> {
            Random ran = new Random();
            for(int i = 0; i < xAxis; i++) {
                for(int j = 0; j < yAxis; j++) {
                    if(ran.nextBoolean()) {
                        world.getCell(i, j).setAlive(true);
                    } else {
                        world.getCell(i, j).setAlive(false);
                    }
                }
            }
        });
    }
    
    private Thread getEvolveThread() {
        return evolveThread = new Thread(() -> {
            while(evolveThread == Thread.currentThread()) {
                try {
                    Thread.sleep(500);
                    world.startEngine();
                    window.repaint();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
