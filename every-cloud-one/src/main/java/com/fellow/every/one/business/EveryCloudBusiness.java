package com.fellow.every.one.business;

import java.util.Collection;

import org.scribe.builder.api.Api;
import org.scribe.model.OAuthConfig;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.blog.BlogAPI;
import com.fellow.every.disk.DiskAPI;
import com.fellow.every.friend.FriendAPI;
import com.fellow.every.status.StatusAPI;
import com.fellow.every.user.UserAPI;

public interface EveryCloudBusiness {
	
	public enum Capability {
		AUTH, USER, FRIEND, STATUS, BLOG, DISK, PHOTO;
	}
	
	public static final String SESSION_LOGIN_ACCESS_TOKEN = "SESSION_LOGIN_ACCESS_TOKEN";
	
	public static final String SESSION_LOGIN_USER_INFO = "SESSION_LOGIN_USER_INFO";
	
	public static final String SESSION_LOGIN_PROVIDER = "SESSION_LOGIN_PROVIDER";
	
	Collection<String> getProviders();
	
	String getProviderName(String provider);
	
	Collection<Capability> getCapabilities(String provider);
	
	boolean isCapable(String provider, Capability capability);
	
	OAuthConfig getOAuthConfig(String provider);
	
	Api getOAuthAPI(String provider);
	
	AccessToken createAccessToken(String provider);
	
	UserAPI getUserAPI(String provider);
	
	FriendAPI getFriendAPI(String provider);
	
	StatusAPI getStatusAPI(String provider);
	
	BlogAPI getBlogAPI(String provider);
	
	DiskAPI getDiskAPI(String provider);
}
