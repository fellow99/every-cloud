package com.fellow.every.provider.dropbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractQuotaInfo;

public class DropboxQuotaInfo extends AbstractQuotaInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public DropboxQuotaInfo(JSONObject json){
		try {
			this.setQuota(json.getLong("quota"));
			this.setUsed(json.getLong("normal") + json.getLong("shared"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
