package com.fellow.every.provider.kuaipan;
import org.json.JSONObject;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.RequestTokenExtractor;
import org.scribe.model.Token;

public class KuaipanOAuthAPI extends DefaultApi10a {
	//http://www.kuaipan.cn/developers/
	private static final String REQUEST_TOKEN_URL = "https://openapi.kuaipan.cn/open/requestToken";
	private static final String ACCESS_TOKEN_URL = "https://openapi.kuaipan.cn/open/accessToken";
	private static final String AUTHORIZE_URL = "https://www.kuaipan.cn/api.php?ac=open&op=authorise&oauth_token=%s";

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

	@Override
	public AccessTokenExtractor getAccessTokenExtractor() {
		return new AccessTokenExtractor(){
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