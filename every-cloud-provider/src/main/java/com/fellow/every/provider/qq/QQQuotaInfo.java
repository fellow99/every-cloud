package com.fellow.every.provider.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractQuotaInfo;

/**
 * @deprecated API not supported.
 *
 */
public class QQQuotaInfo extends AbstractQuotaInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public QQQuotaInfo(JSONObject json){
		try {
			this.setQuota(json.getLong("quota"));
			this.setUsed(json.getLong("used"));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
