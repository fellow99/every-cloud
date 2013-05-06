package com.fellow.every.disk;

import java.io.*;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public interface DiskAPI {
	
	QuotaInfo quota(AccessToken accessToken) throws ServerException, ApiException;
	
	FileInfo metadata(AccessToken accessToken, String path) throws ServerException, ApiException;
	
	FileInfo[] list(AccessToken accessToken, String path) throws ServerException, ApiException;
	
	boolean mkdir(AccessToken accessToken, String path) throws ServerException, ApiException;
	
	boolean rm(AccessToken accessToken, String path, boolean recycle) throws ServerException, ApiException;
	
	boolean mv(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException;
	
	boolean cp(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException;
	
	void download(AccessToken accessToken, String path, OutputStream os, ProgressListener listener) throws ServerException, ApiException;
	
	void upload(AccessToken accessToken, String path, InputStream is, long size, ProgressListener listener) throws ServerException, ApiException;
	
	InputStream downloadStream(AccessToken accessToken, String path, ProgressListener listener) throws ServerException, ApiException;

	OutputStream uploadStream(AccessToken accessToken, String path, ProgressListener listener) throws ServerException, ApiException;

	

	
	ShareInfo share(AccessToken accessToken, String path) throws ServerException, ApiException;

	InputStream thumbnail(AccessToken accessToken, String path, int width, int height) throws ServerException, ApiException;
}
