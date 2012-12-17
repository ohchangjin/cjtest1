package compression;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**************************************************************
 * DESCRIPTION : ZIP파일 압축관련 UTIL
 *
 * AUTHOR : 이현근(takersk@paran.com)
 * DATE : 2010.06
 **************************************************************/
public class ZipUtils {
	private static Logger log = Logger.getLogger(ZipUtils.class.getName());
	
    /**
     * InputStream객체를 받아서 처리한다.
     * @param in
     * @param saveDir
     * @throws Exception
     */
    public static void unzip(InputStream in, String saveDir)  throws Exception {
        ZipInputStream zis = null;
        try {
        	zis = new ZipInputStream(in);
             while(true) {
        		 ZipEntry zipEntry = zis.getNextEntry();
        		 if(zipEntry != null) {
        			 File saveFile = new File(saveDir + zipEntry.getName());
	                 // DIR체크
	                 if(zipEntry.getName().indexOf("/") != -1) {
	                	 File makeDir = new File(saveDir + StringUtils.substringBeforeLast(zipEntry.getName(), "/") + "/");
	                	 if(!makeDir.exists())
	                	 	if(makeDir.mkdirs()){
	                	 		continue;
	                	 	}
	                 }
	                 // 생성
	                 write(zis, saveFile, zipEntry);
	                 zis.closeEntry();
        		 } else {
        			 break;
        		 }
        		 
             }
        } finally {
        	zis.close();
        }
    }
    
    public static void write(ZipInputStream zis, File saveFile, ZipEntry zipEntry) throws IOException {        
		FileOutputStream fos = new FileOutputStream(saveFile);
		int len = 0;
		byte[] temp = new byte[1024];
		 
		while((len = zis.read(temp)) != -1) {
			fos.write(temp, 0, len);
        }
        // close all the open streams
		if(fos != null) fos.flush();fos.close(); 
    }
}