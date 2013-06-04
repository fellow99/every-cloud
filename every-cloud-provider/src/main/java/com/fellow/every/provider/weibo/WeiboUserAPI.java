package com.fellow.every.provider.weibo;

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

public class WeiboUserAPI extends AbstractAPI implements UserAPI{

	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";
	
	public static final String URL_API = "https://api.weibo.com";

	public static final String OP_ACCOUNT_GET_UID = "/2/account/get_uid.json";

	public static final String OP_USERS_SHOW = "/2/users/show.json";

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
		Assert.isInstanceOf(WeiboAccessToken.class, accessToken, "AccessToken is not an instance of " + WeiboAccessToken.class);
	}

	@Override
	public AccountInfo myAccount(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		AccountInfo account = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_ACCOUNT_GET_UID);
			request.addQueryParameters("access_token", accessToken.getAccessToken());

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				account = new WeiboAccountInfo(json);
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
		
		AccountInfo account = this.myAccount(accessToken);

		if(account ==  null || account.getId() == null){
			throw new ApiException("Account not found");
		}
		UserInfo user = this.getInfo(accessToken, account.getId());
		
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
			request.addQueryParameters("uid", id);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				user = new WeiboUserInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return user;
	}
}
