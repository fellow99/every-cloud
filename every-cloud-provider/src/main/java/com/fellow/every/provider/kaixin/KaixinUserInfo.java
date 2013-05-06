package com.fellow.every.provider.kaixin;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;
import com.fellow.every.user.UserInfo;

public class KaixinUserInfo extends KaixinAccountInfo implements UserInfo{
	private JSONObject json;
	
	public KaixinUserInfo(JSONObject json){
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
			return (json.has("screen_name") ?json.getString("screen_name") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDescription() {
		try {
			return (json.has("description") ?json.getString("description") : null);
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
			String str = (json.has("gender") ?json.getString("gender") : null);
			if("0".equalsIgnoreCase(str)){
				return SexType.MALE;
			} else if("1".equalsIgnoreCase(str)){
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
			String str = (json.has("online") ?json.getString("online") : null);
			if("1".equalsIgnoreCase(str)){
				return OnlineType.ONLINE;
			} else if("0".equalsIgnoreCase(str)){
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
		return null;
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
			return (json.has("logo50") ?json.getString("logo50") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getUrlHeadBig() {
		try {
			return (json.has("logo120") ?json.getString("logo120") : null);
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
