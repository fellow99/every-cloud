package com.fellow.every.provider.box;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractQuotaInfo;

public class BoxQuotaInfo extends AbstractQuotaInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BoxQuotaInfo(JSONObject json){
		try {
			this.setQuota(json.getLong("space_amount"));
			this.setUsed(json.getLong("space_used"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
