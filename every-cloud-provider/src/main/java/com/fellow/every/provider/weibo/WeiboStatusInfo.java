package com.fellow.every.provider.weibo;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractStatusInfo;

public class WeiboStatusInfo extends AbstractStatusInfo {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public WeiboStatusInfo(JSONObject json){
		try{
			this.setId(json.getString("id"));
			this.setContent(json.has("text") ?json.getString("text") : null);
			this.setSource(json.has("source") ?json.getString("source") : null);
			this.setDatetime(json.has("timestamp") ?json.getLong("timestamp") : 0);

			JSONObject jsonUser = (json.has("user") ?json.getJSONObject("user") : null);
			if(jsonUser != null){
				this.setUser(new WeiboUserInfo(jsonUser));
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
