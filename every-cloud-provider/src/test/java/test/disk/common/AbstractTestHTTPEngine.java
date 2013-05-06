package test.disk.common;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import com.fellow.every.disk.DiskAPI;
import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.QuotaInfo;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.impl.HTTPClient3;
import com.fellow.every.http.impl.HTTPClient4;
import com.fellow.every.http.impl.HTTPURLConnection;

public class AbstractTestHTTPEngine extends AbstractTestAPI{
	
	protected static Map<Class<? extends HTTPEngine>, DiskAPI> apiMap = new HashMap<Class<? extends HTTPEngine>, DiskAPI>();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void httpClient4() throws Exception{
		DiskAPI api = apiMap.get(HTTPClient4.class);
		assertNotNull(api);
		
		setDiskAPI(api);
		
		doTest();
	}

	@Test
	public void httpClient3() throws Exception{
		DiskAPI api = apiMap.get(HTTPClient3.class);
		assertNotNull(api);
		
		setDiskAPI(api);
		
		doTest();
	}
	

	@Test
	public void httpURLConnection() throws Exception{
		DiskAPI api = apiMap.get(HTTPURLConnection.class);
		assertNotNull(api);
		
		setDiskAPI(api);
		
		doTest();
	}

	public void doTest() throws Exception {

		//配额信息
		QuotaInfo quota = getDiskAPI().quota(getAccessToken());
		assertNotNull(quota);

		//目录信息
		FileInfo file = getDiskAPI().metadata(getAccessToken(), "/");
		assertNotNull(file);

		//目录内容
		FileInfo[] files = getDiskAPI().list(getAccessToken(), "/");
		assertNotNull(files);

		//创建目录
		assertTrue(getDiskAPI().mkdir(getAccessToken(), "/test_dir"));

		//重命名目录
		assertTrue(getDiskAPI().mv(getAccessToken(), "/test_dir", "/test_dir2"));

		//复制目录
		assertTrue(getDiskAPI().cp(getAccessToken(), "/test_dir2", "/test_dir3"));

		//删除目录
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_dir2", false));
		assertTrue(getDiskAPI().rm(getAccessToken(), "/test_dir3", false));
		
		//文件操作
		String local = "src/test/resources/file1.txt";
		String path = "/test-file1.txt";
		new AbstractTestOpsFile().assertFile(local, path);
	}
}
