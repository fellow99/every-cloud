package com.fellow.every.user;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public interface UserAPI {
	
	AccountInfo myAccount(AccessToken accessToken) throws ServerException, ApiException;
	
	UserInfo myInfo(AccessToken accessToken) throws ServerException, ApiException;
	
	UserInfo getInfo(AccessToken accessToken, String id) throws ServerException, ApiException;
}
