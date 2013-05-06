package test.user.baidu;

import org.junit.BeforeClass;

import test.user.common.AbstractTestUserInfo;

public class TestUserInfo extends AbstractTestUserInfo{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUserAPI(BaiduUserTests.getUserAPI());
	}
}
