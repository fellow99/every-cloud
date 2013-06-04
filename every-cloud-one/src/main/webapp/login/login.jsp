<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!doctype html>
<html>
<head>
<%@ include file="/_inc/head.jsp"%>
<%@ include file="/_inc/js.jsp"%>
</head>

<body>

<%@ include file="/_inc/header.jsp"%>

<div id="menu-list">
    <ul>
    	<li><b>请选择登录方式</b></li>
        <li><a href="do.jsp?p=weibo">新浪微博登录</a></li>
		<li><a href="do.jsp?p=qq">腾讯微博登录</a></li>
		<li><a href="do.jsp?p=baidu">百度账号登录</a></li>
		<li><a href="do.jsp?p=renren">人人账号登录</a></li>
		<li><a href="do.jsp?p=360">360账号登录</a></li>
    </ul>
</div>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>