<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.fellow.every.one.business.EveryCloudBusiness" %>
<%@ page import="com.fellow.every.user.UserInfo" %>
<%
UserInfo footer_user = (UserInfo)session.getAttribute(EveryCloudBusiness.SESSION_LOGIN_USER_INFO);
%>
<div id="footer_bottom">
	<div style="float:left">
	<%if(footer_user == null){%>
		<a href="<%=request.getContextPath()%>/login/login.jsp">请登录</a>
	<%} else {//if(user == null){%>
		<a href="<%=request.getContextPath()%>/profile/"><%=footer_user.getNickname()%></a>
		[<a href="<%=request.getContextPath()%>/login/logout.jsp">注销</a>]
	<%}//if(user == null){%>
	</div>
	<div style="float:right">
		| <a href="<%=request.getContextPath()%>/login/about.jsp">关于</a>
	</div>
	<div style="float:right;padding:0px;padding-top:3px;">
<!-- Baidu Button BEGIN -->
<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" style="padding: 0px">
<a class="bds_tsina"></a>
<a class="bds_tqq"></a>
<a class="bds_qzone"></a>
<a class="bds_renren"></a>
<span class="bds_more"></span>
</div>
<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=3574523" ></script>
<script type="text/javascript" id="bdshell_js"></script>
<script type="text/javascript">
document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
</script>
<!-- Baidu Button END -->
	</div>
</div>