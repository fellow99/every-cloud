package test.auth.shell;

import java.io.InputStream;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.provider.box.BoxAccessToken;
import com.fellow.every.provider.box.BoxOAuth2API;

import test.auth.common.AbstractShellAuthenticatorV20;
import test.auth.common.AccessTokenUtil;

public class BoxShellAuthenticator extends AbstractShellAuthenticatorV20{
	
	public final static String ROOT = "/apps/EveryDisk";

	public final static Class<? extends org.scribe.builder.api.Api> PROVIDER = BoxOAuth2API.class;

	public final static Class<? extends AccessToken> ACCESS_TOKEN_CLASS = BoxAccessToken.class;

	public BoxShellAuthenticator() {
        super(System.in);
	}

	public BoxShellAuthenticator(InputStream in) {
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
	        .callback("http://localhost/")
	        .build();
	}

}
