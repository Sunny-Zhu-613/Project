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

    public GameController() {
        this.map = new Map();
        this.currentTurn = Color.BLUE;
    }

    public Map getMap() {return this.map;}
    public Color getCurrentTurn() {return this.currentTurn;}
    public int getDieNumber1() {return this.dieNumber1;}
    public int getDieNumber2() {return this.dieNumber2;}

    public void rollBtnPressed() {
        this.dieNumber1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        this.dieNumber2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        System.out.println("(" + dieNumber1 + ", " + dieNumber2 + ")");

        // *******

        return;
    }

    public void addBtnPressed() {
        return;
    }

    public void minusBtnPressed() {
        return;
    }

    public void timesBtnPressed() {
    }

    public void divideBtnPressed() {
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
}
