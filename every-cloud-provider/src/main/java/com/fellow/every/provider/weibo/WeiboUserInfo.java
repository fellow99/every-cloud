package com.fellow.every.provider.weibo;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class WeiboUserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public WeiboUserInfo(JSONObject json){
		try {
			this.setId(json.getString("id"));
			this.setName(json.getString("name"));
			this.setNickname(json.has("screen_name") ?json.getString("screen_name") : null);
			this.setDescription(json.has("description") ?json.getString("description") : null);
			this.setProvince(json.has("province") ?json.getString("province") : null);
			this.setCity(json.has("city") ?json.getString("city") : null);
			this.setLocation(json.has("location") ?json.getString("location") : null);
			this.setUrlHomepage(json.has("url") ?json.getString("url") : null);
			this.setUrlHead(json.has("profile_image_url") ?json.getString("profile_image_url") : null);
			this.setUrlHeadBig(this.getUrlHead());
			

			String sex = (json.has("gender") ?json.getString("gender") : null);
			if("m".equalsIgnoreCase(sex)){
				this.setSex(SexType.MALE);
			} else if("f".equalsIgnoreCase(sex)){
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
