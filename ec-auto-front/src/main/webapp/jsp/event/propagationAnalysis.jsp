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
		//统计图方法
		judge_event_pa(0);
		
		//预警历史
		loadAlarmInfoList();
		
		//新闻渠道
		typeSearch(1);
	});
	
	//统计图方法
	function judge_event_pa(eventInfoType) {
		var url = getWebPath() + "/event/infoCountChart?eId="+$("#chart_event_pa").attr("eid")+"&eventInfoType=" + eventInfoType;
		var json = $.ecAjax.getReturnJson({
			url : url
		});
		var datas = new Array();
			var obj = new Object();
			var obj2 = new Array();
			$.each(json, function(index, value) {
				var array = new Array();
				array.push(getUTC(value.name));
				array.push(value.value);
				obj2.push(array);
			});
			obj.data = obj2;
			datas.push(obj);
		
		var options = ({
			// 常规图表选项设置
			chart : {
				renderTo : 'chart_event_pa', // 在哪个区域呈现，对应HTML中的一个元素ID
//				backgroundColor : '#f0f0f0', // 绘图区的背景颜色
				plotBorderWidth : null, // 绘图区边框宽度
				plotShadow : true
			// 绘图区是否显示阴影
			},
			tooltip : {
				formatter : function() {
					return this.y;
				}
			},
			legend : {
				enabled:false   //不显示线条name
			},
			title : {
				text : ''
			},
			xAxis : {
				type : 'datetime',
				maxPadding : 0.1,
				minPadding : 0.1,
				tickInterval : 24 * 3600 * 1000 * 1,// 两天画一个x刻度
				// tickPixelInterval : 50,
				tickWidth : 3,// 刻度的宽度
				labels : {
					formatter : function() {
						return Highcharts.dateFormat('%m/%d', this.value);
					}

				}
			},
			yAxis : {
				min: 0, 
				title : {
					text : ''
				},
				labels : {
//					enabled : false,
					overflow : 'justify'
				},
				plotLines : [ {
					value : 0,
					width : 1,
					color : '#808080'
				} ]
			},
			series :datas
				
		});
		for ( var i = 0; i < options.series.length; i++) {
			if (i > 2) {
				options.series[i].visible = false;
			}
		}
		new Highcharts.Chart(options);
	}
	
	//预警历史
	function loadAlarmInfoList(){
		var alarmInfoPage=$("#alarmInfoPage").val();
		var urlAlarmInfo = getWebPath() + "/event/queryAlarmInfoList?eid="+$("#chart_event_pa").attr("eid")+"&pageNum="+alarmInfoPage;
		$.getJSON(
			urlAlarmInfo,
			function(voData) {
					var html = "";
				if (voData) {	
					$.each(voData.list,
							function(index, value) {
								html +="<tr><td >"+new Date(value[0]).format('yyyy年MM月dd日')+"</td>";
								
								if(value[1]=='3'){
									html +="<td ><font color='red'>严重</font></td>";
								}else{
									html +="<td >一般</td>";
								}
								if(value[2].length>35){
									var substr=value[2].substring(0,35)+"...";
									html +="<td title='"+value[2]+"'><a target='_blank' href='"+value[4]+"' >"+substr+"</a></td>";						
								}else{
									html +="<td ><a target='_blank' href='"+value[4]+"' >"+value[2]+"</a></td>";							
								}
								html += "</tr>";
							});
					$("#alarmInfoDt").empty().append(html);
					$("#_articles_alarminfo").paginate({
						totalPage : voData.totalPage,
						currentPage : voData.currentPage,
						display		: 22,
						onLoad : function(page) {
							$('#alarmInfoPage').val(page);
							loadAlarmInfoList();
						}
					});
				}else{
					html += '<tr><td colspan="3" class="td_border">暂无数据</td></tr>';
					$("#alarmInfoDt").empty().append(html);
					$("#_articles_alarminfo").empty();
				}
			});
	}
	
	
	//新闻渠道根据类型查找
	function typeSearch(propagationNodeType){
		if(propagationNodeType ==1 || propagationNodeType!=$("#propagationNodeType").val()){
			getTHeadInfo(propagationNodeType);
			$("#propagationNodeDt").empty();
			$("#popagationNodeNum").val(1);
			var url=getUrl(propagationNodeType);
			$(".title_imp_a_hover").removeClass("title_imp_a_hover");
			$(".title_imp_a :eq("+(propagationNodeType-1)+")").addClass("title_imp_a_hover");
			if(propagationNodeType==1){
				$("#title_type").html("新闻渠道 &gt;");
			}else if(propagationNodeType==2){
				$("#title_type").html("论坛渠道 &gt;");
			}else if(propagationNodeType==3){
				$("#title_type").html("微博渠道 &gt;");
			}
			$("#propagationNodeType").val(propagationNodeType);
			loadPropagationNodeList(url,propagationNodeType);
		}
	} 
	function getUrl(propagationNodeType){
		var popagationNodeNum=$("#popagationNodeNum").val();
		return  getWebPath() + "/event/queryPropagationNodeList?eid="+$("#chart_event_pa").attr("eid")+"&propagationNodeType="+propagationNodeType+"&pageNum="+popagationNodeNum;
	}
	
	function loadPropagationNodeList(url,propagationNodeType) {
		$.getJSON(
				url,
				function(voData) {
					$("#dataTable").empty();
						var html = "";
					if (voData.list.length > 0) {	
						$.each(voData.list,
								function(index, value) {
									html +=getContentInfo(propagationNodeType,index,value);
								});
						$("#propagationNodeDt").empty().append(html);
						$("#_articles").paginate({
							totalPage : voData.totalPage,
							currentPage : voData.currentPage,
							display		: 22,
							onLoad : function(page) {
								$('#popagationNodeNum').val(page);
								var type=$("#propagationNodeType").val();
								var url=getUrl(type);
								loadPropagationNodeList(url);
							}
						});
					}else{
						if(propagationNodeType==3){
							html += '<tr><td colspan="6" class="td_border">暂无数据</td></tr>';
						}else{
							html += '<tr><td colspan="5" class="td_border">暂无数据</td></tr>';
						}
						$("#propagationNodeDt").empty().append(html);
						$("#_articles").empty();
					}

				});
	}
	
	function getTHeadInfo(type){
		var html="";
		html += '<tr>';
		html += '<th width="50px">序号</th>';
		if(type==3){
			html += '<th id="head_name">博主名称</th>';
			html += '<th>博文数</th>';
			html += '<th>评论数</th>';
			html += '<th id="weibo_counts" >转发数</th>';
			html += '<th>粉丝数</th>';
		}else{
			html += '<th id="head_name">网站名称</th>';
			html += '<th>报道量</th>';
			html += '<th>评论数</th>';
			html += '<th>影响力排名</th>';
		}
		html += '</tr> ';
	    $("#thead_info").empty().append(html);
	}
	
	function getContentInfo(type,index,value){
		var html="";
		html +="<tr><td >"+(index+1)+"</td>";
		if(type==3){
			html +="<td ><a target='_blank' href='"+value[6]+"' >"+value[0]+"</a></td>";
		}else{
			html +="<td >"+value[0]+"</td>";
		}
		html +="<td >"+value[1]+"</td>";
		html +="<td >"+value[2]+"</td>";
		if(type==3){
			html +="<td >"+value[3]+"</td>";
		}
		if(type==3){
			html +="<td >"+value[4]+"</td>";
		}else{
			if(value[5]=="none"){
				html +="<td >"+value[4]+"</td>";
			}else{
				html +="<td ><a target='_blank' href='"+value[5]+"' >"+value[4]+"</a></td>";
			}
		}
		html += "</tr>";
		return html;
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
					<a class="event_detail_a event_detail_current" href="${ctx}/event/propagationAnalysis?eid=${eid}&propagationNodeType=${propagationNodeType}">传播分析</a>
					<a class="event_detail_a" href="${ctx}/event/detail2?eid=${eid}">网友情感</a>
					<a class="event_detail_a" href="${ctx}/event/detail3?eid=${eid}&eventInfoType=${eventInfoType}">相关文章</a>
					<a class="event_detail_a" href="${ctx}/event/detail4?eid=${eid}">事件公关</a>
				</div>
				<div class="q_title_input short" >
					<a style="width: 120px;margin-left: 20px" href="javascript:void(0);">生成专报</a>
				</div>
		        <div class="clear" ></div>
		        <div class="q_line"></div>
		        
			</div>
			
			<div class="q_event_title">
			 	<div class="event_title_l">
			 		<a>
			 			<!-- 需要替换为实际图片地址 eventBrief.picPath -->
			 			<img class="event_img" src='${ctx}${eventBrief.picPath}' /> 
			 		</a>
				</div>
				<div class="event_title_r">
			 		<span  class="event_title" >${eventBrief.name}</span> ${eventBrief.eventDescription}
				</div>
				<div class="clear" ></div>
			</div>
			
			
			<div class="q_content no_q_content">
				<div class="q_content_title">
					<span>预警历史 &gt;</span>
					<div class="clear" ></div>
				</div>
				<div class="q_table no_top">
					<table cellspacing="0" cellpadding="0" >
						<thead>
					 		<tr>
								<th  width="200px">时间</th>
								<th  width="200px">级别</th>
								<th  width="500px">标题</th>
							</tr>
					 	</thead>
						<tbody id="alarmInfoDt">
							<tr><td colspan="3" class="td_border">暂无数据</td></tr>
						</tbody>
					</table>
					<!-- 分页控件 -->
					<div class="demo">
						<div id="_articles_alarminfo"></div>
					</div>
					<input type="hidden" id="alarmInfoPage" value="1" />
					<!-- 分页控件尾巴 -->
				</div>
			</div>
			
			<div class="q_content no_q_content">
				<div class="q_content_title">
					<span>传播起源 &gt;</span>
					<div class="clear" ></div>
				</div>
				<div class="q_table no_top">
					<table cellspacing="0" cellpadding="0" >
						<thead>
					 		<tr>
								<th  width="200px">事件起源媒体/网站</th>
								<th  width="150px">报道时间</th>
								<th  width="450px">文章</th>
								<th  width="100px">发布人</th>
							</tr>
					 	</thead>
					 	<c:if test="${not empty propagationSource}">
							<tr>
								<td >${propagationSource[0] }</td>
								<td ><fmt:formatDate value="${propagationSource[1] }" pattern="yyyy年MM月dd日" />  </td>
								<c:choose>  
									<c:when test="${ fn:length(propagationSource[2])>35}">
									<td title="${propagationSource[2] }">
										<a href="${propagationSource[4] }"  target="_blank">${ fn:substring(propagationSource[2], 0, 35)}...</a>
									</td>
									</c:when>
									  
								   <c:otherwise>  
									   <td >
											<a href="${propagationSource[4] }"  target="_blank">${ propagationSource[2]}</a>
										</td>
								   </c:otherwise> 
								</c:choose>
								<td >${propagationSource[3] }</td>
							</tr>
						</c:if>
						<c:if test="${empty propagationSource}">
		              		<tr>
			                   <td colspan="4">暂无数据</td>
			                </tr>
		              	</c:if>
					</table>
				</div>
			</div>
			
			<div class="q_content no_q_content">
			
				<div class="q_content_title">
					<span id="title_type">新闻渠道 &gt;</span>
					<input name="propagationNodeType" id="propagationNodeType" type="hidden" value="1" />
					<a class="title_imp_a margin20 title_imp_a_hover" href="javascript:typeSearch(1);" >新闻传播节点</a>
					<a class="title_imp_a" href="javascript:typeSearch(2);" >论坛传播节点</a>
					<a class="title_imp_a" href="javascript:typeSearch(3);"  >微博传播节点</a>
					<a class="title_imp_a" href="javascript:typeSearch(4);" style="display: none" >平面媒体节点</a>
					<div class="clear" ></div>
				</div>
				<div class="q_table no_top">
					<table cellspacing="0" cellpadding="0" >
						<thead id="thead_info">
					 		<!-- <tr>
					 			<th width="50px">序号</th>
								<th id="head_name">网站名称</th>
								<th>报道量</th>
								<th>评论数</th>
								<th id="weibo_counts" >转发数</th>
								<th>影响力排名</th>
							</tr> -->
					 	</thead>
						<tbody id="propagationNodeDt">
						
						</tbody>
					</table>
					<!-- 分页控件 -->
					<div class="demo">
						<div id="_articles"></div>
					</div>
					<input type="hidden" id="popagationNodeNum" value="1" />
					<!-- 分页控件尾巴 -->
				</div>
			</div>
			
			<div class="q_content">
				<div class="q_content_title">
					<span>报道量数据图 &gt;</span>
				</div>
				<div class="q_content_border">
					<div class="q_content_no_border">
						<div class="q_content_absolute absolute" id="my_affection">
							 <a class="absolute_a current_a" id="allPopagation"  onclick="judge_event_pa(0)">全部</a>
							 <a class="absolute_a m" id="popagation_1" onclick="judge_event_pa(1)">新闻</a>
							 <a class="absolute_a" id="popagation_2" onclick="judge_event_pa(2)">论坛</a>
							 <a class="absolute_a" id="popagation_3" onclick="judge_event_pa(3)">微博</a>
							<!--  <a class="absolute_a" id="popagation_4" onclick="judge_event_pa(4)">平煤</a> -->
						</div>
						<div class="q_content_long_img" style="height: 300px;">
							<div id="chart_event_pa" style="height:260px;width: 930px;" eid="${eid }"></div>
						</div>
						<div class="clear" ></div>
					</div>
					
				</div>
				
			</div>
			
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>