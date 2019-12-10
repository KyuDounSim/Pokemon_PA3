public class Station extends Cell {
    private Coordinate coord;
    private int providedBalls;
    private boolean visited = false;

    public Station(Coordinate coord) {
        super(coord);
        providedBalls = 0;
    }

    public Station(Coordinate coord, int n) {
        super(coord);
        providedBalls = n;
    }

    public int getBallNum() {
        return providedBalls;
    }

    public void setVisited(boolean status) {
        visited = status;
    }
}
