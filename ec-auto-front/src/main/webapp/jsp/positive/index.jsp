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
<script type="text/javascript" src="${ctx}/media/js/ec/ec_search.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_positive.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>

<script type="text/javascript">
	
	$(function() {
		
		$("#startTime,#endTime").datepickerStyle();

		
		$(".nav3 span:last").css("border-bottom","1px solid #2980c4");
		$(".nav3 span:first").css("border-top","1px solid #2980c4");
		$(".frame-menu").hover(function(){
			$(this).find("span").css("display","block");
		},function(){
			$(this).find("span").hide();
		});
		loadTopData();
		judge() ;
		loadData();
	 	$(".q_search_a").click(function(){
	 		$("#cpage").val(1);
	 		loadData();
	 	});
	}); 
	
	function judgePie(id){
		$(".nav3 .info").hide();
		
			var cur = $("#select_serial_" + id).text();
			cur = (cur != null && cur.length > 0) ? cur : $("#select_serial")
			.text();
			$("#select_serial").text(cur);
			$("#serial_id").val(id);
			$(".s_car").text(cur);
			loadTopData();
			judge();
	}
	
	
	function loadData() {
		var grade=$(".grade .current").attr("id");
		var serialId=$("#serial_id").val();
		var param= getSearchUrl();
		var url = getWebPath() + "/positive/queryList?serialId="+serialId+"&grade="+grade;
		if (param != null) {
			url += param;
		}
		$.getJSON(
			url,
			function(voData) {
				$("#dataTable").empty();
					var html = "";
				if (voData.list.length > 0) {	
				
					$.each(voData.list,
							function(index, value) {
								html +="<tr><td >"+(index+1)+"</td>";
								html +="<td ><a href='"+value.url+"' target='_blank' >"+value.title+"</td>";
								if(value.provinceName==null && value.cityName==null){
									html +="<td >未知</td>";
								}else{
									html +="<td >"+(value.provinceName?value.provinceName:"")+(value.cityName?value.cityName:"")+"</td>";
								}
								html +="<td >"+value.serialName+"</td>";
								var grade="";
								if(value.grade =="1"){
									grade="一般";
								}if(value.grade =="2"){
									grade=" <font color='red'>严重</font>";
								}
								html +="<td >"+grade+"</td>";
								html +="<td >"+value.atime+"</td>"; 
								html +="<td ><a href='${ctx}/event/add?title="+value.title+"'  >转为事件</a></td>";
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
					html += '<tr><td colspan="7" class="td_border">暂无数据</td></tr>';
					$("#dataTable").empty().append(html);
					$("#_articles").empty();
				}

			});
	}
	
	function skip(e){
		var serialId=$("#serial_id").val();
		var url = getWebPath()+"/positive/second?serialId="+serialId+"&grade="+e;
		window.location.href=url;
	}
</script>
</head>
<body>
	<center>
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="q_title">
				<div class="q_title_node" >负面信息</div>
				
				<a class="title_model frame-menu nav3" style="color: #ffffff; background-image: url('../media/images/index_a_bg.png');">
			        	<b id="select_serial" >全部</b><input id="serial_id" type="hidden" value="0"/>
			        	<span class="info" id="select_serial_0" onclick="judgePie(0)">全部</span>
				    <c:forEach items="${serials}" var="dto" varStatus="sn">
				        <span class="info" id="select_serial_${dto.id}" onclick="judgePie(${dto.id});">${dto.name}</span>
				        
				    </c:forEach>
				</a>
		        <div class="clear" ></div>
				<div class="q_line" ></div>
			</div>
			<div class="q_content">
		 		<div class="q_content_l">
		 			<span class="content_l_title">累计</span>
		 			<center>
			 			<div class="content_l_div" style="float: none;"  >
			 			负面信息：<span class="content_l_blue" id="allnums">${highNums}+${lowNums}</span>条
			 			</div>
		 			</center>
		 			<div class="clear" ></div>
		 		</div>
		 		<div class="q_content_c">
		 			<span class="content_l_title">负面等级</span>
		 			<div class="content_l_div" >
		 			严重：<span class="p_content_l_reds" id="highnums"   onclick="skip(2)">${highNums}</span>条
		 			</div>
		 			<div class="content_l_div_line" >
		 			一般：<span class="p_content_l_blues" id="lownums"  onclick="skip(1)">${lowNums}</span>条
		 			</div>
		 			<div class="clear" ></div>
		 		</div>
		 		<!-- 演示不需要
		 		<div class="q_content_r" id="viewpoint_type">
		 		 	<span class="content_l_title" >分类</span>
		 		 	<c:forEach items= "${dataList }" var ="dto" varStatus="sn">
		 		 		<div class="content_l_div" > 
		 		 			<c:if test="${dto.name ==1}">产品：<span class="content_l_blue">${dto.value }</span>条</c:if>
		 		 			<c:if test="${dto.name ==3}">服务：<span class="content_l_blue">${dto.value }</span>条</c:if>
		 		 			<c:if test="${dto.name ==4}">其它：<span class="content_l_blue">${dto.value }</span>条</c:if>
		 				</div>
		 		 	</c:forEach>
		 			<div class="clear" ></div>
		 		</div>
		 		 -->
		 		<div class="clear" ></div>
			</div>
			<div class="q_content">
				<div class="q_content_title">
					<span>负面情报趋势图 &gt;</span>
				</div>
				<div class="q_content_border">
					<div class="q_content_long_img">
						<div id="chart" style="height:250px;width: 930px;">
						</div>
					</div>
				</div>
			</div>
			 
			<div class="q_content">
				<div class="q_content_title">
					<span>负面信息筛选 &gt;</span>
					<div class="q_content_more">
						<img src="${ctx}/media/images/q_more_no.png"  />
						<div>收起</div>
					</div>
				</div>
				<div class="q_content_border bottom_border">
					 <div class="q_content_search">
					 	 <div class="search_node">时间：</div>
					 	 <div class="search_content">
					 	 	<input type="text" readonly="readonly" name="startTime" id="startTime" />
					 	 	&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
					 	 	<input type="text" readonly="readonly" name="endTime" id="endTime" />
							<input type="hidden" id="time" value="0" />
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	 <div class="search_node">等级：</div>
					 	 <div class="search_content grade">
					 	 	  <a id="0"  class="current" href="javascript:void(0);" >全部</a>
					 	 	  <a id="1">一般</a>
							  <a id="2">严重</a>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	 <div class="search_node">区域：</div>
					 	 <div class="search_content area">
					 	 	<div  id="0"  class="provinces_div current">
					 	 		<div class="provinces_name">全部</div>
					 	 	</div>
					 	 	<c:forEach items="${provinces}" var="dto" varStatus="sn">
					 	 		<div class="provinces_div" id="${dto.id}">
					 	 			<div class="provinces_name">${dto.name}</div>
					 	 			<div class="city_div" >
						 	 			<div style="color: #ffffff" id="0" class="city_current">全省</div>
					 	 			</div>
					 	 		</div>
							</c:forEach>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	<div class="search_node">已选条件：</div>
					 	<div class="search_content">
					 		<div class="s">
					 			车系：<span class="s_node s_car" >全部</span>
					 		</div>
					 		<div class="s">
					 			等级：<span class="s_node s_grade" >全部</span>
					 		</div>
					 		<div class="s">
					 			区域：<span class="s_node s_area" >全部</span>
					 		</div>
					 		<div class="clear"></div>
					 	</div>
					 	<div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	<a class="q_search_a" href="javascript:void(0);" >开始筛选</a>
					 </div>
				</div>
			</div>
			<div class="q_table">
				<table cellspacing="0" cellpadding="0" >
					<thead>
				 		<tr>
							<th width="50px">序号</th>
							<th>标题</th>
							<th>省份</th>
							<th>车系</th>
							<th>等级</th>
							<th>时间</th>
							<th>转为事件</th>
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
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>