package com.fellow.every.provider.kaixin;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;

public class KaixinAccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public KaixinAccountInfo(JSONObject json){
		try {
			this.setId(json.getString("uid"));
			this.setName(json.getString("name"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
