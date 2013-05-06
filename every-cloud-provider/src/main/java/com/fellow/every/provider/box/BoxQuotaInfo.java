package com.fellow.every.provider.box;

import org.json.JSONObject;

import com.fellow.every.disk.QuotaInfo;

public class BoxQuotaInfo implements QuotaInfo{
	private JSONObject json;
	
	public BoxQuotaInfo(JSONObject json){
		this.json = json;
	}

	@Override
	public long getQuota() {
		try {
			return json.getLong("space_amount");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getUsed() {
		try {
			return json.getLong("space_used");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}

}
