import java.util.ArrayList;

public class Player extends Cell {
    ArrayList<Pokemon> caught;
    int numPokeball;
    ArrayList<Coordinate> path;

    public Player(Coordinate coord) {
        super(coord);
        caught = new ArrayList<Pokemon>();
        numPokeball = 0;
        path = new ArrayList<Coordinate>();
        path.add(coord);
    }

    public void addPokemon(Pokemon a) {
        caught.add(a);
    }

    public void addPokeball(int a) {
        numPokeball += a;
    }

    public void addPath(Coordinate a) {
        path.add(a);
    }

    public int score() {
        //TODO
        return 0;
//        return numPokeball + 5 * caught.size() - path.size();
    }

    public String printScoreElement() {
        //TODO
        return "printScoreElement";
//        return numPokeball + ":";
    }

    public String printPath() {
        //TODO
        return "path!";
//        return null;
    }


}
