package com.fellow.every.disk;

import java.io.*;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public interface DiskAPI {
	
	/**
	 * 获取网盘空间额度
	 */
	QuotaInfo quota(AccessToken accessToken) throws ServerException, ApiException;
	
	/**
	 * 获取一个目录或文件的元数据
	 */
	FileInfo metadata(AccessToken accessToken, String path) throws ServerException, ApiException;
	
	/**
	 * 获取一个目录内的信息
	 */
	FileInfo[] list(AccessToken accessToken, String path) throws ServerException, ApiException;
	
	/**
	 * 创建一个目录
	 */
	boolean mkdir(AccessToken accessToken, String path) throws ServerException, ApiException;
	
	/**
	 * 删除一个目录或文件
	 */
	boolean rm(AccessToken accessToken, String path, boolean recycle) throws ServerException, ApiException;

	/**
	 * 移动一个目录或文件
	 */
	boolean mv(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException;

	/**
	 * 复制一个目录或文件
	 */
	boolean cp(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException;

	/**
	 * 下载一个文件
	 */
	void download(AccessToken accessToken, String path, OutputStream os, ProgressListener listener) throws ServerException, ApiException;

	/**
	 * 上传一个文件
	 */
	void upload(AccessToken accessToken, String path, InputStream is, long size, ProgressListener listener) throws ServerException, ApiException;
	
	/**
	 * 获取一个文件的下载流
	 */
	InputStream downloadStream(AccessToken accessToken, String path, ProgressListener listener) throws ServerException, ApiException;

	/**
	 * 获取一个文件的上传流
	 */
	OutputStream uploadStream(AccessToken accessToken, String path, ProgressListener listener) throws ServerException, ApiException;

	


	/**
	 * 分享一个文件
	 */
	ShareInfo share(AccessToken accessToken, String path) throws ServerException, ApiException;

	/**
	 * 获取一个文件的缩略图
	 */
	InputStream thumbnail(AccessToken accessToken, String path, int width, int height) throws ServerException, ApiException;
}
