import java.security.NoSuchAlgorithmException;

public class PuzzleGenerator {
	
	private Puzzle[] m_puzzles = new Puzzle[1023];
	
	public void generatePuzzles() throws Exception {
		for (int puzzleNo = 0; puzzleNo < m_puzzles.length; puzzleNo++) {
			m_puzzles[puzzleNo] = new Puzzle(puzzleNo);
		}
	}
	
	
}
