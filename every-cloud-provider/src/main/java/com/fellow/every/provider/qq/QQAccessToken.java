package com.fellow.every.provider.qq;

import com.fellow.every.base.AbstractAccessToken;
import com.fellow.every.exception.ApiException;

public class QQAccessToken extends AbstractAccessToken{
	private String openid;
	private String clientip;

	@Override
	public void load(String raw) throws ApiException {
		try {
			raw += "&";
			
			this.setAccessToken(getValue(raw, "access_token"));
			this.setRefreshToken(getValue(raw, "refresh_token"));
			this.setExpiresIn(Integer.parseInt(getValue(raw, "expires_in")));
			this.setOpenid(getValue(raw, "openid"));
		} catch(Exception e){
			throw new ApiException(e);
		}
	}
	
	private String getValue(String raw, String key){
		key += "=";
		
		int i = raw.indexOf(key);
		if(i == -1) return null;
		
		int j = raw.indexOf("&", i);
		if(i == -1) return null;
		
		return raw.substring(i + key.length(), j);
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getClientip() {
		return clientip;
	}

	public void setClientip(String clientip) {
		this.clientip = clientip;
	}
}
