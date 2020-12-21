package Model;

public class Point {
    private final Color color;
    private int position;

    public Point(Color color, int position){
        this.color = color;
        this.position = position;
    }

    public void setPosition(int position){this.position = position;}

    public Color getColor(){ return color;}

    public int getPosition(){return position;}

}
