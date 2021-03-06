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
			$("#div-menu-provider").setParam("method", "USER");
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
	api.user.me(subProvider);
}

api = {};
api.user = {};

api.user.me = function(provider){
	$.ajax({type:"POST",
		async:false,
		url:"../api/user-data.do?method=me&provider=" + provider,
		dataType:"json",
		success: function(data, status){
			$("#div-content").setTemplateURL("../_html/user-info.html");
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

<div id="div-menu-provider">

</div>

<div id="div-operation">

</div>

<div id="div-content">

</div>


<div style="clear:both"></div>

<%@ include file="/_inc/footer.jsp"%>

</body>
</html>