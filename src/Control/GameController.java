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
        System.out.println("Departure Attempt");
        if (toLiftOff.isDepartured() || toLiftOff.getColor() != getCurrentTurn()) return false;
        if (dieNumber1 != 6 && dieNumber2 != 6) return false;
        //Lift off
        toLiftOff.setDepartured();
        return true;
    }

    public void onTurnFinished(){
//        System.out.println("From " + this.getCurrentTurn() + " to " + this.getCurrentTurn().next());
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
    private void battle(AirplaneStack stack1, AirplaneStack stack2) {
        while (stack1.getStackNum() > 0 && stack2.getStackNum() > 0) {
            int battleNumber1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            int battleNumber2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
            if (battleNumber1 > battleNumber2) {
                stack2.setStackNum(stack2.getStackNum() - 1);
            }
            if (battleNumber1 < battleNumber2) {
                stack1.setStackNum(stack1.getStackNum() - 1);
            }
        }
        this.map.setAirplaneStacks(map.getAirplaneStacks().stream().filter(stack -> (stack.getStackNum() > 0)).collect(Collectors.toList()));
    }
    private void stacking(AirplaneStack stack1, AirplaneStack stack2) {
        System.out.println(stack1.getColor().toString() + " stacking " + stack1.getStackNum() + " + " + stack2.getStackNum());
        int num = stack2.getStackNum();
        stack1.setStackNum(stack1.getStackNum() + num);
        map.getAirplaneStacks().remove(stack2);
    }
    private void landing(AirplaneStack toBeMoved, int targetPosition) {
        AirplaneStack adversaryOrFriend = map.getAirplaneStackAt(targetPosition);
        if (adversaryOrFriend != null) {
            if (adversaryOrFriend.getColor() != toBeMoved.getColor())
                battle(toBeMoved, adversaryOrFriend);
            else {
                System.out.println(adversaryOrFriend.getStackNum());
                stacking(toBeMoved, adversaryOrFriend);
            }
        }
    }
    public int moveBy(AirplaneStack toBeMoved, int length) {
        int targetPosition;
        int passLength = toBeMoved.getPassLength();
        if (toBeMoved.getPoint() == null) {
                if (toBeMoved.getColor() == Color.GREEN){toBeMoved.setCurrentPoint(map.getPointByIndex(12));}
                if (toBeMoved.getColor() == Color.RED){toBeMoved.setCurrentPoint(map.getPointByIndex(25));}
                if (toBeMoved.getColor() == Color.YELLOW){toBeMoved.setCurrentPoint(map.getPointByIndex(38));}
                if (toBeMoved.getColor() == Color.BLUE){toBeMoved.setCurrentPoint(map.getPointByIndex(51));}
            }
        int currentPosition = toBeMoved.getPoint().getPosition();

        if (passLength + length < 50) targetPosition = (currentPosition + length) % 52;
        else if (toBeMoved.getColor() == Color.GREEN) {
            targetPosition = (currentPosition + length - 52) + 52;
        } else if (toBeMoved.getColor() == Color.RED) {
            targetPosition = (currentPosition + length - 52) + 58;
        } else if (toBeMoved.getColor() == Color.YELLOW) {
            targetPosition = (currentPosition + length - 52) + 64;
        } else if (toBeMoved.getColor() == Color.BLUE) {
            targetPosition = (currentPosition + length - 52) + 70;
        } else targetPosition = -1; //should never happen

//        toBeMoved.setCurrentPoint(map.getPointByIndex(targetPosition));
//        toBeMoved.addPathLength(length);
        return targetPosition;
    }
    public boolean moveAttempt(AirplaneStack toBeMoved) {
        //jump
        //shortcut
        //battle

        System.out.println("Move Attempt");
        if (toBeMoved.getColor() != getCurrentTurn()) return false;

        int targetPosition = moveBy(toBeMoved, chosenNumber);
        landing(toBeMoved, targetPosition);
        toBeMoved.setCurrentPoint(map.getPointByIndex(targetPosition));
        toBeMoved.addPathLength(chosenNumber);

        System.out.println(toBeMoved.getColor().toString() + " moving, target color "
                + map.getPointByIndex(toBeMoved.getPoint().getPosition()).getColor());
        if (toBeMoved.getPassLength() <= 50
                && map.getPointByIndex(toBeMoved.getPoint().getPosition()).getColor() == toBeMoved.getColor()) {
            if (toBeMoved.getPassLength() == 14 || toBeMoved.getPassLength() == 18) {
//                moveBy(toBeMoved, 12); //shortcut
                targetPosition = moveBy(toBeMoved, 12);
                landing(toBeMoved, targetPosition);
                toBeMoved.setCurrentPoint(map.getPointByIndex(targetPosition));
                toBeMoved.addPathLength(chosenNumber);
                System.out.println(toBeMoved.getColor().toString() + " " + "shortcut");
            } else {
//                moveBy(toBeMoved, 4); //jump
                targetPosition = moveBy(toBeMoved, 4);
                landing(toBeMoved, targetPosition);
                toBeMoved.setCurrentPoint(map.getPointByIndex(targetPosition));
                toBeMoved.addPathLength(chosenNumber);
                System.out.println(toBeMoved.getColor().toString() + " " + "jump");
            }
        }

        return true;
    }

}
