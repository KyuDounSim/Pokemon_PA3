public class Map {

    private Cell[][] map;
    private boolean[][] boolMap;
    private final int rows;
    private final int cols;

    public Map(int rows, int cols, Cell[][] input, boolean[][] boolMap) {
        this.rows = rows;
        this.cols = cols;
        map = input;
        this.boolMap = boolMap;
    }
}
