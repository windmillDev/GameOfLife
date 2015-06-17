package ch.windmill.gameOfLife;

/**
 * This enum provides sets of rules for a <code>gameOfLife.LifeEngine</code>. Every ruleset define rules 
 * for the birth and remain of cell objects. A rule is a list with possible integer values of living neighbour
 * cells.
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public enum RuleSet {
    CONWAY(new int[]{3}, new int[]{2,3}),
    R13_3(new int[]{3}, new int[]{1,3}),
    R34_3(new int[]{3}, new int[]{3,4}),
    R236_3(new int[]{3}, new int[]{2,3,6}),
    R12345_3(new int[]{3}, new int[]{1,2,3,4,5}),
    R1357_1357(new int[]{1,3,5,7}, new int[]{1,3,5,7}),
    R0123_01234(new int[]{0,1,2,3}, new int[]{0,1,2,3,4}),
    R02468_02468(new int[]{0,2,4,6,8}, new int[]{0,2,4,6,8});
    
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
