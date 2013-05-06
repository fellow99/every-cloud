package com.fellow.every.provider.box;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;
import com.fellow.every.user.UserInfo;

public class BoxUserInfo extends BoxAccountInfo implements UserInfo{
	private JSONObject json;

	public BoxUserInfo(JSONObject json){
		super(json);
		this.json = this.getJson();
	}

	@Override
	public String getNickname() {
		try {
			return (json.has("name") ?json.getString("name") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getPhone() {
		try {
			return (json.has("phone") ?json.getString("phone") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getEmail() {
		try {
			return (json.has("login") ?json.getString("login") : null);
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
		try {
			return (json.has("country") ?json.getString("country") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
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
		try {
			return (json.has("address") ?json.getString("address") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getUrlHomepage() {
		return null;
	}

	@Override
	public String getUrlHead() {
		try {
			return (json.has("avatar_url") ?json.getString("avatar_url") : null);
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
		try {
			return (json.has("job_title") ?json.getString("job_title") : null);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
	
}
