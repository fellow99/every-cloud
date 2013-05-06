package com.fellow.every.provider.dropbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.disk.ShareInfo;

public class DropboxShareInfo implements ShareInfo{
	private JSONObject json;
	
	public DropboxShareInfo(JSONObject json){
		this.json = json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}
	
	@Override
	public String getUrl() {
		try {
			return json.getString("url");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public String getAccessCode() {
		return null;
	}
	@Override
	public long getExpireTime() {
		return 0;
	}
}
