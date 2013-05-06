package com.fellow.every.provider.kanbox;

import java.io.*;
import java.util.*;

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
import com.fellow.every.http.impl.SimpleHTTPResponseHandler;
import com.fellow.every.http.impl.StreamHTTPResponseHandler;
import com.fellow.every.http.impl.StringHTTPResponseHandler;
import com.fellow.util.Assert;

public class KanboxDiskAPI extends AbstractAPI implements DiskAPI{
	
	public static final String URL_API = "https://api.kanbox.com";
	public static final String URL_CONTENT = "https://api-upload.kanbox.com";
	

	public static final String OP_INFO = "/0/info";
	public static final String OP_LIST = "/0/list";
	public static final String OP_CREATE_FOLDER = "/0/create_folder";
	public static final String OP_DELETE = "/0/delete";
	public static final String OP_MOVE = "/0/move";
	public static final String OP_COPY = "/0/copy";

	public static final String OP_SHARE = "/0/share";

	public static final String OP_UPLOAD = "/0/upload";
	public static final String OP_DOWNLOAD = "/0/download";

	public void assertInit(){
		Assert.notNull(this.getHttpEngine(), "HTTPEngine Object is null");
		Assert.hasText(this.getAppKey(), "Application's KEY is null");
		Assert.hasText(this.getAppSecret(), "Application's SECRET is null");
	}
	
	public void assertAccessToken(AccessToken accessToken){
		Assert.notNull(accessToken, "AccessToken is null");
		Assert.notNull(accessToken.getAccessToken(), "AccessToken is null");
		Assert.isInstanceOf(KanboxAccessToken.class, accessToken, "AccessToken is not an instance of " + KanboxAccessToken.class);
	}

	@Override
	public QuotaInfo quota(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		
		KanboxQuotaInfo quota = null;

		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_INFO);
			request.addQueryParameters("bearer_token", accessToken.getAccessToken());
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			if(info != null){
				JSONObject json = new JSONObject(info);
				quota = new KanboxQuotaInfo(json);
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
		
		String parent = null;
		
		String[] str = path.split("/");
		ArrayList<String> parts = new ArrayList<String>();
		for(int i = 0; i < str.length; i++){
			if(str[i] != null && str[i].trim().length() > 0){
				parts.add(str[i].trim());
			}
		}
		if(parts.size() == 0){
			try {
				String info = "{fullPath:'/', isFolder: true}";
				JSONObject json = new JSONObject(info);
				file = new KanboxFileInfo(json);
				return file;
			} catch (JSONException e) {
				throw new ApiException(e);
			}
		} else {
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < parts.size() - 1; i++){
				sb.append("/").append(parts.get(i));
			}
			parent = sb.toString();
		}
		
		StringBuffer location = new StringBuffer();
		location.append(OP_LIST)
			.append(parent.indexOf("/") == 0 ? "" : "/").append(parent);

		Map<String, String> params = new HashMap<String, String>();
		params.put("bearer_token", accessToken.getAccessToken());

		try {
			HTTPRequest request = new HTTPRequest(URL_API + location.toString());
			request.addQueryParameters("bearer_token", accessToken.getAccessToken());
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				String status = json.getString("status");
				if("ok".equalsIgnoreCase(status)){
					JSONArray list = json.getJSONArray("contents");
					if(list != null){
						for(int i = 0; i < list.length(); i++){
							JSONObject item = list.getJSONObject(i);
							String p = item.getString("fullPath");
							if(path.indexOf(p) == 0){
								file = new KanboxFileInfo(item);
							}
						}
					}
				}
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
		
		StringBuffer location = new StringBuffer();
		location.append(OP_LIST)
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);

		try {
			HTTPRequest request = new HTTPRequest(URL_API + location.toString());
			request.addQueryParameters("bearer_token", accessToken.getAccessToken());
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("contents");
				if(list != null){
					files = new KanboxFileInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						KanboxFileInfo file = new KanboxFileInfo(item);
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

		StringBuffer location = new StringBuffer();
		location.append(OP_CREATE_FOLDER)
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		HTTPRequest request = new HTTPRequest(URL_API + location.toString());
		request.addQueryParameters("bearer_token", accessToken.getAccessToken());
		
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);

		return true;
	}

	@Override
	public boolean rm(AccessToken accessToken, String path, boolean recycle) throws ServerException, ApiException {
		assertInit();

		StringBuffer location = new StringBuffer();
		location.append(OP_DELETE)
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		HTTPRequest request = new HTTPRequest(URL_API + location.toString());
		request.addQueryParameters("bearer_token", accessToken.getAccessToken());
		
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);

		return true;
	}

	@Override
	public boolean mv(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException {
		assertInit();

		StringBuffer location = new StringBuffer();
		location.append(OP_MOVE)
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		HTTPRequest request = new HTTPRequest(URL_API + location.toString());
		request.addQueryParameters("bearer_token", accessToken.getAccessToken());
		request.addQueryParameters("destination_path", newPath);
		
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);

		return true;
	}

	@Override
	public boolean cp(AccessToken accessToken, String path, String newPath) throws ServerException, ApiException {
		assertInit();

		StringBuffer location = new StringBuffer();
		location.append(OP_COPY)
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		HTTPRequest request = new HTTPRequest(URL_API + location.toString());
		request.addQueryParameters("bearer_token", accessToken.getAccessToken());
		request.addQueryParameters("destination_path", newPath);
		
		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);

		return true;
	}

	@Override
	public void download(AccessToken accessToken, String path, OutputStream os, ProgressListener listener) throws ServerException, ApiException {
		assertInit();
		
		StringBuffer location = new StringBuffer();
		location.append(OP_DOWNLOAD)
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);

		HTTPRequest request = new HTTPRequest(URL_API + location.toString());
		request.addQueryParameters("bearer_token", accessToken.getAccessToken());

		HTTPResponseHandler handler = new StreamHTTPResponseHandler(os, listener);
		this.getHttpEngine().get(request, handler);
	}

	@Override
	public void upload(AccessToken accessToken, String path, InputStream is, long size, ProgressListener listener) throws ServerException, ApiException {
		assertInit();
		
		StringBuffer location = new StringBuffer();
		location.append(OP_UPLOAD)
			.append(path.indexOf("/") == 0 ? "" : "/").append(path);
		
		HTTPRequest request = new HTTPRequest(URL_CONTENT + location.toString());
		request.addQueryParameters("bearer_token", accessToken.getAccessToken());
		
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
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public InputStream thumbnail(AccessToken accessToken, String path, int width, int height)
			throws ServerException, ApiException {
		throw new java.lang.UnsupportedOperationException();
	}
}
