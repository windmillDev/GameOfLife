package ch.windmill.gameOfLife;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public class Cell {
    private State state;
    private boolean isAlive;
    
    /**
     * 
     */
    public Cell() {
        state = State.DEAD;
        isAlive = false;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void setAlive() {
        isAlive = true;
    }
    
    /**
     * 
     * @return 
     */
    public State getState() {
        return state;
    }
    
    /**
     * 
     * @param state 
     */
    public void setState(State state) {
        this.state = state;
    }
}
