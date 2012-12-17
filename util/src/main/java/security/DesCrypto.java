package security;

import http.HttpConnection;

import java.util.Map;
import java.util.TreeMap;

import sun.misc.BASE64Decoder;

/**
 * PC Client 에서 사용 중인 decode모듈
 */
public class DesCrypto {
	char  m_szPwd[];
	short m_wData[];
	
	public DesCrypto(String strPwd)
	{
		m_wData = new short[21];
		m_szPwd = new char[16];
		for( int i=0 ; i<16 ; i++)
		{
			m_szPwd[i] = strPwd.charAt(i);
		}
	}

	void Calc()
	{
		m_wData[3] = (short) (m_wData[6] + m_wData[8]);
		m_wData[0] = m_wData[13 + m_wData[8]];
		m_wData[2] = 0x015a;
		m_wData[1] = 0x4e35;

		m_wData[5] = m_wData[0];
		m_wData[0] = m_wData[4];
		m_wData[4] = m_wData[5];

		m_wData[5] = m_wData[0];
		m_wData[0] = m_wData[3];
		m_wData[3] = m_wData[5];

		if (m_wData[0]!=0)
		{
			m_wData[0] = (short) (m_wData[0] * m_wData[1]);
		}

		m_wData[5] = m_wData[0];
		m_wData[0] = m_wData[2];
		m_wData[2] = m_wData[5];

		if (m_wData[0] != 0)
		{
			m_wData[0] = (short) (m_wData[0] * m_wData[4]);
			m_wData[2] = (short) (m_wData[0] + m_wData[2]);
		}

		m_wData[5] = m_wData[0];
		m_wData[0] = m_wData[4];
		m_wData[4] = m_wData[5];
		m_wData[0] = (short) (m_wData[0] * m_wData[1]);
		m_wData[3] = (short) (m_wData[2] + m_wData[3]);

		m_wData[0] = (short) (m_wData[0] + 1);

		m_wData[6] = m_wData[3];
		m_wData[13 + m_wData[8]] = m_wData[0];

		m_wData[7] = (short) (m_wData[0] ^ m_wData[3]);
		m_wData[8] = (short) (m_wData[8] + 1);
	}
	
	void Run()
	{
		m_wData[13 + 0] = (short) ((m_szPwd[0] * 256) + m_szPwd[1]);
		Calc ();
		m_wData[9] = m_wData[7];

		m_wData[13 + 1] = (short) (m_wData[13 + 0] ^ ((m_szPwd[2] * 256) + m_szPwd[3]));
		Calc ();
		m_wData[9] = (short) (m_wData[9] ^ m_wData[7]);

		m_wData[13 + 2] = (short) (m_wData[13 + 1] ^ ((m_szPwd[4] * 256) + m_szPwd[5]));
		Calc ();
		m_wData[9] = (short) (m_wData[9] ^ m_wData[7]);

		m_wData[13 + 3] = (short) (m_wData[13 + 2] ^ ((m_szPwd[6] * 256) + m_szPwd[7]));
		Calc ();
		m_wData[9] = (short) (m_wData[9] ^ m_wData[7]);

		m_wData[13 + 4] = (short) (m_wData[13 + 3] ^ ((m_szPwd[8] * 256) + m_szPwd[9]));
		Calc ();
		m_wData[9] = (short) (m_wData[9] ^ m_wData[7]);

		m_wData[13 + 5] = (short) (m_wData[13 + 4] ^ ((m_szPwd[10] * 256) + m_szPwd[11]));
		Calc ();
		m_wData[9] = (short) (m_wData[9] ^ m_wData[7]);

		m_wData[13 + 6] = (short) (m_wData[13 + 5] ^ ((m_szPwd[12] * 256) + m_szPwd[13]));
		Calc ();
		m_wData[9] = (short) (m_wData[9] ^ m_wData[7]);

		m_wData[13 + 7] = (short) (m_wData[13 + 6] ^ ((m_szPwd[14] * 256) + m_szPwd[15]));
		Calc ();
		m_wData[9] = (short) (m_wData[9] ^ m_wData[7]);

		m_wData[8] = 0;
	}
	
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
	 
	// byte[] to hex
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

	public String Encode (String str)
	{
		int nLen = str.length();
		String strOut = new String();
		
		char[] cOutData = new char[nLen];
		
		for (int i = 0; i < nLen; i++)
		{
			Run();

			m_wData[10] = (short) (m_wData[9] >> 0x08);
			m_wData[11] = (short) (m_wData[9] & 0xff);

			for (int j = 0; j < 16; j++)
			{
				m_szPwd[j] = (char)(m_szPwd[j]^str.charAt(i));
			}

			byte cData = (byte)(str.charAt(i) ^ (m_wData[10] ^ m_wData[11]));
			
			String strHex = String.format("%02X", cData);
			strOut += strHex;
		}
		return strOut;
	}
	
	public String Decode(String str)
	{
		int nLen = str.length();
		String strOut = new String();

		int nPos = 0;
		for (int i = 0; i < nLen; i+=2)
		{
			Run();

			m_wData[10] = (short) (m_wData[9] >> 0x08);
			m_wData[11] = (short) (m_wData[9] & 0xff);

			String strHex = str.substring( i, i+2);

	        byte [] data;
			data = hexToByteArray(strHex);
			byte bTest = data[0];
			byte cData = (byte)(bTest ^ (m_wData[10] ^ m_wData[11]));
			strOut += String.valueOf( (char)cData);

			for (int j = 0; j < 16; j++)
				m_szPwd[j] = (char) (m_szPwd[j]^strOut.charAt(nPos)) ;
			
			nPos++;
		}

		return strOut;
	}
	
	public static void main(String args[]) {
		try {
			String url = "http://api.book.playy.co.kr/api/exbuyCheckEncode.do";
			Map<String, String> params = new TreeMap<String, String>();
			params.put("user_no", "620010202700");
			params.put("domain", "kth");
			params.put("file_no", "369699");
			params.put("model_name", "");
			params.put("os_ver", "");
			
			String res_str = HttpConnection.getHttpPostDataString(url, params, "UTF-8");
			System.out.println("res_str:"+res_str);
			
			DesCrypto protect = new DesCrypto("34e37563de703919");
			byte[] message = new BASE64Decoder().decodeBuffer(res_str);
			System.out.println(protect.Decode(new String(message)));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

