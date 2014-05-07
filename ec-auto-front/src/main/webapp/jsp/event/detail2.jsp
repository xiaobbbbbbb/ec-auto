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
<script type="text/javascript">
	$(function() {
		judge_event_netizen_emotions();
		loadData();
	}); 
	
	//统计图方法
	function judge_event_netizen_emotions() {
		var url = getWebPath() + "/event/netizenEmotions?eid="+$("#chart_netizen_emotions").attr("eid");
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
				renderTo : 'chart_netizen_emotions', // 在哪个区域呈现，对应HTML中的一个元素ID
//				backgroundColor : '#f0f0f0', // 绘图区的背景颜色
				plotBorderWidth : null, // 绘图区边框宽度
				plotShadow : true
			// 绘图区是否显示阴影
			},
			plotOptions: {
				line:{
					dataLabels: {
	                    enabled: true
	                }
				}
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
	
	
	//网友情感  列表数据
	function loadData() {
		var eid=$("#eid").val();  //当前页码
		var positiveNum=$("#positiveNum").val();  //总条数
		var positivePage=$("#positivePage").val();  //当前页码
		var urlPositive = getWebPath() + "/event/queryPositiveOrNegativeList?type=1&eid="+eid+"&totalRows="+positiveNum+"&pageNum="+positivePage;
		$.getJSON(
			urlPositive,
			function(voData) {
					var html = "";
				if (voData.list.length > 0) {	
					$.each(voData.list,
							function(index, value) {
								if(value[0].length>15){
									var substr=value[0].substring(0,15)+"...";
									html +="<tr><td title='"+value[0]+"'><a target='_blank' href='"+value[2]+"' >"+substr+"</a></td>";						
								}else{
									html +="<tr><td ><a target='_blank' href='"+value[2]+"' >"+value[0]+"</a></td>";							
								}
								html +="<td >"+new Date(value[1]).format('yyyy年MM月dd日')+"</td>";
								html += "</tr>";
							});
					$("#positiveDt").empty().append(html);
					$("#_articles_positive").paginate({
						totalPage : voData.totalPage,
						currentPage : voData.currentPage,
						display		: 12,
						onLoad : function(page) {
							$('#positivePage').val(page);
							loadData();
						}
					});
				}else{
					html += '<tr><td colspan="2" class="td_border">暂无数据</td></tr>';
					$("#positiveDt").empty().append(html);
					$("#_articles_positive").empty();
				}
			});
		
		var negativeNum=$("#negativeNum").val();  //总条数
		var negativePage=$("#negativePage").val();  //当前页码
		var url = getWebPath() + "/event/queryPositiveOrNegativeList?type=3&eid="+eid+"&totalRows="+negativeNum+"&pageNum="+negativePage;
		$.getJSON(
			url,
			function(voData) {
					var html = "";
				if (voData.list.length > 0) {	
					$.each(voData.list,
							function(index, value) {
							if(value[0].length>15){
								var substr=value[0].substring(0,15)+"...";
								html +="<tr><td title='"+value[0]+"'><a target='_blank' href='"+value[2]+"' >"+substr+"</a></td>";						
							}else{
								html +="<tr><td ><a target='_blank' href='"+value[2]+"' >"+value[0]+"</a></td>";							
							}
								html +="<td >"+new Date(value[1]).format("yyyy年MM月dd日")+"</td>";
								html += "</tr>";
							});
					$("#negativeDt").empty().append(html);
					$("#_articles_negative").paginate({
						totalPage : voData.totalPage,
						currentPage : voData.currentPage,
						display		: 12,
						onLoad : function(page) {
							$('#negativePage').val(page);
							loadData();
						}
					});
				}else{
					html += '<tr><td colspan="2" class="td_border">暂无数据</td></tr>';
					$("#negativeDt").empty().append(html);
					$("#_articles_negative").empty();
				}
			});
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
					<a class="event_detail_a event_detail_current" href="${ctx}/event/detail2?eid=${eid}">网友情感</a>
					<a class="event_detail_a" href="${ctx}/event/detail3?eid=${eid}">相关文章</a>
					<a class="event_detail_a" href="${ctx}/event/detail4?eid=${eid}">事件公关</a>
				</div>
				<div class="q_title_input short" >
					<a style="width: 120px;margin-left: 20px" href="javascript:void(0);">生成专报</a>
				</div>
		        <div class="clear" ></div>
		        <div class="q_line"></div>
		        
			</div>
			
			<div class="q_content">
				<div class="q_content_title">
					<span>负面报道量 &gt;</span>
				</div>
				<div class="q_content_border">
					<div class="q_content_long_img" style="height: 300px;">
						<div id="chart_netizen_emotions" style="height:300px;width: 930px;" eid="${eid }"></div>
					</div>
				</div>
			</div>
			
			<div class="q_content">
				<input type="hidden" id="eid"  value="${eid}"/>
				<div class="q_content_title">
					<span>网友态度 &gt;</span>
				</div>
				<div class="q_content_equally">
					<div class="event_number" >
						<br/>
						正面评论数<br/>
						<span class="number" >${positiveNum }</span>
					</div>
					
					<div class="q_table width430">
						<input type="hidden" id="positiveNum"  value="${positiveNum}"/> 
						<table cellpadding="0" cellspacing="0">
							<tr>
								<th width="310px">评论内容</th>
								<th width="120px;">时间</th>
							</tr>
							<tbody id="positiveDt">
								<tr><td colspan="2" class="td_border">暂无数据</td></tr>
							</tbody>
						</table>
						<!-- 分页控件 -->
						<div class="demo">
							<div id="_articles_positive"></div>
						</div>
						<input type="hidden" id="positivePage" value="1" />
						<!-- 分页控件尾巴 -->
					</div>
					
				</div>
				
				<div class="q_content_equally margin5">
					<div class="event_number negative" >
						<br/>
						负面评论数<br/>
						<span class="number" >${negativeNum }</span>
					</div>
					
					<div class="q_table width430">
						<input type="hidden" id="negativeNum"  value="${negativeNum}"/> 
						<table cellpadding="0" cellspacing="0">
							<tr>
								<th width="310px">评论内容</th>
								<th width="120px;">时间</th>
							</tr>
							<tbody id="negativeDt">
								<tr><td colspan="2" class="td_border">暂无数据</td></tr>
							</tbody>
						</table>
						<!-- 分页控件 -->
						<div class="demo">
							<div id="_articles_negative"></div>
						</div>
						<input type="hidden" id="negativePage" value="1" />
						<!-- 分页控件尾巴 -->
					</div>
				</div>
				<div class="clear" ></div>
			</div>
			
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>