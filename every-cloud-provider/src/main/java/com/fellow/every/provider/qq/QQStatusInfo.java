package com.fellow.every.provider.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractStatusInfo;

public class QQStatusInfo extends AbstractStatusInfo {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public QQStatusInfo(JSONObject json){
		try{
			this.setId(json.getString("id"));
			this.setContent(json.has("text") ?json.getString("text") : null);
			this.setSource(json.has("source") ?json.getString("source") : null);
			this.setDatetime(json.has("timestamp") ?json.getLong("timestamp") : 0);
			this.setUser(new QQUserInfo(json));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
