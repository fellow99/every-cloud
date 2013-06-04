package com.fellow.every.one.business.profile;

import java.util.*;
import java.util.concurrent.*;
import org.springframework.stereotype.Service;

import com.fellow.every.one.business.EveryCloudBusiness;
import com.fellow.every.one.business.ProfileBusiness;
import com.fellow.every.auth.AccessToken;
import com.fellow.every.exception.ApiException;

@Service
public class ProfileBusinessImpl implements ProfileBusiness{

	private EveryCloudBusiness everyCloudBusiness;
	private Map<String, ProfileInfo> profileContexts;
	
	public ProfileBusinessImpl(){
		profileContexts = new ConcurrentHashMap<String, ProfileInfo>();
	}

	public EveryCloudBusiness getEveryCloudBusiness() {
		return everyCloudBusiness;
	}

	public void setEveryCloudBusiness(EveryCloudBusiness everyCloudBusiness) {
		this.everyCloudBusiness = everyCloudBusiness;
	}

	protected String getKey(String provider, String userId){
		return provider + "-" + userId;
	}
	
	@Override
	public boolean existsProfile(String provider, String userId) {
		final String KEY = this.getKey(provider, userId);
		return profileContexts.containsKey(KEY);
	}

	@Override
	public void addProfile(String provider, String userId, String raw) {
		final String KEY = this.getKey(provider, userId);
		
		AccessToken accessToken = everyCloudBusiness.createAccessToken(provider);
		try {
			accessToken.load(raw);
		} catch (ApiException e) {
			throw new RuntimeException();
		}
		ProfileInfo profile = new ProfileInfo(provider, userId, accessToken);
		profileContexts.put(KEY, profile);
	}

	@Override
	public void removeProfile(String provider, String userId) {
		final String KEY = this.getKey(provider, userId);
		profileContexts.remove(KEY);
	}

	@Override
	public void addSubToken(String provider, String userId, String subProvider, String raw) {
		final String KEY = this.getKey(provider, userId);
		ProfileInfo profile = profileContexts.get(KEY);
		if(profile == null){
			throw new RuntimeException("Profile not found: " + KEY);
		}
		
		try {
			AccessToken subToken = everyCloudBusiness.createAccessToken(subProvider);
			subToken.load(raw);
			profile.addSubToken(subProvider, subToken);
		} catch (ApiException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void removeSubToken(String provider, String userId, String subProvider) {
		final String KEY = this.getKey(provider, userId);
		ProfileInfo profile = profileContexts.get(KEY);
		if(profile == null){
			throw new RuntimeException("Profile not found: " + KEY);
		}
		
		profile.removeSubToken(subProvider);
	}

	@Override
	public AccessToken getAccessToken(String provider, String userId) {
		final String KEY = this.getKey(provider, userId);
		ProfileInfo profile = profileContexts.get(KEY);
		if(profile == null){
			throw new RuntimeException("Profile not found: " + KEY);
		}
		
		return profile.getAccessToken();
	}

	@Override
	public AccessToken getSubToken(String provider, String userId, String subProvider) {
		final String KEY = this.getKey(provider, userId);
		ProfileInfo profile = profileContexts.get(KEY);
		if(profile == null){
			throw new RuntimeException("Profile not found: " + KEY);
		}
		
		return profile.getSubToken(subProvider);
	}
}
