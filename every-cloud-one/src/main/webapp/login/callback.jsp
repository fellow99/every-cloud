<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.scribe.model.Verifier" %>
<%@ page import="org.scribe.model.OAuthConfig" %>
<%@ page import="org.scribe.model.Token" %>
<%@ page import="org.scribe.builder.api.Api" %>
<%@ page import="org.scribe.oauth.OAuthService" %>
<%@ page import="com.fellow.every.one.business.ProfileBusiness" %>
<%@ page import="com.fellow.every.one.business.EveryCloudBusiness" %>
<%@ page import="com.fellow.every.user.UserAPI" %>
<%@ page import="com.fellow.every.user.UserInfo" %>
<%@ page import="com.fellow.every.auth.AccessToken" %>
<%
UserInfo loginUser = (UserInfo)session.getAttribute(EveryCloudBusiness.SESSION_LOGIN_USER_INFO);
String loginProvider = (String)session.getAttribute(EveryCloudBusiness.SESSION_LOGIN_PROVIDER);
%>
<%
String provider = request.getParameter("p");
if(provider == null || provider.length() == 0){
	out.println("请选择登录服务。<a href='../'>返回</a>>>");
	return;
}

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
		request.getSession().getServletContext());

ProfileBusiness profileBusiness = ctx.getBean("profileBusiness", ProfileBusiness.class);
EveryCloudBusiness everyCloudBusiness = ctx.getBean("everyCloudBusiness", EveryCloudBusiness.class);
OAuthConfig oauthConfig = everyCloudBusiness.getOAuthConfig(provider);
Api oauth2API = everyCloudBusiness.getOAuthAPI(provider);
UserAPI userAPI = everyCloudBusiness.getUserAPI(provider);

OAuthService oauthService = oauth2API.createService(oauthConfig);

Token token = null;
if(oauthService != null && oauthService.getVersion().indexOf("1") == 0){

	String code = request.getParameter("oauth_verifier");
	if(code == null || code.length() == 0){
		out.println("无法获取登录码。<a href='../'>返回</a>>>");
		return;
	}

	Verifier verifier = new Verifier(code);
	Token requestToken = (Token)session.getAttribute("_OAUTH_REQUEST_TOKEN_");
	token = oauthService.getAccessToken(requestToken, verifier);

} else if(oauthService != null && oauthService.getVersion().indexOf("2") == 0){
	String code = request.getParameter("code");
	if(code == null || code.length() == 0){
		out.println("无法获取登录码。<a href='../'>返回</a>>>");
		return;
	}
	
	Verifier verifier = new Verifier(code);
	Token EMPTY_TOKEN = null;
	token = oauthService.getAccessToken(EMPTY_TOKEN, verifier);
	
} else {
	out.println("未知登录服务。<a href='../'>返回</a>>>");
	return;
}

AccessToken accessToken = everyCloudBusiness.createAccessToken(provider);
if(accessToken == null && accessToken.getAccessToken() == null){
	out.println("无法获取登录票据。<a href='../'>返回</a>>>");
	return;
}
accessToken.load(token.getRawResponse());

if(loginUser == null || loginProvider == null){
	UserInfo user = userAPI.myInfo(accessToken);
	String userId = user.getId();
	
	profileBusiness.addProfile(provider, userId, token.getRawResponse());
	
	session.setAttribute(EveryCloudBusiness.SESSION_LOGIN_PROVIDER, provider);
	session.setAttribute(EveryCloudBusiness.SESSION_LOGIN_ACCESS_TOKEN, accessToken);
	session.setAttribute(EveryCloudBusiness.SESSION_LOGIN_USER_INFO, user);
} else {
	String userId = loginUser.getId();
	profileBusiness.addSubToken(loginProvider, userId, provider, token.getRawResponse());
}
response.sendRedirect("../index.jsp");
return;
%>