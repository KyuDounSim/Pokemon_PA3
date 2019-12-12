import java.io.*;
import java.util.ArrayList;

public class Game {

    private map map;
    private Player player;
    private ArrayList<Pokemon> pokemonArrayList;
    private ArrayList<Station> stationArrayList;

    public void initialize(File inputFile) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(inputFile));

        // Read the first of the input file
        String line = br.readLine();
        int M = Integer.parseInt(line.split(" ")[0]);
        int N = Integer.parseInt(line.split(" ")[1]);
        int stationNumber = 0, pokemonNumber = 0;

        Cell[][] cell = new Cell[M][N];
//        boolean[][] boolMaps = new boolean[M][N];

        player = new Player(new Coordinate(0, 0));

        // Read the following M lines of the Map
        for (int i = 0; i < M; i++) {
            line = br.readLine();
            for (int j = 0; j < N; j++) {
                switch (line.charAt(j)) {
                    case '#':
                        cell[i][j] = new Wall(new Coordinate(i, j));
//                        boolMaps[i][j] = false;
                        break;
                    case ' ':
                        cell[i][j] = new WalkableCell(new Coordinate(i, j));
//                        boolMaps[i][j] = true;
                        break;
                    case 'P':
                        cell[i][j] = new Pokemon(new Coordinate(i, j));
//                        boolMaps[i][j] = true;
                        ++pokemonNumber;
                        break;
                    case 'S':
                        cell[i][j] = new Station(new Coordinate(i, j));
//                        boolMaps[i][j] = true;
                        ++stationNumber;
                        break;
                    case 'B':
                        cell[i][j] = new TermCell(new Coordinate(i, j), TermCell.Type.START);
//                        boolMaps[i][j] = true;
                        player = new Player(new Coordinate(i, j));
                        break;
                    case 'D':
                        cell[i][j] = new TermCell(new Coordinate(i, j), TermCell.Type.DESTINATION);
//                        boolMaps[i][j] = true;
                }
            }
        }

        pokemonArrayList = new ArrayList<>(pokemonNumber);
        stationArrayList = new ArrayList<>(stationNumber);

        // Find the number of stations and pokemons in the map
        // Continue read the information of all the stations and pokemons by using br.readLine();

        for (int i = 0; i < pokemonNumber; ++i) {
            line = br.readLine();
            String extracted = line.substring(line.indexOf('<'), line.lastIndexOf('>')).substring(1);

            int x_coord = Integer.parseInt(extracted.split(",")[0]);
            int y_coord = Integer.parseInt(extracted.split(",")[1]);

            String name = line.split(", ")[1], type = line.split(", ")[2];
            int combatPower = Integer.parseInt(line.split(", ")[3]), requiredBalls = Integer.parseInt(line.split(", ")[4]);

            cell[x_coord][y_coord] = new Pokemon(new Coordinate(x_coord, y_coord), name, type, combatPower, requiredBalls);

            pokemonArrayList.add((Pokemon) cell[x_coord][y_coord]);
        }

        for (int i = 0; i < stationNumber; ++i) {
            line = br.readLine();
            String extracted = line.substring(line.indexOf('<'), line.lastIndexOf('>')).substring(1);

            int x_coord = Integer.parseInt(extracted.split(",")[0]), y_coord = Integer.parseInt(extracted.split(",")[1]);

            int balls = Integer.parseInt(line.split(", ")[1]);

            cell[x_coord][y_coord] = new Station(new Coordinate(x_coord, y_coord), balls);
            stationArrayList.add((Station) cell[x_coord][y_coord]);
        }

        map = new map(M, N, cell);

        br.close();
    }

    public void findPath() {
        ArrayList<Cell> allCombination = new ArrayList<>(pokemonArrayList);
        allCombination.addAll(stationArrayList);
    }

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
        // and output the results to the file outputFiles
        Game game = new Game();
        game.initialize(inputFile);
        game.findPath();

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(game.player.score() + "\n");
        writer.write(game.player.printScoreElement() + "\n");
        writer.write(game.player.printPath() + "\n");

        writer.close();
    }
}
