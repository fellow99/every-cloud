package com.fellow.every.provider.kanbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractQuotaInfo;

public class KanboxQuotaInfo extends AbstractQuotaInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public KanboxQuotaInfo(JSONObject json){
		try {
			this.setQuota(json.getLong("spaceQuota"));
			this.setUsed(json.getLong("spaceUsed"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
