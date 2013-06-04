package test.auth.shell;

import java.io.InputStream;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.provider.a360.A360AccessToken;
import com.fellow.every.provider.a360.A360OAuth2API;

import test.auth.common.AbstractShellAuthenticatorV20;
import test.auth.common.AccessTokenUtil;

public class A360ShellAuthenticator extends AbstractShellAuthenticatorV20{

	public final static Class<? extends org.scribe.builder.api.Api> PROVIDER = A360OAuth2API.class;

	public final static Class<? extends AccessToken> ACCESS_TOKEN_CLASS = A360AccessToken.class;

	public A360ShellAuthenticator() {
        super(System.in);
	}

	public A360ShellAuthenticator(InputStream in) {
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
	        .callback("oob")
	        .scope("basic")
	        .build();
	}

}
