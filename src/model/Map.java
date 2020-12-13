package model;

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
    private Color setColor(int a){
        int r = a / 4;
        if (r == 0){return Color.YELLOW;}
        if (r == 1){return Color.BLUE;}
        if (r == 2){return Color.GREEN;}
        if (r == 3){return Color.RED;}
        return null;
    }
    private void initMap(){
        for (int i = 1; i <= 52; i++){
            points.add(new Point(setColor(i), i));
        }
        for (int i = 0; i < 4; i++){
            for (int j = 1 ; j <= 4; j++){
                airplaneStacks.add(new AirplaneStack(setColor(j)));
            }
        }
    }

    public void setPlayers(Player player){
        players.add(player);
    }
    public void setAirplaneStacks(AirplaneStack airplaneStack){
        airplaneStacks.add(airplaneStack);
    }
}
