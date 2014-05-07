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
<link href="${ctx}/media/js/paginate/css/style.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/question.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_search.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript">
	$(function() {
		loadData();
		
	});
	function loadData(){
		var cpage=$("#cpage").val();
		var url = getWebPath() + "/mediaanalyze/weibolist?cpage="+cpage+"&pageSize=25";
		$.getJSON(
			url,
			function(voData) {
					var html = "";
				if (voData.list.length > 0) {	
					$.each(voData.list,
							function(index, value) {
								html +="<tr><td >"+((cpage-1)*25+index+1)+"</td>";
								html +="<td ><a href='"+value.url+"' target='_blank' >"+value.name+"</a></td>";
								html +="<td >"+value.mediaName+"</td>";
								html +="<td >"+value.fansCount+"</td>";
								html +="<td >"+value.aboutArticleCount+"</td>";
								html +="<td >"+value.avgTransmitCount+"</td>";
								html +="<td >"+value.avgCommentCount+"</td>";
								if(value.provinceName!=value.cityName){
									html +="<td >"+value.provinceName+'-'+value.cityName+"</td>";
								}else{
									html +="<td >"+(value.cityName?value.cityName:"未知")+"</td>";
								}
								
								html += "</tr>";
							});
					$("#dataTable").empty().append(html);
					$("#_articles").paginate({
						totalPage : voData.totalPage,
						currentPage : voData.currentPage,
						display		: 22,
						onLoad : function(page) {
							$('#cpage').val(page);
							loadData();
						}
					});
				}
				else{
					html += '<tr><td colspan="8" class="td_border">暂无数据</td></tr>';
					$("#dataTable").empty().append(html);
					$("#_articles").empty();
				}

			});
	}

</script>
</head>
<body>
	<center> 
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="q_content no_q_content">
				<div class="q_content_title" style="margin-top: 10px">
					<span class="title_imp_media">微博 &gt;</span>
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
							
						</tbody>
					</table>
					<div class="demo">
					<div id="_articles"></div>
					</div>
					<input type="hidden" id="cpage" value=1 />
				</div>
			</div>
			
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>