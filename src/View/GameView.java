package View;

import Control.GameController;
import Model.AirplaneStack;
import Model.Point;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameView implements java.io.Serializable {
    private double height, width;
    private static final double pointRadius = 20.0;
    private static final double planeRadius = 0.5 * pointRadius;
    private GameController gameController;
    private Scene gameView;
    private HBox root;
    private Pane map;
    private VBox operations;
    private VBox stateColumn;
    private Button rollBtn;
    private List<Circle> pointList;
    private int stage; //0 for before roll, 1 for before choosing operation, 2 for before choosing chess

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
    public static String colorPlaneCSS(Model.Color color) {
        if (color.equals(Model.Color.RED)) {
            return "#B22222";
        } else if (color.equals(Model.Color.BLUE)) {
            return "#1E90FF";
        } else if (color.equals(Model.Color.GREEN)) {
            return "#008000";
        } else if (color.equals(Model.Color.YELLOW)) {
            return "#FFD700";
        } else return "#000000";
    }

    private void addStackAt(int index, AirplaneStack stack) {
        StackPane airPlane = new StackPane();

        for (int j = 0; j < stack.getStackNum(); j++) {
//            double x = hangerCenterX(index) + 2 * pointRadius, y = hangerCenterY(index) + 2 * pointRadius;

            double x = pointList.get(index).getCenterX(), y = pointList.get(index).getCenterY();
            System.out.println("New stack of index " +index + " at (" + x + ", " +y +")");

            Circle planeCircle = new Circle();
            planeCircle.setFill(Color.valueOf(colorPlaneCSS(stack.getColor())));
            planeCircle.setRadius(planeRadius);

            airPlane.getChildren().add(planeCircle);

            if (j == 0) {
                x -= 2 * planeRadius;
                y -= 2 * planeRadius;
            } else if (j == 1) {
                x -= 2 * planeRadius;
            } else if (j == 2) {
                y -= 2 * planeRadius;
            }
            planeCircle.setCenterX(x);
            planeCircle.setCenterY(y);
            airPlane.setLayoutX(x);
            airPlane.setLayoutY(y);

            map.getChildren().remove(airPlane);
            map.getChildren().add(airPlane);
            planeCircle.setOnMouseClicked(mouseEvent -> {
                if (stage != 2) return;

                if (gameController.moveAttempt(stack)) {
                    TranslateTransition move = new TranslateTransition(Duration.seconds(0.5), airPlane);
                    double deltaX = pointList.get(stack.getPoint().getPosition()).getCenterX() - planeCircle.getCenterX();
                    double deltaY = pointList.get(stack.getPoint().getPosition()).getCenterY() - planeCircle.getCenterY();

                    deltaX -= planeRadius;
                    deltaY -= planeRadius;

                    move.setByX(deltaX);
                    move.setByY(deltaY);

                    move.play();
                    move.setOnFinished(event -> {
                        System.out.println(map.getChildren().remove(airPlane));
                        addStackAt(stack.getPoint().getPosition(), stack);
                    });
//                    map.getChildren().remove(airPlane);
                    System.out.println("Target: " + stack.getPoint().getPosition());
//                    System.out.println(map.getChildren().remove(airPlane));

                    gameController.onTurnFinished();
                    stateColumnUpdate();
                    operations.getChildren().removeAll(operations.getChildren());
                    operations.getChildren().add(rollBtn);
                    stage = 0;
                    return;
                }
            });

        }
    }
    private void addStackAt(double x, double y, AirplaneStack stack) {
        StackPane airPlane = new StackPane();

        for (int j = 0; j < stack.getStackNum(); j++) {
//            double x = hangerCenterX(index) + 2 * pointRadius, y = hangerCenterY(index) + 2 * pointRadius;

            System.out.println("New stack of index at (" + x + ", " +y +")");

            Circle planeCircle = new Circle();
            planeCircle.setFill(Color.valueOf(colorPlaneCSS(stack.getColor())));
            planeCircle.setRadius(planeRadius);

            airPlane.getChildren().add(planeCircle);

            if (j == 0) {
                x -= 2 * planeRadius;
                y -= 2 * planeRadius;
            } else if (j == 1) {
                x -= 2 * planeRadius;
            } else if (j == 2) {
                y -= 2 * planeRadius;
            }
            planeCircle.setCenterX(x);
            planeCircle.setCenterY(y);
            airPlane.setLayoutX(x);
            airPlane.setLayoutY(y);

            map.getChildren().remove(airPlane);
            map.getChildren().add(airPlane);
            planeCircle.setOnMouseClicked(mouseEvent -> {
                if (stage != 2) return;

                if (gameController.moveAttempt(stack)) {
                    TranslateTransition move = new TranslateTransition(Duration.seconds(0.5), airPlane);
                    double deltaX = pointList.get(stack.getPoint().getPosition()).getCenterX() - planeCircle.getCenterX();
                    double deltaY = pointList.get(stack.getPoint().getPosition()).getCenterY() - planeCircle.getCenterY();

                    deltaX -= planeRadius;
                    deltaY -= planeRadius;

                    move.setByX(deltaX);
                    move.setByY(deltaY);

                    move.play();
                    move.setOnFinished(event -> {
                        System.out.println(map.getChildren().remove(airPlane));
                        addStackAt(stack.getPoint().getPosition(), stack);
                    });
//                    map.getChildren().remove(airPlane);
                    System.out.println("Target: " + stack.getPoint().getPosition());
//                    System.out.println(map.getChildren().remove(airPlane));

                    gameController.onTurnFinished();
                    stateColumnUpdate();
                    operations.getChildren().removeAll(operations.getChildren());
                    operations.getChildren().add(rollBtn);
                    stage = 0;
                    return;
                }
            });

        }
    }

    private List<StackPane> hangers_planes() {
        List<StackPane> allPlanes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Model.Color color = Model.Color.values()[(i + 1) % 4];
            List<AirplaneStack> undepartured = gameController.getMap()
                    .getAirplaneStacksByColor(color)
                    .stream()
                    .filter(air -> !(air.isDepartured()))
                    .collect(Collectors.toList());
            int j = 0;
            for (AirplaneStack air : undepartured) {
                StackPane airPlane = new StackPane();
                airPlane.setUserData(air);

                Circle planeCircle = new Circle();
                planeCircle.setFill(Color.valueOf(colorPlaneCSS(color)));
                planeCircle.setRadius(planeRadius);
                planeCircle.setOnMouseClicked(mouseEvent -> {
                    if (stage != 2) return;
                    if (air.getPassLength() != 0) return;
                    if (air.isDepartured()) {
                        if (gameController.moveAttempt(air)) {
                            System.out.println("Target: " + air.getPoint().getPosition());

                            TranslateTransition move = new TranslateTransition(Duration.seconds(0.5), airPlane);
                            double deltaX = pointList.get(air.getPoint().getPosition()).getCenterX() - waitingAreaCenterX(air.getColor());
                            double deltaY = pointList.get(air.getPoint().getPosition()).getCenterY() - waitingAreaCenterY(air.getColor());

                            deltaX -= planeRadius;
                            deltaY -= planeRadius;

                            move.setByX(deltaX);
                            move.setByY(deltaY);

                            move.play();
                            move.setOnFinished(event -> {
                                System.out.println(map.getChildren().remove(airPlane));
                                addStackAt(air.getPoint().getPosition(), air);
                            });
//                            System.out.println(map.getChildren().remove(airPlane));

                            gameController.onTurnFinished();
                            stateColumnUpdate();
                            operations.getChildren().removeAll(operations.getChildren());
                            operations.getChildren().add(rollBtn);
                            stage = 0;
                            return;
                        }

                        gameController.onTurnFinished();
                        stateColumnUpdate();
                        operations.getChildren().removeAll(operations.getChildren());
                        operations.getChildren().add(rollBtn);
                        stage = 0;
                        return;
                    }
                    if (gameController.departureAttempt(air)) {
                        TranslateTransition departure = new TranslateTransition(Duration.seconds(0.5), airPlane);
                        double deltaX = waitingAreaCenterX(air.getColor()) - planeCircle.getCenterX();
                        double deltaY = waitingAreaCenterY(air.getColor()) - planeCircle.getCenterY();

                        departure.setByX(deltaX);
                        departure.setByY(deltaY);

                        departure.play();

                        gameController.onTurnFinished();
                        stateColumnUpdate();
                        operations.getChildren().removeAll(operations.getChildren());
                        operations.getChildren().add(rollBtn);

                        stage = 0;
                    }
                });

                airPlane.getChildren().add(planeCircle);

                double x = hangerCenterX(i) + 2 * pointRadius, y = hangerCenterY(i) + 2 * pointRadius;
                if (j == 0) {
                    x -= 2 * planeRadius;
                    y -= 2 * planeRadius;
                } else if (j == 1) {
                    x -= 2 * planeRadius;
                } else if (j == 2) {
                    y -= 2 * planeRadius;
                }
                planeCircle.setCenterX(x);
                planeCircle.setCenterY(y);
                airPlane.setLayoutX(x);
                airPlane.setLayoutY(y);

                allPlanes.add(airPlane);
                j += 1;
            }
        }

        return allPlanes;
    }

    private static double waitingAreaCenterX(Model.Color color) {
        double x = 0;
        switch (color){
            case BLUE -> { x = 7 * pointRadius; }
            case RED -> { x = 23 * pointRadius; }
            case YELLOW -> { x = 29 * pointRadius; }
            case GREEN -> { x = pointRadius; }
        }
        return x - planeRadius;
    }

    private static double waitingAreaCenterY(Model.Color color) {
        double y = 0;
        switch (color){
            case BLUE -> y =  29 * pointRadius;
            case RED -> { y = pointRadius; }
            case YELLOW -> { y = 23 * pointRadius; }
            case GREEN -> { y = 7 * pointRadius; }
        }
        return y - planeRadius;
    }

    private static double hangerCenterX(int i) {
        double x;
        i %= 4;
        if (i == 0) {
            x = 2 * pointRadius;
        } else if (i == 1) {
            x = 2 * pointRadius;
        } else if (i == 2) {
            x = 12 * 2 * pointRadius;
        } else {
            x = 12 * 2 * pointRadius;
        }
        return x;
    }
    private static double hangerCenterY(int i) {
        double y;
        i %= 4;
        if (i == 0) {
            y = 12 * 2 * pointRadius;
        } else if (i == 1) {
            y = 2 * pointRadius;
        } else if (i == 2) {
            y = 2 * pointRadius;
        } else {
            y = 12 * 2 * pointRadius;
        }
        return y;
    }

    private void initialMap() {
        this.map = new Pane();

        pointList = new ArrayList<>();
        pointList.addAll(this.pointEdge(4, 14, 4, 11, 0));
        pointList.addAll(this.pointEdge(3, 10, 0, 10, 4));
        pointList.addAll(this.pointEdge(0, 9, 0, 5, 8));
        pointList.addAll(this.pointEdge(0, 4, 3, 4, 13));
        pointList.addAll(this.pointEdge(4, 3, 4, 0, 17));
        pointList.addAll(this.pointEdge(5, 0, 9, 0, 21));
        pointList.addAll(this.pointEdge(10, 0, 10, 3, 26));
        pointList.addAll(this.pointEdge(11, 4, 14, 4, 30));
        pointList.addAll(this.pointEdge(14, 5, 14, 9, 34));
        pointList.addAll(this.pointEdge(14, 10, 11, 10, 39));
        pointList.addAll(this.pointEdge(10, 11, 10, 14, 43));
        pointList.addAll(this.pointEdge(9, 14, 5, 14, 47));

        //Final Tracks
        pointList.addAll(this.pointEdge(1, 7, 6, 7, 52));
        pointList.addAll(this.pointEdge(7, 1, 7, 6, 58));
        pointList.addAll(this.pointEdge(13, 7, 8, 7, 64));
        pointList.addAll(this.pointEdge(7, 13, 7, 8, 70));

        //Hangars
        List<StackPane> hangers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            double x = hangerCenterX(i), y = hangerCenterY(i);

            StackPane hanger = new StackPane();
            hanger.setLayoutX(x);
            hanger.setLayoutY(y);
            Circle hangerCircle = new Circle();
            hangerCircle.setRadius(2 * pointRadius);
            hangerCircle.setFill(Color.valueOf(colorCSS(gameController.getMap().getPointByIndex(i + 1).getColor())));
            hanger.getChildren().add(hangerCircle);
            hanger.setId("hanger_"+gameController.getMap().getPointByIndex(i + 1).getColor().toString());

            hangers.add(hanger);
        }

        int i = 0;
        for (Circle point: pointList) {
            point.setId("Point_" + i);
            map.getChildren().addAll(point);
            i += 1;
        }

        map.getChildren().addAll(hangers);

        //********* add planes
        map.getChildren().addAll(hangers_planes());

    }
    private void initialMap(Model.Map loadMap) {
        this.map = new Pane();
        gameController.setMap(loadMap);

        pointList = new ArrayList<>();
        pointList.addAll(this.pointEdge(4, 14, 4, 11, 0));
        pointList.addAll(this.pointEdge(3, 10, 0, 10, 4));
        pointList.addAll(this.pointEdge(0, 9, 0, 5, 8));
        pointList.addAll(this.pointEdge(0, 4, 3, 4, 13));
        pointList.addAll(this.pointEdge(4, 3, 4, 0, 17));
        pointList.addAll(this.pointEdge(5, 0, 9, 0, 21));
        pointList.addAll(this.pointEdge(10, 0, 10, 3, 26));
        pointList.addAll(this.pointEdge(11, 4, 14, 4, 30));
        pointList.addAll(this.pointEdge(14, 5, 14, 9, 34));
        pointList.addAll(this.pointEdge(14, 10, 11, 10, 39));
        pointList.addAll(this.pointEdge(10, 11, 10, 14, 43));
        pointList.addAll(this.pointEdge(9, 14, 5, 14, 47));

        //Final Tracks
        pointList.addAll(this.pointEdge(1, 7, 6, 7, 52));
        pointList.addAll(this.pointEdge(7, 1, 7, 6, 58));
        pointList.addAll(this.pointEdge(13, 7, 8, 7, 64));
        pointList.addAll(this.pointEdge(7, 13, 7, 8, 70));

        //Hangars
        List<StackPane> hangers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            double x = hangerCenterX(i), y = hangerCenterY(i);

            StackPane hanger = new StackPane();
            hanger.setLayoutX(x);
            hanger.setLayoutY(y);
            Circle hangerCircle = new Circle();
            hangerCircle.setRadius(2 * pointRadius);
            hangerCircle.setFill(Color.valueOf(colorCSS(gameController.getMap().getPointByIndex(i + 1).getColor())));
            hanger.getChildren().add(hangerCircle);
            hanger.setId("hanger_"+gameController.getMap().getPointByIndex(i + 1).getColor().toString());

            hangers.add(hanger);
        }

        int i = 0;
        for (Circle point: pointList) {
            point.setId("Point_" + i);
            map.getChildren().addAll(point);
            i += 1;
        }

        map.getChildren().addAll(hangers);

        //********* add planes
        map.getChildren().addAll(hangers_planes());
        for (AirplaneStack stack : gameController.getMap().getAirplaneStacks()) {
            if (stack.isDepartured() && stack.getPassLength() == 0) {
                addStackAt(waitingAreaCenterX(stack.getColor()) + pointRadius,
                        waitingAreaCenterY(stack.getColor()) + pointRadius,
                        stack);
            } else if (stack.isDepartured()) {
                addStackAt(stack.getPoint().getPosition(), stack);
            }
        }

    }


    private void stateColumnUpdate() {
        stateColumn.getChildren().removeAll(stateColumn.getChildren());

        Button restartBtn = new Button("restart");
        restartBtn.setOnAction(actionEvent -> {
            System.out.println("\n\n\n\n Restarted!\n");

            gameController = new GameController();
            rollBtn = new Button("Roll!");
            gameController = new GameController();

            root = new HBox();

            stateColumn = new VBox();
            stateColumnUpdate();
            initialMap();
            VBox operations = operationColumn();

            root.getChildren().addAll(stateColumn, map, operations);

            gameView.setRoot(root);
//                gameView = new Scene(root, width, height);
        });

        Button saveBtn = new Button("save");
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FileOutputStream fileOut =
                            new FileOutputStream("../savedGames/save.greatAeroplane");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(gameController.getMap());
                    out.close();
                    fileOut.close();
                    System.out.println("Game saved");
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        });

        stateColumn.getChildren().add(restartBtn);
        stateColumn.getChildren().add(saveBtn);

        HBox labels = new HBox();
        Label currentColorLabel = new Label("Current Player: ");
        currentColorLabel.setFont(new Font(24));
        Label currentColor = new Label(String.format("%7s", this.gameController.getCurrentTurn().toString()));
        currentColor.setTextFill(Color.valueOf(colorCSS(this.gameController.getCurrentTurn())));
        currentColor.setFont(new Font(24));
        labels.getChildren().addAll(currentColorLabel, currentColor);

        stateColumn.getChildren().add(labels);
    }

    private VBox operationColumn() {
        this.operations = new VBox();

        Button addBtn = new Button("+"); addBtn.setId("addBtn");
        Button minusBtn = new Button("-"); minusBtn.setId("minusBtn");
        Button timesBtn = new Button("*"); timesBtn.setId("timesBtn");
        Button divideBtn = new Button("/"); divideBtn.setId("divideBtn");
        Button liftOffBtn = new Button("Lift Off!");

        rollBtn.setOnAction(actionEvent -> {
            operations.getChildren().remove(rollBtn);

            gameController.rollBtnPressed();
            int num1 = gameController.getDieNumber1(), num2 = gameController.getDieNumber2();
            HBox dieNum = new HBox();
            Label dieNumberLabel = new Label("Die number: (" + num1 + ", " + num2 + ")");
            dieNumberLabel.setFont(new Font(24));
            dieNum.getChildren().add(dieNumberLabel);
            dieNum.setId("dieNum");
            operations.getChildren().add(dieNum);

            int movableNum = (int) gameController.getMap().getAirplaneStacksByColor(gameController.getCurrentTurn()).stream()
                    .filter(AirplaneStack::isDepartured).count();
            System.out.println(gameController.getCurrentTurn().toString() + " movable: "+ movableNum);
            for (AirplaneStack a : gameController.getMap().getAirplaneStacksByColor(gameController.getCurrentTurn())) {
                System.out.println(a.isDepartured());
            }

            if (movableNum == 0 && num1 != 6 && num2 != 6) {
                Button skip = new Button("skip");
                skip.setOnAction(actionEvent1 -> {
                    operations.getChildren().removeAll(operations.getChildren());
                    stage = 0;
                    gameController.skipBtnPressed();
                    operations.getChildren().add(rollBtn);
                    gameController.onTurnFinished();
                    stateColumnUpdate();
                });

                operations.getChildren().add(skip);

                return;
            }
            if (movableNum != 0) {
                operations.getChildren().add(addBtn);
                if (num1 != num2) operations.getChildren().add(minusBtn);
                operations.getChildren().add(timesBtn);
                if (num1 % num2 == 0 || num2 % num1 == 0) operations.getChildren().add(divideBtn);

                if (gameController.getMap().getNumInHanger(gameController.getCurrentTurn()) != 0
                        && (num1 == 6 || num2 == 6))
                    operations.getChildren().add(liftOffBtn);

                stage = 1;
                return;
            }

            Label choosePlane = new Label("Please choose a plane to lift off");
            choosePlane.setFont(new Font(24));
            operations.getChildren().add(choosePlane);

            stage = 2;
        });


        // ***************************
        //event handlers for buttons here

        addBtn.setOnAction(actionEvent -> {
            operations.getChildren().removeAll(operations.getChildren());

            gameController.addBtnPressed();
            operationChosen(operations);

//                operations.getChildren().add(rollBtn);
        });
        minusBtn.setOnAction(actionEvent -> {
            operations.getChildren().removeAll(operations.getChildren());

            gameController.minusBtnPressed();
            operationChosen(operations);

//                operations.getChildren().add(rollBtn);
        });
        timesBtn.setOnAction(actionEvent -> {
            operations.getChildren().removeAll(operations.getChildren());

            gameController.timesBtnPressed();
            operationChosen(operations);

//                operations.getChildren().add(rollBtn);
        });
        divideBtn.setOnAction(actionEvent -> {
            operations.getChildren().removeAll(operations.getChildren());

            gameController.divideBtnPressed();
            operationChosen(operations);
//                operations.getChildren().add(rollBtn);
        });
        liftOffBtn.setOnAction(actionEvent -> {
            Label choosePlane = new Label("Please choose a plane to lift off");
            choosePlane.setFont(new Font(24));
            operations.getChildren().add(choosePlane);

            stage = 2;
        });

        operations.getChildren().add(rollBtn);
        stage = 0;

        return operations;
    }

    private void operationChosen(VBox operations) {
        operations.getChildren().removeAll(operations.getChildren());

        Label choosePlane = new Label("Please choose a plane to move");
        choosePlane.setFont(new Font(24));

        int chosenNumber = gameController.getChosenNumber();

        Label chosenNumberLabel = new Label("Your chosen number is: " + chosenNumber);
        chosenNumberLabel.setFont(new Font(24));

        operations.getChildren().add(choosePlane);
        operations.getChildren().add(chosenNumberLabel);
        stage = 2;
    }

    public GameView(double width, double height) {

        this.rollBtn = new Button("Roll!");
        this.height = height;
        this.width = width;
        this.gameController = new GameController();

        this.root = new HBox();

        this.stateColumn = new VBox();
        stateColumnUpdate();
        initialMap();
        VBox operations = operationColumn();

        root.getChildren().addAll(stateColumn, this.map, operations);

        //debug
//        for (int i = 0; i < 76; i++) {
//            System.out.println(i + " "
//                    + gameController.getMap().getPointByIndex(i).getColor() + " "
//                    + gameController.getMap().getPointByIndex(i).getPosition()
//                    );
//        }
//        addStackAt(13, gameController.getMap().getAirplaneStacksByColor(Model.Color.BLUE).get(0));
//        addStackAt(14, gameController.getMap().getAirplaneStacksByColor(Model.Color.BLUE).get(0));
//        addStackAt(15, gameController.getMap().getAirplaneStacksByColor(Model.Color.RED).get(0));
//        addStackAt(16, gameController.getMap().getAirplaneStacksByColor(Model.Color.RED).get(0));
//

        this.gameView = new Scene(root, width, height);
    }
    public GameView(double width, double height, Model.Map loadMap) {

        this.rollBtn = new Button("Roll!");
        this.height = height;
        this.width = width;
        this.gameController = new GameController();

        this.root = new HBox();

        this.stateColumn = new VBox();
        stateColumnUpdate();
        initialMap(loadMap);
        VBox operations = operationColumn();

        root.getChildren().addAll(stateColumn, this.map, operations);

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


            if (start > end) start -= 1;
            else start += 1;

            index += 1;
        }

        return edge;
    }
}
