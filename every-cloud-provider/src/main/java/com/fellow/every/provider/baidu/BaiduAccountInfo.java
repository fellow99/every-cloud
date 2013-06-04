package com.fellow.every.provider.baidu;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;

public class BaiduAccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BaiduAccountInfo(JSONObject json){
		try {
			this.setId(json.getString("uid"));
			this.setName(json.getString("uname"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
