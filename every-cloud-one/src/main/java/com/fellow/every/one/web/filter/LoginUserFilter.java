package com.fellow.every.one.web.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fellow.every.one.business.EveryCloudBusiness;
import com.fellow.every.one.business.ProfileBusiness;
import com.fellow.every.auth.AccessToken;
import com.fellow.every.user.UserInfo;

public class LoginUserFilter implements Filter{
	private String loginUrl;
	/** 需要排除（不拦截）的URL的正则表达式 */
	private Pattern[] passUrlPatterns;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String contextPath = filterConfig.getServletContext().getContextPath();
		loginUrl = filterConfig.getInitParameter("login-url");
		if(loginUrl == null || loginUrl.length() == 0){
			throw new ServletException("Parameter login-url not found.");
		}
		String passUrlRegexp = filterConfig.getInitParameter("pass-url-regexp");
		if(passUrlRegexp != null && passUrlRegexp.length() > 0){
			String[] strs = passUrlRegexp.split("\n");
			passUrlPatterns = new Pattern[strs.length];
			for(int i = 0; i <strs.length; i++){
				String regexp = contextPath + strs[i].replaceAll("\t", "").trim();
				passUrlPatterns[i] = Pattern.compile(regexp);
			}
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;

		
		//检查登录用户信息
		String provider = (String)request.getSession().getAttribute(
				EveryCloudBusiness.SESSION_LOGIN_PROVIDER);
		AccessToken accessToken = (AccessToken)request.getSession().getAttribute(
				EveryCloudBusiness.SESSION_LOGIN_ACCESS_TOKEN);
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(
				EveryCloudBusiness.SESSION_LOGIN_USER_INFO);
	    
	    //把登陆用户信息放入LoginUserContextHolder中
	    LoginUserContextHolder.set(provider, accessToken, userInfo);


		//如果请求的路径与forwardUrl相同，或请求的路径是排除的URL时，则直接放行
		String servletPath = request.getRequestURI();
		for(int i = 0; i <passUrlPatterns.length; i++){
			if(passUrlPatterns[i].matcher(servletPath).matches()){
				filterChain.doFilter(request, response);
				return;
			}
		}
		
	    if(accessToken == null || provider == null || userInfo == null){
	    	response.sendRedirect(request.getContextPath() + loginUrl);
	    	return;
	    }
	    
		filterChain.doFilter(request, response);
	}


}
