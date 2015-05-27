package ch.windmill.gameOfLife;

/**
 *
 * @author Cyrill Jauner
 * @version 1.0.0
 */
public enum RuleSet {
    CONWAY(new int[]{0}, new int[]{0});
    
    private int[] birth;
    private int[] remain;
    
    /**
     * 
     * @param birth
     * @param remain 
     */
    RuleSet(final int[] birth, final int[] remain) {
        this.birth = birth;
        this.remain = remain;
    }

    public int[] getBirth() {
        return birth;
    }

    public int[] getRemain() {
        return remain;
    }
}
