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
<link href="${ctx}/media/js/paginate/css/style.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/css/question.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_search.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>

<script type="text/javascript">
	
	$(function() {
		loadData();


	}); 
	
	function loadData() {
		var cpage = $('#cpage').val();
		if(!cpage || cpage <= 0) {
			cpage = 1;
		}
		var url = getWebPath() + "/advert/list?cpage="+cpage;
	
		$.getJSON(
			url,
			function(voData) {
					var html = "";
				if (voData.list.length > 0) {	
				
					$.each(voData.list,
							function(index, value) {
								html+='<div class="q_content"><div class="q_content_title_else">';
								html+= '<span class="advert_title">活动说明:</span>';
								html+='<div class="q_content_more_else" >';
								html+='<img src="${ctx}/media/images/q_more__no.png"  />';
								html+=	'<div>收起</div></div><div class="clear" ></div>';
								html+='<span class="advert_context">'+value.content+'</span></div>';
								html+='<div class="q_table no_top">'+
								'<table cellspacing="0" cellpadding="0" >'+
									'<thead>'+
								 	'<tr>'+
											'<th>序号</th>'+
											'<th>投放媒体</th>'+
											'<th>投放时间</th>'+
											'<th>投放版面</th>'+
											'<th>投放地址</th>'+
											'<th>标题</th>'+
											'<th>平均数值</th>'+
											'<th>是否达标</th>'+
											'<th>状态</th>'+
											'<th>操作</th>'+
										'</tr>'+
								 	'</thead>'+
									'<tbody id="dataTable">';
									if(value.detail.length>0){
										$.each(value.detail,
												function(index, value) {
											html+='<tr><td>'+(index+1)+'</td>';
											html+='<td>'+value.mediaName+'</td>';
											html+='<td><font color="#047ebc">计划投放时间:'+value.planStime+'</font></br><font color="#ff6600">实际投放时间:'+value.realStime+'</font></td>';
											html+='<td>'+value.pageId+'</td>';
											html+='<td>'+value.url+'</td>';
											html+='<td>'+value.title+'</td>';
											html+='<td><font color="#047ebc">转发数：'+value.transmitNum+'</font></br><font color="#ff6600">评论数：'+value.commentNum+'</font></td>';
											html+='<td>'+value.isStandard+'</td>';
											html+='<td>'+value.status+'</td>';
											html+='<td><a href="${ctx}/advert/goUpdate?id='+value.id+'">编辑</a></td>';
										});
									}else{
										html+=
										'<tr>'+
										'	<td colspan="10" >暂无数据</td>';
									}
									html+=	'</tr></tbody></table></div></div></div>';
							});
					$("#q_content").empty().append(html);
					
					$(".q_content_more_else").click(function(){
						if($(this).find("div").html()=="收起"){
							$(this).find("div").html("展开");
							$(this).find("img").attr("src",getWebPath() +"/media/images/q_more__.png");
						}else{
							$(this).find("div").html("收起");
							$(this).find("img").attr("src",getWebPath() +"/media/images/q_more__no.png");
						}
						$(this).parent().parent().find(".q_table").slideToggle("slow");
						
					});
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
					html += '<tr><td colspan="6" class="td_border">暂无数据</td></tr>';
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
			<div class="q_title">
				<div class="q_title_node" >广宣分析</div>
				<div class="q_title_input" >
					<a class="q_title_input_a" style="width: 120px;" href="${ctx}/advert/goAdd" >配置</a>
					<a href="javascript:void(0);" >搜索</a>
					<input value="请输入关键字进行检索" />
				</div>
		        <div class="clear" ></div>
				<div class="q_line" ></div>
			</div>
			<div id="q_content">
				
			</div>
			<div class="demo">
					<div id="_articles"></div>
				</div>
						<input type="hidden" id="cpage" value=1 />
			<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>