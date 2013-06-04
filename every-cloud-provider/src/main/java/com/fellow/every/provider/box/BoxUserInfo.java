package com.fellow.every.provider.box;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class BoxUserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public BoxUserInfo(JSONObject json){
		try {
			this.setId(json.getString("id"));
			this.setName(json.getString("login"));
			this.setNickname(json.has("name") ?json.getString("name") : null);
			this.setPhone(json.has("phone") ?json.getString("phone") : null);
			this.setEmail(json.has("login") ?json.getString("login") : null);
			this.setCountry(json.has("country") ?json.getString("country") : null);
			this.setLocation(json.has("address") ?json.getString("address") : null);
			this.setUrlHead(json.has("avatar_url") ?json.getString("avatar_url") : null);
			this.setJob(json.has("job_title") ?json.getString("job_title") : null);
			this.setSex(SexType.UNKNOWN);
			this.setOnline(OnlineType.UNKNOWN);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
