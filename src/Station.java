/**
 * 
 * Station cell
 *
 */
public class Station extends Cell {
	// Number of provided balls
	private int supply;
	
	/**
	 * Construct Station cell object
	 * @param row - map row
	 * @param col - map column
	 */
	public Station(int row, int col) { super(row, col); }
	
	/**
	 * Returns number of balls that can station provide to player 
	 * @return supply value
	 */
	public int getSupply() { return this.supply; }

	/**
	 * Setter for number of supply 
	 * @param supply - new supply value
	 */
	public void setSupply(int supply) { this.supply = supply; }
	
}
