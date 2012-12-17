/*
 * HttpConnectionUtil.java	1.0 2011/11/30
 * 
 */

package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * HTTP 연동 관련 UTIL을 정리 한 클래스 입니다.
 * org.apache.commons.httpclient를 사용 합니다.
 * 
 * @author 		takersk@paran.com
 * @version		1.0 2011/11/30
 */
public class HttpConnection {
	
	/**
	 * POST 방식 HTTP CONNECTION 사용
	 * @param url
	 * @param params
	 * @param charSet
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String getHttpPostDataString(String url, Map<String, String> params, String charSet) throws HttpException, IOException {
		
		PostMethod post = null;
		HttpConnectionManagerParams connectionParams = new HttpConnectionManagerParams();
		
		HttpConnectionManager manager = new SimpleHttpConnectionManager();
		manager.setParams(connectionParams);
		
		HttpClient client = new HttpClient(manager);
		post = new PostMethod(url);
		
		try {
			if(params != null && params.size() > 0) {
				NameValuePair nameValues[] = new NameValuePair[params.size()];
				
				int count = 0;				
				for(Map.Entry<String, String> entry : params.entrySet()) {
					nameValues[count] = new NameValuePair(entry.getKey(), entry.getValue());
					count++;
				}
				post.setRequestBody(nameValues);
				post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + charSet);
				post.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

				int result = client.executeMethod(post);
				
				if(result == 200)
					return post.getResponseBodyAsString();
			}
		} finally {
			post.releaseConnection();
		}
		return null;
	}
	
	/**
	 * POST 방식 HTTP CONNECTION 사용
	 * @param url
	 * @param params
	 * @param charSet
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static InputStream getHttpPostDataStream(String url, Map<String, String> params, String charSet) throws HttpException, IOException {
		
		PostMethod post = null;
		HttpConnectionManagerParams connectionParams = new HttpConnectionManagerParams();
		
		HttpConnectionManager manager = new SimpleHttpConnectionManager();
		manager.setParams(connectionParams);
		
		HttpClient client = new HttpClient(manager);
		post = new PostMethod(url);
		
		try {
			if(params != null && params.size() > 0) {
				NameValuePair nameValues[] = new NameValuePair[params.size()];
				
				int count = 0;				
				for(Map.Entry<String, String> entry : params.entrySet()) {
					nameValues[count] = new NameValuePair(entry.getKey(), entry.getValue());
					count++;
				}
				post.setRequestBody(nameValues);
				post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=" + charSet);
				post.getParams().setParameter(HttpMethodParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
				
				int result = client.executeMethod(post);
				
				if(result == 200)
					return post.getResponseBodyAsStream();
			}
		} finally {
			post.releaseConnection();
		}
		return null;
	}
	
	/**
	 * GET 방식 HTTP CONNECTION 사용
	 * @param url
	 * @param charSet
	 * @throws Exception
	 */
	public static void getHttpGetDataString(String url, String charSet) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer result = new StringBuffer();
		
		try {		
			conn = (HttpURLConnection)(new URL(url).openConnection());
			conn.setRequestMethod("GET");
			conn.setDefaultUseCaches(false);
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			
			if(conn.getResponseCode() == 200){
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName(charSet)));
				String tmp = "";
				while((tmp = br.readLine()) != null){
					result.append(tmp);
				}
			}
		} finally {
			if(conn != null) conn.disconnect();
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
			if(result != null) result.delete(0, result.length());
		}
	}
}
