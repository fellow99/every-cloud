package test.disk.qq;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestOpsStream;


public class TestOpsStream extends AbstractTestOpsStream {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(QQDiskTests.getDiskAPI());
		
		setAccessToken(QQDiskTests.getAuthenticator().requestAuthentication());
	}
}
