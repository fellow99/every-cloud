package com.fellow.every.one.business.profile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fellow.every.auth.AccessToken;

public class ProfileInfo {
	private String provider;
	private String userId;
	private AccessToken accessToken;
	private Map<String, AccessToken> subTokens;
	
	public ProfileInfo(String provider, String userId, AccessToken accessToken){
		this.provider = provider;
		this.userId = userId;
		this.accessToken = accessToken;
		this.subTokens = new ConcurrentHashMap<String, AccessToken>();
		this.addSubToken(provider, accessToken);
	}
	
	public String getProvider() {
		return provider;
	}
	public String getUserId() {
		return userId;
	}
	public AccessToken getAccessToken() {
		return accessToken;
	}
	public void addSubToken(String subProvider, AccessToken subToken){
		subTokens.put(subProvider, subToken);
	}
	public void removeSubToken(String subProvider){
		subTokens.remove(subProvider);
	}
	public AccessToken getSubToken(String subProvider){
		return subTokens.get(subProvider);
	}
	public String[] getSubProviders(){
		String[] providers = new String[subTokens.size()];
		subTokens.keySet().toArray(providers);
		return providers;
	}
}
