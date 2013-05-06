package com.fellow.every.provider.dropbox;

import org.json.JSONObject;

import com.fellow.every.disk.QuotaInfo;

public class DropboxQuotaInfo implements QuotaInfo{
	private JSONObject json;
	
	public DropboxQuotaInfo(JSONObject json){
		this.json = json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
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
			return json.getLong("normal") + json.getLong("shared");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
