package test.user.renren;

import org.junit.BeforeClass;

import test.disk.baidu.BaiduDiskTests;
import test.user.common.AbstractTestUserInfo;

public class TestUserInfo extends AbstractTestUserInfo{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUserAPI(RenrenUserTests.getUserAPI());
		
		setAccessToken(BaiduDiskTests.getAuthenticator().requestAuthentication());
	}
}
