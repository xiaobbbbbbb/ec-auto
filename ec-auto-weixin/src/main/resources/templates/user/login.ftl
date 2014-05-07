<!DOCTYPE html>
<html>
<head>
<title>企业情报分析</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.js"></script>
<#include "../common/header_script.ftl">
</head>
<body>

	<div data-role="page" id="home">
		<div data-role="header" data-theme="c">
			<h1></h1>
		</div>

		<div data-role="content">
			<form action="/web/insure/list" method="get" id="form">
				<label for="carModel">用户名:</label> 
				<input type="text" name="loginName" id="loginName" value=""> 
				
				<label for="buyCarDate">密码:</label> 
				<input type="password" name="passwd" id="passwd" value=""> 
				<input type="hidden" name="openId" id="openId" value="${openId}"> 
				<a href="#" data-role="button" onclick="doLogin(); return false;" data-ajax="false" data-theme="c">提交</a>
				<script type="text/javascript">
					function doLogin() {
						if ($("#form").attr("submitting")=="true") {
							return;
						}
						$("#form").attr("submitting", "true");
						
						var loginName = $.trim($("#loginName").val());
						var passwd = $.trim($("#passwd").val());
						if (loginName=='') {
							alert("登录名不能为空");
							return;
						}
						if (passwd=='') {
							alert("密码不能为空");
							return;
						}
						$.ajax({type: "post", 
			                url: "/user/dologin", 
			                dataType: "json",
			                data: {"loginName": loginName, "passwd": passwd, "openId": $("#openId").val()},
			                success: function (json) { 
			                	if (json.succ=='Y') {
			                		message("登录成功");
			                		$("#form").attr("submitting", "false");
			                		window.location.href = "/user/setting?userId=" + json.jsonData.userId;
			                	} else {
			                		message(json.desc);
			                		$("#form").attr("submitting", "false");
			                	}
			                }, 
			                error: function (XMLHttpRequest, textStatus, errorThrown) { 
			                	message("登录失败");
			                	$("#form").attr("submitting", "false");
			                }
			        	});
					}
				</script>
			</form>
		</div>

		<#include "../common/footer.ftl">
		<#include "../common/message.ftl">
	</div>
</body>
</html>