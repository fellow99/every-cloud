package com.fellow.every.provider.kuaipan;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.disk.ShareInfo;

public class KuaipanShareInfo implements ShareInfo{
	private JSONObject json;
	
	public KuaipanShareInfo(JSONObject json){
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
		try {
			return (json.has("access_code") ?json.getString("access_code") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public long getExpireTime() {
		return 0;
	}
}
