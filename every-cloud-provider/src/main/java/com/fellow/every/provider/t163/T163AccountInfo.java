package com.fellow.every.provider.t163;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.user.AccountInfo;

public class T163AccountInfo implements AccountInfo{
	private JSONObject json;
	
	public T163AccountInfo(JSONObject json){
		this.json = json;
	}
	
	protected JSONObject getJson(){
		return json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}

	@Override
	public String getId() {
		try {
			return json.getString("user_id");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		try {
			return json.getString("user_name");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
