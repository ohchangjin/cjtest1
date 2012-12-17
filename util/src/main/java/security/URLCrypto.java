package security;

import java.io.UnsupportedEncodingException;

/**
 * URL 인코딩, 디코딩 관련 클래스 입니다.
 * @author leehyunkeun
 *
 */
public class URLCrypto {
	
	/**
	 * URL ENCODE
	 * @param str
	 * @param charSet
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getStrToURLEncode(String str, String charSet) throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(str, charSet);
	}
	
	/**
	 * URL DECODE
	 * @param str
	 * @param charSet
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getStrToURLDecode(String str, String charSet) throws UnsupportedEncodingException {
		return java.net.URLDecoder.decode(str, charSet);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			URLCrypto.getStrToURLDecode("http://m.book.playy.co.kr//getSearchMain.do?query=%EB%B2%A8%EB%B2%B3&mc", "UTF-8");
			URLCrypto.getStrToURLDecode("http://m.book.playy.co.kr//getSearchMain.do?query=%EB%B2%A8%EB%B2%B3&mc", "UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}