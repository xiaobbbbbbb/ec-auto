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
<script type="text/javascript" src="${ctx}/media/js/ec/ec_business.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_discuss.js"></script>

<script type="text/javascript">
	$(function() {
		discussJudge(0,null);
		productDiscuss(); //对比
		$("#startTime,#endTime").datepickerStyle();

		$("#search_btn").bind("click", function() {
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			if (startTime.length > 0 && endTime.length > 0) {
				if(validateDate(startTime,endTime)){
					return ;
				}
			}
			var param = getSearchUrl();
			discussJudge(0, param);
			getDiscuss(3);
		});

	});
</script>

</head>
<body>
	<center>
		<jsp:include page="common/head.jsp" />

		<div class="all">
			<div class="detail_left">
				<div class="detail_left_index">
					<a href="javascript:void();" style="color: #ffffff; background-image: url('../media/images/detail_left_as.png');">产品口碑</a> 
					<a href="${ctx}/discuss/list">车友口碑明细</a>
				</div>
			</div>
			<div class="detail_right">
				
				<table cellpadding="0" cellspacing="0" id="search_table" >
					<tr>
						<td class="td_border" colspan="2" ><div class="search_tag">筛选</div></td>
					</tr>
					<tr>
						<td  class="td_no_border_right"  valign="top" style="width: 100px;"><div class="search_tag">时间：</div></td>
						<td  class="td_border_left"  id="a_time"><span>今天<input type="hidden" value="0" /></span> <span>昨天<input type="hidden" value="-1" /></span> <span class="search_a">最近7天<input
								type="hidden" value="-6" /></span> <span>最近15天<input type="hidden" value="-14" /></span> <span>最近30天<input type="hidden" value="-29" /></span> <span
							class="custom">自定义时间<input type="hidden" value="1" /></span>
							<div class="clear"></div>
							<div id="search_time" style="display: none;">
								开始时间：<input type="text" readonly="readonly" name="startTime" id="startTime" />&nbsp;&nbsp;&nbsp;结束时间：<input type="text" readonly="readonly"
									name="endTime" id="endTime" />
							</div> <input type="hidden" id="time" value="-6" /></td>
					</tr>
					<tr>
						<td  class="td_no_border_right"  valign="top" ><div class="search_tag">品牌：</div></td>
						<td  class="td_border_left"  id="a_brand"><c:forEach items="${brands }" var="dto" varStatus="sn">
								<c:choose>
									<c:when test="${sn.index==0}">
										<span title="${dto.name}"  class="search_a">${dto.name}<input type="hidden" value="${dto.id}" /></span>
									</c:when>
									<c:otherwise>
										<span title="${dto.name}"  >${dto.name}<input type="hidden" value="${dto.id}" /></span>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<div class="clear"></div></td>
					</tr>
					<tr id="seacrh_tr">
						<td class="td_no_border_right" ><div class="search_tag"></div></td>
						<td class="td_border_left"  id="a_serials"></td>
					</tr>
					<tr>
						<td class="td_no_border_right" valign="top"  ><div class="search_tag">选中产品：</div></td>
						<td class="td_border_left" >
							<table width="100%" id="clean_border1">
								<tr>
									<td width="595px" style="padding-bottom: 5px;" id="select_ser"><c:forEach items="${serials }" var="dto" varStatus="sn">
											<div class="search_brand_item serial_${dto.id}">
														<table class="select_serial">
															<tr>
																<td><div class="brandName" style="padding-top: 5px; padding-bottom: 5px;">${dto.name}<input type="hidden" name="product" value="${dto.id}" /></div></td>															<td><div class="select_d" onclick="removeSerial('serial_${dto.id}');">X</div></td>
															</tr>
														</table>
													</div>
										</c:forEach></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_border" colspan="2" ><div id="search_btn">立即筛选</div></td>
					</tr>
				</table>
				
				<span class="search_tag">产品对比分析：</span>
				<div class="clear"></div>
				<div class="detail_right_content">
					
					<div class="detail_right_content_no_boder_img" id="kbfx_qst" style="height: 350px;"></div>
					<div class="detail_right_content_table">
					<div id="kbfx_qst_table" >
					</div>
					</div>
					<a class="detail_right_content_a select_a" id="good" href="javascript:void(0);">好</a>
					<a class="detail_right_content_a" id="middle" href="javascript:void(0);">中</a>
					<a class="detail_right_content_a" id="bad" href="javascript:void(0);">差</a>
					<div class="clear"></div>
					<div id="productDiscuss_outsite">
						<div id="productDiscuss">
							<c:forEach items="${vos }" var="dt" varStatus="sn">
							    <div class="detail_right_content_div">
									<span class="search_tags">${dt.serialName }</span>
									<div class="clear"></div>
									<table cellspacing="0" cellpadding="0">
										<tr>
											<th>观点</th>
											<th>评价数</th>
											<th>占比</th>
										</tr>
										<c:forEach items="${dt.viewpoint }" var="dto" varStatus="sn">
											<tr>
												<td>${dto.name}</td>
												<td class='td_border td_border_a'><a id="discuss_list_info" href="javascript:discussList('${dto.id }','${dt.carSerialId }');">${dto.articleNums}</a></td>
												<td>${dto.rate}</td>
											</tr>
										</c:forEach>
										<c:if test="${empty dt.viewpoint}">
											<tr>
												<td colspan="3" >暂无数据</td>
											</tr> 
										</c:if>
									</table>
								</div>	
							</c:forEach>
							<div class="clear"></div>
						</div>
					</div>
					<div class="clear"></div>
					
						<a id="productDiscuss_more" href="javascript:productDiscussMore();">查看更多</a>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			
		</div>
		<jsp:include page="common/footer.jsp" />
	</center>
</body>
</html>
