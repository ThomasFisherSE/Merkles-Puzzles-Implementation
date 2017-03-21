import javax.crypto.SecretKey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Cracking {
	
	public void crackPuzzle(Puzzle puzzle, SecretKey key) throws Exception {
		DES encryptor = new DES();
		
		encryptor.decrypt(puzzle.toString(), key);
	}
	
	public Puzzle[] readPuzzleFile(String filepath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		
		//String[] cryptograms = new String[]
		
		String line;
		
		while (reader.readLine() != null) {
			
		}
		
		return null;
	}
	
	public void crackPuzzles(Puzzle[] puzzles) {
		for (int i = 0; i < puzzles.length; i++) {
			//crackPuzzle(puzzles[i], )
		}
	}
}
