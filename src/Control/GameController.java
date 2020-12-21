package Control;

import Model.AirplaneStack;
import Model.Color;
import Model.Map;
import View.GameView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.concurrent.ThreadLocalRandom;

public class GameController {
    private Map map;
    private Color currentTurn;
    private int dieNumber1, dieNumber2;
    private int count = 0;
    private int chosenNumber;

    public GameController() {
        this.map = new Map();
        this.currentTurn = Color.BLUE;
    }

    public Map getMap() {return this.map;}
    public Color getCurrentTurn() {return this.currentTurn;}
    public int getDieNumber1() {return this.dieNumber1;}
    public int getDieNumber2() {return this.dieNumber2;}

    public void rollBtnPressed() {
        if (count >=3){
            this.currentTurn = currentTurn.next();
            count = 0;
        }
        else {
            this.dieNumber1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            this.dieNumber2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            System.out.println("(" + dieNumber1 + ", " + dieNumber2 + ")");
            int sum = dieNumber1 + dieNumber2;

            if (sum >= 10){
                count += 1;
            }

            if (sum < 10){
                count += 3;
            }
        }

        // *******

        return;
    }

    public void addBtnPressed() {
        int addNumber = Math.min(dieNumber1 + dieNumber2,12);
        System.out.println(addNumber);
        chosenNumber = addNumber;

        return;
    }
    public void minusBtnPressed() {
        int minusNumber = Math.max(dieNumber1, dieNumber2) - Math.min(dieNumber1, dieNumber2);
        System.out.println(minusNumber);
        chosenNumber = minusNumber;

        return;
    }
    public void timesBtnPressed() {
        int timesNumber = Math.min(dieNumber1 * dieNumber2,12);
        System.out.println(timesNumber);
        chosenNumber = timesNumber;

        return;
    }

    public void divideBtnPressed() {
        int divideNumber = Math.max(dieNumber1, dieNumber2) / Math.min(dieNumber1, dieNumber2);
        System.out.println(divideNumber);
        chosenNumber = divideNumber;

        return;
    }

    public boolean departureAttempt(AirplaneStack toLiftOff) {
        //legal attempt checking
        //...
        //return false if illegal;
        if (toLiftOff.isDepartured() || toLiftOff.getColor() != getCurrentTurn()) return false;
        if (dieNumber1 != 6 && dieNumber2 != 6) return false;
        //Lift off
        toLiftOff.setDepartured();
        return true;
    }

    public void moveAttempt(AirplaneStack toMove){
        if (toMove.getColor() == getCurrentTurn()){
            AirplaneStack x = map.getAirplaneStackAt(toMove.getPoint().getPosition()+chosenNumber);
            int a = x.getStackNum();
            map.getAirplaneStacks().remove(x);
            map.getAirplaneStacks().remove(toMove);
            AirplaneStack b = new AirplaneStack(currentTurn);
            b.setDepartured();
            b.setStackNum(a + toMove.getStackNum());
            b.update(toMove.getPassLength()+chosenNumber);
            map.addAirplaneStacks(b);
        }
        return;
    }

}
