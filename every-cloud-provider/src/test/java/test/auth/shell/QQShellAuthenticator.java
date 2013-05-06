package test.auth.shell;

import java.io.InputStream;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.provider.qq.QQAccessToken;
import com.fellow.every.provider.qq.QQOAuth2API;

import test.auth.common.AbstractShellAuthenticatorV20;
import test.auth.common.AccessTokenUtil;

public class QQShellAuthenticator extends AbstractShellAuthenticatorV20{

	public final static String IP = "127.0.0.1";

	public final static Class<? extends org.scribe.builder.api.Api> PROVIDER = QQOAuth2API.class;

	public final static Class<? extends AccessToken> ACCESS_TOKEN_CLASS = QQAccessToken.class;

	public QQShellAuthenticator() {
        super(System.in);
	}

	public QQShellAuthenticator(InputStream in) {
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
	        .callback("http://127.0.0.1")
	        .build();
	}

}
