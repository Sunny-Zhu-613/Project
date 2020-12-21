package Model;

public enum Color {
    YELLOW, BLUE, GREEN, RED, BLANK;
    // BLANK for FinalPath
    public Color next()
    {
        return values()[(this.ordinal()+1) % 4];
    }
}


