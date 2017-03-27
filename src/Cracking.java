import javax.crypto.SecretKey;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cracking {
	
	static int m_position;
	
	public int crackingProcess() throws Exception {
		// Choose random puzzle
		String puzzle = randomPuzzle();
		System.out.println("Cracking puzzle: " + puzzle);
		String crackedPuzzle = crack(puzzle);
		
		byte[] puzzleNoBytes = extractPuzzleNo(crackedPuzzle);
		
		String plaintextPuzzleNo = CryptoLib.byteArrayToString(puzzleNoBytes);
		
		int puzzleNumber = CryptoLib.byteArrayToSmallInt(puzzleNoBytes);
		
		System.out.println("Plaintext Puzzle Number: " + plaintextPuzzleNo + " | Actual Number: " + puzzleNumber);
		
		byte[] keyBytes = extractKey(crackedPuzzle);
		
		String plaintextKey = CryptoLib.byteArrayToString(keyBytes);
		
		System.out.println("Plaintext Key: " + plaintextKey);
		
		return puzzleNumber;
	}
	
	public static String crack(String puzzle) throws Exception {
		ArrayList<String> plaintextPuzzles = new ArrayList<String>();
		Set<String> cleanDuplicates = new HashSet<>();
		String crackedPuzzle = "N/A";
		
		for (int i=0; i<Math.pow(2, 16); i++) {
			byte[] keyBits = CryptoLib.smallIntToByteArray(i);
			
			byte[] zeros = new byte[6];
			Arrays.fill(zeros, (byte) 0);
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			stream.write(keyBits);
			stream.write(zeros);
			byte[] key = stream.toByteArray();
			
			try {
				byte[] decryptedPuzzle = new DES().decrypt(puzzle, CryptoLib.createKey(key));
				
				
				plaintextPuzzles.add(CryptoLib.byteArrayToString(decryptedPuzzle));
			} catch (Exception e) {
				// Invalid key
			}
			
			
		}
		
		cleanDuplicates.addAll(plaintextPuzzles);
		plaintextPuzzles.clear();
		plaintextPuzzles.addAll(cleanDuplicates);
		//System.out.println(plaintextPuzzles);
	
		for (String elem: plaintextPuzzles) {
			if (elem.subSequence(0, 15).equals("AAAAAAAAAAAAAAA")) {
				crackedPuzzle = elem;
				System.out.println("Cracked plaintext: " + elem);
				//System.out.println("Position of puzzle: " + m_position);
			}
		}
		
		return crackedPuzzle;
	}
	
	public static byte[] extractKey(String puzzle)  {
		byte[] puzzleBytes = CryptoLib.stringToByteArray(puzzle);
		
		return Arrays.copyOfRange(puzzleBytes, 18, 26);
	}
	
	public static byte[] extractPuzzleNo(String puzzle) {
		byte[] puzzleBytes = CryptoLib.stringToByteArray(puzzle);
		
		return Arrays.copyOfRange(puzzleBytes, 16, 18);
	}
	
	public static String randomPuzzle() throws IOException {
		String[] puzzles = readPuzzleFile("puzzles.txt");
		m_position = (int)(Math.random() * puzzles.length);
		String puzzle = puzzles[m_position];
		return puzzle;
	}
	
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
