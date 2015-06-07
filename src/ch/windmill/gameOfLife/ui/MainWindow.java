package ch.windmill.gameOfLife.ui;

import ch.windmill.gameOfLife.RuleSet;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class MainWindow implements MouseListener{
    private final int xAxis, yAxis;
    private final JComboBox comRules;
    private final World world;
    private int generationCounter;
    private Thread evolveThread;
    private ProcessState state;
    private JButton btnClear;
    private JLabel lblState, lblGeneration;
    private JFrame window;
    
    /**
     * Main method to start the programm.
     * @param args Console line arguments.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args) {
        //new MainWindow();
        new MainWindow2();
    }
    
    /**
     * 
     */
    public MainWindow() {
        xAxis = 10;
        yAxis = 10;
        generationCounter = 0;
        comRules = new JComboBox(RuleSet.values());
        world = new World(xAxis, xAxis);
        state = ProcessState.STOPPED;
        
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
        JButton btnStart = new JButton("Start");
        JButton btnStop = new JButton("Stop");
        JButton btnRandom = new JButton("Random");
        btnClear = new JButton("Clear");
        lblState = new JLabel();
        lblGeneration = new JLabel();
        window = new JFrame("Game of life");
        
        separator.setPreferredSize(new Dimension(140, 2));
        separator.setBorder(BorderFactory.createLineBorder(Color.black));
        
        // buttons
        btnStart.setPreferredSize(new Dimension(80,18));
        btnStop.setPreferredSize(new Dimension(80,18));
        btnRandom.setPreferredSize(new Dimension(80,18));
        btnClear.setPreferredSize(new Dimension(80,18));
        
        addStartAction(btnStart);
        addStopAction(btnStop);
        addRandomPatternAction(btnRandom);
        addClearAction(btnClear);
        
        // labels
        lblState.setText("Current state: "+state.getText());
        lblGeneration.setText("Current generation: "+generationCounter);
        
        // panels
        panelFields.setPreferredSize(new Dimension(400, 400));
        panelControl.setPreferredSize(new Dimension(150, 100));
        panelControl.setBorder(BorderFactory.createLineBorder(Color.black));
        panelControl.add(lblState);
        panelControl.add(lblGeneration);
        panelControl.add(btnStart);
        panelControl.add(btnStop);
        panelControl.add(separator);
        panelControl.add(comRules);
        panelControl.add(btnRandom);
        panelControl.add(btnClear);
        
        // frame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.add(panelFields, BorderLayout.CENTER);
        window.add(panelControl, BorderLayout.EAST);
        
        // add all fields to the frame
        for(int i = (world.getYAxis()-1); i >= 0; i--) {
            for(int j = 0; j < world.getXAxis(); j++) {
                /**world.getCell(i, j).getPanel().addMouseListener(this);      // add listener
                panelFields.add(world.getCell(i, j).getPanel());            // add panel to the frame
            */}
        }
        
        window.pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /**for(int i = 0; i < world.getXAxis(); i++) {
            for(int j = 0; j < world.getYAxis(); j++) {
                if(e.getSource().equals(world.getCell(i, j).getPanel())) {
                    world.getCell(i, j).setAlive(true);
                }
            }
        }*/
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
    
    
    private void addStartAction(final JButton b) {
        b.addActionListener((ActionEvent e) -> {
            if(evolveThread == null) {
                evolveThread = getEvolveThread();
            }
            world.setEngineRules((RuleSet) comRules.getSelectedItem());
            evolveThread.start();
            changeProcessState(true);
        });
    }
    
    private void addStopAction(final JButton b) {
        b.addActionListener((ActionEvent e) -> {
            evolveThread = null;
            changeProcessState(false);
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
            window.repaint();
        });
    }
    
    private void addClearAction(final JButton b) {
        b.addActionListener((ActionEvent e) -> {
            if(state.equals(ProcessState.STOPPED)) {
                for(int i = 0; i < xAxis; i++) {
                    for(int j = 0; j < yAxis; j++) {
                        world.getCell(i, j).setAlive(false);
                    }
                }
                generationCounter = 0;
                window.repaint();
            }
        });
    }
    
    private Thread getEvolveThread() {
        return evolveThread = new Thread(() -> {
            while(evolveThread == Thread.currentThread()) {
                try {
                    Thread.sleep(100);
                    world.startEngine();
                    generationCounter++;
                    lblGeneration.setText("Generation: "+generationCounter);
                    window.repaint();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void changeProcessState(final boolean running) {
        if(!running) {
            btnClear.setEnabled(true);
            lblState.setText("Current state: "+ProcessState.STOPPED.getText());
            state = ProcessState.STOPPED;
        } else {
            btnClear.setEnabled(false);
            lblState.setText("Current state: "+ProcessState.RUNNING.getText());
            state = ProcessState.RUNNING;
        }
    }
}
