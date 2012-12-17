package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

/**
 * JSON 
 * @author leehyunkeun
 *
 */
public class JsonUtil {
	static Logger log = Logger.getLogger(JsonUtil.class.getName());
	
	/**
	 * STREAM to JSON
	 * @param is
	 * @param charSet
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getStreamToJson(InputStream is, String charSet) throws Exception {
		BufferedReader br = null;	
		String temp = "";
		StringBuffer sb = null;
		
		try {
			br= new BufferedReader(new InputStreamReader(is, charSet));
			sb = new StringBuffer();
			while((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			JSONObject _o = (JSONObject)JSONSerializer.toJSON(sb.toString());
			return _o;
		} finally {
			if(br != null)
				br.close();
		}		
	}
	
	public static void main(String[] args) {
		try {
			JSONObject _o = JsonUtil.getStreamToJson(new URL("http://book.playy.co.kr/getThemaListAjax.do?_=1354773216724&page=1&thema_type=THE_WEB02&start_no=0&view_no=20&sub1=ALL&sub2=NEW").openStream(), "UTF-8");
			System.out.println(_o.get("result"));
			
			JSONArray arr = (JSONArray)_o.get("contentInfo");
			for(@SuppressWarnings("unchecked")
			Iterator<JSONObject> ite = arr.iterator(); ite.hasNext(); ) {
				JSONObject _sub = ite.next();
				System.out.println(_sub.get("a_yn"));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
