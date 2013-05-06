package test.user.baidu;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.scribe.model.Token;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.auth.OAuthTokenAuthenticator;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.impl.HTTPURLConnection;
import com.fellow.every.provider.baidu.BaiduAccessToken;
import com.fellow.every.provider.baidu.BaiduDiskAPI;
import com.fellow.every.provider.baidu.BaiduOAuth2API;
import com.fellow.every.provider.baidu.BaiduUserAPI;
import com.fellow.every.user.UserAPI;

import test.auth.common.AccessTokenUtil;

@RunWith(Suite.class)
@SuiteClasses({ TestUserInfo.class })
public class BaiduUserTests {
	
	public final static String ROOT = "/apps/EveryDisk";

	public final static HTTPEngine HTTP_ENGINE = new HTTPURLConnection();

	public final static Class<? extends org.scribe.builder.api.Api> OAUTH_PROVIDER = BaiduOAuth2API.class;

	public final static OAuthTokenAuthenticator getAuthenticator(){
		return new OAuthTokenAuthenticator(){
			public AccessToken requestAuthentication() throws Exception{
				String raw = AccessTokenUtil.loadAuthFile(OAUTH_PROVIDER.getName() + ".log");

				if(raw != null && raw.length() > 0){
					AccessToken accessToken = new BaiduAccessToken();
					accessToken.load(raw);

			        return accessToken;
				} else {
					throw new RuntimeException("AccessToken info is null");
				}
			}
		};
	}

	public final static UserAPI getUserAPI() throws Exception{
		Token appToken = AccessTokenUtil.loadAppToken("api.properties", OAUTH_PROVIDER.getName());

        BaiduUserAPI api = new BaiduUserAPI();
        api.setAppKey(appToken.getToken());
        api.setAppSecret(appToken.getSecret());
        api.setHttpEngine(HTTP_ENGINE);
        api.setProperty(BaiduDiskAPI.PROPERTY_APP_ROOT, ROOT);

        return api;
	}

	public final static UserAPI getUserAPI(HTTPEngine httpEngine) throws Exception{
		Token appToken = AccessTokenUtil.loadAppToken("api.properties", OAUTH_PROVIDER.getName());
        
        BaiduUserAPI api = new BaiduUserAPI();
        api.setAppKey(appToken.getToken());
        api.setAppSecret(appToken.getSecret());
        api.setHttpEngine(httpEngine);
        api.setProperty(BaiduDiskAPI.PROPERTY_APP_ROOT, ROOT);

        return api;
	}
}
