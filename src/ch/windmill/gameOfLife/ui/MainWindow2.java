package ch.windmill.gameOfLife.ui;

import ch.windmill.gameOfLife.RuleSet;
import ch.windmill.gameOfLife.World;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class MainWindow2 {
    private final static int WIDTH = 1000;
    private final static int HEIGHT = 1000;
    private final static int WAITTIME = 100;
    
    private final JFrame frame;
    private final World world;
    private Canvas canvas;
    private ControlPanel controlPanel;
    private Thread evolveThread;
    
    /**
     * Start the game of life application.
     * @param args Console line arguments.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        new MainWindow2();
    }
    
    /**
     * Create a new game of life.
     */
    public MainWindow2() {
        frame = new JFrame("Game of life");
        world = new World(WIDTH, HEIGHT);
        evolveThread = getEvolveThread();
        
        initUI();
        frame.setVisible(true);
    }
    
    /**
     * Initialize all ui components.
     */
    private void initUI() {
        canvas = new Canvas();
        controlPanel = new ControlPanel();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.EAST);
        frame.pack();
    }
    
    /**
     * Initialize a new evolving thread. This thread generates a new generation with the life engine. The thread
     * will stop after the number of <code>WAITTIME</code> milliseconds.
     * @return The evolve thread.
     */
    private Thread getEvolveThread() {
        return evolveThread = new Thread(() -> {
            while(evolveThread == Thread.currentThread()) {
                try {
                    Thread.sleep(WAITTIME);
                    world.startEngine();
                    controlPanel.incCounter();
                    canvas.repaint();
                } catch (InterruptedException ex) { }
            }
        });
    }
    
    /**
     * Provides a canvas panel to draw the cells onto.
     */
    private class Canvas extends JPanel {
        
        public Canvas() {
            super();
            setBorder(BorderFactory.createLineBorder(Color.yellow));
        }
        
        /**
         * Draw the whole world of cells onto the panel.
         * @param g The graphics context.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            world.drawWorld(g);
        }
    }
    
    /**
     * Provides a panel with components to control the game.
     */
    private class ControlPanel extends JPanel {
        private final static int BTNWIDTH = 80;
        private final static int BTNHEIGHT = 18;
        
        private JLabel lblProcessState, lblGeneration;
        private JButton btnStart, btnStop, btnKill, btnRandom;
        private JComboBox boxRules;
        private ProcessState state;
        private int generationCounter;
        
        public ControlPanel() {
            generationCounter = 0;
            state = ProcessState.STOPPED;
            initUI();
        }
        
        public void incCounter() {
            generationCounter++;
            lblGeneration.setText("Generation: "+generationCounter);
            lblGeneration.repaint();
        }
        
        private void initUI() {
            lblProcessState = new JLabel();
            lblGeneration = new JLabel();
            btnStart = new JButton("Start");
            btnStop = new JButton("Stop");
            btnKill = new JButton("Kill");
            btnRandom = new JButton("Random");
            boxRules = new JComboBox(RuleSet.values());
            
            // set the text of the labels
            changeStateText(false);
            setCounterToZero();
            
            // action listeners
            addStartAction(btnStart, btnKill);
            addStopAction(btnStop, btnKill);
            addKillAction(btnKill);
            addRandomAction(btnRandom);
            
            // configure the jframe
            setLayout(new FlowLayout());
            setPreferredSize(new Dimension(150, 100));
            add(lblProcessState);
            add(lblGeneration);
            add(boxRules);
            add(btnStart);
            add(btnStop);
            add(btnKill);
            add(btnRandom);
        }
        
        private void changeStateText(final boolean running) {
            if(running) {
                state = ProcessState.RUNNING;
            } else {
                state = ProcessState.STOPPED;
            }
            lblProcessState.setText("Current state: "+state.getText());
        }
        
        private void addStartAction(final JButton b, final JButton bKill) {
            b.addActionListener((ActionEvent e) -> {
                if(evolveThread == null) {
                    evolveThread = getEvolveThread();
                }
                world.setEngineRules((RuleSet) boxRules.getSelectedItem());
                evolveThread.start();
                changeStateText(true);
                bKill.setEnabled(false);
            });
        }
        
        private void addStopAction(final JButton b, final JButton bKill) {
            b.addActionListener((ActionEvent e) -> {
                evolveThread = null;
                changeStateText(false);
                bKill.setEnabled(true);
            });
        }
        
        private void addKillAction(final JButton b) {
            b.addActionListener((ActionEvent e) -> {
                world.killGeneration();
                setCounterToZero();
                canvas.repaint();
            });
        }
        
        private void addRandomAction(final JButton b) {
            b.addActionListener((ActionEvent e) -> {
                world.killGeneration();
                world.randomGeneration(0.2f);
                canvas.repaint();
            });
        }
        
        private void setCounterToZero() {
            lblGeneration.setText("Generation: "+(generationCounter = 0));
        }
    }
}
