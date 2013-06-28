package com.fellow.every.provider.renren;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractStatusInfo;
import com.fellow.every.user.UserInfo;

public class RenrenStatusInfo extends AbstractStatusInfo {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public RenrenStatusInfo(JSONObject json){
		try{
			this.setId(json.getString("status_id"));
			this.setContent(json.has("message") ?json.getString("message") : null);
			this.setSource(json.has("source_name") ?json.getString("source_name") : null);
			this.setDatetime(json.has("time") ?json.getLong("time") : 0);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
