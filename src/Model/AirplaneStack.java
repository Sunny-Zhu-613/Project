package Model;

import Control.GameController;

public class AirplaneStack {
    private final Color color;
    private boolean isDepartured;
    private boolean isFinished;
    private boolean isEnteredFinalPath;
    private int passLength;
    private int stackNum;
    private Point currentPoint;

    public AirplaneStack(Color color){
        this.color = color;
        isDepartured = false;
        isFinished = false;
        isEnteredFinalPath = false;
        stackNum = 1;
        passLength = 0;
        currentPoint = null;
    }
    public void setStackNum(int stackNum){
        this.stackNum = stackNum;
    }
    public void setDepartured(){
        this.isDepartured = true;
    }
    public void setFinished(){
        this.isFinished = true;
    }
    public void setEnteredFinalPath(){
        if (this.passLength > 50){
            this.isEnteredFinalPath = true;
        }
    }
    public void setCurrentPoint(Point point) {
        this.currentPoint = point;
    }
    public boolean isDepartured() {
        return isDepartured;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isEnteredFinalPath() {
        return isEnteredFinalPath;
    }


//    public void update(int passLength){
//        if (!isDepartured && passLength > 0){
//
//            setEnteredFinalPath();
//            if (passLength == 56){
//                setFinished();
//                this.passLength = passLength;
//            }
//            if (passLength > 56){
//                this.passLength = 56 - (passLength - 56);
//                currentPoint = new Point(Color.BLANK, passLength - 51);
//            }
//            if (isEnteredFinalPath){
//                this.passLength = passLength;
//                currentPoint = new Point(Color.BLANK, passLength - 51);
//            }
//            else {
//                this.passLength = passLength;
//                if (color == Color.GREEN){
//                    currentPoint = new Point(Map.getColorByIndex(passLength), passLength - 1);
//                }
//                if (color == Color.RED){
//                    currentPoint = new Point(Map.getColorByIndex(passLength + 13), passLength + 13 - 1);
//                }
//                if (color == Color.YELLOW){
//                    currentPoint = new Point(Map.getColorByIndex(passLength + 26), passLength + 26 - 1);
//                }
//                if (color == Color.BLUE){
//                    currentPoint = new Point(Map.getColorByIndex(passLength + 39), passLength + 39 - 1);
//                }
//            }
//        }
//    }

//1    public void moveBy(int steps){
//        if (!isDepartured && steps > 0){
//            this.passLength = passLength + steps;
//            stepsupdate(this.passLength);
//        }
//    }
    public void addPathLength(int steps){
        this.passLength = passLength + steps;
    }


    public Point getPoint() {return currentPoint;}
    public Color getColor() {return color;}
    public int getStackNum() {return this.stackNum;}
    public int getPassLength() {return this.passLength;}
}
