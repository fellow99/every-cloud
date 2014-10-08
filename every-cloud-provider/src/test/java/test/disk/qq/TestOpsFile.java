package test.disk.qq;

import org.junit.BeforeClass;

import test.disk.common.AbstractTestOpsFile;


public class TestOpsFile extends AbstractTestOpsFile {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setDiskAPI(QQDiskTests.getDiskAPI());
		
		setAccessToken(QQDiskTests.getAuthenticator().requestAuthentication());
	}
}
