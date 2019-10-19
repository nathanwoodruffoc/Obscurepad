package pd;

import java.io.IOException;
import java.security.SecureRandom;

import cipherTypes.AES;
import cipherTypes.CipherType;
import cipherTypes.DES;

public class Test {
	public static void main(String[] args) throws IOException {
		byte[] message="A much longer message!!!!!   A much longer message!!!!!".getBytes();
		char[] password = "password".toCharArray();
		
		DES des = new DES();
		des.setCipherMode("CBC");
		des.genIv();
		des.deriveKey(password);
		
		
		

		

		
		try {
		    String result_enc = ByteConv.toBase64(des.encrypt(message));
		    String result_key = ByteConv.toHex(des.getKey());
		    String result_iv  = ByteConv.toBase64(des.getIv());
		    String result_dec = new String(des.decrypt(des.encrypt(message)));
		   
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
		FileIO.saveFile(fileName, (CipherType) des, new String(message), password);
		
		try {
			String result = FileIO.readFile(fileName, (CipherType) des, password);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
}
