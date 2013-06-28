package com.fellow.every.provider.renren;

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
import com.fellow.every.user.UserInfo;
import com.fellow.util.Assert;

/**
 * TODO:
 */
public class RenrenStatusAPI extends AbstractAPI implements StatusAPI {

	public static final String PROPERTY_APP_CHARSET = "app.charset";
	public static final String DEFAULT_APP_CHARSET = "UTF-8";

	public static final String URL_API = "https://api.renren.com/restserver.do";

	public static final String OP_USERS_GET_LOGIN = "x";
	public static final String OP_STATUS_SET = "status.set";
	public static final String OP_STATUS_GETS = "status.gets";

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
		Assert.isInstanceOf(RenrenAccessToken.class, accessToken, "AccessToken is not an instance of " + RenrenAccessToken.class);
	}

	@Override
	public StatusInfo get(AccessToken accessToken, String id) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_LOGIN);
			request.addQueryParameters("id", id);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
			String info = handler.getString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new RenrenStatusInfo(json);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API);
		request.addQueryParameters("access_token", renrentoken.getAccessToken());
		request.addQueryParameters("v", renrentoken.getAppVersion());
		request.addQueryParameters("format", renrentoken.getAppFormat());
		request.addQueryParameters("method", OP_USERS_GET_LOGIN);
		request.addQueryParameters("ids", this.joinIds(ids, ","));
		
		return listRequest(request);
	}

	@Override
	public StatusInfo[] listPublic(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API);
		request.addQueryParameters("access_token", renrentoken.getAccessToken());
		request.addQueryParameters("v", renrentoken.getAppVersion());
		request.addQueryParameters("format", renrentoken.getAppFormat());
		request.addQueryParameters("method", OP_USERS_GET_LOGIN);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API);
		request.addQueryParameters("access_token", renrentoken.getAccessToken());
		request.addQueryParameters("v", renrentoken.getAppVersion());
		request.addQueryParameters("format", renrentoken.getAppFormat());
		request.addQueryParameters("method", OP_USERS_GET_LOGIN);
		request.addQueryParameters("page", Integer.toString(page + 1));
		request.addQueryParameters("count", Integer.toString(size));
		
		return listRequest(request);
	}

	@Override
	public String[] idsHome(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo[] listFriends(AccessToken accessToken, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API);
		request.addQueryParameters("access_token", renrentoken.getAccessToken());
		request.addQueryParameters("v", renrentoken.getAppVersion());
		request.addQueryParameters("format", renrentoken.getAppFormat());
		request.addQueryParameters("method", OP_USERS_GET_LOGIN);
		request.addQueryParameters("page", Integer.toString(page + 1));
		request.addQueryParameters("count", Integer.toString(size));
		
		return listRequest(request);
	}

	@Override
	public String[] idsFriends(AccessToken accessToken, int page, int size) throws ServerException,
			ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo[] listMentions(AccessToken accessToken, int page,
			int size) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API);
		request.addQueryParameters("access_token", renrentoken.getAccessToken());
		request.addQueryParameters("v", renrentoken.getAppVersion());
		request.addQueryParameters("format", renrentoken.getAppFormat());
		request.addQueryParameters("method", OP_USERS_GET_LOGIN);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API);
		request.addQueryParameters("access_token", renrentoken.getAccessToken());
		request.addQueryParameters("v", renrentoken.getAppVersion());
		request.addQueryParameters("format", renrentoken.getAppFormat());
		request.addQueryParameters("method", OP_USERS_GET_LOGIN);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo[] listRepost(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API);
		request.addQueryParameters("access_token", renrentoken.getAccessToken());
		request.addQueryParameters("v", renrentoken.getAppVersion());
		request.addQueryParameters("format", renrentoken.getAppFormat());
		request.addQueryParameters("method", OP_USERS_GET_LOGIN);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusCommentInfo[] listComments(AccessToken accessToken, String id, int page, int size)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		StatusCommentInfo[] comments = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_LOGIN);
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
					comments = new RenrenStatusCommentInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						RenrenStatusCommentInfo comment = new RenrenStatusCommentInfo(item);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_LOGIN);
			request.addQueryParameters("status", content);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new RenrenStatusInfo(json);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatusInfo addUrl(AccessToken accessToken, String content, StatusURL url)
			throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		if(url != null && url.getType() != StatusURL.URLType.URL && url.getType() != StatusURL.URLType.PICTURE){
			throw new java.lang.UnsupportedOperationException();
		}


		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_LOGIN);
			request.addQueryParameters("status", content);
			request.addQueryParameters("url", url.getUrl());

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new RenrenStatusInfo(json);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		HTTPRequest request = new HTTPRequest(URL_API);
		request.addQueryParameters("access_token", renrentoken.getAccessToken());
		request.addQueryParameters("v", renrentoken.getAppVersion());
		request.addQueryParameters("format", renrentoken.getAppFormat());
		request.addQueryParameters("method", OP_USERS_GET_LOGIN);
		request.addQueryParameters("id", id);

		HTTPResponseHandler handler = new SimpleHTTPResponseHandler();
		this.getHttpEngine().post(request, handler);
	}

	@Override
	public StatusInfo repost(AccessToken accessToken, String id, String content) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		StatusInfo mb = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_LOGIN);
			request.addQueryParameters("id", id);
			request.addQueryParameters("status", content);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				mb = new RenrenStatusInfo(json);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		throw new java.lang.UnsupportedOperationException();
	}

	@Override
	public StatusCommentInfo comment(AccessToken accessToken, String id, String comment) throws ServerException, ApiException {
		assertInit();
		assertAccessToken(accessToken);
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;

		StatusCommentInfo cmt = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_LOGIN);
			request.addQueryParameters("id", id);
			request.addQueryParameters("comment", comment);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().post(request, handler);
			String info = handler.getString();

			if(info != null){
				JSONObject json = new JSONObject(info);
				cmt = new RenrenStatusCommentInfo(json);
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
		RenrenAccessToken renrentoken = (RenrenAccessToken)accessToken;
		
		String uid = null;
		try {
			HTTPRequest request = new HTTPRequest(URL_API);
			request.addQueryParameters("access_token", renrentoken.getAccessToken());
			request.addQueryParameters("v", renrentoken.getAppVersion());
			request.addQueryParameters("format", renrentoken.getAppFormat());
			request.addQueryParameters("method", OP_USERS_GET_LOGIN);

			StringHTTPResponseHandler handler = new StringHTTPResponseHandler(this.getCharset());
			this.getHttpEngine().get(request, handler);
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
			HTTPResponseHandler handler = new StringHTTPResponseHandler();
			this.getHttpEngine().get(request, handler);
			String info = handler.toString();
			
			if(info != null){
				JSONObject json = new JSONObject(info);
				JSONArray list = json.getJSONArray("statuses");
				if(list != null){
					mbs = new RenrenStatusInfo[list.length()];
					for(int i = 0; i < list.length(); i++){
						JSONObject item = list.getJSONObject(i);
						RenrenStatusInfo mb = new RenrenStatusInfo(item);
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
