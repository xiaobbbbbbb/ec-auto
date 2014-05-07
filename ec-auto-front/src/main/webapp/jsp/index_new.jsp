<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/jquery.hotcloudtag-0.0.1.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index_.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/event.css" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>

<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_business.js"></script>
<script type="text/javascript" src="${ctx}/media/js/index.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.hotcloudtag-0.0.1.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {

				$(".nav1 span:last,.nav2 span:last,.nav3 span:last").css(
						"border-bottom", "1px solid #2980c4");
				$(".nav1 span:first,.nav2 span:first,.nav3 span:first").css(
						"border-top", "1px solid #2980c4");
				$(".frame-menu").hover(function() {
					$(this).find("span").css("display", "block");
				}, function() {
					$(this).find("span").hide();
				});

				$("#table_news tr th:last,#table_news tr td:last").css(
						"border-right", "none");
				if ('${importantList.size()}' == 5) {
					$("#table_news tr:last td").css("border-bottom", "none");
				}

				$("#table_incidents tr th:last,#table_incidents tr td:last")
						.css("border-right", "none");
				if ('${eventList.size()}' == 8) {
					//默认数据先注释
					//$("#table_incidents tr:last td").css("border-bottom","none");
				}

				$("#table_negatives tr th:last,#table_negatives tr td:last")
						.css("border-right", "none");
				if ('${positiveList.size()}' == 5) {
					$("#table_negatives tr:last td").css("border-bottom",
							"none");
				}

				pie(2, document.getElementById("buyCar"));//买车

				mouth();
			});

	function mouthHtml(obj, serName) {
		var json = $.ecAjax.getReturnJson({
			url : getWebPath() + "/index_new/mouthKey?id=" + obj
		});
		var keywords = json.obj;

		var html = "";
		var good = 0;
		var bad = 0;
		$(keywords)
				.each(
						function(index, value) {
							var name = value.keyword;
							//alert(name+"''"+value.affection);
							if (good < 3) {
								if (value.affection == 1) {
									good++;
									if (good == 1)
										html += '<div class="red_div_l">'
												+ name + '</div>';
									if (good == 2)
										html += '<div class="red_div_r"><div class="red_div">'
												+ name + '</div>';
									if (good == 3)
										html += '<div class="red_div top8">'
												+ name + '</div></div>';
								}
							}
							if (bad < 3) {
								if (value.affection == 3) {
									bad++;
									if (bad == 1) {
										html += '<div class="blue_div_l">'
												+ name + '</div></div>';
									}
									if (bad == 2) {
										html += '<div class="blue_div_c">'
												+ name + '</div>';
									}
									if (bad == 3) {
										html += '<div class="blue_div_r">'
												+ name + '</div>';
									}
								}
							}

							if (good == 3 && bad == 3) {
								return;
							}
						});

		$("#keywords").html(html);
		$("#car_div_c").text(serName);
	}

	function mouth() {

		var s = '${mouthNames}';
		var obj = eval(s);
		var dadas = new Array();
		$(obj).each(function(index) {
			var val = obj[index];
			if (index == 0) {
				mouthHtml(val.id, val.serialName);
			}
			var _obj = new Array();
			_obj.name = val.serialName;
			_obj.id = parseInt(val.id);
			dadas.push(_obj);
		});

		var len = dadas.length;
		$("#car_up").click(function() {
			var serIndex = parseInt($("#serIndex").val());
			if (serIndex == 0)
				serIndex = len - 1;
			else
				serIndex -= 1;
			mouthHtml(dadas[serIndex].id, dadas[serIndex].name);
			$("#serIndex").val(serIndex);
		});

		$("#car_next").click(function() {
			var serIndex = parseInt($("#serIndex").val()) + 1;
			if (len == serIndex)
				serIndex = 0;
			mouthHtml(dadas[serIndex].id, dadas[serIndex].name);
			$("#serIndex").val(serIndex);
		});

	}

	function pie(type, obj) {
		$(".vcenter").addClass("div_circle_gray_bg");
		$(obj).removeClass("div_circle_gray_bg");
		
		var json = $.ecAjax.getReturnJson({
			url : getWebPath() + "/index_new/question?type=" + type
		});
		var dadas = new Array();
		$.each(json, function(index, value) {
			var obj = new Array();
			obj.push(value.name);
			obj.push(parseInt(value.value));
			dadas.push(obj);
		});
		
		var totle=0;
		$.each(dadas,function(index,value){
			totle+=value[1];
		});
		var title = null;
		if (type == 1) {
			title = '用车询问数';
		}
		if (type == 2) {
			title = '买车询问数';
		}
		var options = ({
			chart : {
				renderTo : 'pie', // 在哪个区域呈现，对应HTML中的一个元素ID
				backgroundColor : '', // 绘图区的背景颜色
				plotBorderWidth : null, // 绘图区边框宽度
				plotShadow : true
			// 绘图区是否显示阴影
			},
			title : {
				text : '累计'+title+totle+'条'
			},
			colors : [ '#057dbc', '#e53b3c', '#15c30c', '#494949', '#ff6600',
					'#64099e' ],
			tooltip : {
				pointFormat : '<b>{point.percentage:.1f}%</b>'
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
		                    enabled: true,
					}
// 					showInLegend : true
				}

			},
			series : [ {
				type : 'pie',
				name : '',
				data : dadas
			} ]
		});
		if (options.series.length == 1) {
			options.series[0].size = 130;
		}
		new Highcharts.Chart(options);
	}
</script>
</head>
<body>
	<center>
		<jsp:include page="common/head.jsp" />
		<div class="all">
			<div class="index_piece">
				<div class="index_piece_left">
					<div class="index_piece_top">
						<div class="title_name">重点新闻</div>						
						<a class="title_get_more" target="_blank" href="${ctx}/important/index">更多>></a>
						<div class="clear"></div>
					</div>
					<div class="index_piece_center">
						<div class="piece_center_l" >
							 <div class="div_circle_bg">
							 	与我<br />相关
							 </div>
						</div>
						<div class="piece_center_c">
							今日：<span class="span_a orage"><a target="_blank" href="${ctx}/important/about?type=1&date=1">${importantDayCount }</a></span> &nbsp;条
						</div>
						<div class="piece_center_r">
							本周：<span class="span_a blue"><a target="_blank" href="${ctx}/important/about?type=1&date=2">${importantWeekCount }</a></span> &nbsp;条
						</div>
						<div class="clear" ></div>
					</div>
					<div class="index_bottom_div">
						<table id="table_news" cellspacing="0" cellpadding="0" >
							<thead>
						 		<tr>
									<th>新闻标题</th>
									<th>热度</th>
									<th width="100px">时间</th>
								</tr>
						 	</thead>
							<tbody id="dataTable">
								<c:forEach items="${importantList }" var="important">
									<tr>
										<td style="text-align: left;padding-left: 10px" title="${important.title}" >
											<a href='${important.url }'  target="_blank" >${important.stitle}</a>
										</td>
										<td >
											<c:if test="${important.startCount==1 }">
												<img src= '${ctx }/media/images/star.png'  />
											</c:if>
											<c:if test="${important.startCount==2 }">
												<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />
											</c:if>
											<c:if test="${important.startCount==3 }">
												<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />
											</c:if>
											<c:if test="${important.startCount==4 }">
												<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />
											</c:if>
											<c:if test="${important.startCount==5 }">
												<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />&nbsp;<img src= '${ctx }/media/images/star.png'  />
											</c:if>
										 </td>
										<td >${important.ptime }</td>
									</tr>
								</c:forEach>
								<c:if test="${empty importantList}">
									<tr>
										<td colspan="3">暂无数据</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>

				<input type="hidden" id="serIndex" value="0" />
				<div class="index_piece_right">
					<div class="index_piece_top">
						<div class="title_name">用户口碑</div>
						<a class="title_get_more" target="_blank" href="${ctx}/mouth/index">更多>></a>
						<div class="clear"></div>
					</div>
					<div>
						<div class="piece_center_l">
							<div class="div_circle_bg">
								本周<br />观点
							</div>
						</div>
						<div class="piece_center_lr">
							<div class="car_div_l ban_l" id="car_up"></div>
							<div class="car_div_c" id="car_div_c">${serial.name}</div>
							<div class="car_div_r" id="car_next"></div>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
						<div id = "keywords">
						
						</div>
						<div class="clear"></div>
					</div>
				</div>				
				<div class="clear"></div>
			</div>
			
			<div class="index_piece" >
				<div class="index_piece_left">
					<div class="index_piece_top">
						<div class="title_name">需求研究</div>						
						<a class="title_get_more" target="_blank" href="${ctx}/question/index">更多>></a>
						<div class="clear"></div>
					</div>
					<div>
						 <div class="piece_center_l" >
							 <div id="buyCar" onclick="pie(2,this)" class="div_circle_bg vcenter">
							 	买车
							 </div>
							 <div id="useCar" onclick="pie(1,this)" class="div_circle_bg div_circle_gray_bg vcenter">
							 	用车
							 </div>
						</div>
						<div class="piece_center_lr">
							<div id="pie" style="height:250px">
							
							</div>
						 
						</div>
						<div class="clear"></div>
					</div>
					
				</div>
				
				<div class="index_piece_right">
					<div class="index_piece_top">
						<div class="title_name">事件追踪</div>
						<a class="title_get_more" target="_blank" href="${ctx}/event/index">更多>></a>
						<div class="clear"></div>
					</div>
					<div class="index_bottom_divss" >
						<table id="table_incidents" cellspacing="0" cellpadding="0" >
							<thead>
						 		<tr>
									<th>事件标题</th>
									<th width="130px">报道量</th>
									<th width="100px">状态</th>
								</tr>
						 	</thead>
							<tbody id="dataTable">
								<c:forEach items="${eventList }" var="event">
									<tr>
										<td title="${event.name}" >
											<a target="_blank" href="${ctx}/event/propagationAnalysis?eid=${event.eid}&propagationNodeType=${event.type}"  >${event.name}</a>
										</td>
										<td >
											 <fmt:formatDate value="${event.createTime }" pattern="yyyy年MM月dd日" />
										 </td>
										<td>
											<c:if test="${event.status==1}">
												监控中
											</c:if>
											<c:if test="${event.status!=1}">
												监控完成
											</c:if>
										</td>
									</tr>
								</c:forEach>
								<c:if test="${empty eventList}">
									<tr>
										<td colspan="3">暂无数据</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>				
				<div class="clear"></div>
			</div>
			
			<div class="index_piece">
				<div class="index_piece_left">
					<div class="index_piece_top">
						<div class="title_name">负面信息</div>						
						<a class="title_get_more" target="_blank" href="${ctx}/positive/index">更多>></a>
						<div class="clear"></div>
					</div>
					<div class="index_piece_center">
						<div class="piece_center_l" >
							 <div class="div_circle_bg">
							 	与我<br />相关
							 </div>
						</div>
						<div class="piece_center_c">
						 	严重：<span class="span_a orage" ><a target="_blank" href="${ctx}/positive/second?grade=2">${highNums }</a></span> &nbsp;条
						</div>
						<div class="piece_center_r">
							一般：<span class="span_a blue" ><a target="_blank" href="${ctx}/positive/second?grade=1">${lowNums }</a></span> &nbsp;条
						</div>
						<div class="clear" ></div>
					</div>
					<div class="index_bottom_div">
						<table id="table_negatives" cellspacing="0" cellpadding="0" >
							<thead>
						 		<tr>
									<th>标题</th>
									<th>等级</th>
									<th  width="100px">时间</th>
								</tr>
						 	</thead>
							<tbody id="dataTable">
								<c:forEach items="${positiveList }" var="positive">
									<tr>
										<td style="text-align: left;padding-left: 10px" title="${positive.title}" >
											<a href='${positive.url }'  target="_blank" >${positive.stitle}</a>
										</td>
										<td >
											<c:if test="${positive.grade==1 }">
												一般
											</c:if>
											<c:if test="${positive.grade==2 }">
												<font color='red'>严重</font>
											</c:if>
										 </td>
										<td >${positive.atime }</td>
									</tr>
								</c:forEach>
								<c:if test="${empty positiveList}">
									<tr>
										<td colspan="3">暂无数据</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="index_piece_right">
					<div class="index_piece_top">
						<div class="title_name">广宣分析</div>
						<a class="title_get_more" target="_blank">更多>></a>
						<div class="clear"></div>
					</div>
					<div>
						<div class="advert_title">
							起亚K5上市相关活动
						</div>
					 	投放媒体：<span class="span_a orage" ><a  >113</a></span> &nbsp;家
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						累计文章：<span class="span_a blue" ><a  >352</a></span> &nbsp;条
			
					</div>
					<div class="index_bottom_div">
						
						<div  >
							<img src="${ctx}/media/images/static_ad.png" height="176px"/>
						</div>
						
					</div>
				</div>				
				<div class="clear"></div>
			</div>
			
		</div>
		<jsp:include page="common/footer.jsp" />
	</center>
</body>
	

</html>
