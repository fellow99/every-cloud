package com.fellow.every.status;

import java.io.InputStream;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.disk.ProgressListener;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public interface StatusAPI {

	/**
	 * 根据ID获取单条微博信息
	 */
	StatusInfo get(AccessToken accessToken, String id) throws ServerException, ApiException;

	/**
	 * 根据微博ID批量获取微博信息
	 */
	StatusInfo[] get(AccessToken accessToken, String[] ids) throws ServerException, ApiException;

	/**
	 * 获取最新的公共微博
	 */
	StatusInfo[] listPublic(AccessToken accessToken, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取当前登录用户的最新微博
	 */
	StatusInfo[] listMe(AccessToken accessToken, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取当前登录用户的最新微博的ID
	 */
	String[] idsMe(AccessToken accessToken, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 */
	StatusInfo[] listHome(AccessToken accessToken, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取当前登录用户及其所关注用户的最新微博的ID
	 */
	String[] idsHome(AccessToken accessToken, int page, int size) throws ServerException, ApiException;
	
	
	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 */
	StatusInfo[] listFriends(AccessToken accessToken, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取当前登录用户及其所关注用户的最新微博的ID
	 */
	String[] idsFriends(AccessToken accessToken, int page, int size) throws ServerException, ApiException;

	
	/**
	 * 获取@当前用户的最新微博
	 */
	StatusInfo[] listMentions(AccessToken accessToken, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取@当前用户的最新微博的ID
	 */
	String[] idsMentions(AccessToken accessToken, int page, int size) throws ServerException, ApiException;
	
	
	/**
	 * 获取用户发布的微博
	 */
	StatusInfo[] listUser(AccessToken accessToken, String uid, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取用户发布的微博的ID
	 */
	String[] idsUser(AccessToken accessToken, String uid, int page, int size) throws ServerException, ApiException;
	
	
	/**
	 * 返回一条原创微博的最新转发微博
	 */
	StatusInfo[] listRepost(AccessToken accessToken, String statusId, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取一条原创微博的最新转发微博的ID
	 */
	String[] idsRepost(AccessToken accessToken, String statusId, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取某条微博的评论列表
	 */
	StatusCommentInfo[] listComments(AccessToken accessToken, String statusId, int page, int size) throws ServerException, ApiException;

	
	
	
	/**
	 * 发表一条微博信息
	 */
	StatusInfo add(AccessToken accessToken, String content) throws ServerException, ApiException;

	/**
	 * 发表一条带图片的微博
	 */
	StatusInfo addPic(AccessToken accessToken, String content, InputStream is, long size, ProgressListener listener) throws ServerException, ApiException;

	/**
	 * 发表一条微博信息，带URL功能（图片、音乐、视频等）
	 */
	StatusInfo addUrl(AccessToken accessToken, String content, StatusURL url) throws ServerException, ApiException;
	
	/**
	 * 删除一条微博
	 */
	void delete(AccessToken accessToken, String statusId) throws ServerException, ApiException;
	
	/**
	 * 转发一条微博
	 */
	StatusInfo repost(AccessToken accessToken, String statusId, String content) throws ServerException, ApiException;
	
	/**
	 * 回复一条微博
	 */
	StatusCommentInfo reply(AccessToken accessToken, String statusId, String comment) throws ServerException, ApiException;
	
	/**
	 * 评论一条微博
	 */
	StatusCommentInfo comment(AccessToken accessToken, String id, String comment) throws ServerException, ApiException;
}
