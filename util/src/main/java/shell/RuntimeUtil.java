package shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RuntimeUtil {
	
	public static boolean exec(String cmd) throws Exception {
		BufferedReader br = null;
		
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String temp = null;
			while( (temp = br.readLine())!=null){
				if(temp != null && (temp.toLowerCase()).indexOf("error") != -1)
					return false;
			}
			return true;
		} catch(Exception e) {
			return false;
		} finally {
			if(br != null) br.close();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			RuntimeUtil.exec("su - k2 -c /home/k2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}