package test.microblog.weibo;

import org.junit.BeforeClass;

import test.disk.baidu.BaiduDiskTests;
import test.microblog.common.AbstractTestOps;

public class TestOps extends AbstractTestOps{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setMicroBlogAPI(WeiboMicroBlogTests.getMicroBlogAPI());
		
		setAccessToken(BaiduDiskTests.getAuthenticator().requestAuthentication());
	}
}
