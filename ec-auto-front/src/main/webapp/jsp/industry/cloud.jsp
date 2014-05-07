<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />

<link href="${ctx}/media/css/jquery.hotcloudtag-0.0.1.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>

<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.hotcloudtag-0.0.1.js"></script>

<script type="text/javascript">
$(function() {
		var url = '${ctx}/industry/hotClouds?maxRows=30';
		$.getJSON(url, function(data) {
			var tag_list = data;
    		$(".hot-cloud-tag").hotCloudTag({
                radius:210,
                speed:2.5,
    			fontsize:26,
    			checkbar:true
            }, tag_list);
		});
		
	});
</script>
</head>
<body>
	<center>
	<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="detail_left">
				<div class="detail_left_index">
					<a href="${ctx }/industry/index">动态列表</a> 
					<a href="${ctx}/industry/cloud" style="color: #aaccdd; background-image: url('../media/images/detail_left_as.png');">行业云视图</a>
				</div>
			</div>
			<div class="detail_right">
				<div class="detail_right_content">
					<div class="hot-cloud-tag" style="width: 720px;height: 500px;"></div>
				</div>
			</div>		
			<div class="clear"></div>	
		</div>
			
			<jsp:include page="../common/footer.jsp" />
	</center>
</body>
</html>
