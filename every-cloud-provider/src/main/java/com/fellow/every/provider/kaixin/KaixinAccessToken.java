package com.fellow.every.provider.kaixin;

import org.json.JSONObject;

import com.fellow.every.base.AbstractAccessToken;
import com.fellow.every.exception.ApiException;

public class KaixinAccessToken extends AbstractAccessToken{
	private String scope;

	@Override
	public void load(String raw) throws ApiException {
		try {
		JSONObject json = new JSONObject(raw);
		this.setAccessToken(json.getString("access_token"));
		this.setRefreshToken(json.getString("refresh_token"));
		this.setExpiresIn(json.getInt("expires_in"));
		this.setScope(json.getString("scope"));
		} catch(Exception e){
			throw new ApiException(e);
		}
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
