<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
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
<script type="text/javascript">
	var keywords = '${cloud.keywords}';
	
	function loadArticles(keywords,cpage) {
		if(!keywords) {
			return false;
		}
		var url = '${ctx}/industry/clouds?keywords='+keywords+'&cpage='+cpage;
	    url =encodeURI(url);
		$.getJSON(url, function(data) {
			
			var tbody = $('#articleDataTable');
			tbody.empty();
			var tb = '';
			for (var i = 0; i < data.list.length; i++) {
				var obj = data.list[i];
				tb += '<tr><td class="td_border td_first"><a href="${ctx}/industry/listArticles/'+obj.id+'">' + obj.title + '</a></td>';
				tb += '<td class="td_border">' + (obj.mediaName?obj.mediaName:'') + '</td>';
				tb += '<td class="td_border">' + obj.ptime + '</td></tr>';
			}
			tbody.append(tb);
			var cloudArticleSize = 0;
			if(data.list.length > 0) {
				cloudArticleSize = data.totalRows;
 				$("#_articles").paginate({
 					currentPage : data.currentPage,
 					totalPage 	: data.totalPage,
 					display     : 30,
 					onLoad    : function(page){
 									loadArticles(keywords,page);
 								}
				
 				});
				
			}
			$('#cloudArticleSize').text(cloudArticleSize);
		});
	}
	
	$(function() {
		//judge(0);
		loadArticles(keywords,1);
		
		
		
	});
	
	
</script>

</head>
<body>
	<center>
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="detail_center">
				<span class="detail_center_tag"><a href="${ctx}/index/">首页</a>    &gt; <a href="${ctx}/industry/cloud">热点追踪</a> &gt; 行业云视图 <span class="a_title"></span></span>
				<span class="detail_center_about">
						<span>“${cloud.title }” </span>相关信息(<span id="cloudArticleSize"></span>)
				</span>
				
				<div class="detail_center_else" >
					<div id="one" class="page_content_div">
						<div class="page_content">
							<table cellspacing="0" cellpadding="0" style="border-left: 1px solid #bcbcbc;">
								<tbody id="articleDataTable">
								</tbody>
							</table>
						</div>
						<div class="demo">
							<div id="_articles" >
							</div>	
						</div>
					</div>
				</div>
				
			</div>
			<div class="clear"></div>
		</div>

		
		<jsp:include page="../common/footer.jsp" />
	</center>
</body>
</html>
