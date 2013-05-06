package test.microblog.common;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.mircoblog.MicroBlogAPI;

public class AbstractTestAPI {
	private static AccessToken accessToken;
	private static MicroBlogAPI microBlogAPI;

	public static MicroBlogAPI getMicroBlogAPI() {
		return microBlogAPI;
	}

	public static void setMicroBlogAPI(MicroBlogAPI api) {
		microBlogAPI = api;
	}

	public static AccessToken getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(AccessToken accessToken) {
		AbstractTestAPI.accessToken = accessToken;
	}
	
}
