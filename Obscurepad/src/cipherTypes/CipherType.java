package cipherTypes;

import java.security.SecureRandom;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class CipherType {
	public abstract int getKeySize();
	public abstract int getIVSize();
	public abstract byte[] encrypt(byte[] plainText) throws Exception;
	public abstract byte[] decrypt(byte[] cipherText) throws Exception;
	public abstract void destroyKey();
	public abstract String toString();
    
	public abstract byte[] getKey();
    public abstract void setKey(byte[] key);
   
    public abstract void deriveKey(char[] password);
    public void genIv() {
		// Generate the IV
		SecureRandom secureRandom = new SecureRandom();
		byte[] iv = new byte[getIVSize()];
		secureRandom.nextBytes(iv);
		setIv(iv);
    }
    
    public abstract byte[] getIv();
    public abstract void setIv(byte[] iv);
    public abstract String getCipherMode();
	public abstract void setCipherMode(String cipherMode);
	
    
}
