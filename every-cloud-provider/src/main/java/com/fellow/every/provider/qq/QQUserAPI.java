package com.fellow.every.provider.qq;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.base.AbstractAPI;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.impl.StringHTTPResponseHandler;
import com.fellow.every.user.AccountInfo;
import com.fellow.every.user.UserAPI;
import com.fellow.every.user.UserInfo;
import com.fellow.util.Assert;

public class QQUserAPI extends AbstractAPI implements UserAPI {

	public static final String APP_OAUTH_VERSION = "2.a";
	public static final String APP_FORMAT = "json";
	
	public static final String URL_API = "https://open.t.qq.com";

	public static final String OP_USER_INFO = "/api/user/info";
	public static final String OP_USER_OTHER_INFO = "/api/user/other_info";
	
	public void assertInit(){
		Assert.notNull(this.getHttpEngine(), "HTTPEngine Object is null");
		Assert.hasText(this.getAppKey(), "Application's KEY is null");
		Assert.hasText(this.getAppSecret(), "Application's SECRET is null");
	}
	
	public void assertAccessToken(AccessToken accessToken){
		Assert.notNull(accessToken, "AccessToken is null");
		Assert.notNull(accessToken.getAccessToken(), "AccessToken is null");
		Assert.isInstanceOf(QQAccessToken.class, accessToken, "AccessToken is not an instance of " + QQAccessToken.class);
	}

	@Override
	public AccountInfo myAccount(AccessToken accessToken) throws ServerException, ApiException {
		return myInfo(accessToken);
	}

	@Override
	public UserInfo myInfo(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		UserInfo user = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_USER_INFO);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				user = new QQUserInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return user;
	}

	@Override
	public UserInfo getInfo(AccessToken accessToken, String id) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		UserInfo user = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_USER_OTHER_INFO);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addQueryParameters("fopenid", id);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				user = new QQUserInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return user;
	}

}
