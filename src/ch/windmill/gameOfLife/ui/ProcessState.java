package ch.windmill.gameOfLife.ui;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public enum ProcessState {
    RUNNING("Running"), STOPPED("Stopped");
    
    private String text;
    
    ProcessState(final String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }
}
