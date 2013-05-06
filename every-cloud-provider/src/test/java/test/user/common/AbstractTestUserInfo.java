package test.user.common;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.fellow.every.user.AccountInfo;
import com.fellow.every.user.UserInfo;

public class AbstractTestUserInfo extends AbstractTestAPI{
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void my() throws Exception{

		AccountInfo account = getUserAPI().myAccount(getAccessToken());
		assertTrue(account != null && account.getId() != null);
		
		UserInfo info = getUserAPI().myInfo(getAccessToken());
		assertTrue(info != null && info.getName() != null);

		assertTrue(account.getId().equals(info.getId()));
		
		System.out.println(info);
	}


	@Test
	public void get() throws Exception{

		AccountInfo account = getUserAPI().myAccount(getAccessToken());
		assertTrue(account != null && account.getId() != null);
		
		String id = account.getId();
		UserInfo info = getUserAPI().getInfo(getAccessToken(), id);
		assertTrue(info != null && info.getName() != null);
		
		System.out.println(info);
	}
}
