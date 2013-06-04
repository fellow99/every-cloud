package com.fellow.every.provider.kaixin;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class KaixinUserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public KaixinUserInfo(JSONObject json){
		try {
			this.setId(json.getString("uid"));
			this.setName(json.getString("name"));
			this.setNickname(json.has("screen_name") ?json.getString("screen_name") : null);
			this.setDescription(json.has("description") ?json.getString("description") : null);
			this.setCity(json.has("city") ?json.getString("city") : null);
			this.setUrlHead(json.has("logo50") ?json.getString("logo50") : null);
			this.setUrlHeadBig(json.has("logo120") ?json.getString("logo120") : null);
			this.setMarriage(json.has("marriage") ?json.getString("marriage") : null);
			
			String sex = (json.has("gender") ?json.getString("gender") : null);
			if("1".equalsIgnoreCase(sex)){
				this.setSex(SexType.MALE);
			} else if("2".equalsIgnoreCase(sex)){
				this.setSex(SexType.FEMALE);
			} else {
				this.setSex(SexType.UNKNOWN);
			}
			
			String online = (json.has("online_status") ?json.getString("online_status") : null);
			if("true".equalsIgnoreCase(online)){
				this.setOnline(OnlineType.ONLINE);
			} else if("false".equalsIgnoreCase(online)){
				this.setOnline(OnlineType.OFFLINE);
			} else {
				this.setOnline(OnlineType.UNKNOWN);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
