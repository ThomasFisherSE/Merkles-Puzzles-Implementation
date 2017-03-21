import javax.crypto.SecretKey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cracking {
	
	public static void main(String[] args) throws Exception {
		// Choose random puzzle
		String puzzle = randomPuzzle();
		System.out.println("Cracking puzzle: " + puzzle);
		crack(puzzle);
	}
	
	public static void crack(String puzzle) throws Exception {
		DES encryptor = new DES();
	
		byte[] keyBytes = new byte[8];
		
		for (int i = 0; i < keyBytes.length; i++) {
			keyBytes[i] = 0;
		}
		
		for (int i = 0; i < keyBytes.length; i++) {
			for (int j = 0; j < 8; j++) {
				keyBytes[i] |= (byte) (1 << j);
				SecretKey key = CryptoLib.createKey(keyBytes);
				encryptor.decrypt(puzzle, key);
			}
		}
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
