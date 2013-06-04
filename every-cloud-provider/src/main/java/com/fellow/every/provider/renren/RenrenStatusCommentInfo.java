package com.fellow.every.provider.renren;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.status.StatusCommentInfo;
import com.fellow.every.status.StatusInfo;
import com.fellow.every.user.UserInfo;

public class RenrenStatusCommentInfo implements StatusCommentInfo{
	private JSONObject json;
	
	public RenrenStatusCommentInfo(JSONObject json){
		this.json = json;
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
		try {
			JSONObject subjson = (json.has("user") ?json.getJSONObject("user") : null);
			if(subjson == null){
				return null;
			} else {
				return new RenrenUserInfo(subjson);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public StatusInfo getMicroBlog() {
		try {
			JSONObject subjson = (json.has("status") ?json.getJSONObject("status") : null);
			if(subjson == null){
				return null;
			} else {
				return new RenrenStatusInfo(subjson);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}


}
