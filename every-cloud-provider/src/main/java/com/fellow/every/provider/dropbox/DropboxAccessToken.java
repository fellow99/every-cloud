package com.fellow.every.provider.dropbox;

import org.json.JSONObject;

import com.fellow.every.base.AbstractAccessToken;
import com.fellow.every.exception.ApiException;

public class DropboxAccessToken extends AbstractAccessToken{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String uid;

	@Override
	public void load(String raw) throws ApiException {
		try {
			raw += "&";
			
			this.setAccessToken(getValue(raw, "oauth_token"));
			this.setAccessTokenSecret(getValue(raw, "oauth_token_secret"));
			this.setUid(getValue(raw, "uid"));
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
