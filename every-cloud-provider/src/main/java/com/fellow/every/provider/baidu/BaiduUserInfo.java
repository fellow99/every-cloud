package com.fellow.every.provider.baidu;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;
import com.fellow.every.user.UserInfo;

public class BaiduUserInfo implements UserInfo{
	private JSONObject json;
	
	public BaiduUserInfo(JSONObject json){
		this.json = json;
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}

	@Override
	public String getId() {
		try {
			return json.getString("userid");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		try {
			return json.getString("username");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getNickname() {
		try {
			return (json.has("realname") ?json.getString("realname") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDescription() {
		try {
			return (json.has("userdetail") ?json.getString("userdetail") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getPhone() {
		return null;
	}

	@Override
	public String getEmail() {
		return null;
	}

	@Override
	public String getBirthday() {
		try {
			return (json.has("birthday") ?json.getString("birthday") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public SexType getSex() {
		try {
			String str = (json.has("sex") ?json.getString("sex") : null);
			if("男".equalsIgnoreCase(str)){
				return SexType.MALE;
			} else if("女".equalsIgnoreCase(str)){
				return SexType.FEMALE;
			} else {
				return SexType.UNKNOWN;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public OnlineType getOnline() {
		return OnlineType.UNKNOWN;
	}

	@Override
	public String getCountry() {
		return null;
	}

	@Override
	public String getProvince() {
		try {
			return (json.has("province") ?json.getString("province") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getCity() {
		try {
			return (json.has("city") ?json.getString("city") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getLocation() {
		return null;
	}

	@Override
	public String getUrlHomepage() {
		return null;
	}

	@Override
	public String getUrlHead() {
		try {
			return "http://tb.himg.baidu.com/sys/portraitn/item/" +
					(json.has("portrait") ?json.getString("portrait") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getUrlHeadBig() {
		try {
			return "http://tb.himg.baidu.com/sys/portrait/item/" +
					(json.has("portrait") ?json.getString("portrait") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getMarriage() {
		try {
			return (json.has("marriage") ?json.getString("marriage") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getEducation() {
		try {
			return (json.has("education") ?json.getString("education") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getTrade() {
		try {
			return (json.has("trade") ?json.getString("trade") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getJob() {
		try {
			return (json.has("job") ?json.getString("job") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
}
