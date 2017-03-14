import java.io.*;
import java.security.NoSuchAlgorithmException;

public class PuzzleGenerator {
	
	private static Puzzle[] m_puzzles = new Puzzle[1023];
	private static final String OUTPUT_FILE = "puzzles.txt";
	
	public static void generatePuzzles() throws Exception {
		for (int puzzleNo = 0; puzzleNo < m_puzzles.length; puzzleNo++) {
			m_puzzles[puzzleNo] = new Puzzle(puzzleNo);
			writeToFile(m_puzzles[puzzleNo]);
		}
	}
		
	public static void writeToFile(Puzzle puzzle) throws Exception {
		PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_FILE, true));
		out.println(puzzle.toString() + '\n');
		out.close();
	}
	
	public static void main(String[] args) throws Exception {
		generatePuzzles();
	}
}
