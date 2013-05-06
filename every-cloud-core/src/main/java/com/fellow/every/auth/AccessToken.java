package com.fellow.every.auth;

import com.fellow.every.exception.ApiException;

public interface AccessToken {
	
	void load(String raw) throws ApiException;
	
	String getAccessToken();
	
	String getAccessTokenSecret();
	
	String getRefreshToken();
	
	int getExpiresIn();
}
