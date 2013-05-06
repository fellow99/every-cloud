package test.disk.kanbox;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.scribe.model.Token;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.auth.OAuthTokenAuthenticator;
import com.fellow.every.disk.DiskAPI;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.impl.HTTPURLConnection;
import com.fellow.every.provider.kanbox.KanboxAccessToken;
import com.fellow.every.provider.kanbox.KanboxDiskAPI;
import com.fellow.every.provider.kanbox.KanboxOAuth2API;

import test.auth.common.AccessTokenUtil;

@RunWith(Suite.class)
@SuiteClasses({ TestMetadata.class, TestOpsFolder.class, TestOpsExt.class, TestOpsFile.class, TestOpsStream.class, TestHTTPEngine.class })
public class KanboxDiskTests {

	public final static HTTPEngine HTTP_ENGINE = new HTTPURLConnection();

	public final static Class<? extends org.scribe.builder.api.Api> OAUTH_PROVIDER = KanboxOAuth2API.class;

	public final static OAuthTokenAuthenticator getAuthenticator(){
		return new OAuthTokenAuthenticator(){
			public AccessToken requestAuthentication() throws Exception{
				String raw = AccessTokenUtil.loadAuthFile(OAUTH_PROVIDER.getName() + ".log");

				if(raw != null && raw.length() > 0){
					AccessToken accessToken = new KanboxAccessToken();
					accessToken.load(raw);

			        return accessToken;
				} else {
					throw new RuntimeException("AccessToken info is null");
				}
			}
		};
	}

	public final static DiskAPI getDiskAPI() throws Exception{
		Token appToken = AccessTokenUtil.loadAppToken("api.properties", OAUTH_PROVIDER.getName());
        
		KanboxDiskAPI api = new KanboxDiskAPI();
        api.setAppKey(appToken.getToken());
        api.setAppSecret(appToken.getSecret());
        api.setHttpEngine(HTTP_ENGINE);

        return api;
	}

	public final static DiskAPI getDiskAPI(HTTPEngine httpEngine) throws Exception{
		Token appToken = AccessTokenUtil.loadAppToken("api.properties", OAUTH_PROVIDER.getName());
        
		KanboxDiskAPI api = new KanboxDiskAPI();
        api.setAppKey(appToken.getToken());
        api.setAppSecret(appToken.getSecret());
        api.setHttpEngine(httpEngine);

        return api;
	}
}
