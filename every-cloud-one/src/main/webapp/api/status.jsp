<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ include file="/_inc/head.jsp"%>
<%@ include file="/_inc/js.jsp"%>

<script type="text/javascript">

$(document).ready(function(){
	$.ajax({type:"POST",
		async:false,
		url:"../sub-provider/data.do?method=list",
		dataType:"json",
		success: function(data, status){
			$("#div-menu-provider").setTemplateURL("../_html/sub-provider-menu.html");
			$("#div-menu-provider").setParam("method", "STATUS");
			$("#div-menu-provider").processTemplate(data);
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
});

function subProviderLogin(subProvider){
	alert("loginFunc" + subProvider);
}
function subProviderClick(subProvider){
	api.status.list(subProvider, "public");
}


api = {};
api.status = {};
api.status.list = function(provider, method, page, size){
	if(!page)page=0;
	if(!size)size=20;
	var url = "../api/status-data.do?method="+method+"&provider="+provider+"&page="+page+"&size="+size;
	$.ajax({type:"POST",
		async:false,
		url: url,
		dataType:"json",
		success: function(data, status){
			$("#div-content").setTemplateURL("../_html/status-list.html");
			$("#div-content").setParam("provider", provider);
			$("#div-content").processTemplate(data);
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
}
</script>

</head>

<body>

<%@ include file="/_inc/header.jsp"%>

<%@ include file="/_inc/menu.jsp"%>

<div id="div-menu-provider"></div>

<div id="div-content"></div>

<div style="clear:both"></div>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>