package pd;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	public static byte[] hash(String s) {
		MessageDigest digest;
		byte[] result = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-256");
			result = digest.digest(s.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
