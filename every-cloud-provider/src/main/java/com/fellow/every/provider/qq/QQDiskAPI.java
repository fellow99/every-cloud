package com.fellow.every.provider.qq;

import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
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
import com.fellow.every.http.impl.StringHTTPResponseHandler;
import com.fellow.every.provider.baidu.BaiduAccessToken;
import com.fellow.every.provider.baidu.BaiduFileInfo;
import com.fellow.util.Assert;

public class QQDiskAPI extends AbstractAPI implements DiskAPI{
	
	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";

	public static final String URL_API_20 = "https://graph.qq.com/weiyun/";
	
	public static final String OP_GET_LIBRARY_TYPE = "get_library_type";
	public static final String OP_GET_FILE_LIST = "get_file_list";
	public static final String OP_UPLOAD_FILE = "upload_file";


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
		Assert.isInstanceOf(QQAccessToken.class, accessToken, "AccessToken is not an instance of " + QQAccessToken.class);
	}
	
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
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		FileInfo[] files = null;
		
		try {
			HTTPRequest request = new HTTPRequest(URL_API_20 + OP_GET_FILE_LIST);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("appid", "100701199");
			request.addQueryParameters("libtype", "document");
			request.addQueryParameters("offset", "0");
			request.addQueryParameters("number", "200");

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				int ret = json.getInt("ret");
				if(ret == 0){
					JSONObject data = json.getJSONObject("data");
					JSONArray list = (data != null ? json.getJSONArray("content") : null);
					if(list != null){
						files = new QQFileInfo[list.length()];
						for(int i = 0; i < list.length(); i++){
							JSONObject item = list.getJSONObject(i);
							QQFileInfo file = new QQFileInfo(item);
							files[i] = file;
						}
					}
				}
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return files;
	}

	@Override
	public boolean mkdir(AccessToken accessToken, String path)
			throws ServerException, ApiException {
		throw new java.lang.UnsupportedOperationException("API unsupported");
	}

	@Override
	public boolean rm(AccessToken accessToken, String path, boolean recycle)
			throws ServerException, ApiException {
		throw new java.lang.UnsupportedOperationException("API unsupported");
	}

	@Override
	public boolean mv(AccessToken accessToken, String path, String newPath)
			throws ServerException, ApiException {
		throw new java.lang.UnsupportedOperationException("API unsupported");
	}

	@Override
	public boolean cp(AccessToken accessToken, String path, String newPath)
			throws ServerException, ApiException {
		throw new java.lang.UnsupportedOperationException("API unsupported");
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
