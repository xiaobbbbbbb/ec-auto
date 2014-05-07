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
<script type="text/javascript" src="${ctx}/media/js/ec/ec_question.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_search.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_important.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_dateformat.js"></script>
<script type="text/javascript" src="${ctx}/media/js/a_hover.js"></script>
<script type="text/javascript">
	$(function() {
		typeSearch(1);
	}); 
	
	 
	
	//根据类型查找
	function typeSearch(type){
		getTHeadInfo(type);  //头部文字
		$("#cpage").val(1);
		var url=getUrl(type);
		$(".title_imp_a_hover").removeClass("title_imp_a_hover");
		$(".title_imp_a :eq("+(type-1)+")").addClass("title_imp_a_hover");
		$("#type").val(type);
		loadData(url,type);
	} 
	function getUrl(type){
		var title = $('#title').val();  //观点属性
		if(title=="请输入关键字进行检索"){
			title="";
		}
		 
		var cpage=$("#cpage").val();
		return  getWebPath() + "/event/queryEventInfoList?eid=${eid}&title="+title+"&eventInfoType="+type+"&pageNo="+cpage;
	}
	
	function loadData(url,type) {
		$.getJSON(
				url,
				function(voData) {
					$("#dataTable").empty();
						var html = "";
					if (voData.list.length > 0) {	
						$.each(voData.list,
								function(index, value) {
									html +="<tr><td >"+(index+1)+"</td>";
									html +="<td >"+value[0]+"</td>";
									if(value[1].length>25){
										var substr=value[1].substring(0,25)+"...";
										html +="<td title='"+value[1]+"' style='text-align: left;padding-left: 20px;'><a target='_blank' href='"+value[6]+"' >"+substr+"</a></td>";
									}else{
										html +="<td style='text-align: left;padding-left: 20px;'><a target='_blank' href='"+value[6]+"' >"+value[1]+"</a></td>";
									}

									if(value[2]==1){
										value[2]='正面';
									}
									if(value[2]==2){
										value[2]='中性';
									}
									if(value[2]==3){
										value[2]='负面';
									}
									html +="<td >"+value[2]+"</td>";
									
									html +="<td >"+value[3]+"</td>";
									if(type==3){
										html +="<td >"+value[4]+"</td>";
									}
									html +="<td >"+new Date(value[5]).format('yyyy年MM月dd日')+"</td>";
									html += "</tr>";
								});
						$("#dataTable").empty().append(html);
						$("#_articles").paginate({
							totalPage : voData.totalPage,
							currentPage : voData.currentPage,
							display		: 22,
							onLoad : function(page) {
								$('#cpage').val(page);
								var url=getUrl(type);
								loadData(url);
							}
						});
					}
					else{
						if(type==3){
							html += '<tr><td colspan="7" class="td_border">暂无数据</td></tr>';
						}else{
							html += '<tr><td colspan="6" class="td_border">暂无数据</td></tr>';
						}
						$("#dataTable").empty().append(html);
						$("#_articles").empty();
					}

				});
	}
	
	
	function search(){
		var type=$("#type").val();
		var url=getUrl(type);
		loadData(url,type);
	}
	
	function getTHeadInfo(type){
		var html="";
		html += '<tr>';
		html += '<th width="50px">序号</th>';
		html += '<th>来源</th>';
		html += '<th width="400px" >文章</th>';
		html += '<th>情感</th>';
		if(type==3){
			html += '<th>转发数</th>';
		}
		html += '<th>评论数</th>';
		html += '<th width="120px" >时间</th>';
		html += '</tr>';
	    $("#thead_info").empty().append(html);
	}
	
</script>
</head>
<body>
	<center> 
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="q_title no_q_title margint30">
				<div class="q_title_node_a" >
					<a class="event_detail_a" href="${ctx}/event/index">返回事件分类</a>
					<a class="event_detail_a"  href="${ctx}/event/propagationAnalysis?eid=${eid}&propagationNodeType=${propagationNodeType}">传播分析</a>
					<a class="event_detail_a" href="${ctx}/event/detail2?eid=${eid}">网友情感</a>
					<a class="event_detail_a event_detail_current" href="${ctx}/event/detail3?eid=${eid}">相关文章</a>
					<a class="event_detail_a" href="${ctx}/event/detail4?eid=${eid}">事件公关</a>
				</div>
				<div class="q_title_input short" >
					<a style="width: 120px;margin-left: 20px" href="javascript:void(0);">生成专报</a>
				</div>
		        <div class="clear" ></div>
		        <div class="q_line"></div>
		        
			</div>
			
			<div class="q_title no_q_title margint10">
				<div class="q_title_node"></div>
				<div class="q_title_input">
					<a href="javascript:search();">搜索</a>
					<input placeholder="请输入关键字进行检索" id="title" name="title" onfocus="enter()">
				</div>
		        <div class="clear"></div>
			</div>
			<div class="q_content no_q_content">
				<div class="q_content_title">
					<input name="type" id="type" type="hidden" value="1" />
					<a class="title_imp_a margin20 title_imp_a_hover" href="javascript:typeSearch(1);" >新闻</a>
					<a class="title_imp_a" href="javascript:typeSearch(2);"  >论坛</a>
					<a class="title_imp_a" href="javascript:typeSearch(3);"  >微博</a>
					<a class="title_imp_a" href="javascript:typeSearch(4);" style="display: none"  >平媒</a>
					<div class="clear" ></div>
				</div>
				<div class="q_table no_top">
					<table cellspacing="0" cellpadding="0" >
						<thead  id="thead_info">
					 		<!-- <tr>
					 			<th width="50px">序号</th>
								<th>来源</th>
								<th width="400px" >文章</th>
								<th>情感</th>
								<th id="weibo_colspan" style="display: none">转发数</th>
								<th>评论数</th>
								<th width="120px" >时间</th>
							</tr> -->
					 	</thead>
						<tbody id="dataTable">
						</tbody>
					</table>
					<!-- 分页控件 -->
					<div class="demo">
						<div id="_articles"></div>
					</div>
					<input type="hidden" id="cpage" value="1" />
					<!-- 分页控件尾巴 -->
				</div>
			</div>
			
			 
			
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>