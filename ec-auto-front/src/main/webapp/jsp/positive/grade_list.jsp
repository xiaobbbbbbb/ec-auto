<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/media/css/index-new.css" type="text/css"
	rel="stylesheet" />
<link
	href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/media/js/paginate/css/style.css" media="screen"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/media/css/question.css" media="screen"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7"
	type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_positive.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_search.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_important.js"></script>

<script type="text/javascript">
	$(function() {
		loadData();
	});

	function loadData() {
		var url=getUrl();
		$.getJSON(url, function(voData) {
			$("#dataTable").empty();
			var html = "";
			if (voData.list.length > 0) {
				$.each(voData.list, function(index, value) {
					html += "<tr><td >" + (index + 1) + "</td>";
					html += "<td ><a target='_blank' href='"+value.url+"'>" + value.title
							+ "</td>";
					html += "<td >" + value.brand_name+ value.serialName+ "</td>";
					html += "<td >" + value.mediaName + "</td>";
					html += "<td >" + value.ctime + "</td>";
					html += "</tr>";
				});
				$("#dataTable").empty().append(html);
				$("#_articles").paginate({
					totalPage : voData.totalPage,
					currentPage : voData.currentPage,
					display : 22,
					onLoad : function(page) {
						$('#cpage').val(page);
						loadData();
					}
				});
			} else {
				html += '<tr><td colspan="6" class="td_border">暂无数据</td></tr>';
				$("#dataTable").empty().append(html);
				$("#_articles").empty();
			}
		});
	}

	function getUrl(date) {
		var title = $('#title').val(); //观点属性
		if (title == "请输入关键字进行检索") {
			title = "";
		}
		var cpage = $("#cpage").val();
		var grade = ${grade};
		var serialId = ${serialId};
		return getWebPath() + "/positive/secondList?serialId=" + serialId + "&title="
				+ title + "&grade=" + grade + "&cpage=" + cpage;
	}
	
	function search(){
		$("#cpage").val(1);
		loadData();
	}
</script>
</head>
<body>
	<center>
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="q_title no_q_title">
				<div class="q_title_node">
					<a href="${ctx}/positive/index"  style="color: #ff6600">负面信息</a> &gt;
					<c:if test="${grade==1 }">一般</c:if>
					<c:if test="${grade==2 }">严重</c:if>
				</div>
				<div class="q_title_input">
					<a href="javascript:search();">搜索</a> <input value="请输入关键字进行检索"
						id="title" name="title" onfocus="enter()" />
				</div>
				<div class="clear"></div>
			</div>
			<div class="q_content no_q_content">

				<div class="q_table no_top">
					<table cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th width="50px">序号</th>
								<th>标题</th>
								<th width="120px">车系</th>
								<th width="140px">来源</th>
								<th width="140px">时间</th>
							</tr>
						</thead>
						<tbody id="dataTable">
							<tr>
								<td colspan="5">暂无数据</td>
							</tr>
						</tbody>
					</table>
					<div class="demo">
						<div id="_articles"></div>
					</div>
					<input type="hidden" id="cpage" value="1" />
				</div>
			</div>

			<jsp:include page="../common/footer.jsp" />
		</div>
	</center>
</body>
</html>