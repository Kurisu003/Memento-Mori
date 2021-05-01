package Model;

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
    public Levels next(){
        return vals[(this.ordinal()+1) % vals.length];
    }
}
