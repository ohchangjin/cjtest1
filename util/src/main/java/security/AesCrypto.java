package security;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * AES 암/복호화 관련 UTIL입니다.
 * @author leehyunkeun
 *
 */
public class AesCrypto {
		
	/**
	 * 암호화 > BYTE to HEX > BASE64
	 * @param msg
	 * @param key
	 * @param algorithm
	 * @return
	 */
	public static String getStrEncryptToBase64(String msg, String key, String algorithm) throws Exception {
		Key secureKey = generateKey("AES", key.getBytes());
		
		Cipher cipher = Cipher.getInstance(algorithm);
		
		// 0. CBC 일때 사용
		//byte [] iv = KEY.getBytes();
		//cipher.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv));
		
		// 0. ECB 일때 사용
		cipher.init(Cipher.ENCRYPT_MODE, secureKey);
		
		// 1. 암호화
		byte[] data = cipher.doFinal(msg.getBytes());
		// 2. byte to hex
		String byteArrayToHex = byteArrayToHex(data);
		System.out.println("암호화 > hex : "+byteArrayToHex);
		
		// 3. hex to base64
		return Base64.encode(byteArrayToHex.getBytes());
	}
	
	/**
	 * BASE64 > HEX to BYTE > 복호화
	 * @param msg
	 * @param key
	 * @param algorithm
	 * @return
	 */
	public static String getStrDncryptToBase64(String msg, String key, String algorithm) throws Exception  {		
		Key secureKey = generateKey("AES", key.getBytes());
		
		Cipher cipher = Cipher.getInstance(algorithm);
		
		// CBC
		//byte [] iv = KEY.getBytes();
		//cipher.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv));
		
		// ECB
		cipher.init(Cipher.DECRYPT_MODE, secureKey);			
		
		// 1. base64 decode
		byte[] decode = Base64.decode(msg);
		String orignStr = new String(decode);
		System.out.println("base 64 decode : "+orignStr);
		
		// 2. hex to byte
		byte[] hexToByteArray = hexToByteArray(orignStr);
		
		// 3. 복호화
		byte[] data = cipher.doFinal(hexToByteArray);
		return new String(data);
	}
	
	/**
	 * KEY 생성
	 * @param algorithm
	 * @param keyData
	 * @return
	 */
	public static Key generateKey(String algorithm, byte[] keyData) {		 
		SecretKeySpec keySpec = new SecretKeySpec(keyData, algorithm);
		return keySpec;
	}

	/**
	 * hex to byte[]
	 * @param hex
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {
	    if (hex == null || hex.length() == 0) {
	        return null;
	    }
	 
	    byte[] ba = new byte[hex.length() / 2];
	    for (int i = 0; i < ba.length; i++) {
	    	ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return ba;
	}
	 
	/**
	 * byte[] to hex
	 * @param ba
	 * @return
	 */
	public static String byteArrayToHex(byte[] ba) {
	    if (ba == null || ba.length == 0) {
	        return null;
	    }
	 
	    StringBuffer sb = new StringBuffer(ba.length * 2);
	    String hexNumber;
	    for (int x = 0; x < ba.length; x++) {
	        hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	    }
	    return sb.toString();
	}
	
	/**
	 * @param args
	원문 : TEST_MiGm2zFacU
	암호화 > hex : 6693a4803807067d7d93edbf6a7e3b1a
	base64 encode : NjY5M2E0ODAzODA3MDY3ZDdkOTNlZGJmNmE3ZTNiMWE=
	urlencode(UTF-8) : NjY5M2E0ODAzODA3MDY3ZDdkOTNlZGJmNmE3ZTNiMWE%3D
	urldecode(UTF-8) : NjY5M2E0ODAzODA3MDY3ZDdkOTNlZGJmNmE3ZTNiMWE=
	base 64 decode : 6693a4803807067d7d93edbf6a7e3b1a
	복호화 : TEST_MiGm2zFacU
	 */
	public static void main(String[] args) {
		String KEY = "8cc8f86e04c04636";
		//String ALGORITHM = "AES/CBC/PKCS5Padding";
		String ALGORITHM = "AES/ECB/PKCS5Padding";
		String msg = "TEST_MiGm2zFacU";
		
		try {
			System.out.println("원문 : "+msg);
			msg = AesCrypto.getStrEncryptToBase64(msg, KEY, ALGORITHM);
			System.out.println("base64 encode : "+msg);
			msg = java.net.URLEncoder.encode(msg, "UTF-8");
			System.out.println("urlencode(UTF-8) : "+msg);
			msg = java.net.URLDecoder.decode(msg, "UTF-8");
			System.out.println("urldecode(UTF-8) : "+msg);
			msg = AesCrypto.getStrDncryptToBase64(msg, KEY, ALGORITHM);
			System.out.println("복호화 : "+msg);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
