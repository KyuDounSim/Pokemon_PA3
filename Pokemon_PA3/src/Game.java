import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class Game {
	
	public void initialize(File inputFile) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		
		// Read the first of the input file
		String line = br.readLine();
		int M = Integer.parseInt(line.split(" ")[0]);
		int N = Integer.parseInt(line.split(" ")[1]);
		
		// TODO: define a map
		
		// Read the following M lines of the Map
		for (int i = 0; i < M; i++) {
			line = br.readLine();
			
			// TODO: Read the map line by line
		}
		
		// TODO:
		// Find the number of stations and pokemons in the map 
		// Continue read the information of all the stations and pokemons by using br.readLine();
		
		br.close();
	}
	
	public static void main(String[] args) throws Exception{
		File inputFile = new File("./sampleInput.txt");
		File outputFile = new File("./sampleOut.txt");
		
		if (args.length > 0) {
			inputFile = new File(args[0]);
		} 

		if (args.length > 1) {
			outputFile = new File(args[1]);
		}
		
		// TODO:
		// Read the configures of the map and pokemons from the file inputFile
		// and output the results to the file outputFile
	}
}
