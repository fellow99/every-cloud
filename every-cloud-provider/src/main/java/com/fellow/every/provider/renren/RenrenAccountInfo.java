package com.fellow.every.provider.renren;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;

public class RenrenAccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public RenrenAccountInfo(JSONObject json){
		try {
			this.setId(json.getString("uid"));
			this.setName(json.has("name") ?json.getString("name") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
