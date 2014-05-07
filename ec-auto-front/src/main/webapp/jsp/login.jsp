<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/login.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/login.js"></script>
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>

</head>
<body>
	<center>
		<input type="hidden" id="hidden" value="${ctx}" />
		<div class="main" >
			<img src="${ctx}/media/images/login_logo.png" alt="" />
			<div class="clear" ></div>
			<div class="input_name" >
				<span id="name_text">请输入用户名</span>
				<input id="name" value="user1" />
			</div>
			<div class="input_passwd" >
				<span id="passwd_text">请输入密码</span>
				<input id="passwd" type="password" value="123456"  />			
			</div>
			<input id="submit" type="button" value="登录" />
		</div>	
	</center>
</body>
</html>
