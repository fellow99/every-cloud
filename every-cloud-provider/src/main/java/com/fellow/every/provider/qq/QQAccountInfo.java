package com.fellow.every.provider.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;

public class QQAccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public QQAccountInfo(JSONObject json){
		try {
			this.setId(json.getString("openid"));
			this.setName(json.getString("name"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

}
