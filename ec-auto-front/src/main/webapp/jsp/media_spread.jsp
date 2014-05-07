<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />

<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>

<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>

<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_negative.js"></script>

<script type="text/javascript">
	$(function() {
		$("#startTime,#endTime").datepickerStyle();
		$("#defaultTime_").click();
		$("#search_btn").bind("click", function() {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			if (startTime.length > 0 && endTime.length > 0) {
				if(validateDate(startTime,endTime)){
				return ;
				}
			}
			var param = getSearchUrl();
			spreadTrend(0, param);
			statistics(param);
			statisticsRanking(param);

		});
		spreadTrend(0, null);
		statistics();
		statisticsRanking();
	});
</script>
</head>
<body>
	<center>
		<jsp:include page="common/head.jsp" />
		<div class="all">
			<div class="detail_left">
				<div class="detail_left_index">
					<a href="javascript:void();" style="color: #ffffff; background-image: url('../media/images/detail_left_as.png');">传播效力</a>
				</div>
			</div>
			<div class="detail_right">
				<table id="search_table">
					<tr>
						<td class="td_border" colspan="2" ><div class="search_tag">筛选</div></td>
					</tr>
					<tr>
						<td  class="td_no_border_right" valign="top" style="width: 100px;"><div class="search_tag">时间：</div></td>
						<td  class="td_border_left" id="a_time">
						<span class="search_a">今天<input
								type="hidden" value="0" /></span> <span>昨天<input type="hidden" value="-1" /></span> <span>最近7天<input
								type="hidden" value="-6" id="defaultTime_" /></span> <span>最近15天<input type="hidden" value="-14" /></span> <span>最近30天<input type="hidden" value="-29" /></span> <span
							class="custom" id="custom" >自定义时间<input type="hidden" value="1" /></span>
							<div class="clear"></div>
							<div id="search_time" style="display: none;">
								开始时间：<input type="text" readonly="readonly" name="startTime" id="startTime" />&nbsp;&nbsp;&nbsp;结束时间：<input type="text" readonly="readonly"
									name="endTime" id="endTime" />
							</div> <input type="hidden" id="time" value="0" /></td>
					</tr>
					<tr>
						<td  class="td_no_border_right" valign="top" ><div class="search_tag">品牌：</div></td>
						<td  class="td_border_left" id="a_brand"><c:forEach items="${brands }" var="dto" varStatus="sn">
								<c:choose>
									<c:when test="${sn.index==0}">
										<span title="${dto.name}" class="search_a">${dto.name}<input type="hidden" value="${dto.id}" /></span>
									</c:when>
									<c:otherwise>
										<span title="${dto.name}" >${dto.name}<input type="hidden" value="${dto.id}" /></span>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<div class="clear"></div></td>
					</tr>
					<tr>
						<td  class="td_no_border_right" valign="top"  ><div class="search_tag">选中品牌：</div></td>
						<td  class="td_border_left" >
							<table width="100%" id="clean_border1">
								<tr>
									<td width="595px" style="padding-bottom: 5px;" id="select_ser">
											<div class="search_brand_item serial_${brand.id}">
														<table class="select_serial">
															<tr>
																<td><div class="brandNames" title="${brand.name}" style="padding-top: 5px; padding-bottom: 5px;">${brand.name}<input type="hidden" name="product" value="${brands[0].id}" /></div></td>															<td>
																<div class="select_d" onclick="removeBrand('serial_${brand.id}');">X</div></td>
															</tr>
														</table>
													</div>
										</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="td_border" ><div id="search_btn">立即筛选</div></td>
					</tr>
				</table>
				
				<div class="detail_right_content_boder">
					<div class="detail_right_title">
						<span class="search_title">传播趋势</span>
					</div>
					<div class="detail_right_content_img" id="spread_trend"></div>
					<div class="detail_right_content_table">
					<div id="spread_trend_table" >
					</div>
					</div>
				</div>
				<div class="detail_right_content_boder">
					<div class="detail_right_title">
						<span class="search_title">传播占比</span>
					</div>
					<div class="detail_right_content_img" style="height: 300px;" id="cbxl_right"></div>
					<div class="detail_right_content_table">
					<div id="cbxl_right_table" >
					</div>
					</div>
				</div>
				<div class="detail_right_content_boder">
					<div class="detail_right_title">
						<span class="search_title">媒体排行</span>
						
					</div>
					
					<div class="detail_right_content_img" id="cbxl_left"></div>
					<div class="detail_right_content_table">
					<div id="cbxl_left_table" >
					</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
				
		</div>
		<jsp:include page="common/footer.jsp" />
	</center>
</body>
</html>
