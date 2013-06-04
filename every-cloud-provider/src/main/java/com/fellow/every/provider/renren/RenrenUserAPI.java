package com.fellow.every.provider.renren;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.base.AbstractAPI;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.impl.StringHTTPResponseHandler;
import com.fellow.every.user.AccountInfo;
import com.fellow.every.user.UserAPI;
import com.fellow.every.user.UserInfo;
import com.fellow.util.Assert;

public class RenrenUserAPI extends AbstractAPI implements UserAPI{

	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";
	
	public static final String URL_API = "https://api.renren.com/restserver.do";

	public static final String OP_USERS_GET_INFO = "users.getInfo";
	public static final String OP_USERS_GET_LOGIN = "users.getLoggedInUser";

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
		Assert.isInstanceOf(RenrenAccessToken.class, accessToken, "AccessToken is not an instance of " + RenrenAccessToken.class);
	}

	@Override
	public AccountInfo myAccount(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		AccountInfo account = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_LOGIN);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				account = new RenrenAccountInfo(json);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		UserInfo user = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_INFO);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONArray json = new JSONArray(info);
				user = new RenrenUserInfo(json.getJSONObject(0));
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		UserInfo user = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_INFO);
			request.addQueryParameters("uids", id);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONArray json = new JSONArray(info);
				user = new RenrenUserInfo(json.getJSONObject(0));
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return user;
	}
}
