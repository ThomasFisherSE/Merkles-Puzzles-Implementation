import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import javax.crypto.SecretKey;

/**
 * A class that will generate a single puzzle
 * @author Thomas Fisher, James Parry-Turner
 */

public class Puzzle {
	
	private int m_number;
	private SecretKey m_keyPart;
	private SecretKey m_encryptionKey;
	private byte[] m_encryptedPuzzle;
	private byte[] m_unencryptedPuzzle;
	private String m_cryptogram;
	private DES m_encryptor;
	
	/**
	 * A constructor that generates a puzzle by concatenating each byte 
	 * array together and then encrypting it with the DES encrypt method
	 * @param Integer number for unique puzzle number
	 * @throws Exception
	 */
	public Puzzle(int number)throws Exception {
		m_number = number;
		m_encryptor = new DES();
		
		m_encryptionKey = m_encryptor.generateRandomKey();
		
		// First 128 zero bits
		byte[] zeros = new byte[16];
		
		// 16 bit number part
		byte[] numberPart = CryptoLib.smallIntToByteArray(number);
		
		// 64 bit key part
		m_keyPart = m_encryptor.generateRandomKey();
		
		// Concatenate each part of the puzzle
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		stream.write(zeros);
		stream.write(numberPart);
		stream.write(m_keyPart.getEncoded());
		m_unencryptedPuzzle = stream.toByteArray();
		
		// Set last 48 bits of encryption key to 0s
		byte[] encryptKey = m_encryptionKey.getEncoded();
		Arrays.fill(encryptKey, 2, 8, (byte) 0);
		m_encryptionKey = CryptoLib.createKey(encryptKey);

		// Encrypt
		m_encryptedPuzzle = m_encryptor.encrypt(m_unencryptedPuzzle, m_encryptionKey);
		
		// Turn into plaintext
		m_cryptogram = CryptoLib.byteArrayToString(m_encryptedPuzzle);
	}
	
	/**
	 * Accessor method for puzzle number
	 * @return Integer puzzle number
	 */
	public int getPuzzleNo() {
		return m_number;
	}
	
	/**
	 * Accessor method to get the key part of the puzzle
	 * @return A byte array of the key
	 */
	public byte[] getKey() {
		return m_keyPart.getEncoded();
	}
	
	/**
	 * Give a meaningful way to display puzzle 
	 * @return A string of the puzzle
	 */
	public String toString() {
		return m_cryptogram;
		
	}
}
