package Model;

public class Player implements java.io.Serializable {
    private int playerNum;
    private Color color;
    public Player(int playerNum, Color color){
        this.color = color;
        this.playerNum = playerNum;
    }
}
