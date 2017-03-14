import java.security.NoSuchAlgorithmException;
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
		
		byte[] numberInBytes = CryptoLib.smallIntToByteArray(number);
		
		m_encryptor = new DES();
		
		m_key = m_encryptor.generateRandomKey(64);

		byte[] encodedKey = m_key.getEncoded();
		
		m_encryptionKey = CryptoLib.createKey(encodedKey);
	}
	
	public byte[] encrypt() throws Exception {
		DES desEncryptor = new DES();
		
		desEncryptor.encrypt(m_cryptogram, m_encryptionKey);
		
		return m_encryptedPuzzle;
		
	}
}
