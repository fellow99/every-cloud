package com.fellow.every.provider.kuaipan;

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
import com.fellow.every.provider.baidu.BaiduAccessToken;
import com.fellow.util.Assert;

public class KuaipanDiskAPI extends AbstractAPI implements DiskAPI{

	public static final String PROPERTY_APP_ROOT = "app.root";
	public static final String APP_ROOT_KUAIPAN = "kuaipan";
	public static final String APP_ROOT_APP_FOLDER = "app_folder";
	
	public static final String URL_API = "openapi.kuaipan.cn";
	public static final String URL_CONTENT = "api-content.dfs.kuaipan.cn";
	public static final String URL_CONV = "conv.kuaipan.cn";

	public static final String OP_ACCOUNT = "/1/account_info";
	public static final String OP_METADATA = "/1/metadata";
	public static final String OP_CREATE_FOLDER = "/1/fileops/create_folder";
	public static final String OP_DELETE = "/1/fileops/delete";
	public static final String OP_MOVE = "/1/fileops/move";
	public static final String OP_COPY = "/1/fileops/copy";
	public static final String OP_SHARES = "/1/shares";
	public static final String OP_THUMBNAIL = "/1/fileops/thumbnail";

	public static final String OP_UPLOAD_LOCATE = "/1/fileops/upload_locate";
	public static final String OP_UPLOAD = "/1/fileops/upload_file";
	public static final String OP_DOWNLOAD = "/1/fileops/download_file";

	private OAuth10Util oauth10Util;
	
	public OAuth10Util getOAuth10Util(){
		if(oauth10Util == null){
			new OAuth10Util(this.getAppKey(), this.getAppSecret());
		}
		return oauth10Util;
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
		Assert.isInstanceOf(BaiduAccessToken.class, accessToken, "AccessToken is not an instance of " + BaiduAccessToken.class);
	}

	@Override
	public QuotaInfo quota(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		
		KuaipanQuotaInfo quota = null;

		Map<String, String> params = new HashMap<String, String>();
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, OP_ACCOUNT, params, false);

		try {
			HTTPRequest request = new HTTPRequest(url);
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				quota = new KuaipanQuotaInfo(json);
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
				accessToken, "GET", URL_API, location.toString(), params, false);

		try {
			HTTPRequest request = new HTTPRequest(url);
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				file = new KuaipanFileInfo(json, null);
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
		
		FileInfo[] files = null;

		Map<String, String> params = new HashMap<String, String>();
		params.put("list",new Boolean(true).toString());
		
		StringBuffer location = new StringBuffer();
		location.append(OP_METADATA).append("/").append(this.getRoot())
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, location.toString(), params, false);

		try {
			HTTPRequest request = new HTTPRequest(url);
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("files");
				if(list != null){
					files = new KuaipanFileInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						KuaipanFileInfo file = new KuaipanFileInfo(item, path);
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
				accessToken, "GET", URL_API, OP_CREATE_FOLDER, params, false);

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
		params.put("to_recycle", new Boolean(recycle).toString());
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_API, OP_DELETE, params, false);

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
				accessToken, "GET", URL_API, OP_MOVE, params, false);

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
				accessToken, "GET", URL_API, OP_COPY, params, false);

		HTTPRequest request = new HTTPRequest(url);
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().get(request, handler);

		return true;
	}

	@Override
	public void download(AccessToken accessToken, String path, OutputStream os, ProgressListener listener) throws ServerException, ApiException {
		assertInit();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("root", this.getRoot());
		params.put("path", path);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_CONTENT, OP_DOWNLOAD, params, false);

		HTTPRequest request = new HTTPRequest(url);
		HTTPResponseHandler handler = new StreamHTTPResponseHandler(os, listener);
		this.getHttpEngine().get(request, handler);
	}

	@Override
	public void upload(AccessToken accessToken, String path, InputStream is, long size, ProgressListener listener) throws ServerException, ApiException {
		assertInit();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("root", this.getRoot());
		params.put("path", path);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_CONTENT, OP_UPLOAD_LOCATE, params, false);

		try {
			HTTPRequest request = new HTTPRequest(url);
			for(Map.Entry<String, String> entity : params.entrySet()){
				request.addQueryParameters(entity.getKey(), entity.getValue());
			}
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				url = json.getString("url");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if(url == null){
			throw new RuntimeException("url is null");
		}
		if(url.indexOf("https://") == 0) url = url.substring("https://".length());
		if(url.indexOf("http://") == 0) url = url.substring("http://".length());
		if(url.lastIndexOf("/") == url.length() - 1) url = url.substring(0, url.length() - 1);
		
		url = getOAuth10Util().buildURL(
				accessToken, "POST", URL_CONTENT, OP_UPLOAD, params, false);
		
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
	public ShareInfo share(AccessToken accessToken, String path) throws ServerException, ApiException{
		assertInit();
		
		ShareInfo share = null;
	
		Map<String, String> params = new HashMap<String, String>();
		
		StringBuffer location = new StringBuffer();
		location.append(OP_SHARES).append("/").append(this.getRoot())
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_CONTENT, location.toString(), params, false);
	
		try {
			HTTPRequest request = new HTTPRequest(url);
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				share = new KuaipanShareInfo(json);
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
		return share;
	}

	@Override
	public InputStream thumbnail(AccessToken accessToken, String path, int width, int height)
			throws ServerException, ApiException {
		assertInit();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("root", this.getRoot());
		params.put("path", path);
		params.put("width", new Integer(width).toString());
		params.put("height", new Integer(height).toString());
		
		String url = getOAuth10Util().buildURL(
				accessToken, "GET", URL_CONTENT, OP_THUMBNAIL, params, false);

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