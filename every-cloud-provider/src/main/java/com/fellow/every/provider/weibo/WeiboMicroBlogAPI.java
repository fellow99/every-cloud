package com.fellow.every.provider.weibo;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.base.AbstractAPI;
import com.fellow.every.disk.ProgressListener;
import com.fellow.every.exception.ApiException;
import com.fellow.every.exception.ServerException;
import com.fellow.every.http.HTTPRequest;
import com.fellow.every.http.HTTPResponseHandler;
import com.fellow.every.http.impl.SimpleHTTPResponseHandler;
import com.fellow.every.http.impl.StringHTTPResponseHandler;
import com.fellow.every.mircoblog.MicroBlogAPI;
import com.fellow.every.mircoblog.MicroBlogCommentInfo;
import com.fellow.every.mircoblog.MicroBlogInfo;
import com.fellow.every.mircoblog.MicroBlogURL;
import com.fellow.util.Assert;

public class WeiboMicroBlogAPI extends AbstractAPI implements MicroBlogAPI {
	
	public static final String URL_API = "https://api.weibo.com";

	public static final String OP_STATUSES_SHOW = "/2/statuses/show.json";
	public static final String OP_STATUSES_SHOW_BATCH = "/2/statuses/show_batch.json";

	public static final String OP_STATUSES_UPDATE = "/2/statuses/update.json";
	public static final String OP_STATUSES_REPOST = "/2/statuses/repost.json";
	public static final String OP_STATUSES_DESTORY = "/2/statuses/destroy.json";
	
	public static final String OP_COMMENTS_CREATE = "/2/comments/create.json";
	public static final String OP_COMMENTS_REPLY = "/2/comments/reply.json";
	
	public void assertInit(){
		Assert.notNull(this.getHttpEngine(), "HTTPEngine Object is null");
		Assert.hasText(this.getAppKey(), "Application's KEY is null");
		Assert.hasText(this.getAppSecret(), "Application's SECRET is null");
	}
	
	public void assertAccessToken(AccessToken accessToken){
		Assert.notNull(accessToken, "AccessToken is null");
		Assert.notNull(accessToken.getAccessToken(), "AccessToken is null");
		Assert.isInstanceOf(WeiboAccessToken.class, accessToken, "AccessToken is not an instance of " + WeiboAccessToken.class);
	}

	@Override
	public MicroBlogInfo get(AccessToken accessToken, String id) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		MicroBlogInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_SHOW);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("id", id);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new WeiboMicroBlogInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return mb;
	}

	@Override
	public MicroBlogInfo[] get(AccessToken accessToken, String[] ids) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		MicroBlogInfo[] mbs = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_SHOW);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("ids", this.joinIds(ids, ","));
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("statuses");
				if(list != null){
					mbs = new WeiboMicroBlogInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						WeiboMicroBlogInfo mb = new WeiboMicroBlogInfo(item);
						mbs[i] = mb;
					}
				}
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return mbs;
	}

	@Override
	public MicroBlogCommentInfo[] listComments(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo[] listHome(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] idsHome(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo[] listFriend(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] idsFriend(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo[] listUser(AccessToken accessToken, String uid, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] idsUser(AccessToken accessToken, String uid, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo[] listRepost(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] idsRepost(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo add(AccessToken accessToken, String content) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);

		MicroBlogInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_UPDATE);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("status", content);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().post(request, handler);
			String info = handler.toString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new WeiboMicroBlogInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return mb;
	}

	@Override
	public MicroBlogInfo addPic(AccessToken accessToken, String content, InputStream is, long size,
			ProgressListener listener) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo addUrl(AccessToken accessToken, String content, MicroBlogURL url)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(AccessToken accessToken, String id) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_DESTORY);
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("id", id);

		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);
	}

	@Override
	public MicroBlogInfo repost(AccessToken accessToken, String id, String content) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);

		MicroBlogInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_REPOST);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("id", id);
			request.addQueryParameters("status", content);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().post(request, handler);
			String info = handler.toString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new WeiboMicroBlogInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return mb;
	}

	@Override
	public MicroBlogCommentInfo reply(AccessToken accessToken, String id, String comment) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public MicroBlogCommentInfo comment(AccessToken accessToken, String id, String comment) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);

		MicroBlogCommentInfo cmt = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_COMMENTS_CREATE);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("id", id);
			request.addQueryParameters("comment", comment);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().post(request, handler);
			String info = handler.toString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				cmt = new WeiboMicroBlogCommentInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return cmt;
	}
	
	
	
	
	
	
	
	

	String joinIds(String[] ids, String sp){
		if(ids == null) return null;
		if(sp == null) sp = ",";
		
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < ids.length; i++){
			buffer.append(ids[i]);
			if(i < ids.length - 1)buffer.append(sp);
		}
		
		return buffer.toString();
	}
}
