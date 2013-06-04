package com.fellow.every.provider.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class QQUserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public QQUserInfo(JSONObject json){
		try {
			this.setId(json.getString("openid"));
			this.setName(json.getString("name"));
			this.setNickname(json.has("nick") ?json.getString("nick") : null);
			this.setDescription(json.has("introduction") ?json.getString("introduction") : null);
			this.setProvince(json.has("province") ?json.getString("province") : null);
			this.setCity(json.has("city") ?json.getString("city") : null);
			this.setLocation(json.has("location") ?json.getString("location") : null);
			this.setUrlHomepage(json.has("homepage") ?json.getString("homepage") : null);
			this.setUrlHead(json.has("head") ?json.getString("head")+"/50" : null);
			this.setUrlHeadBig(json.has("head") ?json.getString("head")+"/100" : null);

			String sex = (json.has("sex") ?json.getString("sex") : null);
			if("1".equalsIgnoreCase(sex)){
				this.setSex(SexType.MALE);
			} else if("2".equalsIgnoreCase(sex)){
				this.setSex(SexType.FEMALE);
			} else {
				this.setSex(SexType.UNKNOWN);
			}

			String online_status = (json.has("online_status") ?json.getString("online_status") : null);
			if("true".equalsIgnoreCase(online_status)){
				this.setOnline(OnlineType.ONLINE);
			} else if("false".equalsIgnoreCase(online_status)){
				this.setOnline(OnlineType.OFFLINE);
			} else {
				this.setOnline(OnlineType.UNKNOWN);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

	}
}
