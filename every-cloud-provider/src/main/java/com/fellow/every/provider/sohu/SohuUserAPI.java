package com.fellow.every.provider.sohu;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.auth.OAuth10Util;
import com.fellow.every.base.AbstractAPI;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.impl.StringHTTPResponseHandler;
import com.fellow.every.user.AccountInfo;
import com.fellow.every.user.UserAPI;
import com.fellow.every.user.UserInfo;
import com.fellow.util.Assert;

public class SohuUserAPI extends AbstractAPI implements UserAPI{

	public static final String URL_API = "openapi.kuaipan.cn";

	public static final String OP_ACCOUNT = "/1/account_info";

	private OAuth10Util oauth10Util;
	
	public OAuth10Util getOAuth10Util(){
		if(oauth10Util == null){
			new OAuth10Util(this.getAppKey(), this.getAppSecret());
		}
		return oauth10Util;
	}

	public void assertInit(){
		Assert.notNull(this.getHttpEngine(), "HTTPEngine Object is null");
		Assert.hasText(this.getAppKey(), "Application's KEY is null");
		Assert.hasText(this.getAppSecret(), "Application's SECRET is null");
		Assert.notNull(this.getOAuth10Util(), "OAuth10Util is null");
	}
	
	public void assertAccessToken(AccessToken accessToken){
		Assert.notNull(accessToken, "AccessToken is null");
		Assert.notNull(accessToken.getAccessToken(), "AccessToken is null");
		Assert.isInstanceOf(SohuAccessToken.class, accessToken, "AccessToken is not an instance of " + SohuAccessToken.class);
	}

	@Override
	public AccountInfo myAccount(AccessToken accessToken) throws ServerException, ApiException {
		return myInfo(accessToken);
	}

	@Override
	public UserInfo myInfo(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();

		UserInfo user = null;

		Map<String, String> params = new HashMap<String, String>();

		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, OP_ACCOUNT, params, true);

		try {
			HTTPRequest request = new HTTPRequest(url);
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				user = new SohuUserInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return user;
	}

	@Override
	public UserInfo getInfo(AccessToken accessToken, String id) throws ServerException, ApiException {
		throw new java.lang.UnsupportedOperationException();
	}

}
