package com.fellow.every.provider.kuaipan;

import org.json.JSONObject;

import com.fellow.every.base.AbstractAccessToken;
import com.fellow.every.exception.ApiException;

public class KuaipanAccessToken extends AbstractAccessToken{
	
	@Override
	public void load(String raw) throws ApiException {
		try {
		JSONObject json = new JSONObject(raw);
		this.setAccessToken(json.getString("oauth_token"));
		this.setAccessTokenSecret(json.getString("oauth_token_secret"));
		} catch(Exception e){
			throw new ApiException(e);
		}
	}
}
