package socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SocketUtil {
	
	private static Logger log = Logger.getLogger(SocketUtil.class.getName());
	
	public static String connect(String host, int port, String message, String charSet, int closeTimeMill) throws Exception {
		Socket socket = null;
		DataOutputStream out = null;
		BufferedReader in = null;
		
		try {
			socket = new Socket(host, port);		
			socket.setSoTimeout(closeTimeMill);
			
			out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			out.write(message.getBytes(charSet));
			out.flush();	
		
			return  in.readLine().trim();
			
		} finally {
			if (in != null)	in.close();
			if (out != null) out.close();	
			if (socket != null)	socket.close();
		}
	}
}
