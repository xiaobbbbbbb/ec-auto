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
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_dateformat.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_event.js"></script>
<script type="text/javascript">
	$(function() {
		$(".nav3 span:last").css("border-bottom","1px solid #2980c4");
		$(".nav3 span:first").css("border-top","1px solid #2980c4");
		$(".frame-menu").hover(function(){
			$(this).find("span").css("display","block");
		},function(){
			$(this).find("span").hide();
		});
		
		
		judge();
		
		$(".q_content_more_date").click(
				 
				function() {
					$("#search_time").toggle();
					$("#sdate,#edate").datepickerStyle();
				});
		
		$("#search").bind("click",
				function() {
					var sdate=$("#sdate").val();
					var edate= $("#edate").val();
					var eid = $("#eid").val();
					if(validateDate(sdate,edate)){
						return;
					}
					judge();
					var url1 = getWebPath() + "/event/prDate?eid="+eid+"&sdate="+sdate+"&edate="+edate;
					$.getJSON(url1,
						function(voData) {
								
								html='<b id="select_prDate" >公关时间</b><input id="prDate" type="hidden" value="0"/>';
								if(voData){
									$.each(voData,
										function(index, value) {	
												html+='<span class="info" id="prDate_'+value.id+' ">'+dateTostr(value.prDate)+'</span>';
										});
									
								}
								$(".frame-menu").empty().append(html);
								$(".nav3 span:last").css("border-bottom","1px solid #2980c4");
								$(".nav3 span").click(function(){
										$(".nav3 .info").hide();
										var eid = $("#eid").val();
										var cur = $(this).text();
										cur = (cur != null && cur.length > 0) ? cur : $("#select_prDate").text();
										$("#select_prDate").text(cur);
										$(".s_car").text(cur);
										url =  getWebPath() + "/event/getPrData?eid="+eid+"&prDate="+cur;
										var json = $.ecAjax.getReturnJson({
											url : url
										});
										$("#data1").text(json[0]);
										$("#data2").text(json[1]);
										$("#data3").text(json[2]);
										$("#data4").text(json[3]);
										
									});
						});
					});
			
				
	}); 
</script>
</head>
<body>
	<center> 
		<jsp:include page="../common/head.jsp" />
		<div class="all">
			<div class="q_title no_q_title margint30">
				<div class="q_title_node_a" >
					<a class="event_detail_a" href="${ctx}/event/index">返回事件分类</a>
					<a class="event_detail_a" href="${ctx}/event/propagationAnalysis?eid=${eid}&propagationNodeType=${propagationNodeType}">传播分析</a>
					<a class="event_detail_a" href="${ctx}/event/detail2?eid=${eid}">网友情感</a>
					<a class="event_detail_a" href="${ctx}/event/detail3?eid=${eid}">相关文章</a>
					<a class="event_detail_a event_detail_current" href="${ctx}/event/detail4?eid=${eid}">事件公关</a>
				</div>
				<div class="q_title_input short" >
					<a style="width: 120px;margin-left: 20px" href="javascript:void(0);">生成专报</a>
				</div>
		        <div class="clear" ></div>
		        <div class="q_line"></div>
		        
			</div>
			 
			
			<div class="q_content">
				<div class="q_content_title">
					<span>公关效果 &gt;</span>
					<div class="q_content_more_date">
						<input type="hidden" name= "eid"  id="eid" value="${eid}"/>
						<input placeholder="最近十五天" class="time" type="text" readonly="readonly" name="startTime" id="startTime" />
						
					</div>
				</div>
				<div id="search_time" style="display: none;border-left: 2px solid #b3b3b3;border-right: 2px solid #b3b3b3;margin-bottom: 0;">
								开始时间：<input type="text" readonly="readonly" name="sdate"
									id="sdate" />&nbsp;&nbsp;&nbsp;结束时间：<input type="text"
									readonly="readonly" name="edate" id="edate" />
									<span id = "search" style = "color:#ffffff;mrgin-left:10px;background-color: #3780b5;cursor: pointer;">筛选</span>
				</div>
				<div class="q_content_border">
						<div class="q_content_absolute absolute" style ="margin-top: 100px;" id="my_affection">
							
							<a class="title_model frame-menu nav3" style="color: #ffffff; background-image: url('../media/images/index_a_bg.png');margin-left: -50px;">
						        	<b id="select_prDate" >公关时间</b><input id="prDate" type="hidden" value="0"/>
<!-- 						        	<span class="info" id="select_serial_0" onclick="">公关时间</span> -->
							    <c:forEach items="${listPr}" var="dto" varStatus="sn">
							        <span class="info" id="prDate_${dto.id}" onclick="changeData(${dto.id})"><fmt:formatDate value="${dto.prDate}" pattern="yyyy-MM-dd"/> </span>
							    </c:forEach>
							</a>
						</div>
					<div class="q_content_gray_bg">
						公关建议：建议所属公关部门和相关网络媒体沟通协商，撤销或增加正面评价及文章进行澄清，以减轻对营销部门的压力。
					</div>
					<div class="q_content_long_img margint30">
						<div id="chart" style=" height:250px;width:850px;">
						</div>
					</div>
					
					<div class="q_content_grid_bg margint10">
						<div class="grid">
							新增：<span class="font36" id="data1"></span>条
						</div>
						<div class="grid" >
							正面数：<span class="font36" id="data2"></span>
						</div>
						<div class="grid"  >
							负面数：<span class="font36" id="data3"></span>
						</div>
						目前负面占：<span class="red font36" id="data4"></span>
					</div>
					
					
					<div class="q_table width900 margint30">
						<div class="event_title_node">仍需要关注的负面信息：</div>
						<table class="margint10" cellpadding="0" cellspacing="0">
							<tr>
								<th width="50px">序号</th>
								<th width="420px;">标题</th>
								<th>转发数</th>
								<th>评论数</th>
								<th>来源</th>
								<th>时间</th>
							</tr>
							<tbody id="concernNegativeDt">
								<tr><td colspan="6">暂无数据</td></tr>
							</tbody>
						</table>
						 
						<!-- 分页控件 -->
						<div class="demo">
							<div id="_articles"></div>
						</div>
						<input type="hidden" id="pageNum" value="1" />
						<!-- 分页控件尾巴-->
					</div> 
			 
					
				</div>
				
				
			</div>
			
		<jsp:include page="../common/footer.jsp" />
	</div>
	</center>
</body>
</html>