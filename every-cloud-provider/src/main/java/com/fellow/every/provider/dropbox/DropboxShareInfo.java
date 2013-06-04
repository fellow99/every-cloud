package com.fellow.every.provider.dropbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractShareInfo;

public class DropboxShareInfo extends AbstractShareInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public DropboxShareInfo(JSONObject json){
		try {
			this.setUrl(json.getString("url"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
