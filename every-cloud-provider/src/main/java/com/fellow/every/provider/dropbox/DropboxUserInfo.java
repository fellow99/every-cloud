package com.fellow.every.provider.dropbox;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class DropboxUserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public DropboxUserInfo(JSONObject json){
		try {
			this.setId(json.getString("uid"));
			this.setName(json.getString("display_name"));
			this.setEmail(json.has("email") ?json.getString("email") : null);
			this.setCountry(json.has("country") ?json.getString("country") : null);
			this.setSex(SexType.UNKNOWN);
			this.setOnline(OnlineType.UNKNOWN);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
}
