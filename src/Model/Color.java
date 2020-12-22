package Model;

public enum Color implements java.io.Serializable {
    YELLOW, BLUE, GREEN, RED, BLANK;
    // BLANK for FinalPath
    public Color next()
    {
        return values()[(this.ordinal()+1) % 4];

    }
    public static Color shortcut(Color color){
        if (color == GREEN){
            return YELLOW;
        }
        if (color == RED){
            return BLUE;
        }
        if (color == YELLOW){
            return GREEN;
        }
        if (color == BLUE){
            return RED;
        }
        return BLANK;
    }
}


