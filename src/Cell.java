/**
 * Map cell object
 *
 */
public class Cell implements Comparable<Object> {
	// Cell values - row/column
	public int row = 0;
	public int col = 0;

	/**
	 * Construct cell object
	 * @param row - map row
	 * @param col - map column
	 */
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Copy constructor for cell object
	 * Create cell object based on "other" field values
	 * @param other - object that should be copied
	 */
	public Cell(Cell other) {
		this.row = other.row;
		this.col = other.col;
	}

	/**
	 * Cell hashcode
	 */
	@Override
	public int hashCode() {
		int var1 = 31 + this.row;
		return var1 * 31 + this.col;
	}

	
	/**
	 * Cell equality 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(this == obj) return true;
		//if (this.getClass() != obj.getClass()) return false;
		
		Cell cell = (Cell)obj;
		return this.row == cell.row && this.col == cell.col;
	}

	/**
	 * Override string representation for cell object
	 */
	@Override
	public String toString() {
		return "<" + this.row + "," + this.col + ">";
	}

	/**
	 * Implement Comparable interface
	 * Compare this cell with second object.
	 */
	@Override
	public int compareTo(Object obj) {
		Cell cell = (Cell)obj;
		Integer left;
		Integer right;
		if (this.row == cell.row) {
			left = new Integer(this.col);
			right = new Integer(cell.col);
			return left.compareTo(right);
		}
		left = new Integer(this.row);
		right = new Integer(cell.row);
		return left.compareTo(right);
	}

	/**
	 * Returns manhattan distance between this and specified cell
	 * @param cell - second cell to measure distance 
	 * @return manhattan distance
	 */
	public int distance(Cell cell) {
		return Math.abs(this.row - cell.row) + Math.abs(this.col - cell.col);
	}
	
}
