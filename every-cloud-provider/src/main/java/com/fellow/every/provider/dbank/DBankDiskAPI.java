package com.fellow.every.provider.dbank;

import java.io.InputStream;
import java.io.OutputStream;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.base.AbstractAPI;
import com.fellow.every.disk.DiskAPI;
import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.ProgressListener;
import com.fellow.every.disk.QuotaInfo;
import com.fellow.every.disk.ShareInfo;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;

public class DBankDiskAPI extends AbstractAPI implements DiskAPI{

	@Override
	public QuotaInfo quota(AccessToken accessToken) throws ServerException,
			ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileInfo metadata(AccessToken accessToken, String path)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileInfo[] list(AccessToken accessToken, String path)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean mkdir(AccessToken accessToken, String path)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rm(AccessToken accessToken, String path, boolean recycle)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mv(AccessToken accessToken, String path, String newPath)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cp(AccessToken accessToken, String path, String newPath)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void download(AccessToken accessToken, String path, OutputStream os,
			ProgressListener listener) throws ServerException, ApiException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upload(AccessToken accessToken, String path, InputStream is,
			long size, ProgressListener listener) throws ServerException,
			ApiException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream downloadStream(AccessToken accessToken, String path,
			ProgressListener listener) throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream uploadStream(AccessToken accessToken, String path,
			ProgressListener listener) throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShareInfo share(AccessToken accessToken, String path)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream thumbnail(AccessToken accessToken, String path,
			int width, int height) throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

}
