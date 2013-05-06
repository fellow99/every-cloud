package test.disk.common;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.ShareInfo;

public class AbstractTestOpsExt extends AbstractTestAPI{

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void share() throws Exception{
		String local = "src/test/resources/file2.jpg";
		String path = "/share-file.jpg";

		//clear file on disk
		FileInfo fileInfo = getDiskAPI().metadata(getAccessToken(), path);
		if(!(fileInfo == null || fileInfo.isDeleted())){
			getDiskAPI().rm(getAccessToken(), path, false);
			fileInfo = getDiskAPI().metadata(getAccessToken(), path);
		}
		assertTrue(fileInfo == null || fileInfo.isDeleted());
		
		File file = new File(local);
		assertTrue(file.exists() && file.canRead());

		long size = file.length();
		FileInputStream is = new FileInputStream(file);
		getDiskAPI().upload(getAccessToken(), path, is, size, null);
		is.close();
		
		ShareInfo share = getDiskAPI().share(getAccessToken(), path);
		assertTrue(share != null && share.getUrl() != null);
		
		System.out.println("share: " + share.getUrl());
	}
	
	@Test
	public void thumbnail() throws Exception{
		String local = "src/test/resources/file2.jpg";
		String path = "/thumbnail-file.jpg";

		//clear file on disk
		FileInfo fileInfo = getDiskAPI().metadata(getAccessToken(), path);
		if(!(fileInfo == null || fileInfo.isDeleted())){
			getDiskAPI().rm(getAccessToken(), path, false);
			fileInfo = getDiskAPI().metadata(getAccessToken(), path);
		}
		assertTrue(fileInfo == null || fileInfo.isDeleted());
		
		File file = new File(local);
		assertTrue(file.exists() && file.canRead());

		long size = file.length();
		FileInputStream is = new FileInputStream(file);
		getDiskAPI().upload(getAccessToken(), path, is, size, null);
		is.close();
		
		InputStream thumbnail = getDiskAPI().thumbnail(getAccessToken(), path, 32, 32);
		assertTrue(thumbnail != null);
		
		File tmp = File.createTempFile(UUID.randomUUID().toString(), ".jpg");
		FileOutputStream fos = new FileOutputStream(tmp);
		int len = 0;
		byte[] buf = new byte[1024];
        while((len = thumbnail.read(buf)) != -1) {
        	fos.write(buf, 0, len);
        }
        fos.close();
		System.out.println("thumbnail: " + tmp.getPath());
	}
}
