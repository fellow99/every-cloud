package test.status.common;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.status.StatusAPI;

public class AbstractTestAPI {
	private static AccessToken accessToken;
	private static StatusAPI microBlogAPI;

	public static StatusAPI getMicroBlogAPI() {
		return microBlogAPI;
	}

	public static void setMicroBlogAPI(StatusAPI api) {
		microBlogAPI = api;
	}

	public static AccessToken getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(AccessToken accessToken) {
		AbstractTestAPI.accessToken = accessToken;
	}
	
}
