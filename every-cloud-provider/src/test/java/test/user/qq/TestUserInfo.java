package test.user.qq;

import org.junit.BeforeClass;

import test.disk.baidu.BaiduDiskTests;
import test.user.common.AbstractTestUserInfo;

public class TestUserInfo extends AbstractTestUserInfo{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUserAPI(QQUserTests.getUserAPI());
		
		setAccessToken(BaiduDiskTests.getAuthenticator().requestAuthentication());
	}
}
