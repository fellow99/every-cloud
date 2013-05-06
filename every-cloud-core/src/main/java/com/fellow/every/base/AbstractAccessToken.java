package com.fellow.every.base;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.exception.ApiException;

public class AbstractAccessToken implements AccessToken{

	private String accessToken;
	private String accessTokenSecret;
	private String refreshToken;
	private int expiresIn = -1;

	@Override
	public void load(String raw) throws ApiException {
	}

	@Override
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	@Override
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}
