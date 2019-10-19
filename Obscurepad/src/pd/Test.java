package pd;

import java.security.SecureRandom;

import cipherTypes.AES;
import cipherTypes.CipherType;

public class Test {
	public static void main(String[] args) {
		byte[] message="A much longer message!!!!!   A much longer message!!!!!".getBytes();
		
		// Hash the password
		String password = "password";
		byte[] key = SHA256.hash(password);
		
		// Generate the IV
		SecureRandom secureRandom = new SecureRandom();
		byte[] iv = new byte[16];
		secureRandom.nextBytes(iv);
		

		AES aes = new AES(key,iv,"CBC");
		try {
		    String result_enc = ByteConv.toBase64(aes.encrypt(message));
		    String result_key = ByteConv.toHex(key);
		    String result_iv  = ByteConv.toBase64(iv);
		    String result_dec = new String(aes.decrypt(aes.encrypt(message)));
		   
		    System.out.println("Encrypted:\t" + result_enc);
		    System.out.println("Key:      \t" + result_key);
		    System.out.println("IV:       \t" + result_iv);
		    System.out.println("Decrypted:\t" + result_dec);
		} catch(Exception e) {
		    e.printStackTrace();
		}
		
		//write to file
		//read from file and display
		String fileName = "C:\\stuff.txt";
		//FileStuff.saveFile(fileName, (CipherType) aes, new String(message));
		
		try {
			String result = FileStuff.readFile(fileName, (CipherType) aes);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
}
