import java.io.PrintWriter;

public class Main { 
       public static void main(String [] args) throws Exception {
    	   // Clear / Create text file
    	   PrintWriter clear = new PrintWriter(PuzzleGenerator.OUTPUT_FILE);
    	   clear.close();
    	   
    	   // Generate puzzles
           PuzzleGenerator gen = new PuzzleGenerator();
           gen.generatePuzzles();
           
       }
}
