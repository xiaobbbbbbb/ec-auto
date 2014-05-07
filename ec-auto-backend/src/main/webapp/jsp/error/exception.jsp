<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
		<title>车信4S管理平台——错误信息</title>
	</head>

		<body>
			
			<h1>系统处理错误!</h1>
			<br/>
			<a href="javascript:void(0);" style="display:none;" onclick="$('#error_detail').toggle(); return false;">显示详细信息</a>
			<div id="error_detail" style="display:none;">
			${errorMsg }
			</div>
			
		</body>
</html>

