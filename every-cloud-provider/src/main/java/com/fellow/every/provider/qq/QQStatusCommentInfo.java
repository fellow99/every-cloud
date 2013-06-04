package com.fellow.every.provider.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractStatusCommentInfo;

public class QQStatusCommentInfo extends AbstractStatusCommentInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public QQStatusCommentInfo(JSONObject json){
		try{
			this.setId(json.getString("id"));
			this.setContent(json.has("text") ?json.getString("text") : null);
			this.setSource(json.has("source") ?json.getString("source") : null);
			this.setUser(new QQUserInfo(json));
			this.setStatus(null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
