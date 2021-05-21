package Model;

/**
 * This enum contains every level's name.
 */
public enum Levels {
    Limbo,
    Lust,
    Gluttony,
    Greed,
    Anger,
    Heresy,
    Violence,
    Fraud;

    private static final Levels[] vals = values();

    /**
     * Returns the next level of the enum. Every object inside the enum is saved in an array and this method reads
     * the next position of the current level.
     * @return the next level after the one which called this method
     */
    public Levels next(){
        return vals[(this.ordinal()+1) % vals.length];
    }
}
