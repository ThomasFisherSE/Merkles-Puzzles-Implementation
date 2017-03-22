import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Puzzle {
	private int m_number;
	private SecretKey m_keyPart;
	private SecretKey m_encryptionKey;
	private byte[] m_encryptedPuzzle;
	private String m_cryptogram;
	private DES m_encryptor;
	private byte[] m_unencryptedPuzzle;
	
	public Puzzle(int number) throws Exception {
		m_number = number;
		m_encryptor = new DES();
		
		m_encryptionKey = m_encryptor.generateRandomKey();
		
		// First 128 zero bits
		byte[] zeros = new byte[16];
		
		Arrays.fill(zeros, (byte) 0);
		
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
		
		// Print puzzle
		System.out.println(m_cryptogram);
	}
	
	public byte[] getKey() {
		return m_encryptionKey.getEncoded();
	}
	
	public String toString() {
		return m_cryptogram;
		
	}
}
