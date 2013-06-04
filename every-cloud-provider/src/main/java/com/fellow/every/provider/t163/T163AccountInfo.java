package com.fellow.every.provider.t163;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;

public class T163AccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public T163AccountInfo(JSONObject json){
		try {
			this.setId(json.getString("user_id"));
			this.setName(json.getString("user_name"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
