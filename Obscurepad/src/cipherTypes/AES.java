package cipherTypes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.DestroyFailedException;

public class AES extends CipherType {
    private SecretKeySpec key;
    private IvParameterSpec iv;
    private String cipherMode;
    
    private int keySize = 32;
    private int IVSize = 16;
    private String name = "AES";
    public int getKeySize() { return keySize;	}
    public int getIVSize() { return IVSize; }
	public String toString() { return name; }
    
    private static final String ALGORITHM="AES";
    
    

    public AES() {
    	
    }

	public String getCipherMode() {
		return cipherMode;
	}
	public void setCipherMode(String cipherMode) {
		this.cipherMode = cipherMode;
	}
	
	public AES(byte[] key, byte[] iv, String cipherMode) {
        this.key = new SecretKeySpec(key,ALGORITHM);
        this.iv = new IvParameterSpec(iv);
        this.cipherMode = cipherMode;
    }

    public byte[] encrypt(byte[] plainText) throws BadPaddingException {
        Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES/" + cipherMode + "/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE,key,iv);
	        return cipher.doFinal(plainText);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			// Never happens
			e.printStackTrace();
		}
		return null;
        
    }
    
    public byte[] decrypt(byte[] cipherText) throws Exception {
        Cipher cipher=Cipher.getInstance("AES/" + cipherMode + "/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,key,iv);
        return cipher.doFinal(cipherText);
    }
    
    public void destroyKey() {
    	this.key = null;

    }
    
    
    

    public byte[] getKey() {
        return key.getEncoded();
    }

    public void setKey(byte[] key) {
    	this.key= new SecretKeySpec(key,ALGORITHM);
    }

    public byte[] getIv() {
        return iv.getIV();
    }

    public void setIv(byte[] iv) {
    	this.iv = new IvParameterSpec(iv);
    }
}
