package ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class FtpUtil {
	
	private int port;
	private String server;
	private String id;
	private String pass;
	private String ftpRootPath;
	private FTPClient ftp = null;

	private Logger log = Logger.getLogger(this.getClass().getName());
	
	public FtpUtil(String server, String id, String pass, int port, String ftpRootPath ) {
		this.server = server;
		this.id = id;
		this.pass = pass;
		this.port = port;
		this.ftpRootPath = ftpRootPath;
		ftp = new FTPClient();
	}
	
	/**
	 * connect
	 * @param ftpSavePath
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public boolean connect(String ftpSavePath, File file) throws Exception {
		makeDir(ftpSavePath);		
		if(workinfDir(ftpSavePath)) {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			if(uploadFile(file))
				return true;
		} else {
			return false;
		}
		return false;
	}
	
	public boolean login() throws Exception {
		ftp.connect(server, port);
		return ftp.login(id, pass);
	}
	
	public boolean workinfDir(String saveFtpDir) throws Exception {
		return ftp.changeWorkingDirectory(ftpRootPath + saveFtpDir);
	}
	
	public void makeDir(String saveFtpDir) throws Exception {
		String dirMove = "";
		if(!ftp.makeDirectory(saveFtpDir)) {
			StringTokenizer st = new StringTokenizer(saveFtpDir, "/");
			while(st.hasMoreTokens()) {
				String dir = (String)st.nextToken();
				ftp.mkd(dir);
				dirMove += dir + "/";
				ftp.changeWorkingDirectory(ftpRootPath + dirMove);
			}
		}
		ftp.changeWorkingDirectory(ftpRootPath + saveFtpDir);
	}
	
	public boolean uploadFile(File file) throws Exception  {
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		if(ftp.storeFile(file.getName(), fis)) {
			if(fis != null)	fis.close();
			return true;
		}			
		if(fis != null)	fis.close();
		return false;
	}
	
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String savePath, String ftpPath) throws Exception {
		
		log.info(" ## downloadFile ##");
		InputStream in = null;
		OutputStream out = null;
		try {
			File f = new File(savePath);
			in = ftp.retrieveFileStream(ftpPath);
			response.reset();
			String contentType = "Content-type: multipart/formed-data";
			response.setContentType(contentType);
			
			if (request.getHeader("User-Agent").indexOf("MSIE 5.5") > -1) {
				response.setHeader(
						"Content-Disposition",
						"filename=\"" + new String(f.getName().getBytes(), "UTF-8") + "\"");
			} else {
				response.setHeader(
						"Content-Disposition",
						"attachment; filename=\"" + new String(f.getName().getBytes(), "UTF-8") + "\"");
			}
			out = response.getOutputStream();
			byte[] dd = new byte[4096];
			int leng = 0;
			while ((leng = in.read(dd)) > 0) {
				out.write(dd, 0, leng);
			}
			out.flush();
		} finally {
			if (in != null) in.close();
			if (out != null) out.close();
		}
	}
	
	public void logout() throws Exception  {
		log.info(" ## ftp logout ##");
		ftp.logout();
	}
	
	public void disconnect() throws Exception {
		if(ftp != null && ftp.isConnected()) {
			ftp.disconnect();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
