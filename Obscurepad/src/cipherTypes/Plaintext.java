package cipherTypes;

public class Plaintext extends CipherType {

	public Plaintext() {
		
	}
	
	@Override
	public int getKeySize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIVSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] encrypt(byte[] plainText) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] decrypt(byte[] cipherText) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "None";
	}

	@Override
	public byte[] getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKey(byte[] key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getIv() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIv(byte[] iv) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCipherMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCipherMode(String cipherMode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyKey() {
		// TODO Auto-generated method stub
		
	}

}
