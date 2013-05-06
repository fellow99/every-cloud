package com.fellow.every.provider.dropbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.user.AccountInfo;

public class DropboxAccountInfo implements AccountInfo{
	private JSONObject json;
	
	public DropboxAccountInfo(JSONObject json){
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
			return json.getString("uid");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		try {
			return json.getString("display_name");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
