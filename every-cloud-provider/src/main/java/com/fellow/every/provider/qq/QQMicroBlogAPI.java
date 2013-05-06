package com.fellow.every.provider.qq;

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

public class QQMicroBlogAPI extends AbstractAPI implements MicroBlogAPI {

	public static final String APP_OAUTH_VERSION = "2.a";
	public static final String APP_FORMAT = "json";
	
	public static final String URL_API = "https://open.t.qq.com";

	public static final String OP_T_SHOW = "/api/t/show";
	public static final String OP_T_LIST = "/api/t/list";

	public static final String OP_T_ADD = "/api/t/add";
	public static final String OP_T_ADD_PIC = "/api/t/add_pic";
	public static final String OP_T_ADD_PIC_URL = "/api/t/add_pic_url";
	public static final String OP_T_ADD_EMOTION = "/api/t/add_emotion";
	public static final String OP_T_ADD_MUSIC = "/api/t/add_music";
	public static final String OP_T_ADD_VIDEO = "/api/t/add_video";
	public static final String OP_T_ADD_MULTI = "/api/t/add_multi";
	public static final String OP_T_UPLOAD_PIC = "/api/t/upload_pic";
	public static final String OP_T_RE_ADD = "/api/t/re_add";
	public static final String OP_T_DEL = "/api/t/del";
	public static final String OP_T_REPLY = "/api/t/reply";
	public static final String OP_T_COMMENT = "/api/t/comment";
	
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
	public MicroBlogInfo get(AccessToken accessToken, String id) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		MicroBlogInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_T_SHOW);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addQueryParameters("id", id);
			
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				mb = new QQMicroBlogInfo(json);
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
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		MicroBlogInfo[] mbs = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_T_LIST);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addQueryParameters("ids", this.joinIds(ids, ","));
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				JSONArray list = json.getJSONArray("info");
				if(list != null){
					mbs = new QQMicroBlogInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						QQMicroBlogInfo mb = new QQMicroBlogInfo(item);
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
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo[] listHome(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] idsHome(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo[] listFriend(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] idsFriend(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo[] listUser(AccessToken accessToken, String uid, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] idsUser(AccessToken accessToken, String uid, int page, int size)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo[] listRepost(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] idsRepost(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo add(AccessToken accessToken, String content) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		MicroBlogInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_T_ADD);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addBodyParameters("content", content);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().post(request, handler);
			String info = handler.toString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				mb = this.get(accessToken, json.getString("id"));
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
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MicroBlogInfo addUrl(AccessToken accessToken, String content, MicroBlogURL url)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(AccessToken accessToken, String id) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API + OP_T_DEL);
		request.addQueryParameters("oauth_consumer_key", this.getAppKey());
		request.addQueryParameters("access_token", qqToken.getAccessToken());
		request.addQueryParameters("openid", qqToken.getOpenid());
		request.addQueryParameters("clientip", qqToken.getClientip());
		request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
		request.addQueryParameters("format", APP_FORMAT);
		request.addBodyParameters("id", id);

		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);
	}

	@Override
	public MicroBlogInfo repost(AccessToken accessToken, String id, String content) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		MicroBlogInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_T_RE_ADD);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addBodyParameters("reid", id);
			request.addBodyParameters("content", content);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().post(request, handler);
			String info = handler.toString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				mb = this.get(accessToken, json.getString("id"));
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
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		MicroBlogCommentInfo cmt = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_T_REPLY);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addBodyParameters("reid", id);
			request.addBodyParameters("content", comment);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().post(request, handler);
			String info = handler.toString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				QQMicroBlogInfo mb = (QQMicroBlogInfo)this.get(accessToken, json.getString("id"));
				cmt = new QQMicroBlogCommentInfo(mb.getJson());
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return cmt;
	}

	@Override
	public MicroBlogCommentInfo comment(AccessToken accessToken, String id, String comment) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		MicroBlogCommentInfo cmt = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_T_COMMENT);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addBodyParameters("reid", id);
			request.addBodyParameters("content", comment);
			
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().post(request, handler);
			String info = handler.toString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				QQMicroBlogInfo mb = (QQMicroBlogInfo)this.get(accessToken, json.getString("id"));
				cmt = new QQMicroBlogCommentInfo(mb.getJson());
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
