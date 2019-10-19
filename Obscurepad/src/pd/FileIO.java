package pd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;

import javax.crypto.BadPaddingException;

import cipherTypes.CipherType;

public class FileIO {
	public static void saveFile(String fileName, CipherType encMode, String plaintext) {
		//hash the plaintext and prepend to plaintext
		//char[] beginning = new char[32];
		//plaintext.getChars(0, 32, beginning, 0);
		String beginningHash = new String(SHA256.hash(plaintext));
		
		String toEncrypt = beginningHash + plaintext;
		//System.out.println("Hashing: \"" + new String(beginning) + "\"");
		
		
		byte[] ciphertext = null;
		try {
			ciphertext = encMode.encrypt(toEncrypt.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(fileName);
			outputStream.write(encMode.getIv());
			outputStream.write(ciphertext);
		    outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		//encrypt plaintext
		//write IV and ciphertext to file
		
		OutputStream os = null;
		try {
			os = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			os.write(encMode.getIv());
			os.write(ciphertext);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static String readFile(String filename, CipherType encMode) {
		File file = new File(filename);
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Get IV from file -> iv
		byte[] iv = new byte[encMode.getIVSize()];
		System.arraycopy(fileContent, 0, iv, 0, iv.length);
		encMode.setIv(iv);
		
		//Get cipherText from file -> cipherText
		byte[] cipherText = new byte[fileContent.length - iv.length];
		System.arraycopy(fileContent, iv.length, cipherText, 0, cipherText.length);
		
		
		//Decrypt the ciphertext -> plainText
		byte[] plainText = null;
		try {
			plainText = encMode.decrypt(cipherText);
		} catch (Exception e) {
			return null;
		}

		
		//Check if the hash of the plaintext matches the one stored in the file
		byte[] beginning = new byte[plainText.length - 32];
		System.arraycopy(plainText, 32, beginning, 0, beginning.length);
		
		byte[] beginningHash = SHA256.hash(new String(beginning));
		
		byte[] beginningHashInFile = new byte[32];
		System.arraycopy(plainText, 0, beginningHashInFile, 0, beginningHashInFile.length);
		
		if (!Arrays.equals(beginningHashInFile, beginningHash)) {
			return null;
		}
		
		
		//Return the plaintext
		byte[] originalText = new byte[plainText.length - 32];
		System.arraycopy(plainText, 32, originalText, 0, originalText.length);
		
		
		return new String(originalText);
	}
}
