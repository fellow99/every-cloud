package com.fellow.every.provider.baidu;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.base.AbstractAPI;
import com.fellow.every.disk.DiskAPI;
import com.fellow.every.disk.DownloadStream;
import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.ProgressListener;
import com.fellow.every.disk.QuotaInfo;
import com.fellow.every.disk.ShareInfo;
import com.fellow.every.disk.UploadStream;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.impl.ByteArrayHTTPResponseHandler;
import com.fellow.every.http.impl.SimpleHTTPResponseHandler;
import com.fellow.every.http.impl.StreamHTTPResponseHandler;
import com.fellow.every.http.impl.StringHTTPResponseHandler;
import com.fellow.util.Assert;

public class BaiduDiskAPI extends AbstractAPI implements DiskAPI{

	public static final String PROPERTY_APP_ROOT = "app.root";
	public static final String URL_PCS_QUOTA = "https://pcs.baidu.com/rest/2.0/pcs/quota";
	public static final String URL_PCS_FILE = "https://pcs.baidu.com/rest/2.0/pcs/file";
	public static final String URL_PCS_THUMBNAIL = "https://pcs.baidu.com/rest/2.0/pcs/thumbnail";

	public String getRoot(){
		return this.getProperty(PROPERTY_APP_ROOT);
	}

	public void assertInit(){
		Assert.notNull(this.getHttpEngine(), "HTTPEngine Object is null");
		Assert.hasText(this.getAppKey(), "Application's KEY is null");
		Assert.hasText(this.getAppSecret(), "Application's SECRET is null");
		
		Assert.hasText(this.getRoot(), "Application root folder is null");
	}
	
	public void assertAccessToken(AccessToken accessToken){
		Assert.notNull(accessToken, "AccessToken is null");
		Assert.notNull(accessToken.getAccessToken(), "AccessToken is null");
		Assert.isInstanceOf(BaiduAccessToken.class, accessToken, "AccessToken is not an instance of " + BaiduAccessToken.class);
	}

	@Override
	public QuotaInfo quota(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		BaiduQuotaInfo quota = null;

		try {
			HTTPRequest request = new HTTPRequest(URL_PCS_QUOTA);
			request.addQueryParameters("method", "info");
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				quota = new BaiduQuotaInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return quota;
	}

	@Override
	public FileInfo metadata(AccessToken accessToken, String path) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		String root = this.getRoot();
		
		FileInfo file = null;
		
		try {
			HTTPRequest request = new HTTPRequest(URL_PCS_FILE);
			request.addQueryParameters("method", "meta");
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("path", root + path);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("list");
				if(list != null && list.length() > 0){
					file = new BaiduFileInfo(list.getJSONObject(0), root);
				}
			}
		} catch (ServerException e) {
			if("404".equalsIgnoreCase(e.getCode())){
				return null;
			} else {
				throw e;
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return file;
	}

	@Override
	public FileInfo[] list(AccessToken accessToken, String path) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		String root = this.getRoot();
		
		FileInfo[] files = null;
		
		try {
			HTTPRequest request = new HTTPRequest(URL_PCS_FILE);
			request.addQueryParameters("method", "list");
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("path", root + path);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("list");
				if(list != null){
					files = new BaiduFileInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						BaiduFileInfo file = new BaiduFileInfo(item, root);
						files[i] = file;
					}
				}
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return files;
	}

	@Override
	public boolean mkdir(AccessToken accessToken, String path) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		String root = this.getRoot();
		
		HTTPRequest request = new HTTPRequest(URL_PCS_FILE);
		request.addQueryParameters("method", "mkdir");
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("path", root + path);
		
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);

		return true;
	}

	@Override
	public boolean rm(AccessToken accessToken, String path, boolean recycle) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		String root = this.getRoot();
		
		HTTPRequest request = new HTTPRequest(URL_PCS_FILE);
		request.addQueryParameters("method", "delete");
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("path", root + path);
		
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);

		return true;
	}

	@Override
	public boolean mv(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		String root = this.getRoot();
		
		HTTPRequest request = new HTTPRequest(URL_PCS_FILE);
		request.addQueryParameters("method", "move");
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("from", root + path);
		request.addQueryParameters("to", root + newPath);
		
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);

		return true;
	}

	@Override
	public boolean cp(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		String root = this.getRoot();
		
		HTTPRequest request = new HTTPRequest(URL_PCS_FILE);
		request.addQueryParameters("method", "copy");
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("from", root + path);
		request.addQueryParameters("to", root + newPath);
		
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);

		return true;
	}

	@Override
	public void download(AccessToken accessToken, String path, OutputStream os, ProgressListener listener) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		String root = this.getRoot();
		
		HTTPRequest request = new HTTPRequest(URL_PCS_FILE);
		request.addQueryParameters("method", "download");
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("path", root + path);

		HTTPResponseHandler handler = new StreamHTTPResponseHandler(os, listener);
		this.getHttpEngine().get(request, handler);
	}

	@Override
	public void upload(AccessToken accessToken, String path, InputStream is, long size,
			ProgressListener listener) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		String root = this.getRoot();
		
		HTTPRequest request = new HTTPRequest(URL_PCS_FILE);
		request.addQueryParameters("method", "upload");
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("path", root + path);
		

		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().upload(request, handler, is, size);
	}


	@Override
	public InputStream downloadStream(AccessToken accessToken, String path, ProgressListener listener)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		try {
			return new DownloadStream(this, accessToken, path, listener);
        } catch (IOException e) {
        	throw new ApiException(e);
        }
	}

	@Override
	public OutputStream uploadStream(AccessToken accessToken, String path, ProgressListener listener)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		return new UploadStream(this, accessToken, path, listener);
	}

	@Override
	public ShareInfo share(AccessToken accessToken, String path) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public InputStream thumbnail(AccessToken accessToken, String path, int width, int height)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		String root = this.getRoot();
		
		HTTPRequest request = new HTTPRequest(URL_PCS_THUMBNAIL);
		request.addQueryParameters("method", "generate");
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("path", root + path);
		request.addQueryParameters("quality", new Integer(100).toString());
		request.addQueryParameters("width", new Integer(width).toString());
		request.addQueryParameters("height", new Integer(height).toString());

		ByteArrayHTTPResponseHandler handler = new ByteArrayHTTPResponseHandler(null);
		this.getHttpEngine().get(request, handler);
		
		byte[] buffer = handler.getByteArray();
		if(buffer == null){
			return null;
		} else {
			return new ByteArrayInputStream(buffer);
		}
	}
}
