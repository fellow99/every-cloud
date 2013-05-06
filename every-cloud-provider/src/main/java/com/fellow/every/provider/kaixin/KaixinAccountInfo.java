package com.fellow.every.provider.kaixin;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.user.AccountInfo;

public class KaixinAccountInfo implements AccountInfo{
	private JSONObject json;
	
	public KaixinAccountInfo(JSONObject json){
		this.json = json;
	}
	
	public JSONObject getJson(){
		return json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}

	@Override
	public String getId() {
		try {
			return json.getString("uid");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		try {
			return json.getString("name");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
