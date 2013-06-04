package com.fellow.every.provider.baidu;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractQuotaInfo;

public class BaiduQuotaInfo extends AbstractQuotaInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BaiduQuotaInfo(JSONObject json){
		try {
			this.setQuota(json.getLong("quota"));
			this.setUsed(json.getLong("used"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
