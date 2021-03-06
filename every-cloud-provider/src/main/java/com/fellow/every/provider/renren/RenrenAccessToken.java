package com.fellow.every.provider.renren;

import org.json.JSONObject;

import com.fellow.every.base.AbstractAccessToken;
import com.fellow.every.exception.ApiException;

public class RenrenAccessToken extends AbstractAccessToken{
	public static final String APP_VERSION = "1.0";
	public static final String APP_FORMAT = "JSON";
	
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
	
	public String getAppVersion(){
		return APP_VERSION;
	}
	
	public String getAppFormat(){
		return APP_FORMAT;
	}
}
