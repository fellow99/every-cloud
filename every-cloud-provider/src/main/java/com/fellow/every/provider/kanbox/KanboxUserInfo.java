package com.fellow.every.provider.kanbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class KanboxUserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public KanboxUserInfo(JSONObject json){
		try {
			this.setId(json.getString("email"));
			this.setName(json.getString("email"));
			this.setPhone(json.has("mobile") ?json.getString("mobile") : null);
			this.setEmail(json.has("email") ?json.getString("email") : null);
			this.setSex(SexType.UNKNOWN);
			this.setOnline(OnlineType.UNKNOWN);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
}
