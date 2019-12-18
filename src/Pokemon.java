/**
 * 
 * Pokemon cell
 *
 */
public class Pokemon extends Cell {
	private String name;
	private String type;
	private int power;
	private int numOfBalls;

	/**
	 * Constructor of pokemon cell object
	 * @param row - map row
	 * @param col - map column
	 */
	public Pokemon(int row, int col) {
		super(row, col);
	}

	////// Geters	
	/**
	 * Name getter
	 * @return name
	 */
	public String getName() { return name; }
	
	/**
	 * Type of pokemon getter
	 * @return type
	 */
	public String getType() { return type; }
	
	/**
	 * Pokemon power getter
	 * @return power
	 */
	public int getPower() { return this.power; }
	
	/**
	 * Number of balls required to catch this pokemon
	 * @return numOfBalls
	 */
	public int getNumOfBalls() { return this.numOfBalls; }
	
	////// Setters
	/**
	 * Pokemon name setter
	 * @param name - new name value
	 */
	public void setName(String name) { this.name = name; }
	
	/**
	 * Pokemon type setter
	 * @param type - new type value
	 */
	public void setType(String type) { this.type = type; }
	
	/**
	 * Pokemon power setter
	 * @param power - new power value
	 */
	public void setPower(int power) {  this.power = power; }
	
	/**
	 * Pokemon "number of balls required" setter
	 * @param numOfBalls - new "number of balls required" value
	 */
	public void setNumOfBalls(int numOfBalls) { this.numOfBalls = numOfBalls; }

}
