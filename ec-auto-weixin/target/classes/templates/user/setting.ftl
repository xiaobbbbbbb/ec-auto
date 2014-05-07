<!DOCTYPE html>
<html>
<head>
<title>用户设置</title>
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
				<label for="carModel">姓名:</label> 
				<input type="text" name="displayName" id=displayName value="${(user.displayName)!}"> 
				
				<label for="buyCarDate">email:</label> 
				<input type="text" name="email" id="email" value="${(user.email)!}"> 
	
				<label for="buyCarDate">手机号:</label> 
				<input type="text" name="telNo" id="telNo" value="${(user.telNo)!}"> 
				
				<input type="hidden" name="userId" id="userId" value="${(user.id)!}"> 
				
				<a href="#" data-role="button" onclick="dosetting(); return false;" data-ajax="false" data-theme="c">保存设置</a>
				<script type="text/javascript">
					function dosetting() {
						if ($("#form").attr("submitting")=="true") {
							return;
						}
						$("#form").attr("submitting", "true");
						
						var displayName = $.trim($("#displayName").val());
						var email = $.trim($("#email").val());
						var telNo = $.trim($("#telNo").val());
						var userId = $.trim($("#userId").val());
						$.ajax({type: "post", 
			                url: "/user/dosetting", 
			                dataType: "json",
			                data: {"displayName": displayName, "email": email, "telNo":telNo, "userId":userId},
			                success: function (json) { 
			                	if (json.succ=='Y') {
			                		message("保存成功");
			                		$("#form").attr("submitting", "false");
			                	} else {
			                		message(json.desc);
			                		$("#form").attr("submitting", "false");
			                	}
			                }, 
			                error: function (XMLHttpRequest, textStatus, errorThrown) { 
			                	message("保存失败");
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