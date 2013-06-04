package com.fellow.every.provider.renren;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class RenrenUserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public RenrenUserInfo(JSONObject json){
		try {
			this.setId(json.getString("uid"));
			this.setName(json.has("name") ?json.getString("name") : null);
			this.setNickname(json.has("screen_name") ?json.getString("screen_name") : getName());
			this.setDescription(json.has("description") ?json.getString("description") : null);
			this.setProvince(json.has("province") ?json.getString("province") : null);
			this.setCity(json.has("city") ?json.getString("city") : null);
			this.setLocation(json.has("location") ?json.getString("location") : null);
			this.setUrlHomepage(json.has("url") ?json.getString("url") : null);
			this.setUrlHead(json.has("tinyurl") ?json.getString("tinyurl") : null);
			this.setUrlHeadBig(json.has("headurl") ?json.getString("headurl") : null);
			
			String sex = (json.has("sex") ?json.getString("sex") : null);
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
