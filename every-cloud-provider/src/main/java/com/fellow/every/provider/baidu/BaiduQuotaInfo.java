package com.fellow.every.provider.baidu;

import org.json.JSONObject;

import com.fellow.every.disk.QuotaInfo;

public class BaiduQuotaInfo implements QuotaInfo{
	private JSONObject json;
	
	public BaiduQuotaInfo(JSONObject json){
		this.json = json;
	}

	@Override
	public long getQuota() {
		try {
			return json.getLong("quota");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long getUsed() {
		try {
			return json.getLong("used");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}

}
