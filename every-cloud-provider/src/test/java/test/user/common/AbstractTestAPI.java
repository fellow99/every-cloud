package test.user.common;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.user.UserAPI;

public class AbstractTestAPI {
	private static AccessToken accessToken;
	private static UserAPI userAPI;

	public static UserAPI getUserAPI() {
		return userAPI;
	}

	public static void setUserAPI(UserAPI api) {
		userAPI = api;
	}

	public static AccessToken getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(AccessToken accessToken) {
		AbstractTestAPI.accessToken = accessToken;
	}
	
}
