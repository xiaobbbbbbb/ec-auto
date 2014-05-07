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

<script type="text/javascript">
	
	$(function() {
		loadData();
		var serialId= $("#serial_id").val();
		$("#startTime,#endTime").datepickerStyle();
		buyChart(serialId);
		askChart(serialId);
		chartPie("buy_chart_pie",2,serialId);
		chartPie("ask_chart_pie",1,serialId);
		$(".q_search_a").click(function(){
			$("#cpage").val(1);
			loadData();
		});
		
	}); 
	
	function showDialog(){	
		//防止重复弹框
		if(art.dialog.list[123]){
			art.dialog.list[123].close();
		}
		var brandId = $("#brand_id").val();
		var serialId = $("#serial_id").val();
		var url ="${ctx}/question/select?type=1&serialId="+serialId+"&brandId="+brandId;
		art.dialog.open(url, {
			id:123,
		    width: 440,
		    height: 210,
	        title:'选择车系',
		    ok: function(){
		    	var $form=$(this.iframe.contentWindow.document);
		    	var $serial = $form.find("#serials");
		    	var $brand = $form.find("#brands");
		    	if(!$serial.val()){
		    		art.dialog.alert("请选择车系");
		    		return false;
		    	}
		    	var brand_name = $brand.find("option:selected").text();
		    	var serial_name = $serial.find("option:selected").text();
		    	$("#defulet_").html(brand_name+"&nbsp;"+serial_name);
		    	$("#serial_id").val($serial.val());
		    	$("#brand_id").val($brand.val());
		    	buyChart($serial.val());
				askChart($serial.val());
				chartPie("buy_chart_pie",2,$serial.val());
				chartPie("ask_chart_pie",1,$serial.val());
				$(".s_car").html($("#defulet_").text());
		    },
		    cancel: true
	    });
	}
	function loadData() {
		var serialId=$("#serial_id").val();
		var param= getSearchUrl();
		var url = getWebPath() + "/question/queryList?serialId="+serialId;
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
								if(value.type=='1'){
									value.type="用车";
								}
								if(value.type=='2'){
									value.type="买车";
								}
								html +="<td >"+value.type+"</td>";
// 								html +="<td >"+value.questionName+"</td>";
								html +="<td >"+value.ptime+"</td>";
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
					html += '<tr><td colspan="5" class="td_border">暂无数据</td></tr>';
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
				<div class="q_title_node" >当前车系</div>
				<div class="q_title_car_type" onclick="showDialog()" >
					<span  id="defulet_">${brand.name}&nbsp;${serial.name}</span>
					<input type="hidden" id= "serial_id" value="${serial.id }"/>
					<input type="hidden" id= "brand_id" value="${brand.id }"/>
					<img src="${ctx}/media/images/q_select.png"  />
					<div class="clear" ></div>
				</div>
		        <div class="clear" ></div>
				<div class="q_line" ></div>
			</div>
			<div class="q_content">
				<div class="q_content_title">
					<span>买车关注 &gt;</span>
				</div>
				<div class="q_content_border">
					<div class=" ">
						<div id="buy_chart_pie" style="height: 250px;float: left;"></div>
						<div id="buy_chart" style="height:250px">
						</div>
					</div>
					<div class="clear" ></div>
				</div>
			</div>
			
			<div class="q_content">
				<div class="q_content_title">
					<span>用车询问 &gt;</span>
				</div>
				<div class="q_content_border">
					<div class=" ">
						<div id="ask_chart_pie" style="height: 250px;float: left;"></div>
						<div id="ask_chart" style="height:250px">
						</div>
					</div>
					<div class="clear" ></div>
				</div>
			</div>
			
			<div class="q_content">
				<div class="q_content_title">
					<span>问题筛选 &gt;</span>
					<div class="q_content_more">
						<img src="${ctx}/media/images/q_more_no.png"  />
						<div>收起</div>
					</div>
				</div>
				<div class="q_content_border bottom_border">
					 
					 <div class="q_content_search">
					 	 <div class="search_node">问题：</div>
					 	 <div class="search_content question">
					 	 	<a id="0"  class="current" href="javascript:void(0);" >全部</a>
					 	 	<c:forEach items="${questions }" var="dto" varStatus="sn">
					 	 		<a id="${dto.id}">${dto.name}</a>
							</c:forEach>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
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
					 	<div class="search_node">已选条件：</div>
					 	<div class="search_content">
					 		<div class="s">
					 			车系：<span class="s_node s_car" onclick='showDialog()'>${brand.name}&nbsp;${serial.name}</span>
					 		</div>
					 		<div class="s">
					 			问题：<span class="s_node s_question" >全部</span>
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
							<th>序号</th>
							<th>标题</th>
							<th>属性</th>
<!-- 							<th>问题</th> -->
<!-- 							<th>地区</th> -->
							<th>时间</th>
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