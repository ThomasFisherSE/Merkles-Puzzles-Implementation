import java.io.*;
import java.security.NoSuchAlgorithmException;

public class PuzzleGenerator {
	
	private Puzzle[] m_puzzles = new Puzzle[1023];
	public static final String OUTPUT_FILE = "puzzles.txt";
	
	public void puzzleGenerationProcess() throws Exception {
		// Clear / Create text file
 	   PrintWriter clear = new PrintWriter(PuzzleGenerator.OUTPUT_FILE);
 	   clear.close();
 	   
 	   // Generate puzzles
 	   generatePuzzles();
	}
	
	public void generatePuzzles() throws Exception {
		for (int puzzleNo = 0; puzzleNo < m_puzzles.length; puzzleNo++) {
			m_puzzles[puzzleNo] = new Puzzle(puzzleNo);
			writeToFile(m_puzzles[puzzleNo]);
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
