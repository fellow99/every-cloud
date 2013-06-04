package com.fellow.every.one.web.filter;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.user.UserInfo;

public class LoginUserContextHolder {
	private static ThreadLocal<String> holderProvider = new ThreadLocal<String>();
	private static ThreadLocal<AccessToken> holderAccessToken = new ThreadLocal<AccessToken>();
	private static ThreadLocal<UserInfo> holderUserInfo = new ThreadLocal<UserInfo>();

	public static void set(String provider, AccessToken accessToken, UserInfo userInfo){
		holderProvider.set(provider);
		holderAccessToken.set(accessToken);
		holderUserInfo.set(userInfo);
	}
	
	public static String getProvider(){
		return holderProvider.get();
	}
	
	public static AccessToken getAccessToken(){
		return holderAccessToken.get();
	}
	
	public static UserInfo getUserInfo(){
		return holderUserInfo.get();
	}
}
