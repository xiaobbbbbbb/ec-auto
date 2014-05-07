<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/media/js/paginate/css/style.css" media="screen"/>
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_discuss.js"></script>
<script type="text/javascript">
	$(function() {
		loadData();
	});
</script>
</head>
<body>
	<center>
		<jsp:include page="common/head.jsp" />
		<div class="all">
			<div class="detail_center">
				<span class="detail_center_tag"><a href="${ctx}/index/">首页</a>&gt; <a href="${ctx}/discuss/index">产品评价 </a>&gt; <a href="${ctx}/discuss/index">产品口碑</a> &gt;评论列表 <span class="a_title"></span></span>
				<span class="detail_center_about">
					&nbsp;&nbsp;&nbsp;&nbsp;产品名称：<span>${serialName}</span>
					&nbsp;&nbsp;&nbsp;&nbsp;观点：<span>${viewPointName}</span>
				</span>
				<input type="hidden" id="basePath" value="${ctx}"/>
				<input type="hidden" id="viewPointId" value="${viewPointId }" />
				<input type="hidden" id="carSerialId" value="${carSerialId }" />
				<input type="hidden" id="viewPointAffection" value="${viewPointAffection }" />
				<input type="hidden" id="startDate" value="${startDate }" />
				<input type="hidden" id="endDate" value="${endDate }" />
				<input type="hidden" id="offset" value="1" />
				<span class="search_tag">相关观点列表 </span>
				<div class="clear"></div>
				<div class="detail_center_else" style="margin-top: 0px;" >
					<div id="one" class="page_content_div">
						<div class="page_content">
							<table cellspacing="0" cellpadding="0" style="border-left: 1px solid #bcbcbc;">
								<tr>
									<th class="th_border" >标题</th>
									<th class="th_border" >来源媒体</th>
									<th class="th_border" width="150px" >时间</th>
								</tr>
								<tbody id="articleDataInfo">
								</tbody>
							</table>
						</div>
						<div class="demo">
								<div id="_articles"></div>	
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>

		
		<jsp:include page="common/footer.jsp" />
	</center>
</body>
</html>
