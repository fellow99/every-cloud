package com.fellow.every.provider.t163;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class T163UserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public T163UserInfo(JSONObject json){
		try {
			this.setId(json.getString("user_id"));
			this.setName(json.getString("user_name"));
			this.setNickname(json.has("user_name") ?json.getString("user_name") : null);
			this.setPhone(json.has("mobile") ?json.getString("mobile") : null);
			this.setSex(SexType.UNKNOWN);
			this.setOnline(OnlineType.UNKNOWN);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
