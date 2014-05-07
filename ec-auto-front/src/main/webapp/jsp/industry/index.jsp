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
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>

<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>

<script type="text/javascript" src="${ctx}/media/js/ec/ec_business.js"></script>

<script type="text/javascript">

	function loadData(cpage) {
		if(!cpage) {
			cpage = 1;
		}
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime.length > 0 && endTime.length > 0) {
			if(validateDate(startTime,endTime)){
			return ;
			}
		}
		var url = getURIStr()+'&cpage='+cpage;
		$.getJSON(url, function(data) {
			var tbody = $('#dataTable');
			tbody.empty();
			var tb = '';
			for (var i = 0; i < data.list.length; i++) {
				var obj = data.list[i];
				tb += '<tr><td class="td_border"><a href="${ctx}/industry/listArticles/'+obj.id+'">' + obj.title + '</a></td>';
				tb += '<td class="td_border">' + (obj.mediaName?obj.mediaName:'') + '</td>';
				tb += '<td class="td_border">' + obj.ctime + '</td></tr>';
			}
			
			if(data.list.length==0){
				tb = '<tr><td colspan="3" class="td_border">暂无数据</td></tr>';
				tbody.append(tb);
				$("#_articles").empty();
				return false;
			}
			
			tbody.append(tb);
			$("#_articles").paginate({
				currentPage : data.currentPage,
				totalPage 	: data.totalPage,
				display     : 22,
				onLoad    : function(page){
								loadData(page);
							  }
			});
		});
	}
	
	/**
	*	收集参数
	*/
	function getURIStr() {
		var frommedia = $('#frommedia').val();
		var day = $('#time').val();
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		if(day == 0) {
			day = 1;
		}
		if(!startTime && day) {
			startTime = day;
		}
		var url = '${ctx}/industry/list?mediaId='+frommedia+'&startDate='+startTime+'&endDate='+endTime;
		return url;
	}
	
	function solrSearch(cpage) {
		var day = $('#time').val();
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		if(day == 0) {
			day = 1;
		}
		if(!startTime && day) {
			startTime = day;
		}
		var mediaName = $('#frommedia').find("option:selected").text();
		if(!mediaName) {
			mediaName = "全部";
		}
		var url = "${ctx}/industry/search?keyword="+$('#keyword').val()+"&cpage="+cpage+'&mediaName='+$('#frommedia').find("option:selected").text()+'&startDate='+startTime+'&endDate='+endTime;
		$.getJSON(url, function(data) {
			var tbody = $('#dataTable');
			tbody.empty();
			var tb = '';
			for (var i = 0; i < data.list.length; i++) {
				var obj = data.list[i];
				tb += '<tr><td class="td_border"><a href="${ctx}/industry/listArticles/'+obj.id+'">' + obj.title + '</a></td>';
				tb += '<td class="td_border">' + (obj.mediaName?obj.mediaName:'') + '</td>';
				tb += '<td class="td_border">' + obj.ptime + '</td></tr>';
			}
			
			if(data.list.length==0){
				tb = '<tr><td colspan="3" class="td_border">暂无数据</td></tr>';
				tbody.append(tb);
				$("#_articles").empty();
				return false;
			}
			
			tbody.append(tb);
			$("#_articles").paginate({
				currentPage : data.currentPage,
				totalPage 	: data.totalPage,
				display     : 22,
				onLoad    	: function(page){
						solrSearch(page);
				}
					
			});
		});
	}
	
	function articleSearch(cpage) {
		if($('#keyword').val()) {
			solrSearch(cpage);
		} else {
			loadData(cpage);
		}
	}
	
	$(function() {
		$('#default_').click();
		articleSearch(1);
		
		$("#startTime,#endTime").datepickerStyle();

		$("#search_btn").click(function() {
			articleSearch(1);
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
					<a href="${ctx }/industry/index" style="color: #aaccdd; background-image: url('../media/images/detail_left_as.png');">动态列表</a> <a href="${ctx }/industry/cloud">行业云视图</a>
				</div>
			</div>
			<div class="detail_right">
				<div class="detail_right_search">
					<table id="search_table">
					<tr>
						<td class="td_border" colspan="2" ><div class="search_tag">筛选</div></td>
					</tr>
					<tr>
						<td  class="td_no_border_right" valign="top" style="width: 100px;"><div class="search_tag">时间：</div></td>
						<td  class="td_border_left" id="a_time">
							
							<span class="search_a">今天<input type="hidden" value="1" /></span>
							<span>昨天<input type="hidden" value="-1" /></span> 
							<span id="default_">最近7天<input type="hidden" value="7" /></span> 
							<span>最近15天<input type="hidden" value="15" /></span> 
							<span>最近30天<input type="hidden" value="30" /></span> 
							<span class="custom">自定义时间<input type="hidden" value="1" /></span>
							
							<div class="clear"></div>
							<div id="search_time" style="display: none;">
								开始时间：<input type="text" readonly="readonly" name="startTime" id="startTime" />&nbsp;&nbsp;&nbsp;结束时间：<input type="text" readonly="readonly"
									name="endTime" id="endTime" />
							</div> 
							<input type="hidden" id="time" value="0" />
						</td>
					</tr>
					<tr>
						<td class="td_no_border_right" ><div class="search_tag">来源媒体：</div></td>
						<td class="td_border_left" id="a_brand">
							<select id="frommedia" name="frommedia">
								<option value="-1">全部</option>
								<c:forEach items="${mediaInfos}" var="dto" varStatus="sn">
									<option value="${dto.id}">${dto.name}</option>
								</c:forEach>
							</select>
							<div class="clear"></div>
						</td>
					</tr>
					<tr>
						<td class="td_no_border_right" ><div class="search_tag">关键字：</div></td>
						<td class="td_border_left" id="a_brand">
							<input type="text" id="keyword" name="keyword" value=""/>
							<div class="clear"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="td_border" ><div id="search_btn">立即筛选</div></td>
					</tr>
				</table>
					
				</div>
				<br/>
				<br/>
				<span class="search_tag">动态新闻</span>
				<div class="clear"></div>
				<div class="detail_right_content_table" >
					 <table cellspacing="0" cellpadding="0" >
					 	<thead>
					 		<tr>
								<th class="th_border">动态标题</th>
								<th class="th_border">来源媒体</th>
								<th class="th_border">时间</th>
							</tr>
					 	</thead>
						<tbody id="dataTable">
							<%-- <c:forEach items="${articleSimpleLists.list }" var="dto" varStatus="sn">
							<tr>
								<td class="td_border">${dto.title }</td>
								<td class="td_border">${dto.mediaName}</td>
								<td class="td_border">${dto.createTime }</td>
							</tr>
							</c:forEach> --%>
						
						</tbody>
					</table>
				</div>
				<div class="demo" >
					<div id="_articles" >
					</div>	
				</div>
			</div>
			<div class="clear"></div>
		</div>
			
			<jsp:include page="../common/footer.jsp" />
	</center>
</body>
</html>
