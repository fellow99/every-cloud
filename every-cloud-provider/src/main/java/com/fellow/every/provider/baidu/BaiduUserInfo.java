package com.fellow.every.provider.baidu;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.base.AbstractUserInfo;
import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;

public class BaiduUserInfo extends AbstractUserInfo{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BaiduUserInfo(JSONObject json){
		try {
			this.setId(json.getString("userid"));
			this.setName(json.getString("username"));
			this.setNickname(json.has("realname") ?json.getString("realname") : getName());
			this.setDescription(json.has("userdetail") ?json.getString("userdetail") : null);
			this.setBirthday(json.has("birthday") ?json.getString("birthday") : null);
			this.setProvince(json.has("province") ?json.getString("province") : null);
			this.setCity(json.has("city") ?json.getString("city") : null);
			this.setUrlHead("http://tb.himg.baidu.com/sys/portraitn/item/" +
					(json.has("portrait") ?json.getString("portrait") : null));
			this.setUrlHeadBig(this.getUrlHead());
			this.setMarriage(json.has("marriage") ?json.getString("marriage") : null);
			this.setEducation(json.has("education") ?json.getString("education") : null);
			this.setTrade(json.has("trade") ?json.getString("trade") : null);
			this.setJob(json.has("job") ?json.getString("job") : null);
			
			String sex = (json.has("sex") ?json.getString("sex") : null);
			if("男".equalsIgnoreCase(sex)){
				this.setSex(SexType.MALE);
			} else if("女".equalsIgnoreCase(sex)){
				this.setSex(SexType.FEMALE);
			} else {
				this.setSex(SexType.UNKNOWN);
			}

			this.setOnline(OnlineType.UNKNOWN);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
}
