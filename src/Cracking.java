import javax.crypto.SecretKey;

public class Cracking {
	public void crackPuzzle(Puzzle puzzle, SecretKey key) throws Exception {
		DES encryptor = new DES();
		
		encryptor.decrypt(puzzle.toString(), key);
	}
	
	public void readPuzzleFile(String filepath) {
		
	}
	
	public void crackPuzzles(int numberOfPuzzles, Puzzle[] puzzles) {
		
	}
}
