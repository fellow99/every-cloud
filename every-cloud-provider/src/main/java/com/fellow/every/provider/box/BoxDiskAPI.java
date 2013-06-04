package com.fellow.every.provider.box;

import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.base.AbstractAPI;
import com.fellow.every.disk.DiskAPI;
import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.ProgressListener;
import com.fellow.every.disk.QuotaInfo;
import com.fellow.every.disk.ShareInfo;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.impl.StringHTTPResponseHandler;
import com.fellow.util.Assert;

public class BoxDiskAPI extends AbstractAPI implements DiskAPI{

	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";

	public static final String URL_API = "https://api.box.com";

	public static final String OP_USERS_ME = "/2.0/users/me";

	public String getCharset(){
		String charset = this.getProperty(PROPERTY_APP_CHARSET);
		return (charset == null || charset.length() == 0 ? DEFAULT_APP_CHARSET : charset);
	}

	public void assertInit(){
		Assert.notNull(this.getHttpEngine(), "HTTPEngine Object is null");
		Assert.hasText(this.getAppKey(), "Application's KEY is null");
		Assert.hasText(this.getAppSecret(), "Application's SECRET is null");
	}
	
	public void assertAccessToken(AccessToken accessToken){
		Assert.notNull(accessToken, "AccessToken is null");
		Assert.notNull(accessToken.getAccessToken(), "AccessToken is null");
		Assert.isInstanceOf(BoxAccessToken.class, accessToken, "AccessToken is not an instance of " + BoxAccessToken.class);
	}

	@Override
	public QuotaInfo quota(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		
		QuotaInfo quota = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_USERS_ME);
			request.addHeader("Authorization", "Bearer " + accessToken.getAccessToken());

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				quota = new BoxQuotaInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return quota;
	}

	@Override
	public FileInfo metadata(AccessToken accessToken, String path) throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileInfo[] list(AccessToken accessToken, String path) throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean mkdir(AccessToken accessToken, String path) throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rm(AccessToken accessToken, String path, boolean recycle) throws ServerException,
			ApiException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mv(AccessToken accessToken, String path, String newPath) throws ServerException,
			ApiException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cp(AccessToken accessToken, String path, String newPath) throws ServerException,
			ApiException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void download(AccessToken accessToken, String path, OutputStream os, ProgressListener listener)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upload(AccessToken accessToken, String path, InputStream is, long size,
			ProgressListener listener) throws ServerException, ApiException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream downloadStream(AccessToken accessToken, String path, ProgressListener listener)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream uploadStream(AccessToken accessToken, String path, ProgressListener listener)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShareInfo share(AccessToken accessToken, String path) throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream thumbnail(AccessToken accessToken, String path, int width, int height)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}
}
