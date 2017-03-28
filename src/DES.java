import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES {

	static Cipher cipher;
	
	/**
	  * Constructor
	  * Sets cipher to an AES cipher
	  */
	public DES() throws Exception {
		cipher = Cipher.getInstance("DES");
		
	}

	
	/**
	  * Encrypts a plaintext string into cyphertext using a secret key
	  * @return The cyphertext produced by encrypting plaintext using AES with the secret key
	  */
	public String encrypt(String plainText, SecretKey secretKey) throws Exception {
		
		//Convert plaintext into byte representation
		byte[] plainTextByte = plainText.getBytes();
		
		//Initialise the cipher to be in encrypt mode, using the given key.
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		
		//Perform the encryption
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		
		//Get a new Base64 (ASCII) encoder and use it to convert ciphertext back to a string
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		
		return encryptedText;
	}
	
	/**
	  * Encrypts a plaintext string into cyphertext using a secret key
	  * @return The cyphertext produced by encrypting plaintext using AES with the secret key
	  */
	public byte[] encrypt(byte[] input, SecretKey secretKey) throws Exception {
		
		//Initialise the cipher to be in encrypt mode, using the given key.
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		
		//Perform the encryption
		byte[] encryptedByte = cipher.doFinal(input);
		
		return encryptedByte;
	}

	/**
	  * Decrypts a cyphertext string into plaintext using a secret key
	  * @return The plaintext produced from decrypting cyphertext using AES with a secret key
	  */
	public String decryptToString(String encryptedText, SecretKey secretKey)
			throws Exception {
		byte[] decryptedByte = decrypt(encryptedText, secretKey);
		
		//Convert byte representation of plaintext into a string
		String decryptedText = new String(decryptedByte);
		
		return decryptedText;
	}
	
	public byte[] decrypt(String encryptedText, SecretKey secretKey)
			throws Exception {
		//Get a new Base64 (ASCII) decoder and use it to convert ciphertext from a string into bytes
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		
		//Initialise the cipher to be in decryption mode, using the given key.
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		//Perform the decryption
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);

		return decryptedByte;
	}
	
	/**
	  * Generates a random key using a random number generator
	  * @return Randomly generated key
	  */
	public SecretKey generateRandomKey() throws NoSuchAlgorithmException{
		//Use java's key generator to produce a random key.
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init(56);
		SecretKey secretKey = keyGenerator.generateKey();

		return secretKey;
	}
	
	/**
	  * Generates a key from a specified password
	  * @return Key generated from password
	  */
	public SecretKey generateKeyFromPassword(String password) throws Exception{
	
		//Get byte representation of password.
		//Note here you should ideally also use salt!
		byte[] passwordInBytes = (password).getBytes("UTF-8");
		
		//Use sha to generate a message digest of the password
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		byte[] key = sha.digest(passwordInBytes);
		
		//AES keys are only 128bits (16 bytes) so take first 128 bits of digest.		
		key = Arrays.copyOf(key, 8); 

		//Generate secret key using
		SecretKeySpec secretKey = new SecretKeySpec(key, "DES");
		
		//print the key
		String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		System.out.println(encodedKey);
		
		return secretKey;
	}
	
}