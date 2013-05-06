package com.fellow.every.provider.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;
import com.fellow.every.user.UserInfo;

public class QQUserInfo extends QQAccountInfo implements UserInfo{
	private JSONObject json;
	
	public QQUserInfo(JSONObject json){
		super(json);
		this.json = getJson();
	}

	@Override
	public String toString(){
		return this.getClass().getName() + "-" + json;
	}

	@Override
	public String getNickname() {
		try {
			return (json.has("nick") ?json.getString("nick") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDescription() {
		try {
			return (json.has("introduction") ?json.getString("introduction") : null);
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
		return null;
	}

	@Override
	public SexType getSex() {
		try {
			String str = (json.has("sex") ?json.getString("sex") : null);
			if("1".equalsIgnoreCase(str)){
				return SexType.MALE;
			} else if("2".equalsIgnoreCase(str)){
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
		try {
			String str = (json.has("online_status") ?json.getString("online_status") : null);
			if("true".equalsIgnoreCase(str)){
				return OnlineType.ONLINE;
			} else if("false".equalsIgnoreCase(str)){
				return OnlineType.OFFLINE;
			} else {
				return OnlineType.UNKNOWN;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
		try {
			return (json.has("location") ?json.getString("location") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getUrlHomepage() {
		try {
			return (json.has("homepage") ?json.getString("homepage") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getUrlHead() {
		try {
			return (json.has("profile_image_url") ?json.getString("profile_image_url") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getUrlHeadBig() {
		return getUrlHead();
	}

	@Override
	public String getMarriage() {
		return null;
	}

	@Override
	public String getEducation() {
		return null;
	}

	@Override
	public String getTrade() {
		return null;
	}

	@Override
	public String getJob() {
		return null;
	}
}
