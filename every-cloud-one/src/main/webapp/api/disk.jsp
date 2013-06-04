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
			$("#div-menu-provider").setParam("method", "DISK");
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
	api.disk.list(subProvider);
}


api = {};
api.disk = {};
api.disk.list = function(provider, path){
	if(!path)path = "/";
	var idx = path.lastIndexOf("/");
	var parentPath = (idx == -1 ? path : path.substring(0, idx));
	$.ajax({type:"POST",
		async:false,
		url:"../api/disk-data.do?method=list&provider=" + provider + "&path=" + path,
		dataType:"json",
		success: function(data, status){
			$("#div-content").setTemplateURL("../_html/disk-list.html");
			$("#div-content").setParam("provider", provider);
			$("#div-content").setParam("currentPath", path);
			$("#div-content").setParam("parentPath", parentPath);
			$("#div-content").processTemplate(data);
		},
		error: function(xhr, status, error){
	        alert(status + " " + error);  
		}
	});
}

api.disk.metadata = function(provider, path){
	alert("metadata");
}

api.disk.download = function(provider, path){
	alert("preview");
}

api.disk.cp = function(provider, path){
	alert("cp");
}

api.disk.mv = function(provider, path){
	alert("mv");
}

api.disk.rm = function(provider, path){
	alert("rm");
}

api.disk.mkdir = function(provider, path){
	alert("mkdir");
}

api.disk.share = function(provider, path){
	alert("share");
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