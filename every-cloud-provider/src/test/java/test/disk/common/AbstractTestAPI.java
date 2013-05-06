package test.disk.common;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.disk.DiskAPI;

public class AbstractTestAPI {
	private static AccessToken accessToken;
	private static DiskAPI diskAPI;

	public static DiskAPI getDiskAPI() {
		return diskAPI;
	}

	public static void setDiskAPI(DiskAPI api) {
		diskAPI = api;
	}

	public static AccessToken getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(AccessToken accessToken) {
		AbstractTestAPI.accessToken = accessToken;
	}
}
