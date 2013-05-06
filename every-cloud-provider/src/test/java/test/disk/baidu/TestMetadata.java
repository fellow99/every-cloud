package test.disk.baidu;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestMetadata;

public class TestMetadata extends AbstractTestMetadata{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(BaiduDiskTests.getDiskAPI());
		
		setAccessToken(BaiduDiskTests.getAuthenticator().requestAuthentication());
	}
}
