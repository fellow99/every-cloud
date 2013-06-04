package com.fellow.every.provider.a360;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;

public class A360AccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public A360AccountInfo(JSONObject json){
		try {
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
