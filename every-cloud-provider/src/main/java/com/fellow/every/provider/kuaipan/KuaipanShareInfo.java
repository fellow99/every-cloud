package com.fellow.every.provider.kuaipan;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractShareInfo;

public class KuaipanShareInfo extends AbstractShareInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public KuaipanShareInfo(JSONObject json){
		try {
			this.setUrl(json.getString("url"));
			this.setAccessCode(json.has("access_code") ?json.getString("access_code") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
