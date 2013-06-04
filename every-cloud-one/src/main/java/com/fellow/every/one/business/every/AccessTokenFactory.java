package com.fellow.every.one.business.every;

import com.fellow.every.auth.AccessToken;

public class AccessTokenFactory {
	private String accessTokenClass;

	public String getAccessTokenClass() {
		return accessTokenClass;
	}

	public void setAccessTokenClass(String accessTokenClass) {
		this.accessTokenClass = accessTokenClass;
	}

	AccessToken createAccessToken(){
		try {
			return (AccessToken)Class.forName(accessTokenClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
