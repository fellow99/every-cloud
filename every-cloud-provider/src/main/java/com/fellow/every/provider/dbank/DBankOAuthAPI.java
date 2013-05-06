package com.fellow.every.provider.dbank;
import org.json.JSONObject;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.extractors.RequestTokenExtractor;
import org.scribe.model.Token;

public class DBankOAuthAPI extends DefaultApi10a {
	//http://open.dbank.com/
	private static final String REQUEST_TOKEN_URL = "http://login.dbank.com/oauth1/request_token";
	private static final String ACCESS_TOKEN_URL = "http://login.dbank.com/oauth1/access_token";
	private static final String AUTHORIZE_URL = "http://login.dbank.com/oauth1/authorize?oauth_token=%s";

	@Override
	public String getRequestTokenEndpoint() {
		return REQUEST_TOKEN_URL;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return ACCESS_TOKEN_URL;
	}

	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return String.format(AUTHORIZE_URL, requestToken.getToken());
	}

	@Override
	public RequestTokenExtractor getRequestTokenExtractor() {
		return new RequestTokenExtractor(){
			public Token extract(String response) {
				try {
					JSONObject json = new JSONObject(response);
					String token = json.getString("oauth_token");
					String secret = json.getString("oauth_token_secret");
					return new Token(token, secret, response);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}
}