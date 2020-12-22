package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Map {
    List<Point> points;
    List<AirplaneStack> airplaneStacks;
    public Map(){
        this.points = new ArrayList<>();
        this.airplaneStacks = new ArrayList<>();
        initMap();
    }
    static Color getColorByIndex(int a){
        return Color.values()[a % 4];
    }
    public Point getPointByIndex(int index) {
        return this.points.get(index);
    }
    private void initMap(){
        for (int i = 0; i < 52; i++){
            points.add(new Point(getColorByIndex(i), i));
        }
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 6; i++) {
//                points.add(new Point(Color.values()[(j + 2) % 4], i * j + 51));
                points.add(new Point(Color.values()[(j + 2) % 4], i + 6 * j + 52));
            }
        }
        for (int i = 0; i < 4; i++){
            for (int j = 1 ; j <= 4; j++){
                airplaneStacks.add(new AirplaneStack(getColorByIndex(j)));
            }
        }
    }
    public void addAirplaneStacks(AirplaneStack airplaneStack){
        airplaneStacks.add(airplaneStack);
    }
    public List<AirplaneStack> getAirplaneStacks() {return airplaneStacks;}
    public void setAirplaneStacks(List<AirplaneStack> stacks){this.airplaneStacks = stacks;}

    private AirplaneStack getAirplaneStackAtPoint(Point point){
        for (AirplaneStack x : airplaneStacks){
            if (x.getPoint() == null) continue;
            if (x.getPoint().getPosition() == point.getPosition()){// && x.getPoint().getColor() == point.getColor()

                return x;
            }
        }
        return null;
    }
    public AirplaneStack getAirplaneStackAt(int index) {
        for (AirplaneStack x : airplaneStacks){
            if (x.getPoint() == null) continue;
            if (x.getPoint().getPosition() == index){// && x.getPoint().getColor() == point.getColor()

                return x;
            }
        }
        return null;
    }

    public List<Point> getPoints() {return this.points;}
    public int getNumInHanger(Color color) {
        return getAirplaneStacksByColor(color).stream()
                .filter(a -> (a.isDepartured() && !(a.isFinished())))
                .collect(Collectors.toList())
                .size();
    }
    public List<AirplaneStack> getAirplaneStacksByColor(Color color) {
        return this.airplaneStacks.stream().filter(s -> (s.getColor() == color)).collect(Collectors.toList());
    }
//    public void removeAirplaneStackAt(Point point){
//        airplaneStacks.remove(getAirplaneStackAt(point));
//    }


}
