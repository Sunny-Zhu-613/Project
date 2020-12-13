package model;

public class AirplaneStack {
    private final Color color;
    private boolean isDeparted;
    private boolean isFinished;
    private boolean isEnteredFinalPath;
    private int passLength;
    private int stackNum;
    private Point currentPoint;

    public AirplaneStack(Color color){
        this.color = color;
        isDeparted = false;
        isFinished = false;
        isEnteredFinalPath = false;
        stackNum = 1;
        passLength = 0;
        currentPoint = null;
    }
    public void setStackNum(int stackNum){
        this.stackNum = stackNum;
    }
    public void setDeparted(){
        this.isDeparted = true;
    }
    public void setFinished(){
        this.isFinished = true;
    }
    public void setEnteredFinalPath(){
        if (this.passLength > 50){
            this.isEnteredFinalPath = true;
        }
    }
    private Color setColor(int a){
        int r = a / 4;
        if (r == 0){return Color.YELLOW;}
        if (r == 1){return Color.BLUE;}
        if (r == 2){return Color.GREEN;}
        if (r == 3){return Color.RED;}
        return null;
    }
    public void update(int passLength){
        if (!isDeparted){
            this.passLength = passLength;
            setEnteredFinalPath();
            if (isEnteredFinalPath){currentPoint = new Point(Color.BLANK, passLength - 51);}
            else {
                if (color == Color.GREEN){
                    currentPoint = new Point(setColor(passLength), passLength - 1);
                }
                if (color == Color.RED){
                    currentPoint = new Point(setColor(passLength + 13), passLength + 13 - 1);
                }
                if (color == Color.YELLOW){
                    currentPoint = new Point(setColor(passLength + 26), passLength + 26 - 1);
                }
                if (color == Color.BLUE){
                    currentPoint = new Point(setColor(passLength + 39), passLength + 39 - 1);
                }
            }
        }
    }
    public void moveBy(int steps){
        if (!isDeparted){
            this.passLength = passLength + steps;
            update(this.passLength);
        }
    }
}
