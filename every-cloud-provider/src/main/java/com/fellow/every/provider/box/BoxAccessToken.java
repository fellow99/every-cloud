package com.fellow.every.provider.box;

import org.json.JSONObject;

import com.fellow.every.base.AbstractAccessToken;
import com.fellow.every.exception.ApiException;

public class BoxAccessToken extends AbstractAccessToken{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@Override
	public void load(String raw) throws ApiException {
		try {
		JSONObject json = new JSONObject(raw);
		this.setAccessToken(json.getString("access_token"));
		this.setRefreshToken(json.getString("refresh_token"));
		this.setExpiresIn(json.getInt("expires_in"));
		} catch(Exception e){
			throw new ApiException(e);
		}
	}
}
