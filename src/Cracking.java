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
	
	public static void main(String[] args) throws Exception {
		// Choose random puzzle
		String puzzle = randomPuzzle();
		System.out.println("Cracking puzzle: " + puzzle);
		crack(puzzle);
	}
	
	public static void crack(String puzzle) throws Exception {
		ArrayList<String> plaintextPuzzles = new ArrayList<String>();
		Set<String> cleanDuplicates = new HashSet<>();
		
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
		System.out.println(plaintextPuzzles);
	
		System.out.println("Done cracking");
	}
	
	public static String randomPuzzle() throws IOException {
		String[] puzzles = readPuzzleFile("puzzles.txt");
		return puzzles[(int)(Math.random() * puzzles.length)];
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
	
	public void crackPuzzles(String[] puzzles) throws Exception {
		for (String puzzle : puzzles) {
			crack(puzzle);
		}
	}
}
