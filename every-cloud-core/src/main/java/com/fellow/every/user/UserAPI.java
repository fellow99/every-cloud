package com.fellow.every.user;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public interface UserAPI {
	
	/**
	 * 获取登录用户账号信息
	 */
	AccountInfo myAccount(AccessToken accessToken) throws ServerException, ApiException;

	/**
	 * 获取登录用户信息
	 */
	UserInfo myInfo(AccessToken accessToken) throws ServerException, ApiException;

	/**
	 * 获取指定用户信息
	 */
	UserInfo getInfo(AccessToken accessToken, String uid) throws ServerException, ApiException;
}
