package pd;

import java.util.Base64;
import java.util.Formatter;

public class ByteConv {
	public static String toBase64(byte[] bytes) {
		return new String(Base64.getEncoder().encode(bytes));
	}
	
	
	public static String toHex(byte[] bytes) {
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
		    formatter.format("%02X", b);
		}
		return formatter.toString();
	}
}
