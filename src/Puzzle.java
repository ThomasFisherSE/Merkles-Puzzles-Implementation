import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Puzzle {
	private int m_number;
	private SecretKey m_key;
	private SecretKey m_encryptionKey;
	private byte[] m_encryptedPuzzle;
	private String m_cryptogram;
	private DES m_encryptor;
	private byte[] m_unencryptedPuzzle;
	
	public Puzzle(int number) throws Exception {
		m_number = number;
		
		byte[] zeros = new byte[15];
		Arrays.fill(zeros, (byte) 0);
		
		byte[] numberInBytes = CryptoLib.smallIntToByteArray(number);
		
		m_encryptor = new DES();
		
		m_key = m_encryptor.generateRandomKey();
		byte[] encodedKey = m_key.getEncoded();
		
		Arrays.fill(encodedKey, 1, 7, (byte) 0);
		
		m_encryptionKey = CryptoLib.createKey(encodedKey);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		stream.write(zeros);
		stream.write(numberInBytes);
		stream.write(encodedKey);
		
		m_unencryptedPuzzle = stream.toByteArray();
		
		m_cryptogram = CryptoLib.byteArrayToString(m_unencryptedPuzzle);
		System.out.println(m_cryptogram);
	}
	
	public byte[] encrypt() throws Exception {
		DES desEncryptor = new DES();
		
		desEncryptor.encrypt(m_cryptogram, m_encryptionKey);
		
		return m_encryptedPuzzle;
		
	}
	
	public String toString() {
		return m_cryptogram;
		
	}
}
