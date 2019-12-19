import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class 	Player {
	// Current number of poke balls 
	private int numOfPokeBalls;	
	// Start position
	private Cell startPos;
	// Destination position
	private Cell destPos;
	// Current position
	private Cell curPos;
	// Prev visited cells
	private List<Cell> path;
	
	// Set of caught pokemons
	private HashSet<Pokemon> caughtPoke;
	// Set of visited supply stations
	private HashSet<Station> visitedStations;
	
	// Set of "desired" pokemons
	private HashSet<Pokemon> hasToBeCaught;
	// Set of unvisited supply stations
	private HashSet<Station> hasToBeVisited;
	
	
	/**
	 * Constructor. Create player object
	 * @param startPos - start player position
	 * @param destPos - destination player position
	 */
	public Player(Cell startPos, Cell destPos) {
		numOfPokeBalls = 0;
		this.startPos = startPos;
		this.destPos = destPos;
		curPos = startPos;
		
		path = new ArrayList<Cell>();
		caughtPoke = new HashSet<>();
		visitedStations = new HashSet<>();
		
		hasToBeCaught = new HashSet<>();
		hasToBeVisited = new HashSet<>();
	}
	
	/**
	 * Copy constructor. Create copy of specified "other" player object
	 * @param other - to be copied
	 */
	public Player(Player other) {
		numOfPokeBalls = other.numOfPokeBalls;
		startPos = new Cell(other.startPos);
		destPos = new Cell(other.destPos);
		curPos = new Cell(other.curPos);
		
		path = new ArrayList<Cell>(other.path);
		caughtPoke = new HashSet<>(other.caughtPoke);
		visitedStations = new HashSet<>(other.visitedStations);
		
		hasToBeCaught = new HashSet<>(other.hasToBeCaught);
		hasToBeVisited = new HashSet<>(other.hasToBeVisited);
	}
	
	/**
	 * Returns set of caught pokemons
	 * @return caught poke
	 */
	public HashSet<Pokemon> getCaughtPoke() { return caughtPoke; }
	
	/**
	 * Returns set of visited stations
	 * @return visited stations
	 */
	public HashSet<Station> getVisitedStations() { return visitedStations; }
	
	/**
	 * Check if pokemon in the caught set 
	 * @param pokemon pokemon to check if it is caught
	 * @return true if poke in set false otherwise
	 */
	public boolean isPokeCaught(Pokemon pokemon) { return caughtPoke.contains(pokemon); }
	
	/**
	 * Check if station in visited set
	 * @param station station to check if it is visited
	 * @return true if station in set false otherwise
	 */
	public boolean isStationVisited(Station station) { return visitedStations.contains(station); }

	
	/**
	 * Append new pokemon to "uncaught" set 
	 * @param pokemon - to be caught
	 */
	public void addToDoPoke(Pokemon pokemon) { hasToBeCaught.add(pokemon); }
	
	/**
	 * Append new station to "unvisited" set
	 * @param station - to be visited
	 */
	public void addToDoStation(Station station) { hasToBeVisited.add(station); }
	
	/**
	 * Returns set of "desired" pokemons
	 * @return set of pokemons
	 */
	public HashSet<Pokemon> getDesiredPokemons() { return hasToBeCaught; }
	
	/**
	 * Returns set of "desired" stations
	 * @return set of stations
	 */
	public HashSet<Station> getUnvisitedStations() { return hasToBeVisited; }

	/**
	 * Try to visit cell
	 * @param cell - cell to be visited
	 * @return true if player can visit this cell, false otherwise (walls)
	 */
	public boolean visit(Cell cell) {
		
		// Cant pass through wall
		if(cell instanceof Wall) return false;
		
		// Set new position
		curPos = cell;
		// Update list of visited cells
		path.add(cell);		
      
		// Cell is station type
		// and this station is not visited yet
		if (cell instanceof Station && !visitedStations.contains((Station)cell)) {
			Station station = (Station)cell;
			// Obtain additional poke balls from supply station
			numOfPokeBalls += station.getSupply();
			// Append cell to visited stations set
			visitedStations.add(station);
			// In case if this poke in set of desired - remove
			if(hasToBeVisited.contains(station)) hasToBeVisited.remove(station);
		
		// Cell is pokemon type
		// and this pokemon is not caught yet
		} else if (cell instanceof Pokemon && !caughtPoke.contains((Pokemon)cell)) {
			Pokemon pokemon = (Pokemon)cell;
			// Check if we can catch up this poke
			if (numOfPokeBalls >= pokemon.getNumOfBalls()) {
				// Spend some poke balls
				numOfPokeBalls -= pokemon.getNumOfBalls();
				
				// Append pokemon cell to visited set
				caughtPoke.add(pokemon);				
				// In case if this poke in set of desired - remove
				if(hasToBeCaught.contains(pokemon)) hasToBeCaught.remove(pokemon);
			}
		}
		
		return true;
	}
	
	/**
	 * Returns player start position 
	 * @return startPos
	 */
	public Cell getStartPos() { return startPos; }
	
	/**
	 * Returns player destination position 
	 * @return destPos
	 */
	public Cell getDestPos() { return destPos; }
	
	/**
	 * Returns current player position
	 * @return curPos
	 */
	public Cell getCurPos() { return curPos; }
	
	/**
	 * Check if destination position reached
	 * @return true if cur pos is eq to dest pos, false otherwise
	 */
	public boolean isDestReached() { return curPos.compareTo(destPos) == 0; }
	
	/**
	 * Returns list of visited cells
	 * @return path
	 */
	public List<Cell> getPath() { return path; }

	/**
	 * Calculate final score
	 * @return player score
	 */
	public int getScore() {
		// Calculate result score
		return getNB() + 5 * getNP() + 10 * getNS() + getMCP() - path.size() + 1;
	}
	
	/**
	 * Returns number of poke balls currently available
	 * @return NB
	 */
	public int getNB() { return numOfPokeBalls; }
	
	/**
	 * Returns number of caught pokemons
	 * @return NP
	 */
	public int getNP() { return caughtPoke.size(); }

	/**
	 * Returns caught max poke power 
	 * @return MCP
	 */
	public int getMCP() { 
		if(caughtPoke.isEmpty()) return 0;
		return caughtPoke.stream().max(Comparator.comparing(c -> c.getPower())).get().getPower();
	}

	/**
	 * Returns number of distinct poke types
	 * @return NS
	 */
	public int getNS() {
		HashSet<String> uniquePokeType = new HashSet<>();
		Iterator<Pokemon> it = caughtPoke.iterator();
		while(it.hasNext()) {
			Pokemon pokemon = (Pokemon)it.next();
			uniquePokeType.add(pokemon.getType());
		}

		return uniquePokeType.size();
	}
	
}
