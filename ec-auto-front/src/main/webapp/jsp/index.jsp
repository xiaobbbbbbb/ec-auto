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


<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>

<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_business.js"></script>
<script type="text/javascript" src="${ctx}/media/js/index.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.hotcloudtag-0.0.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$(".nav1 span:last,.nav2 span:last,.nav3 span:last").css("border-bottom","1px solid #2980c4");
		$(".nav1 span:first,.nav2 span:first,.nav3 span:first").css("border-top","1px solid #2980c4");
		$(".frame-menu").hover(function(){
			$(this).find("span").css("display","block");
		},function(){
			$(this).find("span").hide();
		});
		
		
		cpfx("");
		downside("");
		statistics();
		statisticsRanking();
		var url = '${ctx}/industry/hotClouds?maxRows=10';
		$.getJSON(url, function(data) {
			var tag_list = data;
    		$(".hot-cloud-tag").hotCloudTag({
                radius:120,
                speed:3.5,
    			fontsize:24,
    			checkbar:false
            }, tag_list);
		});	
    });
</script>
</head>
<body>
	<center>
	
		<jsp:include page="common/head.jsp" />
		
		<div class="all">
			<div class="kbfx">
				<div class="title">
					<div class="title_tag">产品分析</div>
					<c:forEach items="${brands}" var="dto" varStatus="sn">
					   <c:if test="${sn.index<=4}">
						<a class="title_car_model" href="javascript:cpfx('${dto.id}');"
							<c:if test="${sn.index==0}"> style="color: #ffffff; margin-left: 20px; background-image: url('../media/images/index_a_bg.png');"</c:if>>
							<c:if test="${sn.index==0}">我的</c:if>
							${dto.name} </a>
							</c:if>
					</c:forEach>
				 
					
					<a class="title_car_model frame-menu nav1">其它 ∨
					    <c:forEach items="${brands}" var="dto" varStatus="sn">
					    <c:if test="${sn.index>4}">
					        <span class="info" onclick="cpfx('${dto.id}');">${dto.name}</span>
					    </c:if>
					    </c:forEach>
					</a>

					<a class="title_more" href="${ctx}/discuss/index">查看更多</a>
					<div class="clear"></div>
				</div>

				

				<div class="kbfx_qst">
				   <table width="100%" style="height: 390px;">
				       <tr>
				           <td>
				           		<a class="title_car_model_title"  >
					       			最近7天评论趋势
					       		</a>
				           </td>
				       </tr>
				       <tr>
				           <td><div id="kbfx_qst"></div></td>
				       </tr>
				   </table>

				</div>
				<div class="kbfx_qttj">
					<table cellpadding="0" cellspacing="0" width="100%" style="height: 390px;">
					    <tr>
					       <td height="40px" valign="top" align="center">
					       
						       <div class="pie_content">
							       	<a class="title_model frame-menu nav3" style="color: #ffffff; background-image: url('../media/images/index_a_bg.png');">
								       	<c:forEach items="${serials}" var="dto" varStatus="sn">
								        	<c:if test="${sn.index==0}"><b id="select_serial">${dto.name}</b><input id="select_serial_id" type="hidden" value="${dto.id}"/></c:if>
								   		</c:forEach>
									    <c:forEach items="${serials}" var="dto" varStatus="sn">
									        <c:if test="${sn.index>0}"><span class="info" id="select_serial_${dto.id}" onclick="judgePie(0,${dto.id},0);">${dto.name}</span></c:if>
									        
									    </c:forEach>
									</a>
								</div>
							</td>
					    </tr>
					    <tr>
					       <td height="30px"  valign="top" align="center">
						       <div id="point_view">
						       		<table cellpadding="0" cellspacing="0" width="100%"  >
						       			<tr>
						       				<td id="point_good" class="select_point selected">好评<input type="hidden" value="3" /></td>
						       				<td id="point_middle" class="select_point">中评<input type="hidden" value="2" /></td>
						       				<td id="point_bad" class="select_point">差评<input type="hidden" value="1" /></td>
						       			</tr>
						       		</table>
						       </div>
						    </td>
						 </tr>
						 <tr>
					        <td>
						        <div id="kbfx_qttj"></div>
						    </td>
						 </tr>
					    </tr>
					</table>
				</div>
				<div class="clear"></div>
			</div>
			<div class="fmqb">
				<div class="title">
					<div class="title_tag">负面情报</div>
					<c:forEach items="${brands}" var="dto" varStatus="sn">
					   <c:if test="${sn.index<=4}">
						<a class="title_car_model" href="javascript:aegativeArticles('${dto.id}');"
							<c:if test="${sn.index==0}"> style="color: #ffffff; margin-left: 20px; background-image: url('../media/images/index_a_bg.png');"</c:if>>
							<c:if test="${sn.index==0}">我的</c:if>
							${dto.name} </a>
							</c:if>
					</c:forEach>
					<a class="title_car_model frame-menu nav2">其它 ∨
					    <c:forEach items="${brands}" var="dto" varStatus="sn">
					    <c:if test="${sn.index>4}">
					        <span class="info" onclick="javascript:aegativeArticles('${dto.id}');">${dto.name}</span>
					    </c:if>
					    </c:forEach>
					</a>
					<a class="title_more" href="${ctx}/downside/index">查看更多</a>
					<div class="clear"></div>
				</div>
				<div class="fmqb_msg" id="aegativeArticles">
					<c:forEach items="${aegativeArticles}" var="dto" varStatus="sn">
						<div class="news_line">
							<a class="news_title" href="${ctx}/discuss/detail/${dto.id}?type=0">${dto.title}</a> <span class="news_date"><fmt:formatDate value="${dto.pubDate}" pattern="yyyy-MM-dd" /></span>
							<div class="clear"></div>
						</div>
					</c:forEach>
					<c:if test="${empty aegativeArticles}">
						<div class="no_data" >暂无数据</div>
					</c:if>
				</div>
				<div class="fmqb_img" id="fmqb"></div>
				<div class="clear"></div>
			</div>

			<div class="cbxl">
				<div class="title">
					<div class="title_tag">传播效力排行</div>
					<a class="title_more" href="${ctx}/media/index">查看更多</a>			
					<div class="clear"></div>
				</div>

				<div class="cbxl_left" id="cbxl_left"></div>
				<div class="cbxl_right" id="cbxl_right"></div>
				<div class="clear"></div>
			</div>


			<div class="bottom">
				<div class="hydt">
					<div class="title">
						<div class="title_tag">动态新闻</div>						
						<a class="title_more" href="${ctx}/industry/index">查看更多</a>
						<div class="clear"></div>
					</div>
					<div class="hydt_msg">
						<c:forEach items="${industryArticles}" var="dto" varStatus="sn">
							<div class="news_line">
								<a class="news_title" href="${ctx}/industry/listArticles/${dto.id}">${dto.title}</a> <span class="news_date"><fmt:formatDate value="${dto.pubTime}" pattern="yyyy-MM-dd" /></span>
								<div class="clear"></div>
							</div>
						</c:forEach>
						<c:if test="${empty industryArticles}">
							<div class="no_data" >暂无数据</div>
						</c:if>
					</div>
				</div>
				
				<div class="hyrd">
					<div class="title">
						<div class="title_tag">热点追踪</div>
						<a class="title_more" href="${ctx}/industry/cloud">查看更多</a>
						<div class="clear"></div>
					</div>
					<div class="hot-cloud-tag"></div>
				</div>				
				<div class="clear"></div>
			</div>
		</div>
		<jsp:include page="common/footer.jsp" />
	</center>
</body>
</html>
