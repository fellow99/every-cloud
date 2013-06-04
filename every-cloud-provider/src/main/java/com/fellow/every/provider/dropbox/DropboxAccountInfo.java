package com.fellow.every.provider.dropbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractAccountInfo;

public class DropboxAccountInfo extends AbstractAccountInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public DropboxAccountInfo(JSONObject json){
		try {
			this.setId(json.getString("uid"));
			this.setName(json.getString("display_name"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
