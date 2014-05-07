<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
<meta http-equiv="expires" content="0"/>
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index-new.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/js/paginate/css/style.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/css/question.css" media="screen" rel="stylesheet" type="text/css" />
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/event.css" media="screen" rel="stylesheet" type="text/css" />
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
		 //检索功能
		 $("#a_search_eventBrief").click(function(){
		  /* 	var type=$("#event_type").val();
			var url=getUrl(type);
			loadData(url); */
		 });
		 typeSearch(1);  //加载我的事件 类型
	}); 
 
	//开关
	function switchStatus(st,eid){
		var urlStr = getWebPath()+"/event/switchStatus";
		var param = {"st":st,"eid":eid};
		if(st==1){  //由开-关，需要提示
			artDialog({	
		            content:'是否确认停止对该事件监控？',
		            lock:true,
		            style:'succeed noClose'
		        },
		        function(){
		        	requestFun(urlStr, param);
		        },
		        function(){ }
			);
		}else{
			requestFun(urlStr, param);
		}
	}	
	
	function requestFun(urlStr, param){
		$.post(urlStr, param, function(json){
			if(json.success){
				art.dialog.alert("状态修改成功。");
				 var type=$("#event_type").val();
				 typeSearch(type);  
			 }else{
				 art.dialog.alert(json.msg);
			 }
		});
	}
	
	//仍需要关注的负面信息
	function loadData(url) {
		$.getJSON(
			url,
			function(voData) {
				var html = "";
				var event_type=$("#event_type").val();
				if (voData.list.length > 0) {	
					if(event_type>1){  //表示非我的事件
						html+=getHtmlStrHead();
					}
					$.each(voData.list,
							function(index, value) {
								if(event_type<=1){   //表示非我的事件
									html+=htmlStr(value);
								}else{
									html+=getElseHtmlStr(index,value);
								}
								
							});
					if(event_type>1){  //表示非我的事件
						html+=getHtmlStrBottom();
					}
					$("#date_div").empty().append(html);
					$("#_articles").paginate({
						totalPage : voData.totalPage,
						currentPage : voData.currentPage,
						display		: 22,
						onLoad : function(page) {
							$('#pageNo').val(page);
							var url=getUrl(event_type);
							loadData(url);
						}
					});
				}else{
					if(event_type>1){  //表示非我的事件
						html+=getHtmlStrHead();
						html += '<td colspan="4" class="td_border">暂无数据</td></tr>';
						html+=getHtmlStrBottom();
					}else{
						html += '暂无数据';
					}
					
					$("#date_div").empty().append(html);
					$("#_articles").empty();
				}
			});
	}
	//我的事件
	function htmlStr(value){
		var html = "";
		html +='<div class="q_event_content">';
		html +='	<div class="event_left">';
		html +='		<a href="${ctx}/event/propagationAnalysis?eid='+value.eid+'&propagationNodeType='+value.type+'" >';
		html +='			<img class="event_img" src="${ctx}'+value.picPath+' "/> ';
		html +='		</a>';
		html +='	</div>';
		html +='	<div class="event_right">';
		html +='		<a href="${ctx}/event/propagationAnalysis?eid='+value.eid+'&propagationNodeType='+value.type+'" >';
		html +='		<span  class="event_title" >'+value.name+'</span>'+(value.eventDescription?value.eventDescription:'')+'';
		html +='		</a>';
		html +='		<div style="float: right;">';
		var classStr='event_switch on';
		if(value.status==1){
			classStr='event_switch on';
		}else{
			classStr='event_switch off';
		}
		html +='			<a class="'+classStr+'" st="'+value.status+'" eid="'+value.eid+'" href="javascript:switchStatus('+value.status+','+value.eid+');" ></a>';
		html +='			<a  href="${ctx}/event/edit?eid='+value.eid+'"  title="修改" class="update_a"  style="margin-right: 10px;margin-top:4px;float: right;"></a>';
		html +='		</div>';
		html +='	</div>';
		html +='	<div class="clear" ></div>';
		html +='</div>';
		return html;
	}
	
	function getHtmlStrHead(){
		var htmlStrHead="";
		htmlStrHead+='<div class="q_table no_top">';
		htmlStrHead+='<table cellspacing="0" cellpadding="0" >';
		htmlStrHead+='	<thead>';
		htmlStrHead+='		<tr>';
		htmlStrHead+='			<th width="50px">序号</th>';
		htmlStrHead+='			<th>事件主题</th>';
		htmlStrHead+='			<th>报道时间</th>';
		htmlStrHead+='			<th style="text-align: center;">事件追踪</th>';
		htmlStrHead+='	   </tr>';
		htmlStrHead+='	</thead>';
		return htmlStrHead;
	}
	//其他事件
	function getElseHtmlStr(index,value){
		var tr="";
		tr +="<tr><td >"+(index+1)+"</td>";
		tr +="<td ><a href='${ctx}/event/propagationAnalysis?eid="+value.eid+"&propagationNodeType="+value.type+"'  >"+value.name+"</a></td>";
		tr +="<td >"+new Date(value.createTime).format('yyyy年MM月dd日')+"</td>";
		var classStr='event_switch_td on';
		if(value.status==1){
			classStr='event_switch_td on';
		}else{
			classStr='event_switch_td off';
		}
		tr +='<td style="align:center; text-align: center;width:160px;line-height:26px;">';
		tr +='	<a class="'+classStr+'" st="'+value.status+'" eid="'+value.eid+'" href="javascript:switchStatus('+value.status+','+value.eid+');" ></a>';
		tr +='			<a  href="${ctx}/event/edit?eid='+value.eid+'" title="修改" class="update_a_td"  ></a></td>';
		tr += "</tr>";
		return tr;
	}
	function getHtmlStrBottom(){
		var htmlStrBottom="";
		htmlStrBottom+='</table>';
		htmlStrBottom+='</div> ';
		return htmlStrBottom;
	}
	
	
	//根据类型查找
	function typeSearch(event_type){
		$("#pageNo").val(1);
		var url=getUrl(event_type);
		$(".title_imp_a_hover").removeClass("title_imp_a_hover");
		$(".title_imp_a :eq("+(event_type-1)+")").addClass("title_imp_a_hover");
		$("#event_type").val(event_type);
		loadData(url);
	}
	function getUrl(event_type){
		var searchStr = $("#input_search_eventBrief").val();  //搜索的内容
		if(searchStr=="请输入关键字进行检索"){
			searchStr="";
		}
		var pageNo=$("#pageNo").val();  //当前页码
		var rowsPerPage=5;
		if(event_type!=1){
			rowsPerPage=25;
		}  
		return getWebPath() + "/event/queryIndexEventData?event_type="+event_type+"&searchStr="+searchStr+"&pageNo="+pageNo+"&rowsPerPage="+rowsPerPage;
 
	}
	
	function eventSearch(){
		var searchStr = $("#input_search_eventBrief").val();  //搜索的内容
		if(searchStr=="请输入关键字进行检索"){
			searchStr="";
		}
		if(searchStr.length<=0){
			var type=$("#event_type").val();
			var url=getUrl(type);
			loadData(url);
			return;
		}
	    var urlStr = getWebPath()+"/event/eventSearch";
	    window.location.href=urlStr+"?searchStr="+searchStr+"&pageNo=1"; 
	}
	
</script>
</head>
	<body>
		<center> 
			<jsp:include page="../common/head.jsp" />
			<div class="all">
				<div class="q_title no_q_title margint30">
					<div class="q_title_node" ></div>
					<div class="q_title_input" >
						<a style="width: 120px;margin-left: 20px" href="${ctx}/event/add">添加事件</a>
						<a href="javascript:eventSearch();" id="a_search_eventBrief" >搜索</a>
						<input  placeholder="请输入关键字进行检索" id="input_search_eventBrief" value="${searchStr}"/>
					</div>
			        <div class="clear" ></div>
				</div>
				<div class="q_content no_q_content">
					<div class="q_content_title">
						<input name="event_type" id="event_type" type="hidden" value="1" />
						<span>事件列表 &gt;</span>
						<a class="title_imp_a margin20 title_imp_a_hover" href="javascript:typeSearch(1);" et="1">我的事件</a>
						<a class="title_imp_a" href="javascript:typeSearch(2);" et="2" >行业政策</a>
						<a class="title_imp_a" href="javascript:typeSearch(3);"  et="3" >其他品牌</a>
						<a class="title_imp_a" href="javascript:typeSearch(4);" et="4" >其他事件</a>
			 
						<div class="clear" ></div>
					</div>
					<div id="date_div">
					</div>
					<!-- 分页控件 -->
					<div class="demo">
						<div id="_articles"></div>
					</div>
					<input type="hidden" id="pageNo" value="1" />
					<!-- 分页控件尾巴 -->
				</div>	
				<jsp:include page="../common/footer.jsp" />
			</div>
		</center>
	</body>
</html>