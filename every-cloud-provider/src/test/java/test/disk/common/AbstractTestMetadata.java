package test.disk.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.QuotaInfo;

public class AbstractTestMetadata extends AbstractTestAPI{

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void quota() throws Exception{
		//配额信息
		QuotaInfo quota = getDiskAPI().quota(getAccessToken());
		assertNotNull(quota);
		assertTrue(quota.getQuota() > 0);
	}

	@Test
	public void metadata() throws Exception{
		//目录信息
		FileInfo file = getDiskAPI().metadata(getAccessToken(), "/");
		assertNotNull(file);
	}

	@Test
	public void list() throws Exception {
		//目录内容
		FileInfo[] files = getDiskAPI().list(getAccessToken(), "/");
		assertNotNull(files);
	}

}
