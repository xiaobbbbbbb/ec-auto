<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>海南中科情报系统后台</title>
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
	<script type="text/javascript" src="/static/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="/static/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/static/js/common.js"></script>
</head>
<body style="height:100%;width:100%;overflow:hidden;border:none;visibility:visible;background:#f0f1f1" >
  	<div class="easyui-window" title="用户登陆" style="height:300px;width:500px;background:#fafafa"
  			collapsible="false" minimizable="false" maximizable="false" closable="false" >
  		<div class="header" style="height:60px;padding:0px;magin:0px;background:url(images/bg-nav.png) repeat;">
  			<div style="margin:0 auto;padding:20px 128px;font-size:22px;">海南中科情报系统后台</div>
  		</div>
  		<div style="padding:30px 0px;">
  			<form id="login-form" action="/raluser/dologin" onsubmit="doLogin(); return false;" autocomplete="off">
  				<div style="text-align:center">
		  			<label style="text-align:right;">用户名：</label>
		  			<input type="text" id="username" name="username" style="height:25px;padding-left:10px;"/>
		  		</div>
		  		<div style="text-align:center;margin-top:15px;">
		  			<label style="text-align:right;">密&nbsp;&nbsp;码：</label>
		  			<input id="password" type="password" name="password" style="height:25px;padding-left:10px;"/>
		  		</div>
		  		<div style="padding-left:185px;margin-top:10px;">
		  			<button style="width: 0;height:0;border:0;position:absolute;" type="submit" onclick="doLogin(); return false;"></button> <!-- 是表单支持回车提交 -->
					<a  href="javascript:void(0);" class="easyui-linkbutton" onclick="doLogin(); return false;">登录</a> <!-- 通过链接模拟button使:active选择器生效 -->					
		  			&nbsp;&nbsp;&nbsp;&nbsp;
		  			<a class="easyui-linkbutton" onclick="javascript:document.getElementById('login-form').reset();">重置</a>
		  			
		  		</div>
  			</form>
  		</div>
  	</div>
	<script>
	function doLogin() {
	   	if (checkForm()) {
	   		var params = {};
	   		var $username=$("#username"),
			     $password=$("#password");
	   		 
	   		params.username = $username.val();
	   		params.password = $password.val();
	   		AjaxUtils.ajaxPost("/raluser/dologin", params, function(json) {
	   				if (json.succ=='Y') {
	   					top.document.location.href ="/main/index";
	   				} else {
	   					alert(json.desc);
	   				}
	   		 });
	   	 }
	}
	    
	    function checkForm()
		 {
	   		 var $username=$("#username"),
			     $password=$("#password");
	          if($.trim($username.val()).length==0 || $.trim($username.val())=='请输入用户名')
	          {
	       	   alert("请输入用户名");
			       $username.focus();
	          	   return false; 
	          }
	          else if($.trim($password.val()).length==0 || $.trim($password.val())=='请输入密码')
	          {
	       	   alert("请输入密码");
			       $password.focus();
	          	   return false; 
	          }        
	          return true; 
		 }
	</script>
</body>
</html>