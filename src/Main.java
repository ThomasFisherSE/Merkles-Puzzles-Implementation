public class Main { 
	
	public static final String DIVIDER = "************************************";
       public static void main(String [] args) throws Exception {
    	   DES des = new DES();
    	   
    	   // Part 1 & 2: Puzzle Generation and Storage
    	   System.out.println(DIVIDER + "\n Part 1 and 2:\n" + DIVIDER);
    	   
    	   PuzzleGenerator generator = new PuzzleGenerator();
    	   generator.puzzleGenerationProcess();
    	   System.out.println("Created puzzles.txt");
    	   
    	   System.out.println(DIVIDER + "\n Part 3:\n" + DIVIDER);
    	   
    	   // Part 3: Puzzle Cracking
    	   Cracking cracker = new Cracking();
    	   int crackedPuzzleNo = cracker.crackingProcess();
    	   
    	   System.out.println(DIVIDER + "\n Part 4:\n" + DIVIDER);
    	   
    	   // Part 4: Key lookup
           byte[] puzzleNo = CryptoLib.smallIntToByteArray(crackedPuzzleNo);
           byte[] key = generator.getKey(CryptoLib.byteArrayToString(puzzleNo));
           System.out.println("Given puzzle number " + crackedPuzzleNo + ", the key is: " + CryptoLib.byteArrayToString(key));
           
           // Part 5: Demonstrating Successful Key Exchange
           System.out.println(DIVIDER + "\n Part 5:\n" + DIVIDER);
           String message = "Hello there young padawan.";
           System.out.println("Message: " + message);
           
           String cyphertext = des.encrypt(message, CryptoLib.createKey(key));
           System.out.println("Encrypted Message: " + cyphertext);
           
           String decryptedMessage = des.decryptToString(cyphertext, CryptoLib.createKey(key));
           System.out.println("Decrypting this cyphertext gives: " + decryptedMessage);
       }
}
