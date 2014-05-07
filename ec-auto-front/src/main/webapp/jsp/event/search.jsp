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
<link href="${ctx}/media/css/index_.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
$(function() {
	initData();
}); 
function initData() {
	if( '${page.list.size()}' > 0){
		$("#_articles").paginate({
			totalPage : '${page.totalPage}',
			currentPage : '${page.currentPage}',
			display		: 22,
			onLoad : function(page) {
			   $('#pageNo').val(page);
			   var url =getUrl();
			   loadData(url);
			}
		});
	}else{
		var html= '<tr><td colspan="4" class="td_border">暂无数据</td></tr>';
		$("#dataTable").empty().append(html);
		$("#_articles").empty();
	}
}

function getUrl(){
	var searchStr = $("#title").val();  //搜索的内容
	if(searchStr=="请输入关键字进行检索"){
		searchStr="";
	}
	var pageNo=$("#pageNo").val();
    return "${ctx}/event/secondSearch?searchStr="+searchStr+"&pageNo="+pageNo; 
}

function secondSearch(){
	var url =getUrl();
    loadData(url);
}

//仍需要关注的负面信息
function loadData(url) {
	$.getJSON(
		url,
		function(voData) {
			var html = "";
			if (voData.list.length > 0) {	
				$.each(voData.list,
					function(index, value) {
					html +="<tr><td >"+(index+1)+"</td>";
					html +="<td ><a href='${ctx}/event/propagationAnalysis?eid="+value.eid+"&propagationNodeType="+value.type+"'  >"+value.name+"</a></td>";
					html +="<td >"+new Date(value.createTime).format('yyyy年MM月dd日')+"</td>";
					html += "</tr>";
						
					});
				$("#dataTable").empty().append(html);
				$("#_articles").paginate({
					totalPage : voData.totalPage,
					currentPage : voData.currentPage,
					display		: 22,
					onLoad : function(page) {
						$('#pageNo').val(page);
						loadData(url);
					}
				});
			}else{
				var html= '<tr><td colspan="4" class="td_border">暂无数据</td></tr>';
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
			<div class="q_title no_q_title margint10">
				<div class="q_title_node"><a href="${ctx }/event/index" style="color: #ff6600">事件追踪</a></div>
				<div class="q_title_input">
					<a href="javascript:secondSearch();">搜索</a>
					<input placeholder="请输入关键字进行检索" id="title" name="title" value="${searchStr }" onfocus="enter()">
				</div>
		        <div class="clear"></div>
			</div>
			<hr />
			<div style="text-align: left;margin-top: 8px;">
			   <span style="margin-left: 7px;font-size: 13px;" id="tips">您检索的&quot;${searchStr }&quot;的内容如下</span>
			</div>
			 
			<div class="q_content no_q_content" style="margin-top: 8px;">
				<div class="q_table no_top">
					<table cellspacing="0" cellpadding="0" >
						<thead>
					 		<tr>
					 			<th width="50px">序号</th>
								<th>主题</th>
								<th width="150px" >时间</th>
							</tr>
					 	</thead>
						<tbody id="dataTable">
							<c:forEach items="${page.list}" var="dto" varStatus="sn" >
								<tr>
				                   <td>${sn.count}</td>
				                   <td><a href='${ctx}/event/propagationAnalysis?eid=${dto.eid}&propagationNodeType=${dto.type}'  >${dto.name }</a></td>
			                 	   <td><fmt:formatDate pattern="yyyy年MM月dd日" value="${dto.createTime }"/></td>
				                </tr>
							</c:forEach>
						</tbody>
					</table>
					<!-- 分页控件 -->
					<div class="demo">
						<div id="_articles"></div>
					</div>
					<input type="hidden" id="pageNo" value="1" />
					<!-- 分页控件尾巴 -->
				</div>
			</div>
			
			 
			
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>