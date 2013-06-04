package com.fellow.every.provider.baidu;

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

public class BaiduUserAPI extends AbstractAPI implements UserAPI{

	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";

	public static final String URL_OPEN_API = "https://openapi.baidu.com";
	public static final String URL_PASSPORT_USERS_MY = "/rest/2.0/passport/users/getLoggedInUser";
	public static final String URL_PASSPORT_USERS_INFO = "/rest/2.0/passport/users/getInfo";

	public String getCharset(){
		String charset = this.getProperty(PROPERTY_APP_CHARSET);
		return (charset == null || charset.length() == 0 ? DEFAULT_APP_CHARSET : charset);
	}

	public void assertInit(){
		Assert.notNull(this.getHttpEngine(), "HTTPEngine Object is null");
		Assert.hasText(this.getAppKey(), "Application's KEY is null");
		Assert.hasText(this.getAppSecret(), "Application's SECRET is null");
	}
	
	public void assertAccessToken(AccessToken accessToken){
		Assert.notNull(accessToken, "AccessToken is null");
		Assert.notNull(accessToken.getAccessToken(), "AccessToken is null");
		Assert.isInstanceOf(BaiduAccessToken.class, accessToken, "AccessToken is not an instance of " + BaiduAccessToken.class);
	}

	@Override
	public AccountInfo myAccount(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		AccountInfo account = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_OPEN_API + URL_PASSPORT_USERS_MY);
			request.addQueryParameters("access_token", accessToken.getAccessToken());

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				account = new BaiduAccountInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return account;
	}

	@Override
	public UserInfo myInfo(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		UserInfo user = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_OPEN_API + URL_PASSPORT_USERS_INFO);
			request.addQueryParameters("access_token", accessToken.getAccessToken());

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				user = new BaiduUserInfo(json);
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
		
		UserInfo user = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_OPEN_API + URL_PASSPORT_USERS_INFO);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("uid", id);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				user = new BaiduUserInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return user;
	}
}
