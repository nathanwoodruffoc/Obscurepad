package pd;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

public class Test {
	public static void main(String[] args) {
		byte[] message="A much longer message!!!!!   A much longer message!!!!!".getBytes();
		
		// Hash the password
		String password = "This is a string";
		MessageDigest digest;
		byte[] key = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-256");
			key = digest.digest(password.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// Generate the IV
		SecureRandom secureRandom = new SecureRandom();
		byte[] iv = new byte[16];
		secureRandom.nextBytes(iv);
		

		AesCBC aes=new AesCBC(key,iv);
		try {
		    String result_enc = DatatypeConverter.printBase64Binary(aes.encrypt(message));
		    String result_key = DatatypeConverter.printHexBinary(key);
		    String result_iv  = DatatypeConverter.printBase64Binary(iv);
		    String result_dec = new String(aes.decrypt(aes.encrypt(message)));
		    System.out.println("Encrypted:\t" + result_enc);
		    System.out.println("Key:      \t" + result_key);
		    System.out.println("IV:       \t" + result_iv);
		    System.out.println("Decrypted:\t" + result_dec);
		} catch(Exception e) {
		    e.printStackTrace();
		}
	}
}
