package com.fellow.every.provider.kanbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;
import com.fellow.every.user.AccountInfo;

public class KanboxAccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public KanboxAccountInfo(JSONObject json){
		try {
			this.setId(json.getString("email"));
			this.setName(json.getString("email"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
