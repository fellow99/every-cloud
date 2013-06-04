<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.fellow.every.one.business.EveryCloudBusiness" %>
<%@ page import="com.fellow.every.one.business.ProfileBusiness" %>
<%@ page import="com.fellow.every.user.UserInfo" %>
<%!
class ProviderMenu{
	private EveryCloudBusiness.Capability capability;
	private PrintWriter out;
	private HttpServletResponse response;
	private HttpServletRequest request;
	
	public ProviderMenu(EveryCloudBusiness.Capability capability, PrintWriter out, HttpServletRequest request, HttpServletResponse response){
		this.capability = capability;
		this.out = out;
		this.response = response;
		this.request = request;
	}
	
	public void providerMenu() throws Exception{
		out.println("<div id='menu-provider'><ul>");
		
		providerMenuItem("weibo", "新浪");
		providerMenuItem("qq", "腾讯");
		providerMenuItem("baidu", "百度");
		providerMenuItem("renren", "人人");
		providerMenuItem("360", "360");
		//providerMenuItem("kuaipan", "金山");
		//providerMenuItem("163", "网易");
	
		out.println("</ul></div>");
	}
	
	public void providerMenuItem(String subProvider, String name) throws Exception{
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(
				request.getSession().getServletContext());
		EveryCloudBusiness everyCloudBusiness = ctx.getBean("everyCloudBusiness", EveryCloudBusiness.class);
		ProfileBusiness profileBusiness = ctx.getBean("profileBusiness", ProfileBusiness.class);

		String provider = (String)request.getSession().getAttribute(EveryCloudBusiness.SESSION_LOGIN_PROVIDER);
		UserInfo u = (UserInfo)request.getSession().getAttribute(EveryCloudBusiness.SESSION_LOGIN_USER_INFO);
		String userId = u.getId();
		boolean login = (profileBusiness.getSubToken(provider, userId, subProvider) != null);
		boolean capable = everyCloudBusiness.isCapable(subProvider, capability);
		out.println("<li>");
		
		if(!capable){
			out.println("xx" + name);
		} else if(!login){
			out.println("<a href='javascript:void(0)' onclick='subProviderLogin(\""+subProvider+"\")'>");
			out.println("ll" + name);
			out.println("</a>");
		} else {
			out.println("<a href='javascript:void(0)' onclick='subProviderClick(\""+subProvider+"\")'>");
			out.println("ok" + name);
			out.println("</a>");
		}
		
		out.println("</li>");
	}
}

%>
<script type="text/javascript">
function subProviderLogin(subProvider){
	alert(subProvider);
}
function subProviderClick(subProvider, jsFunc){
	if(jsFunc)jsFunc(subProvider);
}
</script>