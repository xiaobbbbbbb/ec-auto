<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index-new.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/question.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript">
	
</script>
</head>
<body>
	<center> 
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="q_content no_q_content">
				<div class="q_content_title">
					<span class="title_imp_media">网站访问量排名表 &gt;</span>
					<span style="cursor: pointer;float: right;margin-right:15px;" onclick="javascript:window.location.href='${ctx}/mediaanalyze/media'">查看更多&gt;&gt;</span>
					<div class="clear" ></div>
				</div>
				<div class="q_table no_top">
					<table cellspacing="0" cellpadding="0" >
						<thead>
					 		<tr>
								<th width="50px">排名</th>
								<th>网站名称</th>
								<th>综合排名</th>
								<th>日均访问量</th>
								<th>页面访问量排名</th>
								<th>平均每用户浏览页面数</th>
								<th  width="80px">查看详情</th>
							</tr>
					 	</thead>
						<tbody id="dataTable">
							<c:forEach items="${listNet }" var="net"  varStatus="sn" >
								<tr>
								    <td>${sn.count}</td>
								    <td><a href="http://www.${fn:split(net.url, '/')[fn:length(fn:split(net.url, '/'))-1]}" target='_blank'>${net.name}</a></td>
								    <td>${net.synthesisRanking}</td>
								    <td>${net.visitCount}</td>
								    <td>${net.pageCountRanking}</td>
								    <td>${net.avgBrowseCount }</td>
								    <td><a href="${net.url }" target="_blank" >查看详情</a></td>
								 </tr>
							</c:forEach>
							<c:if test="${empty listNet}">
			              		<tr>
				                   <td colspan="7">暂无数据</td>
				                </tr>
			              	</c:if>
						</tbody>
					</table>
				</div>
				<hr />
				<div class="q_content_title" style="margin-top: 10px">
					<span class="title_imp_media">微博 &gt;</span>
					<span  style="cursor: pointer;float: right;margin-right:15px;" onclick="javascript:window.location.href='${ctx}/mediaanalyze/weibo'">查看更多&gt;&gt;</span>
					<div class="clear" ></div>
				</div>
				<div class="q_table no_top">
					<table cellspacing="0" cellpadding="0" >
						<thead>
					 		<tr>
								<th width="50px">排名</th>
								<th>微博名称</th>
								<th>类型</th>
								<th>粉丝数</th>
								<th>微博数</th>
								<th>文章平均转发数</th>
								<th>平均评论数</th>
								<th width="80px">地区</th>
							</tr>
					 	</thead>
						<tbody id="dataTable">
							<c:forEach items="${listWeibo }" var="wb"  varStatus="sn" >
								<tr>
								    <td>${sn.count}</td>
								    <td><a href="${wb.url }" target="_blank" >${wb.name}</a></td>
								    <td>${wb.mediaName}</td>
								    <td>${wb.fansCount}</td>
								    <td>${wb.aboutArticleCount}</td>
								    <td>${wb.avgTransmitCount}</td>
								    <td>${wb.avgCommentCount}</td>
								    <td>
								    	<c:if test="${wb.provinceName==wb.cityName}">${wb.provinceName}</c:if> 
								    	<c:if test="${wb.provinceName!=wb.cityName}">${wb.provinceName}-${wb.cityName}</c:if> 
								    	<c:if test="${empty wb.provinceName}">未知</c:if>
								    </td>
								</tr>
							</c:forEach>
							<c:if test="${empty listWeibo}">
			              		<tr>
				                   <td colspan="8">暂无数据</td>
				                </tr>
			              	</c:if>
						</tbody>
					</table>
				</div>
			</div>
			
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>