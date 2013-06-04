package com.fellow.every.provider.kaixin;

import org.json.JSONArray;
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

public class KaixinUserAPI extends AbstractAPI implements UserAPI{

	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";

	public static final String APP_VERSION = "1.0";
	public static final String APP_FORMAT = "JSON";
	
	public static final String URL_API = "https://api.kaixin001.com";

	public static final String OP_USERS_ME = "/users/me";
	public static final String OP_USERS_SHOW = "/users/show";

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
		Assert.isInstanceOf(KaixinAccessToken.class, accessToken, "AccessToken is not an instance of " + KaixinAccessToken.class);
	}

	@Override
	public AccountInfo myAccount(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		AccountInfo account = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_USERS_ME);
			request.addQueryParameters("access_token", accessToken.getAccessToken());

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				account = new KaixinAccountInfo(json);
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
			HTTPRequest request = new HTTPRequest(URL_API + OP_USERS_ME);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("fields", "uid,name,gender,hometown,city,status,logo120,logo50,birthday,bodyform,blood,marriage,trainwith,interest,favbook,favmovie,favtv,idol,motto,wishlist,intro,education,schooltype,school,class,year,career,company,dept,beginyear,beginmonth,endyear,endmonth,isStar,pinyin,online");

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				user = new KaixinUserInfo(json);
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
			HTTPRequest request = new HTTPRequest(URL_API + OP_USERS_SHOW);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("fields", "uid,name,gender,hometown,city,status,logo120,logo50,birthday,bodyform,blood,marriage,trainwith,interest,favbook,favmovie,favtv,idol,motto,wishlist,intro,education,schooltype,school,class,year,career,company,dept,beginyear,beginmonth,endyear,endmonth,isStar,pinyin,online");
			request.addQueryParameters("uids", id);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("users");
				user = new KaixinUserInfo(list.getJSONObject(0));
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return user;
	}
}
