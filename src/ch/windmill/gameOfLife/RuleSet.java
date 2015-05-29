package ch.windmill.gameOfLife;

/**
 * This enum provides sets of rules for a <code>gameOfLife.LifeEngine</code>. Every ruleset define rules 
 * for the birth and remain of cell objects. A rule is a list with possible integer values of living neighbour
 * cells.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public enum RuleSet {
    CONWAY(new int[]{3}, new int[]{2,3});
    
    private final int[] birth;
    private final int[] remain;
    
    /**
     * Creates a new ruleset enum. It is defined by birth and remain rules.
     * @param birth Number of living cells to birth a cell.
     * @param remain Number of living cells to remain a cell.
     */
    RuleSet(final int[] birth, final int[] remain) {
        this.birth = birth;
        this.remain = remain;
    }
    
    /**
     * 
     * @return The rules to birth a cell.
     */
    public int[] getBirth() {
        return birth;
    }
    
    /**
     * 
     * @return The rules to remain a cell.
     */
    public int[] getRemain() {
        return remain;
    }
}
