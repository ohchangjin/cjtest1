package string;

import java.security.MessageDigest;

/**
 * STRING 관련 유틸 정보
 * @author leehyunkeun
 *
 */
public class StringUtil {
	
	/**
	 * STRING to MessageDigest
	 * @param str
	 * @param algorithm
	 * @param charSet
	 * @return
	 * @throws Exception
	 */
	public static String getStringToMessageDigest(String str, String algorithm, String charSet) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
		return new String(messageDigest.digest(str.getBytes()), charSet);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
