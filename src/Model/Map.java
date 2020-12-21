package Model;

import java.util.ArrayList;
import java.util.List;

public class Map {
    List<Point> points;
    List<Player> players;
    List<AirplaneStack> airplaneStacks;
    public Map(){
        this.points = new ArrayList<>();
        this.players = new ArrayList<>();
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
                points.add(new Point(Color.values()[(j + 2) % 4], i * j + 52));
            }
        }
        for (int i = 0; i < 4; i++){
            for (int j = 1 ; j <= 4; j++){
                airplaneStacks.add(new AirplaneStack(getColorByIndex(j)));
            }
        }
    }

    public void addPlayers(Player player){
        players.add(player);
    }
    public void addAirplaneStacks(AirplaneStack airplaneStack){
        airplaneStacks.add(airplaneStack);
    }
    public AirplaneStack getAirplaneStackAt(Point point){
        for (AirplaneStack x : airplaneStacks){
            if (x.getPoint().getPosition() == point.getPosition() && x.getPoint().getColor() == point.getColor()){
                return x;
            }
        }
        return null;
    }
    public void removeAirplaneStackAt(Point point){
        airplaneStacks.remove(getAirplaneStackAt(point));
    }
}
