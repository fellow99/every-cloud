package com.fellow.every.provider.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.mircoblog.MicroBlogInfo;
import com.fellow.every.user.UserInfo;

public class QQMicroBlogInfo implements MicroBlogInfo {
	private JSONObject json;
	
	public QQMicroBlogInfo(JSONObject json){
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
			return json.getString("id");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getContent() {
		try {
			return (json.has("text") ?json.getString("text") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getSource() {
		try {
			return (json.has("source") ?json.getString("source") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	@Override
	public UserInfo getUser() {
		return new QQUserInfo(json);
	}

}
