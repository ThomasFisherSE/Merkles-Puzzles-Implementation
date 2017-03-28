import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class to generate puzzles according to the Merkle's Puzzles 
 * cryptographic approach
 * @author Thomas Fisher, James Parry-Turner
 */
public class PuzzleGenerator {
	public final String OUTPUT_FILE = "puzzles.txt";
	public final int NUMBER_OF_PUZZLES = 1024;
	public final int NUMBER_OF_BITS = 16;
	
	private Puzzle[] m_puzzles = new Puzzle[NUMBER_OF_PUZZLES - 1];
	
	/**
	 * Runs through the puzzle generation process to generate 1024 puzzles
	 * using the Merkle's puzzles cryptographic approach, and then write them
	 * to a text file.
	 * @throws Exception
	 */
	public void puzzleGenerationProcess() throws Exception {
		// Clear / Create text file
 	   PrintWriter clear = new PrintWriter(OUTPUT_FILE);
 	   clear.close();
 	   
 	   // Generate puzzles
 	   generatePuzzles();
	}
	
	/**
	 * Generates a puzzle based on a random unique puzzle
	 * @throws Exception
	 */
	public void generatePuzzles() throws Exception {
		ArrayList<Integer> usedNumbers = new ArrayList<Integer>();
		
		for (int i = 0; i < m_puzzles.length; i++) {
			int puzzleNo = new Random().nextInt((int) Math.pow(2, NUMBER_OF_BITS));
			
			if (usedNumbers.contains(puzzleNo)) {
				i--; // Repeat the loop again
			} else {
				// Add to the list of used numbers
				usedNumbers.add(puzzleNo);
				
				// Create a puzzle based on the puzzle number and write it to file
				m_puzzles[i] = new Puzzle(puzzleNo);
				writeToFile(m_puzzles[i]);
			}
		}
	}
	
	/**
	 * Get the key of a puzzle in m_puzzles using the puzzle number
	 * @param Puzzle number converted to string
	 * @return The key part of that particular puzzle
	 */
	public byte[] getKey(String puzzleNo) {	
		// Search through the m_puzzles array to find a puzzle with a matching puzzle number
		for (Puzzle elem: m_puzzles) {
			if (elem.getPuzzleNo() == CryptoLib.byteArrayToSmallInt(CryptoLib.stringToByteArray(puzzleNo))) {
				return elem.getKey();
			}
		}
		
		// If the puzzle with that puzzle number was not found, return null
		return null;
	}
		
	/**
	 * Write a puzzle object to a text file
	 * @param The puzzle object to be printed
	 * @throws IOException
	 */
	public void writeToFile(Puzzle puzzle) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_FILE, true));
		out.println(puzzle.toString());
		out.close();
	}
}
