package com.fellow.every.provider.weibo;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractStatusCommentInfo;

public class WeiboStatusCommentInfo extends AbstractStatusCommentInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public WeiboStatusCommentInfo(JSONObject json){
		try{
			this.setId(json.getString("id"));
			this.setContent(json.has("text") ?json.getString("text") : null);
			this.setSource(json.has("source") ?json.getString("source") : null);

			JSONObject jsonUser = (json.has("user") ?json.getJSONObject("user") : null);
			if(jsonUser != null){
				this.setUser(new WeiboUserInfo(jsonUser));
			}

			JSONObject subjson = (json.has("status") ?json.getJSONObject("status") : null);
			if(subjson != null){
				this.setStatus(new WeiboStatusInfo(subjson));
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
