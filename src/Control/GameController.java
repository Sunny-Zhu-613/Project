package Control;

import Model.Color;
import Model.Map;
import View.GameView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class GameController {
    private Map map;
    private Color currentTurn;

    public GameController() {
        this.map = new Map();
        this.currentTurn = Color.BLUE;
    }

    public Map getMap() {return this.map;}
    public Color getCurrentTurn() {return this.currentTurn;}

    public void rollBtnPressed() {
        return;
    }
}
