package test.disk.baidu;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestOpsFile;


public class TestOpsFile extends AbstractTestOpsFile {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(BaiduDiskTests.getDiskAPI());
		
		setAccessToken(BaiduDiskTests.getAuthenticator().requestAuthentication());
	}
}
