import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 2D game map
 *
 */
public class Map {
	// Number of rows and columns
	private int N = 0;
	private int M = 0;
	// 2D array of cells 
	private Cell[][] data = null;
	
	// Start position (initial player position)
	private Cell startPos = null;
	// Destination position (player target position)
	private Cell destPos = null;
	
	// List of stations and pokemons
	private List<Station> stations;
	private List<Pokemon> pokemons;
	
	/**
	 * Constructor
	 * @param M - number of rows in the map
	 * @param N - number of columns in the map
	 */
	public Map(int M, int N) {
		this.M = M;
		this.N = N;
		this.data = new Cell[M][N];
		this.stations = new ArrayList<>();
		this.pokemons = new ArrayList<>();
	}
	
	/**
	 * Returns start cell
	 * @return startPos
	 */
	public Cell getStartPos() {
		return this.startPos;
	}

	/**
	 * Returns destination cell
	 * @return destPos
	 */
	public Cell getDestination() {
		return this.destPos;
	}
	
	/**
	 * Set start cell
	 * @param cell - new startPos cell value
	 */
	public void setStartPos(Cell cell) {
		startPos = data[cell.row][cell.col] = new Cell(cell.row, cell.col);
	}

	/**
	 * Set destination cell
	 * @param cell - new destination cell value 
	 */
	public void setDestPos(Cell cell) {
		destPos = data[cell.row][cell.col] = new Cell(cell.row, cell.col);
	}

	/**
	 * Set new cell
	 * @param row - map row
	 * @param col - map column
	 * @param cell - new cell value
	 */
	public void setCell(int row, int col, Cell cell) {
		// Set new cell
		this.data[row][col] = cell;
		// Update pokemons list
		if (cell instanceof Pokemon) {
			pokemons.add((Pokemon) cell);
		}

		// Update stations list
		if (cell instanceof Station) {
			stations.add((Station) cell);
		}

	}

	/**
	 * Returns list of all pokemons
	 * @return pokemons
	 */
	public List<Pokemon> getPokemons() {
		return pokemons;
	}

	/**
	 * Returns list of all stations
	 * @return stations
	 */
	public List<Station> getStations() {
		return stations;
	}

	/**
	 * Returns number of map rows
	 * @return M
	 */
	public int getRows() { return M; }

	/**
	 * Returns number of map columns
	 * @return N
	 */
	public int getCols() { return N; }

	/**
	 * Returns cell by specified row, col pair
	 * @param row - map row
	 * @param col - map column
	 * @return cell at [row; col]
	 */
	public Cell getCell(int row, int col) { return data[row][col]; }

	/**
	 * Check if specified values of 'row' and 'col' is outside of the map bounds 
	 * (row/col below zero OR row/col above max row/col number)  
	 * @param row - map row
	 * @param col - map column
	 * @return true if [row; col] is outside of the map bounds
	 */
	public boolean isOutOfBounds(int row, int col) { return row < 0 || row >= M || col < 0 || col >= N; }
	
	public boolean isOutOfBounds(Cell cell) { return isOutOfBounds(cell.row, cell.col); }
	
	
}
