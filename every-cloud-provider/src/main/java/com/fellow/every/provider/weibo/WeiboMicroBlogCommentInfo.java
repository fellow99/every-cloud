package com.fellow.every.provider.weibo;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.mircoblog.MicroBlogCommentInfo;
import com.fellow.every.mircoblog.MicroBlogInfo;
import com.fellow.every.user.UserInfo;

public class WeiboMicroBlogCommentInfo implements MicroBlogCommentInfo{
	private JSONObject json;
	
	public WeiboMicroBlogCommentInfo(JSONObject json){
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
				return new WeiboUserInfo(subjson);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public MicroBlogInfo getMicroBlog() {
		try {
			JSONObject subjson = (json.has("status") ?json.getJSONObject("status") : null);
			if(subjson == null){
				return null;
			} else {
				return new WeiboMicroBlogInfo(subjson);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}


}
