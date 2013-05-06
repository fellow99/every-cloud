package test.disk.common;

import static org.junit.Assert.*;

import java.io.*;
import java.util.UUID;

import org.junit.Before;

import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.SimpleProgressListener;

public class AbstractTestOpsStream extends AbstractTestOpsFile{

	@Before
	public void setUp() throws Exception {
	}


	public void assertFile(String local, String path) throws Exception{

		//clear file on disk
		FileInfo fileInfo = getDiskAPI().metadata(getAccessToken(), path);
		if(!(fileInfo == null || fileInfo.isDeleted())){
			getDiskAPI().rm(getAccessToken(), path, false);
			fileInfo = getDiskAPI().metadata(getAccessToken(), path);
		}
		assertTrue(fileInfo == null || fileInfo.isDeleted());
		
		File file = new File(local);
		assertTrue(file.exists() && file.canRead());
		int buf;

		//upload
		FileInputStream is = new FileInputStream(file);
		OutputStream uploadStream = getDiskAPI().uploadStream(getAccessToken(), path, new SimpleProgressListener());
		while ((buf = is.read()) != -1) {
			uploadStream.write(buf); 
		}
		uploadStream.close();
		is.close();
		
		//download
		InputStream downloadStream = getDiskAPI().downloadStream(getAccessToken(), path, new SimpleProgressListener());
		File tmp = File.createTempFile(UUID.randomUUID().toString(), null);
		FileOutputStream os = new FileOutputStream(tmp);
		while ((buf = downloadStream.read()) != -1) {
			os.write(buf); 
		}
		os.close();
		downloadStream.close();

		
		//compare the uploaded file with the local file
		assertTrue(compareMD5(file, tmp));
		
		//clear tmp file
		tmp.delete();
	}
}
