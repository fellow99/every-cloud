package test.auth.common;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.auth.OAuthTokenAuthenticator;

public abstract class AbstractAuthenticator implements OAuthTokenAuthenticator{
	
	public abstract OAuthService getService();
	
	public abstract Class<? extends org.scribe.builder.api.Api> getProvider();
	
	public abstract Token getAppToken();
	
	public abstract AccessToken createAccessToken();
}
