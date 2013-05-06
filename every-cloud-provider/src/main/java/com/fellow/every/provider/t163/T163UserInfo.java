package com.fellow.every.provider.t163;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;
import com.fellow.every.user.UserInfo;

public class T163UserInfo extends T163AccountInfo implements UserInfo{
	private JSONObject json;

	public T163UserInfo(JSONObject json){
		super(json);
		this.json = this.getJson();
	}

	@Override
	public String getNickname() {
		return getName();
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getPhone() {
		try {
			return (json.has("mobile") ?json.getString("mobile") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getEmail() {
		try {
			return (json.has("user_name") ?json.getString("user_name") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getBirthday() {
		return null;
	}

	@Override
	public SexType getSex() {
		return SexType.UNKNOWN;
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
		return null;
	}

	@Override
	public String getCity() {
		return null;
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
		return null;
	}

	@Override
	public String getUrlHeadBig() {
		return null;
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
