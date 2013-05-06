package test.auth.shell;

import java.io.InputStream;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.provider.kuaipan.KuaipanAccessToken;
import com.fellow.every.provider.kuaipan.KuaipanDiskAPI;
import com.fellow.every.provider.kuaipan.KuaipanOAuthAPI;

import test.auth.common.AbstractShellAuthenticatorV10;
import test.auth.common.AccessTokenUtil;

public class KuaipanShellAuthenticator extends AbstractShellAuthenticatorV10{
	
	public final static String ROOT = KuaipanDiskAPI.APP_ROOT_APP_FOLDER;

	public final static Class<? extends org.scribe.builder.api.Api> PROVIDER = KuaipanOAuthAPI.class;

	public final static Class<? extends AccessToken> ACCESS_TOKEN_CLASS = KuaipanAccessToken.class;

	public KuaipanShellAuthenticator() {
        super(System.in);
	}
	
	public KuaipanShellAuthenticator(InputStream in) {
        super(in);
	}

	@Override
	public Class<? extends Api> getProvider() {
		return PROVIDER;
	}

	@Override
	public Token getAppToken() {
		return AccessTokenUtil.loadAppToken("api.properties", PROVIDER.getName());
	}

	@Override
	public AccessToken createAccessToken() {
		try {
			return ACCESS_TOKEN_CLASS.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public OAuthService getService() {
		return new ServiceBuilder()  
	        .provider(this.getProvider())
	        .apiKey(this.getAppToken().getToken())
	        .apiSecret(this.getAppToken().getSecret())
	        .signatureType(SignatureType.QueryString)
	        .callback("http://localhost:8080/")
	        .build();
	}

}
