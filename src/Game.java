import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Game {
	// Game map
	private Map map;
	// Result player that holds optimal path
	private Player optimalPlayer;

	/**
	 * Read configuration file. Create map.
	 * @param inputFile - configuration file
	 */
	private void initialize(File inputFile) throws Exception {
		ArrayList<Character> validMapObjects = new ArrayList<>(Arrays.asList('#', ' ', 'B', 'D', 'S', 'P'));

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputFile));
			// Read the first of the input file
			String line = br.readLine();
			int M = Integer.parseInt(line.split(" ")[0]);
			int N = Integer.parseInt(line.split(" ")[1]);

			// define a map
			map = new Map(M, N);

			// Count number of pokemons and number of stations
			int numOfPoke = 0;
			int numOfStat = 0;

			// Read the following M lines of the Map
			for (int i = 0; i < M; i++) {
				line = br.readLine();

				// Validate map. Row length should equal to num of columns (N)
				if(line.length() != N) { map = null; throw new Exception("Corrupted map definition"); }

				// Parse each character of the line and append appropriate cell type
				for(int j = 0; j < line.length(); ++j) {
					Character ch = line.charAt(j);
					// Validate character
					if (!validMapObjects.contains(ch)) {  map = null; throw new Exception("Corrupted map definition"); }
					// Check cell type and create cell object of that type
					switch(ch) {
						case ' ':
							map.setCell(i, j, new Empty(i, j));
							break;
						case '#':
							map.setCell(i, j, new Wall(i, j));
							break;
						case 'B':
							map.setStartPos(new Cell(i, j));
							break;
						case 'D':
							map.setDestPos(new Cell(i, j));
							break;
						case 'P':
							map.setCell(i, j, new Pokemon(i, j));
							++numOfPoke;
							break;
						case 'S':
							map.setCell(i, j, new Station(i, j));
							++numOfStat;
							break;
					}
				}
			}

			// Find the number of stations and pokemons in the map
			// Continue read the information of all the stations and pokemons by using br.readLine();

			// Read pokemons information
			for(int i = 0; i < numOfPoke; ++i) {
				// Read next line
				line = br.readLine();
				// Split into chunks
				String[] parts = line.split(",");
				// Validate pokemon definition format
				if (parts.length != 6) { map = null; throw new Exception("Corrupted map definition. Pokemon definition: Expected 6 chunks of information."); }

				// Parse row/col number
				int row = Integer.parseInt(parts[0].substring(parts[0].indexOf("<") + 1).trim());
				int col = Integer.parseInt(parts[1].substring(0, parts[1].indexOf(">")).trim());

				// Validate map cell type (require pokemon at specified row/col position)
				if (!(map.getCell(row, col) instanceof Pokemon)) {  map = null; throw new Exception("Expected Pokemon object type at: " + row + "\t" + col + "\t" + this.map.getCell(row, col).getClass()); }
				// Get pokemon cell
				Pokemon pokemon = (Pokemon)map.getCell(row, col);
				// Set pokemon attributes
				pokemon.setName(parts[2].trim());
				pokemon.setType(parts[3].trim());
				pokemon.setPower(Integer.parseInt(parts[4].trim()));
				pokemon.setNumOfBalls(Integer.parseInt(parts[5].trim()));
			}

			// Read stations information
			for(int i = 0; i < numOfStat; ++i) {
				// Read next line
				line = br.readLine();
				// Split into chunks
				String[] parts = line.split(",");
				// Validate station definition format
				if (parts.length != 3) { map = null; throw new Exception("Corrupted map definition. Station definition: Expected 3 chunks of information."); }

				// Parse row/col number
				int row = Integer.parseInt(parts[0].substring(parts[0].indexOf("<") + 1).trim());
				int col = Integer.parseInt(parts[1].substring(0, parts[1].indexOf(">")).trim());
				if (!(this.map.getCell(row, col) instanceof Station)) {  map = null; throw new Exception("Expected Station object type at: " + row + "\t" + col + "\t" + this.map.getCell(row, col).getClass()); }

				// Set station params
				Station station = (Station)this.map.getCell(row, col);
				station.setSupply(Integer.parseInt(parts[2].trim()));
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if(br != null) br.close();
		}
	}

	/**
	 * Search patch between two cells
	 * @param curPos - start position
	 * @param targetPos - end position
	 * @return array of cells that should be visited to reach targetPos
	 */
	private ArrayList<Cell> findPath(Cell curPos, Cell targetPos) {
		ArrayList<Cell> path = new ArrayList<>();

		// Mark current cell as visited
		boolean[][] visited = new boolean[map.getRows()][map.getCols()];
		HashMap<Cell, Cell> parent = new HashMap<>();

		// Create queue of "to be visited" cells
		LinkedList<Cell> queue = new LinkedList<>();
		visited[curPos.row][curPos.col] = true;
		parent.put(curPos, null);
		queue.addLast(curPos);

		while(!queue.isEmpty()) {
			// Get first cell from queue
			Cell cell = queue.removeFirst();

			// Found target cell (cell is reachable return)
			if(cell.compareTo(targetPos) == 0) {
				// Restore path
				Cell cur = targetPos;
				path.add(targetPos);
				while(!parent.isEmpty() && cur != null) {
					cur = parent.remove(cur);
					if(cur != null)
						path.add(cur);
				}
				Collections.reverse(path);
				return path;
			}

			// Possible turns
			ArrayList<Cell> turn = new ArrayList<>();
			turn.add(new Cell(cell.row + 1, cell.col));
			turn.add(new Cell(cell.row - 1, cell.col));
			turn.add(new Cell(cell.row, cell.col - 1));
			turn.add(new Cell(cell.row, cell.col + 1));
			// Check adjacent cells
			for(int i = 0; i < turn.size(); ++i) {
				Cell nextPos = turn.get(i);

				// Ensure cell is within map boundaries
				if(!map.isOutOfBounds(nextPos)) {
					Cell mapCell = map.getCell(nextPos.row, nextPos.col);
					if(mapCell instanceof Wall) continue;


					// Append new cell in "to be visited" queue
					if(!visited[nextPos.row][nextPos.col]) {
						parent.putIfAbsent(nextPos, cell);
						visited[nextPos.row][nextPos.col] = true;
						queue.addLast(nextPos);
					}
				}

			}

		}
		return path;
	}

	/**
	 * Initialize all necessary value before traverse path. Run traverse function
	 * @throws IOException
	 */
	private void findPath() {
		Cell start = map.getStartPos();
		Cell end = map.getDestination();
		Player player = new Player(start, end);
		// Scan map and add all pokemons and stations to "desired" sets
		for(int row = 0; row < map.getRows(); ++row) {
			for(int col = 0; col < map.getCols(); ++col) {
				Cell cell = map.getCell(row, col);
				if(cell instanceof Pokemon) {
					// Ensure that this pokemon is reachable from start position
					ArrayList<Cell> path = findPath(start, cell);
					if(!path.isEmpty()) {
						player.addToDoPoke((Pokemon)cell);
						System.out.println("Found reachable pokemon.");
						System.out.println("Path between: [" + start + "] and [" + cell + "]");
						for(int i = 0; i < path.size(); ++i) {
							System.out.print(path.get(i));
							if(i + 1 < path.size()) System.out.print("->");
						}
						System.out.println();
					} else {
						System.out.println("Ignore pokemon. UNABLE TO REACH POKEMON: from [" + start + "] to [" + cell + "]");
					}
				}
				else if(cell instanceof Station) {
					// Ensure that this station is reachable from start position
					ArrayList<Cell> path = findPath(start, cell);
					if(!path.isEmpty()) {
						player.addToDoStation((Station)cell);
						System.out.println("Found reachable station.");
						System.out.println("Path between: [" + start + "] and [" + cell + "]");
						for(int i = 0; i < path.size(); ++i) {
							System.out.print(path.get(i));
							if(i + 1 < path.size()) System.out.print("->");
						}
						System.out.println();
					} else {
						System.out.println("Ignore station. UNABLE TO REACH STATION: from [" + start + "] to [" + cell + "]");
					}
				}
			}
		}
		player.visit(start);
		traverse(player);
	}

	/**
	 * Perform path traversal. Find optimal path to the destination cell
	 * @param curPlayer - current player stats
	 */
	private void traverse(Player curPlayer) {
		// Destination is reached
		if(curPlayer.isDestReached()) {
			// Update optimal player
			if(optimalPlayer == null || curPlayer.getScore() > optimalPlayer.getScore()) {
				System.out.println("Found new optimal path: newscore = " + curPlayer.getScore() + ", prev score = " + (optimalPlayer == null ? 0 : optimalPlayer.getScore()));
				curPlayer.getPath().forEach(c -> { System.out.print(c + "->"); });
				System.out.println();
				optimalPlayer = new Player(curPlayer);
			}
			return;
		}

		// Get current player position
		Cell curPos = curPlayer.getCurPos();

		HashSet<Pokemon> desiredPoke = curPlayer.getDesiredPokemons();
		HashSet<Station> unvisitedStations = curPlayer.getUnvisitedStations();
		// All available paths (to all objectives, excluding exit
		HashMap<Cell, ArrayList<Cell>> routePlan = new HashMap<>();

		desiredPoke.forEach(poke -> {
			// Append pokemons that can be caught by player
			if(poke.getNumOfBalls() <= curPlayer.getNB()) {
				routePlan.put(poke, findPath(curPos, poke));
			}
		});

		unvisitedStations.forEach(station -> {
			routePlan.put(station, findPath(curPos, station));
		});


		routePlan.put(curPlayer.getDestPos(), findPath(curPos, curPlayer.getDestPos()));

		// Try each possible path combination
		if(!routePlan.isEmpty()) {
			for(Cell cell : routePlan.keySet()) {
				ArrayList<Cell> curPath = routePlan.get(cell);
				if(curPath.isEmpty()) continue;

				// Make copy of current player
				Player rePlayer = new Player(curPlayer);
				curPath.remove(0);
				while(!curPath.isEmpty()) {
					Cell nextPos = curPath.remove(0);
					rePlayer.visit(nextPos);
				}

				traverse(rePlayer);
			}
		}

	}

	/**
	 * Save result route to the output file
	 * @param outputFile - path to output file
	 * @throws IOException - unable to create/write to the file
	 */
	private void saveRoute(File outputFile) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
		bw.write(optimalPlayer.getScore() + "\n");
		bw.write(optimalPlayer.getNB() + ":" + optimalPlayer.getNP() + ":" + optimalPlayer.getNS() + ":" + optimalPlayer.getMCP() + "\n");
		List<Cell> path = optimalPlayer.getPath();

		// Save path
		for(int i = 0; i < path.size(); ++i) {
			bw.write(path.get(i).toString());
			if(i + 1 < path.size()) bw.write("->");
		}
		bw.close();
	}

	/**
	 * Main routine
	 * @param args - command line arguments
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		File inputFile = new File("./sampleInput.txt");
		File outputFile = new File("./sampleOut.txt");

		if (args.length > 0) {
			inputFile = new File(args[0]);
		}

		if (args.length > 1) {
			outputFile = new File(args[1]);
		}


		// Read the configures of the map and pokemons from the file inputFile
		// and output the results to the file outputFile
		Game game = new Game();
		game.initialize(inputFile);
		// If map is valid -> try to find path and save the result
		if (game.map != null) {
			game.findPath();
			System.out.println("Done");
			game.saveRoute(outputFile);
		}
	}

}
