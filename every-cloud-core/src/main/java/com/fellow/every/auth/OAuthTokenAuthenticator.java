package com.fellow.every.auth;


public interface OAuthTokenAuthenticator {
	AccessToken requestAuthentication() throws Exception;
}
