import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PuzzleGenerator {
	
	private Puzzle[] m_puzzles = new Puzzle[NUMBER_OF_PUZZLES - 1];
	public static final String OUTPUT_FILE = "puzzles.txt";
	public static final int NUMBER_OF_PUZZLES = 1024;
	
	public void puzzleGenerationProcess() throws Exception {
		// Clear / Create text file
 	   PrintWriter clear = new PrintWriter(PuzzleGenerator.OUTPUT_FILE);
 	   clear.close();
 	   
 	   // Generate puzzles
 	   generatePuzzles();
	}
	
	public void generatePuzzles() throws Exception {
		for (int i = 0; i < m_puzzles.length; i++) {
			int puzzleNo = new Random().nextInt((int) Math.pow(2, 16));
			m_puzzles[i] = new Puzzle(puzzleNo);
			writeToFile(m_puzzles[i]);
		}
	}
	
	public byte[] getKey(String puzzleNo) {	
		for (Puzzle elem: m_puzzles) {
			if (elem.getPuzzleNo() == CryptoLib.byteArrayToSmallInt(CryptoLib.stringToByteArray(puzzleNo))) {
				return elem.getKey();
			}
		}
		
		return null;
	}
		
	public void writeToFile(Puzzle puzzle) throws Exception {
		PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_FILE, true));
		out.println(puzzle.toString());
		out.close();
	}
}
