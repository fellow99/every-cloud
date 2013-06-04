package com.fellow.every.provider.weibo;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;

public class WeiboAccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public WeiboAccountInfo(JSONObject json){
		try {
			this.setId(json.getString("uid"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
