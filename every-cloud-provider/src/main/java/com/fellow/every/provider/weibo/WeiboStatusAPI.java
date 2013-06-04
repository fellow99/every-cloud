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
import com.fellow.every.status.StatusAPI;
import com.fellow.every.status.StatusCommentInfo;
import com.fellow.every.status.StatusInfo;
import com.fellow.every.status.StatusURL;
import com.fellow.util.Assert;

public class WeiboStatusAPI extends AbstractAPI implements StatusAPI {

	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";
	
	public static final String URL_API = "https://api.weibo.com";
	
	public static final String OP_ACCOUNT_GET_UID = "/2/account/get_uid.json";

	public static final String OP_STATUSES_SHOW = "/2/statuses/show.json";
	public static final String OP_STATUSES_SHOW_BATCH = "/2/statuses/show_batch.json";

	public static final String OP_STATUSES_TIMELINE_PUBLIC = "/2/statuses/public_timeline.json";
	public static final String OP_STATUSES_TIMELINE_HOME = "/2/statuses/home_timeline.json";
	public static final String OP_STATUSES_TIMELINE_FRIENDS = "/2/statuses/friends_timeline.json";
	public static final String OP_STATUSES_TIMELINE_USER = "/2/statuses/user_timeline.json";
	public static final String OP_STATUSES_TIMELINE_MENTIONS = "/api/statuses/mentions";
	public static final String OP_STATUSES_TIMELINE_REPOST = "/2/statuses/repost_timeline.json";

	public static final String OP_STATUSES_TIMELINE_HOME_IDS = "/2/statuses/home_timeline/ids.json";
	public static final String OP_STATUSES_TIMELINE_FRIENDS_IDS = "/2/statuses/friends_timeline/ids.json";
	public static final String OP_STATUSES_TIMELINE_USER_IDS = "/2/statuses/user_timeline/ids.json";
	public static final String OP_STATUSES_TIMELINE_MENTIONS_IDS = "/api/statuses/mentions/ids";
	public static final String OP_STATUSES_TIMELINE_REPOST_IDS = "/2/statuses/repost_timeline/ids.json";

	public static final String OP_COMMENTS_SHOW = "/2/comments/show.json";
	
	public static final String OP_STATUSES_UPDATE = "/2/statuses/update.json";
	public static final String OP_STATUSES_UPLOAD_URL_TEXT = "/2/statuses/upload_url_text.json";
	public static final String OP_STATUSES_REPOST = "/2/statuses/repost.json";
	public static final String OP_STATUSES_DESTORY = "/2/statuses/destroy.json";
	
	public static final String OP_COMMENTS_CREATE = "/2/comments/create.json";
	public static final String OP_COMMENTS_REPLY = "/2/comments/reply.json";

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
		Assert.isInstanceOf(WeiboAccessToken.class, accessToken, "AccessToken is not an instance of " + WeiboAccessToken.class);
	}

	@Override
	public StatusInfo get(AccessToken accessToken, String id) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_SHOW);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("id", id);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new WeiboStatusInfo(json);
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

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_SHOW);
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("ids", this.joinIds(ids, ","));
		
		return listRequest(request);
	}

	@Override
	public StatusInfo[] listPublic(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_PUBLIC);
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("page", Integer.toString(page + 1));
		request.addQueryParameters("count", Integer.toString(size));
		
		return listRequest(request);
	}

	@Override
	public StatusInfo[] listMe(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		String uid = this.getUid(accessToken);
		return this.listUser(accessToken, uid, page, size);
	}

	@Override
	public String[] idsMe(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		String uid = this.getUid(accessToken);
		return this.idsUser(accessToken, uid, page, size);
	}

	@Override
	public StatusInfo[] listHome(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_HOME);
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("page", Integer.toString(page + 1));
		request.addQueryParameters("count", Integer.toString(size));
		
		return listRequest(request);
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
	public StatusInfo[] listFriends(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_FRIENDS);
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("page", Integer.toString(page + 1));
		request.addQueryParameters("count", Integer.toString(size));
		
		return listRequest(request);
	}

	@Override
	public String[] idsFriends(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo[] listMentions(AccessToken accessToken, int page,
			int size) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_MENTIONS);
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("page", Integer.toString(page + 1));
		request.addQueryParameters("count", Integer.toString(size));
		
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

		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_USER);
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("uid", uid);
		request.addQueryParameters("page", Integer.toString(page + 1));
		request.addQueryParameters("count", Integer.toString(size));
		
		return listRequest(request);
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
	public StatusInfo[] listRepost(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_TIMELINE_REPOST);
		request.addQueryParameters("access_token", accessToken.getAccessToken());
		request.addQueryParameters("id", id);
		request.addQueryParameters("page", Integer.toString(page + 1));
		request.addQueryParameters("count", Integer.toString(size));
		
		return listRequest(request);
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
	public StatusCommentInfo[] listComments(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		StatusCommentInfo[] comments = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_COMMENTS_SHOW);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("id", id);
			request.addQueryParameters("page", Integer.toString(page + 1));
			request.addQueryParameters("count", Integer.toString(size));

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("comments");
				if(list != null){
					comments = new WeiboStatusCommentInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						WeiboStatusCommentInfo comment = new WeiboStatusCommentInfo(item);
						comments[i] = comment;
					}
				}
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return comments;
	}

	@Override
	public StatusInfo add(AccessToken accessToken, String content) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);

		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_UPDATE);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("status", content);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new WeiboStatusInfo(json);
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
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addUrl(AccessToken accessToken, String content, StatusURL url)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		if(url != null && url.getType() != StatusURL.URLType.URL && url.getType() != StatusURL.URLType.PICTURE){
			throw new java.lang.UnsupportedOperationException();
		}


		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_UPLOAD_URL_TEXT);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("status", content);
			request.addQueryParameters("url", url.getUrl());

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new WeiboStatusInfo(json);
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return mb;
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
	public StatusInfo repost(AccessToken accessToken, String id, String content) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);

		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_STATUSES_REPOST);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("id", id);
			request.addQueryParameters("status", content);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new WeiboStatusInfo(json);
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
		
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public StatusCommentInfo comment(AccessToken accessToken, String id, String comment) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);

		StatusCommentInfo cmt = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_COMMENTS_CREATE);
			request.addQueryParameters("access_token", accessToken.getAccessToken());
			request.addQueryParameters("id", id);
			request.addQueryParameters("comment", comment);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				cmt = new WeiboStatusCommentInfo(json);
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


	public String getUid(AccessToken accessToken) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		
		String uid = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API + OP_ACCOUNT_GET_UID);
			request.addQueryParameters("access_token", accessToken.getAccessToken());

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				uid = json.getString("uid");
			}
		} catch (JSONException e) {
			throw new ApiException(e);
		}
		return uid;
	}


	public StatusInfo[] listRequest(HTTPRequest request) throws ServerException, ApiException {
		StatusInfo[] mbs = null;
		try {
			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("statuses");
				if(list != null){
					mbs = new WeiboStatusInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						WeiboStatusInfo mb = new WeiboStatusInfo(item);
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
