package View;

import Control.GameController;
import Model.Map;
import Model.Point;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.abs;

public class GameView {
    private double height, width;
    private static double pointRadius = 20.0;
    private GameController gameController;
    private Scene gameView;

    public Scene getGameView() {return this.gameView;}

    public static String colorCSS(Model.Color color) {
        if (color.equals(Model.Color.RED)) {
            return "#FFB6C1";
        } else if (color.equals(Model.Color.BLUE)) {
            return "#00FFFF";
        } else if (color.equals(Model.Color.GREEN)) {
            return "#7FFF00";
        } else if (color.equals(Model.Color.YELLOW)) {
            return "#FFFACD";
        } else {
            return "#ADD8E6";
        }
    }

    private Pane initialMap() {
        Pane map = new Pane();

        List<Circle> pointList = new ArrayList<>();
        pointList.addAll(this.pointEdge(4, 14, 4, 11, 0));
        pointList.addAll(this.pointEdge(3, 10, 0, 10, 4));
        pointList.addAll(this.pointEdge(0, 9, 0, 4, 8));
        pointList.addAll(this.pointEdge(0, 4, 3, 4, 13));
        pointList.addAll(this.pointEdge(4, 3, 4, 0, 17));
        // ***********************************
        //to be finished.

        map.getChildren().addAll(pointList);

        return map;
    }

    private VBox stateColumn() {
        VBox column = new VBox();

        Button restartBtn = new Button("restart");
        Button saveBtn = new Button("save");
        column.getChildren().add(restartBtn);
        column.getChildren().add(saveBtn);

        HBox labels = new HBox();
        Label currentColorLabel = new Label("Current Player: ");
        currentColorLabel.setFont(new Font(24));
        Label currentColor = new Label(this.gameController.getCurrentTurn().toString());
        currentColor.setTextFill(Color.valueOf(colorCSS(this.gameController.getCurrentTurn())));
        currentColor.setFont(new Font(24));
        labels.getChildren().addAll(currentColorLabel, currentColor);

        column.getChildren().add(labels);

        return column;
    }

    private VBox operationColumn() {
        VBox operations = new VBox();

        Button rollBtn = new Button("Roll!");
        rollBtn.setId("rollBtn");
        rollBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                operations.getChildren().remove(rollBtn);
                // ********************************
                // first roll and then
                // add the four (three) buttons here

                gameController.rollBtnPressed();
            }
        });

        Button addBtn = new Button("+");
        Button minusBtn = new Button("-");
        Button timesBtn = new Button("*");
        Button divideBtn = new Button("/");

        // ***************************
        //event handlers for buttons here

        operations.getChildren().add(rollBtn);

        return operations;
    }

    public GameView(double width, double height) {
        this.height = height;
        this.width = width;
        this.gameController = new GameController();

        HBox root = new HBox();
        root.setId("root");

        VBox column = stateColumn();
        Pane map = initialMap();
        VBox operations = operationColumn();

        root.getChildren().addAll(column, map, operations);

        this.gameView = new Scene(root, width, height);
    }

    public List<Circle> pointEdge(int startX, int startY, int endX, int endY, int index) {
        //X, Y from 0 to 14
        if (startX != endX && startY != endY) return null;

        boolean vertical = false;
        if (startX == endX) vertical = true;

        List<Circle> edge = new ArrayList<>();
        int start, end;
        if (vertical) {
            start = startY;
            end = endY;
        } else {
            start = startX;
            end = endX;
        }
        if (start < end) end += 1;
        else end -= 1;
//        System.out.println(start + " " + end);
        while (start != end) {
            Circle point = new Circle();
            point.setRadius(pointRadius);
            double x, y;
            if (vertical) {
                x = pointRadius * (startX * 2 + 1);
                y = pointRadius * (start * 2 + 1);
            } else {
                x = pointRadius * (start * 2 + 1);
                y = pointRadius * (startY * 2 + 1);
            }
            point.setCenterX(x); point.setCenterY(y);
            Point p = this.gameController.getMap().getPointByIndex(index);

            point.setFill(Color.valueOf(colorCSS(p.getColor())));
            point.setUserData(p);
            edge.add(point);

//            System.out.println(start + " " + end + " " + x + ", " + y);

            if (start > end) start -= 1;
            else if (start < end) start += 1;

            index += 1;
        }

        return edge;
    }
}
