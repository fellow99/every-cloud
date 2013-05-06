package com.fellow.every.provider.dropbox;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.extractors.AccessTokenExtractor;
import org.scribe.extractors.RequestTokenExtractor;
import org.scribe.model.Token;

public class DropboxOAuthAPI extends DefaultApi10a {
	//https://www.dropbox.com/developers
	private static final String REQUEST_TOKEN_URL = "https://api.dropbox.com/1/oauth/request_token";
	private static final String ACCESS_TOKEN_URL = "https://api.dropbox.com/1/oauth/access_token";
	private static final String AUTHORIZE_URL = "https://www.dropbox.com/1/oauth/authorize?oauth_token=%s";

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
					String token = null;
					String secret = null;
					String P1 = "oauth_token=";
					String P2 = "oauth_token_secret=";
					String[] sp = response.split("&");
					for(int i = 0; i < sp.length; i++){
						String str = sp[i].trim();
						if(str.indexOf(P1) == 0) token = str.substring(P1.length());
						if(str.indexOf(P2) == 0) secret = str.substring(P2.length());
					}
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
					String token = null;
					String secret = null;
					String P1 = "oauth_token=";
					String P2 = "oauth_token_secret=";
					String[] sp = response.split("&");
					for(int i = 0; i < sp.length; i++){
						String str = sp[i].trim();
						if(str.indexOf(P1) == 0) token = str.substring(P1.length());
						if(str.indexOf(P2) == 0) secret = str.substring(P2.length());
					}
					return new Token(token, secret, response);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}
}