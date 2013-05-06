package com.fellow.every.provider.kuaipan;

import org.json.JSONObject;

import com.fellow.every.disk.QuotaInfo;

public class KuaipanQuotaInfo implements QuotaInfo{
	private JSONObject json;
	
	public KuaipanQuotaInfo(JSONObject json){
		this.json = json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}

	@Override
	public long getQuota() {
		try {
			return json.getLong("quota_total");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getUsed() {
		try {
			return json.getLong("quota_used");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
