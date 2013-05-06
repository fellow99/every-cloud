package com.fellow.every.provider.kanbox;

import org.json.JSONObject;

import com.fellow.every.disk.QuotaInfo;

public class KanboxQuotaInfo implements QuotaInfo{
	private JSONObject json;
	
	public KanboxQuotaInfo(JSONObject json){
		this.json = json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}

	@Override
	public long getQuota() {
		try {
			return json.getLong("spaceQuota");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getUsed() {
		try {
			return json.getLong("spaceUsed");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
