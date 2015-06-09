package ch.windmill.gameOfLife.ui;

import ch.windmill.gameOfLife.RuleSet;
import ch.windmill.gameOfLife.World;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class provides the main window of the ui. It has a main method that will invoke the constructor of this
 * class. 
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class MainWindow {
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private final static int CELLSIZE = 2;
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
        new MainWindow();
    }
    
    /**
     * Create a new game of life.
     */
    public MainWindow() {
        frame = new JFrame("Game of life");
        world = new World(WIDTH, HEIGHT, CELLSIZE);
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
        frame.setResizable(false);
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
     * Provides a canvas panel to draw the cells onto. It has listeners for click and drag events.
     */
    private class Canvas extends JPanel implements MouseListener, MouseMotionListener{
        
        /**
         * Create a new canvas object.
         */
        public Canvas() {
            super();
            addMouseListener(this);
            addMouseMotionListener(this);
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
        
        /**
         * Calculate which cell was clicked and set that cell alive.
         * @param e Object reference with mouse informations.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            world.getCell((e.getX() / world.getCellSize()), (e.getY() / world.getCellSize())).setAlive(true);
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) { }

        @Override
        public void mouseReleased(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) { }

        @Override
        public void mouseExited(MouseEvent e) { }
        
        /**
         * Invokes the mouseClicked event.
         * @param e Object reference with mouse informations.
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            mouseClicked(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) { }
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
        
        /**
         * Create a new control panel.
         */
        public ControlPanel() {
            generationCounter = 0;
            state = ProcessState.STOPPED;
            initUI();
        }
        
        /**
         * Increment the generation counter. Update the text of the label and repaint it.
         */
        public void incCounter() {
            generationCounter++;
            lblGeneration.setText("Generation: "+generationCounter);
            lblGeneration.repaint();
        }
        
        /**
         * Initialize all ui components.
         */
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
            
            // set the size of the buttons
            btnStart.setPreferredSize(new Dimension(BTNWIDTH, BTNHEIGHT));
            btnStop.setPreferredSize(new Dimension(BTNWIDTH, BTNHEIGHT));
            btnKill.setPreferredSize(new Dimension(BTNWIDTH, BTNHEIGHT));
            btnRandom.setPreferredSize(new Dimension(BTNWIDTH, BTNHEIGHT));
            
            // action listeners
            addStartAction(btnStart, btnKill);
            addStopAction(btnStop, btnKill);
            addKillAction(btnKill);
            addRandomAction(btnRandom);
            
            // configure the jframe
            //setLayout(new FlowLayout());
            setLayout(new GridLayout(0, 1));
            setPreferredSize(new Dimension(150, 100));
            add(lblProcessState);
            add(lblGeneration);
            add(boxRules);
            add(btnStart);
            add(btnStop);
            add(btnKill);
            add(btnRandom);
        }
        
        /**
         * Change the state of the evolve thread to running or stopped.
         * @param running If the evolve thread is running.
         */
        private void changeStateText(final boolean running) {
            if(running) {
                state = ProcessState.RUNNING;
            } else {
                state = ProcessState.STOPPED;
            }
            lblProcessState.setText("Current state: "+state.getText());
            lblProcessState.repaint();
        }
        
        /**
         * Add an actionlistener to start the evolve thread.
         * @param b The button to add the actionlistener.
         * @param bDisable The button to disable when the button b is clicked.
         */
        private void addStartAction(final JButton b, final JButton bDisable) {
            b.addActionListener((ActionEvent e) -> {
                if(evolveThread == null) {
                    evolveThread = getEvolveThread();
                }
                world.setEngineRules((RuleSet) boxRules.getSelectedItem());
                evolveThread.start();
                changeStateText(true);
                bDisable.setEnabled(false);
            });
        }
        
        /**
         * Add an actionlistener to stop the evolve thread.
         * @param b The button to add the actionlistener.
         * @param bEnable The button to enable when the button b is clicked.
         */
        private void addStopAction(final JButton b, final JButton bEnable) {
            b.addActionListener((ActionEvent e) -> {
                evolveThread = null;
                changeStateText(false);
                bEnable.setEnabled(true);
            });
        }
        
        /**
         * Add an actionlistener to kill the current generation.
         * @param b The button to add the actionlistener.
         */
        private void addKillAction(final JButton b) {
            b.addActionListener((ActionEvent e) -> {
                world.killGeneration();
                setCounterToZero();
                canvas.repaint();
            });
        }
        
        /**
         * Add an actionlistener set a random generation.
         * @param b The button to add the actionlistener.
         */
        private void addRandomAction(final JButton b) {
            b.addActionListener((ActionEvent e) -> {
                world.killGeneration();
                world.randomGeneration(0.2f);
                canvas.repaint();
            });
        }
        
        /**
         * Set the value of the generation counter to zero. Update the text of the label and repaint it.
         */
        private void setCounterToZero() {
            lblGeneration.setText("Generation: "+(generationCounter = 0));
            lblGeneration.repaint();
        }
    }
}
