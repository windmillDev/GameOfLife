package ch.windmill.gameOfLife.ui;

import ch.windmill.gameOfLife.RuleSet;
import ch.windmill.gameOfLife.World;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class provides the main window of the ui. It has a main method that will invoke the constructor of this
 * class. 
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class MainWindow {
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private final static int CONTROLPWIDTH = 200;
    private final static int BTNWIDTH = 150;
    private final static int BTNHEIGHT = 60;
    private final static int CELLSIZE = 2;
    
    private final JFrame frame;
    private final World world;
    private int delay;
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
        delay = 100;
        evolveThread = getEvolveThread();
        
        initUI();
        frame.setVisible(true);
    }
    
    /**
     * Set the delay time.
     * @param delay Time in milliseconds.
     */
    public void setDelay(final int delay) {
        this.delay = delay;
    }
    
    /**
     * Get the current delay time.
     * @return The delay time.
     */
    public int getDelay() {
        return delay;
    }
    
    /**
     * Resize the world with a new cell size. This methods invokes the resize method of the world object.
     * @param cellSize The new size of cells in pixels.
     */
    public void resizeWorld(final int cellSize) {
        world.resize(HEIGHT, WIDTH, cellSize);
        canvas.repaint();
    }
    
    /**
     * Initialize all ui components.
     */
    private void initUI() {
        canvas = new Canvas();
        controlPanel = new ControlPanel();
        
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        controlPanel.setNumCellText(world.getNumberOfCells());
        controlPanel.setPreferredSize(new Dimension(CONTROLPWIDTH, HEIGHT));
        
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
                    Thread.sleep(delay);
                    world.startEngine();
                    controlPanel.incCounter();
                    controlPanel.drawAliveCellText(world.countAliveCells());
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
        private JPanel pInfo;
        private JLabel lblProcessState, lblGeneration, lblAliveCells, lblNumCells, lblPercentage, lblCellSize,
                lblDelay;
        private JButton btnStart, btnStop, btnKill, btnRandom;
        private JComboBox boxRules;
        private JSpinner spPercentageAlive;
        private JSlider slCellSize, slDelay;
        private int generationCounter;
        
        /**
         * Create a new control panel.
         */
        public ControlPanel() {
            generationCounter = 0;
            initUI();
        }
        
        /**
         * Increment the generation counter. Update the text of the label and repaint it.
         */
        public void incCounter() {
            generationCounter++;
            drawGenerationText();
        }
        
        /**
         * Set the text of the num cell label.
         * @param n Number of cells.
         */
        public void setNumCellText(final int n) {
            lblNumCells.setText("Number of Cells: "+n);
        }
        
        /**
         * Draw the text of the alive cell label. Invoke the repaint method to redraw it.
         * @param n Number of alive cells.
         */
        public void drawAliveCellText(final int n) {
            lblAliveCells.setText("Population: "+n);
            lblAliveCells.repaint();
        }
        
        /**
         * Initialize all ui components.
         */
        private void initUI() {
            pInfo = new JPanel();
            lblProcessState = new JLabel();
            lblAliveCells = new JLabel();
            lblNumCells = new JLabel();
            lblGeneration = new JLabel();
            lblDelay = new JLabel();
            lblPercentage = new JLabel(" live quote: ");
            lblCellSize  = new JLabel("Cell size (pixels): ");
            btnStart = new JButton("Start");
            btnStop = new JButton("Stop");
            btnKill = new JButton("Kill");
            btnRandom = new JButton("Random generation");
            boxRules = new JComboBox(RuleSet.values());
            spPercentageAlive = new JSpinner();
            slCellSize = new JSlider(JSlider.HORIZONTAL);
            slDelay = new JSlider(JSlider.HORIZONTAL);
            
            // set the text of the labels
            drawProcessStateText(ProcessState.STOPPED);
            drawGenerationText();
            drawAliveCellText(0);
            
            // buttons
            btnStart.setPreferredSize(new Dimension(BTNWIDTH/2-2, BTNHEIGHT));
            btnStop.setPreferredSize(new Dimension(BTNWIDTH/2-2, BTNHEIGHT));
            btnRandom.setPreferredSize(new Dimension(BTNWIDTH, BTNHEIGHT));
            btnKill.setPreferredSize(new Dimension(BTNWIDTH, BTNHEIGHT));
            addStartAction(btnStart, btnKill);
            addStopAction(btnStop, btnKill, btnStart);
            addKillAction(btnKill);
            addRandomAction(btnRandom);
            
            // spinner
            SpinnerModel model = new SpinnerNumberModel(0.2, 0.1, 1.0, 0.1);
            spPercentageAlive.setModel(model);
            
            // slider
            slCellSize.setMinimum(2);
            slCellSize.setMaximum(10);
            slCellSize.setValue(2);
            slCellSize.setMajorTickSpacing(1);
            slCellSize.setPaintLabels(true);
            slCellSize.setPaintTicks(true);
            slCellSize.setSnapToTicks(true);
            slCellSize.addChangeListener(new SliderListener());
            
            slDelay.setMinimum(10);
            slDelay.setMaximum(1000);
            slDelay.setValue(100);
            slDelay.setMajorTickSpacing(10);
            slDelay.setSnapToTicks(true);
            slDelay.addChangeListener(new SliderDelayListener());
            drawDelayText(slDelay.getValue());
            
            // combobox
            boxRules.setPreferredSize(new Dimension(BTNWIDTH, BTNHEIGHT/2));
            
            // configure panels
            pInfo.setLayout(new GridLayout(0, 1));
            pInfo.setBorder(BorderFactory.createTitledBorder("Info"));
            pInfo.add(lblProcessState);
            pInfo.add(lblNumCells);
            pInfo.add(lblAliveCells);
            pInfo.add(lblGeneration);
            
            // configure the main panel
            setLayout(new FlowLayout(FlowLayout.CENTER));
            setBorder(BorderFactory.createLineBorder(Color.black));
            add(pInfo);
            add(lblDelay);
            add(slDelay);
            add(lblCellSize);
            add(slCellSize);
            add(boxRules);
            add(btnStart);
            add(btnStop);
            add(btnKill);
            add(lblPercentage);
            add(spPercentageAlive);
            add(btnRandom);

        }
        
        /**
         * Set the text of the generation label. Invoke the repaint method of the jlabel.
         */
        private void drawGenerationText() {
            lblGeneration.setText("Generation: "+generationCounter);
            lblGeneration.repaint();
        }
        
        /**
         * Set the text of the process state label. Invoke the repaint method of the jlabel.
         * @param state The current state of the evolve thread.
         */
        private void drawProcessStateText(final ProcessState state) {
            lblProcessState.setText("Engine state: "+state.getText());
            lblProcessState.repaint();
        }
        
        /**
         * Set the text of the process state label. Invoke the repaint method of the jlabel.
         * @param millis The delay in milliseconds.
         */
        private void drawDelayText(final int millis) {
            lblDelay.setText("Delay: "+millis+" millis");
            lblDelay.repaint();
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
                drawProcessStateText(ProcessState.RUNNING);
                bDisable.setEnabled(false);
                b.setEnabled(false);
            });
        }
        
        /**
         * Add an actionlistener to stop the evolve thread.
         * @param b The button to add the actionlistener.
         * @param bEnable The button to enable when the button b is clicked.
         * @param bEnable2 The button to enable when the button b is clicked.
         */
        private void addStopAction(final JButton b, final JButton bEnable, final JButton bEnable2) {
            b.addActionListener((ActionEvent e) -> {
                evolveThread = null;
                drawProcessStateText(ProcessState.STOPPED);
                bEnable.setEnabled(true);
                bEnable2.setEnabled(true);
            });
        }
        
        /**
         * Add an actionlistener to kill the current generation.
         * @param b The button to add the actionlistener.
         */
        private void addKillAction(final JButton b) {
            b.addActionListener((ActionEvent e) -> {
                world.killGeneration();
                generationCounter = 0;
                drawGenerationText();
                drawAliveCellText(0);
                canvas.repaint();
            });
        }
        
        /**
         * Add an actionlistener to generate a random generation. The random generation will override the existing 
         * generation. The generation counter will be reseted.
         * @param b The button to add the actionlistener.
         */
        private void addRandomAction(final JButton b) {
            b.addActionListener((ActionEvent e) -> {
                world.killGeneration();
                world.randomGeneration(Double.parseDouble(spPercentageAlive.getValue().toString()));
                drawAliveCellText(world.countAliveCells());
                generationCounter = 0;
                drawGenerationText();
                canvas.repaint();
            });
        }
    }
    
    private class SliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider s = (JSlider) e.getSource();
            if(!s.getValueIsAdjusting()) {          // user doesnt move the cursor
                resizeWorld(s.getValue());
                controlPanel.setNumCellText(world.getNumberOfCells());
            }
        }
        
    }
    
    private class SliderDelayListener implements ChangeListener {
        
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider s = (JSlider) e.getSource();
            if(!s.getValueIsAdjusting()) {          // user doesnt move the cursor
                setDelay(s.getValue());
                controlPanel.drawDelayText(s.getValue());
            }
        }
    }
}
