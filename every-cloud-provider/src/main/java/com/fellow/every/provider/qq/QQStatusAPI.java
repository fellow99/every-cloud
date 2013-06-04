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
import com.fellow.every.status.StatusAPI;
import com.fellow.every.status.StatusCommentInfo;
import com.fellow.every.status.StatusInfo;
import com.fellow.every.status.StatusURL;
import com.fellow.util.Assert;

public class QQStatusAPI extends AbstractAPI implements StatusAPI {

	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";

	public static final String APP_OAUTH_VERSION = "2.a";
	public static final String APP_FORMAT = "json";
	
	public static final String URL_API = "https://open.t.qq.com";

	public static final String OP_STATUSES_TIMELINE_PUBLIC = "/api/statuses/public_timeline";
	public static final String OP_STATUSES_TIMELINE_HOME = "/api/statuses/home_timeline";
	public static final String OP_STATUSES_TIMELINE_BROADCAST = "/api/statuses/broadcast_timeline";
	public static final String OP_STATUSES_TIMELINE_FRIENDS = "/api/statuses/friends_timeline";
	public static final String OP_STATUSES_TIMELINE_USER = "/api/statuses/user_timeline";
	public static final String OP_STATUSES_TIMELINE_MENTIONS = "/api/statuses/mentions_timeline";
	public static final String OP_STATUSES_TIMELINE_REPOST = "/api/statuses/repost_timeline";

	public static final String OP_STATUSES_TIMELINE_HOME_IDS = "/api/statuses/home_timeline/ids";
	public static final String OP_STATUSES_TIMELINE_BROADCAST_IDS = "/api/statuses/broadcast_timeline/ids";
	public static final String OP_STATUSES_TIMELINE_FRIENDS_IDS = "/api/statuses/friends_timeline/ids";
	public static final String OP_STATUSES_TIMELINE_USER_IDS = "/api/statuses/user_timeline/ids";
	public static final String OP_STATUSES_TIMELINE_MENTIONS_IDS = "/api/statuses/mentions_timeline/ids";
	public static final String OP_STATUSES_TIMELINE_REPOST_IDS = "/api/statuses/repost_timeline/ids";
	
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
	public StatusInfo get(AccessToken accessToken, String id) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_T_SHOW);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addQueryParameters("id", id);
			

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				mb = new QQStatusInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		
		return mb;
	}

	@Override
	public StatusInfo[] get(AccessToken accessToken, String[] ids) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		StatusInfo[] mbs = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_T_LIST);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addQueryParameters("ids", this.joinIds(ids, ","));

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				JSONArray list = json.getJSONArray("info");
				if(list != null){
					mbs = new QQStatusInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						QQStatusInfo mb = new QQStatusInfo(item);
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
	public StatusInfo[] listPublic(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_PUBLIC);
		request.addQueryParameters("oauth_consumer_key", this.getAppKey());
		request.addQueryParameters("access_token", qqToken.getAccessToken());
		request.addQueryParameters("openid", qqToken.getOpenid());
		request.addQueryParameters("clientip", qqToken.getClientip());
		request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
		request.addQueryParameters("format", APP_FORMAT);
		
		return listRequest(request);
	}

	@Override
	public StatusInfo[] listMe(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_BROADCAST);
		request.addQueryParameters("oauth_consumer_key", this.getAppKey());
		request.addQueryParameters("access_token", qqToken.getAccessToken());
		request.addQueryParameters("openid", qqToken.getOpenid());
		request.addQueryParameters("clientip", qqToken.getClientip());
		request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
		request.addQueryParameters("format", APP_FORMAT);
		
		return listRequest(request);
	}

	@Override
	public String[] idsMe(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo[] listHome(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_HOME);
		request.addQueryParameters("oauth_consumer_key", this.getAppKey());
		request.addQueryParameters("access_token", qqToken.getAccessToken());
		request.addQueryParameters("openid", qqToken.getOpenid());
		request.addQueryParameters("clientip", qqToken.getClientip());
		request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
		request.addQueryParameters("format", APP_FORMAT);
		
		return listRequest(request);
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
	public StatusInfo[] listFriends(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_FRIENDS);
		request.addQueryParameters("oauth_consumer_key", this.getAppKey());
		request.addQueryParameters("access_token", qqToken.getAccessToken());
		request.addQueryParameters("openid", qqToken.getOpenid());
		request.addQueryParameters("clientip", qqToken.getClientip());
		request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
		request.addQueryParameters("format", APP_FORMAT);
		
		return listRequest(request);
	}

	@Override
	public String[] idsFriends(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo[] listMentions(AccessToken accessToken, int page,
			int size) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_MENTIONS);
		request.addQueryParameters("oauth_consumer_key", this.getAppKey());
		request.addQueryParameters("access_token", qqToken.getAccessToken());
		request.addQueryParameters("openid", qqToken.getOpenid());
		request.addQueryParameters("clientip", qqToken.getClientip());
		request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
		request.addQueryParameters("format", APP_FORMAT);
		
		return listRequest(request);
	}

	@Override
	public String[] idsMentions(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo[] listUser(AccessToken accessToken, String uid, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_USER);
		request.addQueryParameters("oauth_consumer_key", this.getAppKey());
		request.addQueryParameters("access_token", qqToken.getAccessToken());
		request.addQueryParameters("openid", qqToken.getOpenid());
		request.addQueryParameters("clientip", qqToken.getClientip());
		request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
		request.addQueryParameters("format", APP_FORMAT);
		request.addQueryParameters("fopenid", uid);
		
		return listRequest(request);
	}

	@Override
	public String[] idsUser(AccessToken accessToken, String uid, int page, int size)
			throws ServerException, ApiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo[] listRepost(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public String[] idsRepost(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public StatusCommentInfo[] listComments(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo add(AccessToken accessToken, String content) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_T_ADD);
			request.addQueryParameters("oauth_consumer_key", this.getAppKey());
			request.addQueryParameters("access_token", qqToken.getAccessToken());
			request.addQueryParameters("openid", qqToken.getOpenid());
			request.addQueryParameters("clientip", qqToken.getClientip());
			request.addQueryParameters("oauth_version", APP_OAUTH_VERSION);
			request.addQueryParameters("format", APP_FORMAT);
			request.addBodyParameters("content", content);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

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
	public StatusInfo addPic(AccessToken accessToken, String content, InputStream is, long size,
			ProgressListener listener) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addUrl(AccessToken accessToken, String content, StatusURL url)
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
	public StatusInfo repost(AccessToken accessToken, String id, String content) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		StatusInfo mb = null;
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

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

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
	public StatusCommentInfo reply(AccessToken accessToken, String id, String comment) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		StatusCommentInfo cmt = null;
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

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				QQStatusInfo mb = (QQStatusInfo)this.get(accessToken, json.getString("id"));
				//TODO: BUG
				//cmt = new QQStatusCommentInfo(mb.getJson());
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return cmt;
	}

	@Override
	public StatusCommentInfo comment(AccessToken accessToken, String id, String comment) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		QQAccessToken qqToken = (QQAccessToken)accessToken;

		StatusCommentInfo cmt = null;
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

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				QQStatusInfo mb = (QQStatusInfo)this.get(accessToken, json.getString("id"));
				//TODO: BUG
				//cmt = new QQStatusCommentInfo(mb.getJson());
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
	

	public StatusInfo[] listRequest(HTTPRequest request) throws ServerException, ApiException {
		StatusInfo[] mbs = null;
		try {
			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				json = json.getJSONObject("data");
				JSONArray list = json.getJSONArray("info");
				if(list != null){
					mbs = new QQStatusInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						QQStatusInfo mb = new QQStatusInfo(item);
						mbs[i] = mb;
					}
				}
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return mbs;
	}
}
