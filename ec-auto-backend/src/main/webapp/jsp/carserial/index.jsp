<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>车系字典</title>
	<jsp:include page="../common/static_header.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<jsp:include page="../common/north.jsp"></jsp:include>
	<jsp:include page="../common/east.jsp"></jsp:include>
	<jsp:include page="../common/west.jsp"></jsp:include>
	<jsp:include page="../common/south.jsp"></jsp:include>
	
	<div data-options="region:'center',title:'功能区'">
		<jsp:include page="center/carserial_list.jsp"></jsp:include>
	</div>	
</body>
</html>