package com.fellow.every.provider.sohu;

import org.json.JSONObject;

import com.fellow.every.base.AbstractAccessToken;
import com.fellow.every.exception.ApiException;

public class SohuAccessToken extends AbstractAccessToken{
	private String uid;

	@Override
	public void load(String raw) throws ApiException {
		try {
		JSONObject json = new JSONObject(raw);
		this.setAccessToken(json.getString("access_token"));
		this.setRefreshToken(json.getString("refresh_token"));
		this.setExpiresIn(json.getInt("expires_in"));
		this.setUid(json.getString("uid"));
		} catch(Exception e){
			throw new ApiException(e);
		}
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
