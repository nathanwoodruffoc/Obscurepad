package pd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;

import cipherTypes.CipherType;
import cipherTypes.Plaintext;

public class FileIO {
	public static void saveFile(String fileName, CipherType cipher, String plaintext, char[] password) throws IOException {
		if (!cipher.getClass().equals(Plaintext.class)) {
			// Generate the key and IV
			cipher.deriveKey(password);
			cipher.genIv();
			
			
			//Hash the plaintext and prepend to plaintext - used to verify the password is correct
			byte[] pt = plaintext.getBytes(StandardCharsets.UTF_8);
			
			byte[] beginningHash = SHA256.hash(new String(pt));
			byte[] toEncrypt = new byte[pt.length + beginningHash.length];
			System.arraycopy(beginningHash, 0, toEncrypt, 0, beginningHash.length); //
			System.arraycopy(pt, 0, toEncrypt, beginningHash.length, pt.length);
			
			//System.out.println("Hashing: " + ByteConv.toHex(pt));
			//System.out.println("Saving pt hash:" + ByteConv.toHex(beginningHash));
			
			
			// Encrypt the plaintext
			byte[] ciphertext = null;
			try {
				ciphertext = cipher.encrypt(toEncrypt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Erase key from memory
		    cipher.destroyKey();
			
		    
		    String tag = "ENCRYPTED_WITH_OBSCUREPAD"; // Len 25
		    
		    
		    
			// Write IV, Ciphertext to the file
		    FileOutputStream outputStream;
			outputStream = new FileOutputStream(fileName);
			outputStream.write(tag.getBytes());
			outputStream.write(cipher.getIv());
			outputStream.write(ciphertext);
		    outputStream.close();
		    
		    
		} else {
			//write unencrypted
			FileOutputStream outputStream;
			outputStream = new FileOutputStream(fileName);
			outputStream.write(plaintext.getBytes());
		    outputStream.close();
		}
		
		
	}
	
	public static String readFile(String filename, CipherType cipher, char[] password) throws IOException {
		File file = new File(filename);
		byte[] raw = Files.readAllBytes(file.toPath());
		
		
		if (!cipher.getClass().equals(Plaintext.class)) {
			//Strip tag
			byte[] fileContent = new byte[raw.length - 25];
			System.arraycopy(raw, 25, fileContent, 0, fileContent.length);
			
			
			//Get IV from file -> iv
			byte[] iv = new byte[cipher.getIVSize()];
			System.arraycopy(fileContent, 0, iv, 0, iv.length);
			cipher.setIv(iv);
			
			//Get cipherText from file -> cipherText
			byte[] cipherText = new byte[fileContent.length - iv.length];
			System.arraycopy(fileContent, iv.length, cipherText, 0, cipherText.length);
			
			
			// Generate the key from the password
			cipher.deriveKey(password);
			
			//Decrypt the ciphertext -> plainText
			byte[] plainText = null;
			try {
				plainText = cipher.decrypt(cipherText);
			} catch (Exception e) {
				System.out.println("decrypting error");
				return null;
			}
			
			// Erase key from memory
		    cipher.destroyKey();
	
			
			//Check if the hash of the plaintext matches the one stored in the file
			byte[] beginning = new byte[plainText.length - 32];
			System.arraycopy(plainText, 32, beginning, 0, beginning.length);
			
			byte[] beginningHash = SHA256.hash(new String(beginning, StandardCharsets.UTF_8));
			//System.out.println("Hashing: " + ByteConv.toHex(beginning));
			//System.out.println("Opening pt hash:" + ByteConv.toHex(beginningHash));
			
			byte[] beginningHashInFile = new byte[32];
			System.arraycopy(plainText, 0, beginningHashInFile, 0, beginningHashInFile.length);
			
			if (!Arrays.equals(beginningHashInFile, beginningHash)) {
				System.out.println("hash didn't match");
				return null;
			}
			
			
			//Return the plaintext
			byte[] originalText = new byte[plainText.length - 32];
			System.arraycopy(plainText, 32, originalText, 0, originalText.length);
			
			
			return new String(originalText);
		} else {
			// read unencrypted
			return new String(raw);
		}
	}
	
	public static boolean isFileEncrypted(File file) throws IOException {
		FileInputStream fin = new FileInputStream(file);
		
		byte fileContent[] = new byte[25];
		fin.read(fileContent);
		fin.close();
	    
		String s = new String(fileContent);
	    
		return (s.equals("ENCRYPTED_WITH_OBSCUREPAD"));
	}
}
