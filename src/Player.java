import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Player extends Cell {
    ArrayList<Pokemon> caught;
    int numPokeball;
    ArrayList<Coordinate> path;

    public Player(Coordinate coord) {
        super(coord);
        caught = new ArrayList<>();
        numPokeball = 0;
        path = new ArrayList<>();
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
        return numPokeball + 5 * caught.size() + 10 * (int) caught.stream().filter(distinctByKey(a -> a.getType())).count() + caught.stream().mapToInt(i -> i.getCombat()).sum() - path.size();
    }

    public String printScoreElement() {
        return numPokeball + ":" + caught.size() + ":" + caught.stream().filter(distinctByKey(a -> a.getType())).count() + ":" + caught.stream().mapToInt(i -> i.getCombat()).sum();
    }

    public String printPath() {
        String sol = "";
        for (var a :
                path) {
            sol += (a.printCoord() + "->");
        }

        return sol.substring(0, sol.length() - 2);
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> ((ConcurrentHashMap) seen).putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
