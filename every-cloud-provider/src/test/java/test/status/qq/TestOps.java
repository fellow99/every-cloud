package test.status.qq;

import org.junit.BeforeClass;

import test.disk.baidu.BaiduDiskTests;
import test.status.common.AbstractTestOps;

public class TestOps extends AbstractTestOps{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setMicroBlogAPI(QQMicroBlogTests.getMicroBlogAPI());
		
		setAccessToken(BaiduDiskTests.getAuthenticator().requestAuthentication());
	}
}
