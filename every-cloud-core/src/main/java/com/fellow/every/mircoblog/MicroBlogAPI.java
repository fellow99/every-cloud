package com.fellow.every.mircoblog;

import java.io.InputStream;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.disk.ProgressListener;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public interface MicroBlogAPI {

	/**
	 * 根据ID获取单条微博信息
	 */
	MicroBlogInfo get(AccessToken accessToken, String id) throws ServerException, ApiException;

	/**
	 * 根据微博ID批量获取微博信息
	 */
	MicroBlogInfo[] get(AccessToken accessToken, String[] ids) throws ServerException, ApiException;
	
	/**
	 * 获取某条微博的评论列表
	 */
	MicroBlogCommentInfo[] listComments(AccessToken accessToken, String id, int page, int size) throws ServerException, ApiException;
	

	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 */
	MicroBlogInfo[] listHome(AccessToken accessToken, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取当前登录用户及其所关注用户的最新微博的ID
	 */
	String[] idsHome(AccessToken accessToken, int page, int size) throws ServerException, ApiException;
	

	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 */
	MicroBlogInfo[] listFriend(AccessToken accessToken, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取当前登录用户及其所关注用户的最新微博的ID
	 */
	String[] idsFriend(AccessToken accessToken, int page, int size) throws ServerException, ApiException;
	
	
	/**
	 * 获取用户发布的微博
	 */
	MicroBlogInfo[] listUser(AccessToken accessToken, String uid, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取用户发布的微博的ID
	 */
	String[] idsUser(AccessToken accessToken, String uid, int page, int size) throws ServerException, ApiException;
	
	
	/**
	 * 返回一条原创微博的最新转发微博
	 */
	MicroBlogInfo[] listRepost(AccessToken accessToken, String id, int page, int size) throws ServerException, ApiException;

	/**
	 * 获取一条原创微博的最新转发微博的ID
	 */
	String[] idsRepost(AccessToken accessToken, String id, int page, int size) throws ServerException, ApiException;
	
	
	
	
	/**
	 * 发表一条微博信息
	 */
	MicroBlogInfo add(AccessToken accessToken, String content) throws ServerException, ApiException;

	/**
	 * 发表一条带图片的微博
	 */
	MicroBlogInfo addPic(AccessToken accessToken, String content, InputStream is, long size, ProgressListener listener) throws ServerException, ApiException;

	/**
	 * 发表一条微博信息，带URL功能（图片、音乐、视频等）
	 */
	MicroBlogInfo addUrl(AccessToken accessToken, String content, MicroBlogURL url) throws ServerException, ApiException;
	
	/**
	 * 删除一条微博
	 */
	void delete(AccessToken accessToken, String id) throws ServerException, ApiException;
	
	/**
	 * 转发一条微博
	 */
	MicroBlogInfo repost(AccessToken accessToken, String id, String content) throws ServerException, ApiException;
	
	/**
	 * 回复一条微博
	 */
	MicroBlogCommentInfo reply(AccessToken accessToken, String id, String comment) throws ServerException, ApiException;
	
	/**
	 * 评论一条微博
	 */
	MicroBlogCommentInfo comment(AccessToken accessToken, String id, String comment) throws ServerException, ApiException;
}
