package test.disk.baidu;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestOpsStream;


public class TestOpsStream extends AbstractTestOpsStream {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(BaiduDiskTests.getDiskAPI());
		
		setAccessToken(BaiduDiskTests.getAuthenticator().requestAuthentication());
	}
}
