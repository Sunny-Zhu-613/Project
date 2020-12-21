package Control;

import Model.AirplaneStack;
import Model.Color;
import Model.Map;
import Model.Point;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
    public int getChosenNumber() {return this.chosenNumber;}

    public void rollBtnPressed() {

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

        // *******

    }

    public void addBtnPressed() {
        int addNumber = Math.min(dieNumber1 + dieNumber2,12);
        System.out.println(addNumber);
        chosenNumber = addNumber;

    }
    public void minusBtnPressed() {
        int minusNumber = Math.max(dieNumber1, dieNumber2) - Math.min(dieNumber1, dieNumber2);
        System.out.println(minusNumber);
        chosenNumber = minusNumber;

    }
    public void timesBtnPressed() {
        int timesNumber = Math.min(dieNumber1 * dieNumber2,12);
        System.out.println(timesNumber);
        chosenNumber = timesNumber;

    }

    public void divideBtnPressed() {
        int divideNumber = Math.max(dieNumber1, dieNumber2) / Math.min(dieNumber1, dieNumber2);
        System.out.println(divideNumber);
        chosenNumber = divideNumber;

    }

    public void skipBtnPressed() {
//        this.currentTurn = currentTurn.next();
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

    public void onTurnFinished(){
        System.out.println("From " + this.getCurrentTurn() + " to " + this.getCurrentTurn().next());
        if (count >=3){
            this.currentTurn = currentTurn.next();
            count = 0;
        }
    }
    public int targetPosition(AirplaneStack stack, int chosenNumber) {
        int currentPosition = stack.getPoint().getPosition();
        if (stack.getPassLength() + chosenNumber < 50) return (currentPosition+chosenNumber) % 52;
        else if (stack.getColor() == Color.GREEN) {
            return (currentPosition + chosenNumber - 52) + 52;
        } else if (stack.getColor() == Color.RED) {
            return (currentPosition + chosenNumber - 52) + 58;
        } else if (stack.getColor() == Color.YELLOW) {
            return (currentPosition + chosenNumber - 52) + 64;
        } else if (stack.getColor() == Color.BLUE) {
            return (currentPosition + chosenNumber - 52) + 70;
        }

        return 0;
    }
    public boolean moveAttempt(AirplaneStack toMove) {
        if (toMove.getColor() == getCurrentTurn()){
            if (toMove.getPoint() == null){
                if (toMove.getColor() == Color.GREEN){toMove.setCurrentPoint(map.getPointByIndex(13));}
                if (toMove.getColor() == Color.RED){toMove.setCurrentPoint(map.getPointByIndex(26));}
                if (toMove.getColor() == Color.YELLOW){toMove.setCurrentPoint(map.getPointByIndex(39));}
                if (toMove.getColor() == Color.BLUE){toMove.setCurrentPoint(map.getPointByIndex(51));}
            }
//            AirplaneStack x = map.getAirplaneStackAt(toMove.getPoint().getPosition()+chosenNumber);
            AirplaneStack x = map.getAirplaneStackAt(targetPosition(toMove, chosenNumber));
            if (x != null){
                System.out.println(x.toString());
                if (x.getColor() == toMove.getColor()){
                    int a = x.getStackNum();
                    map.getAirplaneStacks().remove(x);

                    System.out.println("Stack Num += " + a);
                    toMove.setStackNum(toMove.getStackNum()+a);
                    toMove.setCurrentPoint(map.getPointByIndex(targetPosition(toMove, chosenNumber)));
                }
                if (x.getColor() != toMove.getColor()){
                    toMove.setCurrentPoint(map.getPointByIndex(targetPosition(toMove, chosenNumber)));
                    while (x.getStackNum() > 0 && toMove.getStackNum() > 0){
                        int BattleNumber1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
                        int BattleNumber2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
                        if (BattleNumber1 > BattleNumber2){
                            x.setStackNum(x.getStackNum()-1);
                            map.addAirplaneStacks(new AirplaneStack(x.getColor()));
                        }
                        if (BattleNumber1 < BattleNumber2){
                            toMove.setStackNum(toMove.getStackNum()-1);
                            map.addAirplaneStacks(new AirplaneStack(toMove.getColor()));
                        }
                    }
                }
            }
            else {
                toMove.setCurrentPoint(map.getPointByIndex(targetPosition(toMove, chosenNumber)));
                System.out.println("Got here at:" + toMove.getPoint().getPosition() + " " + toMove.getPoint().getColor().toString());
            }
            if (toMove.getColor() == toMove.getPoint().getColor()){
                if (toMove.getPassLength() == 18 && toMove.getPassLength() == 14){
                    toMove.setCurrentPoint(map.getPointByIndex(targetPosition(toMove, 16)));
                    for (AirplaneStack stack : map.getAirplaneStacks()){
                        if (stack.getColor() == Color.shortcut(toMove.getColor()) && stack.getPassLength() == 54){
                            int n = stack.getStackNum();
                            map.getAirplaneStacks().remove(stack);
                            for (int i = 0; i < n; i++){
                                map.getAirplaneStacks().add(new AirplaneStack(stack.getColor()));
                            }
                        }
                    }
                }
                else {
                    System.out.println("Jump!");
                    toMove.setCurrentPoint(map.getPointByIndex(targetPosition(toMove, 4)));
                }
            }
            return true;
        }
        return false;
    }
    public boolean isCompleted(){
        List<AirplaneStack> a = map.getAirplaneStacks().stream().filter(s -> !s.isFinished()).collect(Collectors.toList());
        Color x = a.get(0).getColor();
        for (AirplaneStack b : a){
            if (b.getColor() != x){
                return false;
            }
        }
        return true;
    }

}
