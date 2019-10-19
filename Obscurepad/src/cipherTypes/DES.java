package cipherTypes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DES extends CipherType {
	private SecretKeySpec key;
    private IvParameterSpec iv;
    private String cipherMode;
    
    private int keySize = 7;
    private int IVSize = 8;
    private String name = "DES";
    public int getKeySize() { return keySize;	}
    public int getIVSize() { return IVSize; }
	public String toString() { return name; }
    
    private static final String ALGORITHM="DES";
    
    



	public DES(byte[] key, byte[] iv, String cipherMode) {
        this.key= new SecretKeySpec(key,ALGORITHM);
        this.iv = new IvParameterSpec(iv);
        this.cipherMode = cipherMode;
    }

    public byte[] encrypt(byte[] plainText) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/" + cipherMode + "/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key,iv);
        return cipher.doFinal(plainText);
    }
    
    public byte[] decrypt(byte[] cipherText) throws Exception {
        Cipher cipher=Cipher.getInstance("DES/" + cipherMode + "/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,key,iv);
        return cipher.doFinal(cipherText);
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
    
	public String getCipherMode() {
		return cipherMode;
	}
	public void setCipherMode(String cipherMode) {
		this.cipherMode = cipherMode;
	}
}
