package com.fellow.every.provider.a360;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class A360UserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public A360UserInfo(JSONObject json){
		try {
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
			this.setNickname(json.has("name") ?json.getString("name") : null);
			this.setLocation(json.has("area") ?json.getString("area") : null);
			this.setUrlHead(json.has("avatar") ?json.getString("avatar") : null);
			this.setUrlHeadBig(json.has("avatar") ?json.getString("avatar") : null);
			this.setSex(SexType.UNKNOWN);
			this.setOnline(OnlineType.UNKNOWN);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
