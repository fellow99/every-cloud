<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.scribe.model.OAuthConfig" %>
<%@ page import="org.scribe.model.Token" %>
<%@ page import="org.scribe.builder.api.Api" %>
<%@ page import="org.scribe.oauth.OAuthService" %>
<%@ page import="com.fellow.every.one.business.EveryCloudBusiness" %>
<%
String provider = request.getParameter("p");
if(provider == null || provider.length() == 0){
	out.println("请选择登录服务。<a href='../'>返回</a>>>");
	return;
}

ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
		request.getSession().getServletContext());

EveryCloudBusiness everyCloudBusiness = ctx.getBean("everyCloudBusiness", EveryCloudBusiness.class);
OAuthConfig oauthConfig = everyCloudBusiness.getOAuthConfig(provider);
Api oauthAPI = everyCloudBusiness.getOAuthAPI(provider);

OAuthService oauthService = oauthAPI.createService(oauthConfig);

if(oauthService != null && oauthService.getVersion().indexOf("1") == 0){
    Token requestToken = oauthService.getRequestToken();  
    String authorizationUrl = oauthService.getAuthorizationUrl(requestToken);

    session.setAttribute("_OAUTH_REQUEST_TOKEN_", requestToken);
	response.sendRedirect(authorizationUrl);
	return;
    
} else if(oauthService != null && oauthService.getVersion().indexOf("2") == 0){
	Token EMPTY_TOKEN = null;
	String authorizationUrl = oauthService.getAuthorizationUrl(EMPTY_TOKEN);

	response.sendRedirect(authorizationUrl);
	return;
	
} else {
	out.println("未知登录服务。<a href='../'>返回</a>>>");
	return;
}
%>