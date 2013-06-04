package com.fellow.every.provider.box;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;

public class BoxAccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BoxAccountInfo(JSONObject json){
		try {
			this.setId(json.getString("id"));
			this.setName(json.getString("login"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
