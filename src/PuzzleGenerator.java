import java.io.*;
import java.security.NoSuchAlgorithmException;

public class PuzzleGenerator {
	
	private Puzzle[] m_puzzles = new Puzzle[1023];
	private final String OUTPUT_FILE = "puzzles.txt";
	
	public void generatePuzzles() throws Exception {
		Puzzle.generateEncryptionKey();
		
		for (int puzzleNo = 0; puzzleNo < m_puzzles.length; puzzleNo++) {
			m_puzzles[puzzleNo] = new Puzzle(puzzleNo);
			writeToFile(m_puzzles[puzzleNo]);
		}
	}
		
	public void writeToFile(Puzzle puzzle) throws Exception {
		PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_FILE, true));
		out.println(puzzle.toString());
		out.close();
	}
}
