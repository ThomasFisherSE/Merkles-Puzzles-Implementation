import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A collection of methods used for cracking Merkle's puzzles
 * @author Thomas Fisher, James Parry-Turner
 */
public class Cracking {
	
	/**
	 * Runs through the cracking process to crack a random puzzle that was generated
	 * using the Merkle's puzzles cryptographic approach.
	 * @return The unique puzzle number of the cracked puzzle
	 * @throws IOException
	 */
	public int crackingProcess() throws IOException {
		// Choose random puzzle
		String puzzle = randomPuzzle();
		System.out.println("Cracking puzzle: " + puzzle);
		
		// Crack the puzzle by brute force
		String crackedPuzzle = crack(puzzle);
		
		// Get the puzzle number in a byte array of the cracked puzzle
		byte[] puzzleNoBytes = extractPuzzleNo(crackedPuzzle);
		
		// Convert the puzzle number into an integer
		int puzzleNumber = CryptoLib.byteArrayToSmallInt(puzzleNoBytes);
		
		// Print the puzzle number
		System.out.println("Puzzle Number: " + puzzleNumber);
		
		// Extract the key from the cracked puzzle
		byte[] keyBytes = extractKey(crackedPuzzle);
		
		// Convert the key from byte array to string
		String plaintextKey = CryptoLib.byteArrayToString(keyBytes);
		
		// Print the key
		System.out.println("Plaintext Key: " + plaintextKey);
		
		return puzzleNumber;
	}
	
	/**
	 * Crack a plaintext puzzle by brute force, trying all possible keys (2^16) until the correct key 
	 * is found. This is found by looking for the key which starts with 128 zero bits.
	 * @param puzzle, a string of a puzzle in plaintext that will be cracked
	 * @return Decrypted puzzle
	 * @throws IOException
	 */
	public static String crack(String puzzle) throws IOException {
		ArrayList<String> plaintextPuzzles = new ArrayList<String>();
		Set<String> cleanDuplicates = new HashSet<>(); // Set data-type used to remove duplicates
		String crackedPuzzle = "N/A";
		
		// For every possible key (2^16), try decrypting the puzzle
		for (int i=0; i<Math.pow(2, 16); i++) {
			// Get the byte array of the current index
			byte[] keyBits = CryptoLib.smallIntToByteArray(i);
			
			// Generate 6 bytes of zeros for the end of the key
			byte[] zeros = new byte[6];
			
			// Concatenate the key bits and the zero bits
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			stream.write(keyBits);
			stream.write(zeros);
			byte[] key = stream.toByteArray();
			
			// Try decrypting the puzzle
			try {
				byte[] decryptedPuzzle = new DES().decrypt(puzzle, CryptoLib.createKey(key));
				
				// If decrypting was successful, add the decrypted puzzle to the list of possible puzzles
				plaintextPuzzles.add(CryptoLib.byteArrayToString(decryptedPuzzle));
			} catch (Exception e) {
				// Invalid key
			}
		}
		
		// Remove duplicate puzzles
		cleanDuplicates.addAll(plaintextPuzzles);
		plaintextPuzzles.clear();
		plaintextPuzzles.addAll(cleanDuplicates);
	
		// Look for a puzzle with 128 zero bits at the start
		for (String elem: plaintextPuzzles) {
			byte[] puzzleBytes = CryptoLib.stringToByteArray(elem);
			
			byte[] zeros = new byte[16];
			byte[] zerosPart = Arrays.copyOfRange(puzzleBytes, 0, 16);
			
			if (Arrays.equals(zerosPart, zeros)) {
				// Select this puzzle as the cracked puzzle
				crackedPuzzle = elem;
				// Print the cracked puzzle
				System.out.println("Cracked plaintext: " + elem);
			}
		}
		
		return crackedPuzzle;
	}
	
	/**
	 * Extracts the key part from a plaintext puzzle by taking the last 8 bytes (64 bits)
	 * @param The string puzzle as plaintext to extract a key from
	 * @return The key part of the puzzle as a byte array
	 */
	public static byte[] extractKey(String puzzle)  {
		byte[] puzzleBytes = CryptoLib.stringToByteArray(puzzle);
		
		return Arrays.copyOfRange(puzzleBytes, 18, 26);
	}
	
	/**
	 * Extracts the unique puzzle number part from a plaintext puzzle by taking the 
	 * 2 bytes (16 bits) that hold the puzzle number
 	 * @param The string puzzle as plaintext to extract a key from
	 * @return The key part of the puzzle as a byte array
	 */
	public static byte[] extractPuzzleNo(String puzzle) {
		byte[] puzzleBytes = CryptoLib.stringToByteArray(puzzle);
		
		return Arrays.copyOfRange(puzzleBytes, 16, 18);
	}
	
	/**
	 * Runs through a file of string puzzles and extracts a random puzzle
	 * @return A random puzzle from puzzles.txt
	 * @throws IOException
	 */
	public static String randomPuzzle() throws IOException {
		String[] puzzles = readPuzzleFile("puzzles.txt");
		int pos = (int)(Math.random() * puzzles.length);
		String puzzle = puzzles[pos];
		return puzzle;
	}
	
	/**
	 * Reads through a file and puts the lines into an array
	 * @param The filepath as a string of the file to read
	 * @return An array of every line in a file
	 * @throws IOException
	 */
	public static String[] readPuzzleFile(String filepath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filepath));	
		List<String> lines = new ArrayList<String>();
        String line = null;
        
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        
        reader.close();
        
        return lines.toArray(new String[lines.size()]);
	}
}
