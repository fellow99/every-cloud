package com.fellow.every.provider.box;

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

public class BoxUserAPI extends AbstractAPI implements UserAPI{

	public static final String URL_API = "https://api.box.com";

	public static final String OP_USERS_ME = "/2.0/users/me";

	public void assertInit(){
		Assert.notNull(this.getHttpEngine(), "HTTPEngine Object is null");
		Assert.hasText(this.getAppKey(), "Application's KEY is null");
		Assert.hasText(this.getAppSecret(), "Application's SECRET is null");
	}
	
	public void assertAccessToken(AccessToken accessToken){
		Assert.notNull(accessToken, "AccessToken is null");
		Assert.notNull(accessToken.getAccessToken(), "AccessToken is null");
		Assert.isInstanceOf(BoxAccessToken.class, accessToken, "AccessToken is not an instance of " + BoxAccessToken.class);
	}

	@Override
	public AccountInfo myAccount(AccessToken accessToken) throws ServerException, ApiException {
		return myInfo(accessToken);
	}

	@Override
	public UserInfo myInfo(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		UserInfo user = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_USERS_ME);
			request.addHeader("Authorization", "Bearer " + accessToken.getAccessToken());
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				user = new BoxUserInfo(json);
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
		
		throw new java.lang.UnsupportedOperationException();
	}

}
