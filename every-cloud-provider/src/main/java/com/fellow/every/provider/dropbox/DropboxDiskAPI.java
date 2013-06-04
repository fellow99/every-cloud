package com.fellow.every.provider.dropbox;

import java.io.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.auth.OAuth10Util;
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
import com.fellow.every.http.HTTPEngine;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.impl.ByteArrayHTTPResponseHandler;
import com.fellow.every.http.impl.SimpleHTTPResponseHandler;
import com.fellow.every.http.impl.StreamHTTPResponseHandler;
import com.fellow.every.http.impl.StringHTTPResponseHandler;
import com.fellow.util.Assert;

public class DropboxDiskAPI extends AbstractAPI implements DiskAPI{

	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";

	public static final String PROPERTY_APP_ROOT = "app.root";
	public static final String APP_ROOT_DROPBOX = "dropbox";
	public static final String APP_ROOT_SANDBOX = "sandbox";
	
	public static final String URL_API = "api.dropbox.com";
	public static final String URL_CONTENT = "api-content.dropbox.com";

	public static final String OP_ACCOUNT = "/1/account/info";
	public static final String OP_METADATA = "/1/metadata";
	public static final String OP_CREATE_FOLDER = "/1/fileops/create_folder";
	public static final String OP_DELETE = "/1/fileops/delete";
	public static final String OP_MOVE = "/1/fileops/move";
	public static final String OP_COPY = "/1/fileops/copy";
	public static final String OP_SHARES = "/1/shares";
	public static final String OP_THUMBNAIL = "/1/thumbnails";

	public static final String OP_FILES = "/1/files";
	public static final String OP_FILES_PUT = "/1/files_put";

	private OAuth10Util oauth10Util;

	public OAuth10Util getOAuth10Util(){
		if(oauth10Util == null){
			oauth10Util = new OAuth10Util(this.getAppKey(), this.getAppSecret());
		}
		return oauth10Util;
	}

	public String getCharset(){
		String charset = this.getProperty(PROPERTY_APP_CHARSET);
		return (charset == null || charset.length() == 0 ? DEFAULT_APP_CHARSET : charset);
	}
	
	public String getRoot(){
		return this.getProperty(PROPERTY_APP_ROOT);
	}

	public void assertInit(){
		Assert.notNull(this.getHttpEngine(), "HTTPEngine Object is null");
		Assert.hasText(this.getAppKey(), "Application's KEY is null");
		Assert.hasText(this.getAppSecret(), "Application's SECRET is null");
		Assert.notNull(this.getOAuth10Util(), "OAuth10Util is null");
		
		Assert.hasText(this.getRoot(), "Application root folder is null");
	}
	
	public void assertAccessToken(AccessToken accessToken){
		Assert.notNull(accessToken, "AccessToken is null");
		Assert.notNull(accessToken.getAccessToken(), "AccessToken is null");
		Assert.isInstanceOf(DropboxAccessToken.class, accessToken, "AccessToken is not an instance of " + DropboxAccessToken.class);
	}

	@Override
	public QuotaInfo quota(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		
		QuotaInfo quota = null;
		
		Map<String, String> params = new HashMap<String, String>();
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, OP_ACCOUNT, params, true);

		try {
			HTTPRequest request = new HTTPRequest(url);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("quota_info");
				quota = new DropboxQuotaInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return quota;
	}
	
	@Override
	public FileInfo metadata(AccessToken accessToken, String path) throws ServerException, ApiException {
		assertInit();
		
		FileInfo file = null;
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("list",new Boolean(false).toString());
		
		StringBuffer location = new StringBuffer();
		location.append(OP_METADATA).append("/").append(this.getRoot())
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);

		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, location.toString(), params, true);

		try {
			HTTPRequest request = new HTTPRequest(url);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				file = new DropboxFileInfo(json);
			}
		} catch (ServerException e) {
			if(new Integer(404).toString().equalsIgnoreCase(e.getCode())){
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
		
		FileInfo[] files = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("list",new Boolean(true).toString());
		
		StringBuffer location = new StringBuffer();
		location.append(OP_METADATA).append("/").append(this.getRoot())
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, location.toString(), params, true);

		try {
			HTTPRequest request = new HTTPRequest(url);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("contents");
				if(list != null){
					files = new DropboxFileInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						DropboxFileInfo file = new DropboxFileInfo(item);
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
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("root", this.getRoot());
		params.put("path", path);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, OP_CREATE_FOLDER, params, true);

		HTTPRequest request = new HTTPRequest(url);
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().get(request, handler);

		return true;
	}

	@Override
	public boolean rm(AccessToken accessToken, String path, boolean recycle) throws ServerException, ApiException {
		assertInit();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("root", this.getRoot());
		params.put("path", path);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, OP_DELETE, params, true);

		HTTPRequest request = new HTTPRequest(url);
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().get(request, handler);

		return true;
	}

	@Override
	public boolean mv(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException {
		assertInit();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("root", this.getRoot());
		params.put("from_path", path);
		params.put("to_path", newPath);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, OP_MOVE, params, true);

		HTTPRequest request = new HTTPRequest(url);
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().get(request, handler);

		return true;
	}

	@Override
	public boolean cp(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException {
		assertInit();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("root", this.getRoot());
		params.put("from_path", path);
		params.put("to_path", newPath);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, OP_COPY, params, true);

		HTTPRequest request = new HTTPRequest(url);
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().get(request, handler);

		return true;
	}

	@Override
	public void download(AccessToken accessToken, String path, OutputStream os, ProgressListener listener) throws ServerException, ApiException {
		assertInit();
		
		StringBuffer location = new StringBuffer();
		location.append(OP_FILES).append("/").append(this.getRoot())
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		Map<String, String> params = new HashMap<String, String>();
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_CONTENT, location.toString(), params, true);

		HTTPRequest request = new HTTPRequest(url);
		HTTPResponseHandler handler = new StreamHTTPResponseHandler(os, listener);
		this.getHttpEngine().get(request, handler);
	}

	@Override
	public void upload(AccessToken accessToken, String path, InputStream is, long size, ProgressListener listener) throws ServerException, ApiException {
		assertInit();

		StringBuffer location = new StringBuffer();
		location.append(OP_FILES).append("/").append(this.getRoot())
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("overwrite", new Boolean(true).toString());
		
		String url = getOAuth10Util().buildURL(
				accessToken, "POST", URL_CONTENT, location.toString(), params, true);

		HTTPRequest request = new HTTPRequest(url);

		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().upload(request, handler, is, size);
	}

	@Override
	public InputStream downloadStream(AccessToken accessToken, String path, ProgressListener listener)
			throws ServerException, ApiException {
		try {
			return new DownloadStream(this, accessToken, path, listener);
        } catch (IOException e) {
        	throw new ApiException(e);
        }
	}

	@Override
	public OutputStream uploadStream(AccessToken accessToken, String path, ProgressListener listener)
			throws ServerException, ApiException {
		return new UploadStream(this, accessToken, path, listener);
	}

	@Override
	public ShareInfo share(AccessToken accessToken, String path) throws ServerException, ApiException {
		assertInit();
		
		ShareInfo share = null;
	
		Map<String, String> params = new HashMap<String, String>();
		
		StringBuffer location = new StringBuffer();
		location.append(OP_SHARES).append("/").append(this.getRoot())
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, location.toString(), params, true);
	
		try {
			HTTPRequest request = new HTTPRequest(url);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				share = new DropboxShareInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return share;
	}

	@Override
	public InputStream thumbnail(AccessToken accessToken, String path, int width, int height)
			throws ServerException, ApiException {
		assertInit();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("root", this.getRoot());
		params.put("path", path);
		if(width < 1024) params.put("size", "xl");
		if(width < 640) params.put("size", "l");
		if(width < 128) params.put("size", "m");
		if(width < 64) params.put("size", "s");
		if(width < 32) params.put("size", "xs");
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, OP_THUMBNAIL, params, true);

		HTTPRequest request = new HTTPRequest(url);
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



