/* Sources:
 * http://www.java2s.com/Code/Java/Security/TripleDES.htm
 * http://www.javafaq.nu/java-example-code-191.html
 * http://stackoverflow.com/questions/18228579/how-to-create-a-secure-random-aes-key-in-java
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class CryptographicAlgorithms {
	public static void main(String[] args){
		try {
			secretKeyGenerator("DES", "deskey.dat", 0);
			secretKeyGenerator("DESede", "3deskey.dat", 0);
			secretKeyGenerator("AES", "AES128key.dat", 128);
			secretKeyGenerator("AES", "AES192key.dat", 192);
			secretKeyGenerator("AES", "AES256key.dat", 256);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// encrypts/decrypts plaintext/ciphertext using caesar cipher and given key
	public String caesarSubstitution(String plaintext, String keyfile, int mode) throws FileNotFoundException{
		int key = 0;
		Scanner scanner = new Scanner(new File(keyfile));
		while(scanner.hasNextInt()){
			key = scanner.nextInt();
		}
		scanner.close();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		if(mode == 1){
			alphabet = new StringBuffer(alphabet).reverse().toString();
		}
		char[] plaintextArray = plaintext.toCharArray();
		String ciphertext = "";
		int location = 0;
		
		for(char element : plaintextArray){
			if(alphabet.indexOf(element) < 0){
				if(alphabet.toUpperCase().indexOf(element) < 0){
					ciphertext += element;
				}else{
					location = (alphabet.toUpperCase().indexOf(element) + key) % 26;
					ciphertext += alphabet.toUpperCase().charAt(location);
				}
			}else{
				location = (alphabet.indexOf(element) + key) % 26;
				ciphertext += alphabet.charAt(location);
			}	
		}
		return DatatypeConverter.printBase64Binary(ciphertext.getBytes());
	}
	
	// encrypts/decrypts plaintext/ciphertext using DES and given key
	public String DES(String message, String keyfile, int mode) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		Cipher desCipher;
		byte[] output = null;
		desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		
		// open and convert secret key to byte array
		FileInputStream input = new FileInputStream(keyfile);
		int size = input.available();
		byte[] keyByte = new byte[size];
		input.read(keyByte);
		input.close();
				
		DESKeySpec keyspec = new DESKeySpec(keyByte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		Key desKey = keyFactory.generateSecret(keyspec);
				
		if(mode == 0){
			desCipher.init(Cipher.ENCRYPT_MODE, desKey);
			output = desCipher.doFinal(message.getBytes());
		}else if(mode == 1){ 
			desCipher.init(Cipher.DECRYPT_MODE, desKey);
			output = desCipher.doFinal(DatatypeConverter.parseBase64Binary(message));
		}
				
		return DatatypeConverter.printBase64Binary(output);
	}
		
	// encrypts/decrypts plaintext/ciphertext using triple DES and given key
	public String TripleDES(String message, String keyfile, int mode) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		Cipher tripleDesCipher;
		tripleDesCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		byte[] output = null;

		// open and convert secret key to byte array
		FileInputStream input = new FileInputStream(keyfile);
		int size = input.available();
		byte[] keyByte = new byte[size];
		input.read(keyByte);
		input.close();
			
		DESedeKeySpec keyspec = new DESedeKeySpec(keyByte);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		Key tripleDesKey = keyFactory.generateSecret(keyspec);
				
		if(mode == 0){
			tripleDesCipher.init(Cipher.ENCRYPT_MODE, tripleDesKey);
			output = tripleDesCipher.doFinal(message.getBytes());
		}else if(mode == 1){ 
			tripleDesCipher.init(Cipher.DECRYPT_MODE, tripleDesKey);
			output = tripleDesCipher.doFinal(DatatypeConverter.parseBase64Binary(message));
		}
		
		return DatatypeConverter.printBase64Binary(output);
	}
	
	// encrypts/decrypts plaintext/ciphertext using triple DES and given key
	public String AES(String message, String keyfile, int mode) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		Cipher AESCipher;
		AESCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		byte[] output = null;

		// open and convert secret key to byte array
		FileInputStream input = new FileInputStream(keyfile);
		int size = input.available();
		byte[] keyByte = new byte[size];
		input.read(keyByte);
		input.close();
			
		SecretKey keyspec = new SecretKeySpec(keyByte, "AES");
						
		if(mode == 0){
			AESCipher.init(Cipher.ENCRYPT_MODE, keyspec);
			output = AESCipher.doFinal(message.getBytes());
		}else if(mode == 1){ 
			AESCipher.init(Cipher.DECRYPT_MODE, keyspec);
			output = AESCipher.doFinal(DatatypeConverter.parseBase64Binary(message));
		}
		
		return DatatypeConverter.printBase64Binary(output);
	}
	
	// writes a secret key to file
	public static void secretKeyGenerator(String algType, String filename, int length) throws IOException, NoSuchAlgorithmException{
		FileOutputStream output = new FileOutputStream(filename);
		// instantiate key generator depending on algorithm type
		KeyGenerator keygen = KeyGenerator.getInstance(algType);
		
		// used for different key sizes in AES
		if(length != 0){
			keygen.init(length);
		}
		
		// generate secret key from key generator instance
		SecretKey secretKey = keygen.generateKey();
		System.out.println(secretKey.getEncoded());
		System.out.println(secretKey.getEncoded().length);
		output.write(secretKey.getEncoded());
		output.close();
	}

	// psuedo-random key generator used for client/server communication
	public static String psuedoRandomString(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
}
