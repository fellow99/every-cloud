package com.fellow.every.provider.kuaipan;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractQuotaInfo;

public class KuaipanQuotaInfo extends AbstractQuotaInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public KuaipanQuotaInfo(JSONObject json){
		try {
			this.setQuota(json.getLong("quota_total"));
			this.setUsed(json.getLong("quota_used"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

}
