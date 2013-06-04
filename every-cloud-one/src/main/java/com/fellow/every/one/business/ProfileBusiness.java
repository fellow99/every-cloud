package com.fellow.every.one.business;

import com.fellow.every.auth.AccessToken;

public interface ProfileBusiness {
	
	boolean existsProfile(String provider, String userId);
	
	
	
	void addProfile(String provider, String userId, String raw);
	
	void removeProfile(String provider, String userId);
	
	void addSubToken(String provider, String userId, String subProvider, String raw);
	
	void removeSubToken(String provider, String userId, String subProvider);
	

	
	AccessToken getAccessToken(String provider, String userId);
	
	AccessToken getSubToken(String provider, String userId, String subProvider);
}
