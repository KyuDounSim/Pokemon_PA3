public class map {

    private Cell[][] map;
    private final int rows;
    private final int cols;

    public map(int rows, int cols, Cell[][] input) {
        this.rows = rows;
        this.cols = cols;
        map = input;
    }
}
