package ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

public class FTPMultiUploadUtil {
	private static Log log = LogFactory.getFactory().getInstance(FTPMultiUploadUtil.class.getName());	
	private final static int _SUCCESS = 1;
	private final static int _MAX_SIZE_ERROR = 2;
	private final static int _EXTENSION_ERROR = 3;
	private final static int BUFFER_SIZE = 1024;		// output buffer size
	
	MultipartFile multipartFile = null;
	String realUploadPath = "";
	String uploadName = "";
	UUID randomUUID = null;
	
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	public void setRealUploadPath(String realUploadPath) {
		this.realUploadPath = realUploadPath;
	}
	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}
	public void setRandomUUID(UUID randomUUID) {
		this.randomUUID = randomUUID;
	}

	public int upload() throws Exception {
		log.debug("realPath : "+realUploadPath);
		
		// 기초정보를 얻는다.
		String originalFileName = multipartFile.getOriginalFilename();
		long fileSize = multipartFile.getSize();

		// 파일 사이즈가 5메가 이상 혹은 0이나 0보다 작은지 판단한다.
		if (fileSize > (1000 * 1024 * 1024) || fileSize <= 0) {
			return _MAX_SIZE_ERROR;
		}

		// 파일의 확장자가 적당한지 판단한다.
		// 확장자가 없는 경우, 허용된 확장자가 아닌 경우는 모두 _MAL_EXTENSION을 던진다.
		if (originalFileName.indexOf(".") == -1) {
			return _EXTENSION_ERROR;
		}

		InputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			if (fileSize > 0) { // 파일이 존재한다면
				// 인풋 스트림을 얻는다.
				inputStream = multipartFile.getInputStream();

				// 입력되어야 할 디렉토리 정보를 가져온다.
				File realUploadDir = new File(realUploadPath);

				// 디렉토리가 존재하는지 판단해서 없으면 만든다.
				if (!realUploadDir.exists()) {
					log.debug("** make dirs");
					boolean check = realUploadDir.mkdirs();
					if(check) {
						// 처리안함
					}
				}

				String organizedFilePath = realUploadDir + "/" + uploadName;
				outputStream = new FileOutputStream(organizedFilePath);

				// 버퍼를 만든다.
				int readBytes = 0;
				byte[] buffer = new byte[BUFFER_SIZE];

				while ((readBytes = inputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
			}
		} finally {
			// 선언된 스트림을 모두 닫는다.
			if(outputStream != null) outputStream.close();
			if(inputStream != null) inputStream.close();
		}
		return _SUCCESS;
	}
	
	/**
	 * UNZIP
	 * @param zipFile
	 */
	public void unzip(File zipFile) {
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry entry = null;
		File targetDir = new File(realUploadPath);

		try {
			fis = new FileInputStream(zipFile);
			zis = new ZipInputStream(fis);

			while((entry = zis.getNextEntry()) != null) {
				String temp = entry.getName();
				File targetFile = new File(targetDir, temp);

				if(entry.isDirectory()) {	// dir
					FileUtils.forceMkdir(new File(targetFile.getAbsolutePath()));
				} else {					// file
					FileUtils.forceMkdir(new File(targetFile.getParent()));
					unzipEntry(zis, targetFile);
				}
			}
		} catch(Exception e) {
			log.error("", e);
		} finally {
			try {
				if(zis != null) zis.close();
				if(fis != null) fis.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ZIP entry output
	 * @param zis
	 * @param targetFile
	 * @throws Exception
	 */
	public void unzipEntry(ZipInputStream zis, File targetFile) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(targetFile);
			byte[] b = new byte[BUFFER_SIZE];
			int length = 0;
			while((length = zis.read(b)) != -1) {
				fos.write(b, 0, length);
			}
		} catch(Exception e) {
			log.error("", e);
		} finally {
			if(fos != null) fos.close();
		}
	}
}
