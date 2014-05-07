<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/media/js/paginate/css/style.css" media="screen"/>
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>

<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>

<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>

<script type="text/javascript" src="${ctx}/media/js/ec/ec_business.js"></script>

<script type="text/javascript">
	$(function() {
		$("#startTime,#endTime").datepickerStyle();
		loadData();
		$("#search_btn").bind("click", function() {
			$('#offset').val("1");
			loadData();
		});
	});
	/**
	*	收集参数
	*/
	function getURIStr() {	
		var param = getSearchUrl();
		var day = $('#time').val();
		if(day == 0) {
			day = 1;
		}
		var affection = $('#affectionId').val();  //观点属性
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		var offset = $('#offset').val();
		if(!offset || offset <= 0) {
			offset = 1;
		}
		if(!startTime && day) {
			startTime = day;
		}
		var url = '${ctx}/discuss/query?startTime='+startTime+'&endTime='+endTime+'&affection='+affection+'&offset='+offset;
		var area = $("#area").val();
		if (area > 0)
			url += "&areaId=" + area;
		var provinceId = $("#provinceId").val();
		var array = new Array();
		$("input[name='product']").each(function() {
			array.push($(this).val());
		});
		if (array.length > 0) {
			url += "&serialIds=" + $.toArray(array);
		}
		if (provinceId > 0) {
			url += "&provinceId=" + provinceId;
		}
		return url;
	}
	
	function loadData() {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime.length > 0 && endTime.length > 0) {
			if(validateDate(startTime,endTime)){
			return ;
			}
		}
		var url = getURIStr();
		$.getJSON(url, function(data) {
			var tbody = $('#dataTable');
			tbody.empty();
			var tb = '';
			for (var i = 0; i < data.list.length; i++) {
				var obj = data.list[i];
				var title=obj.title;
				if(title.length>15){
					title=title.substr(0,15)+"……";
				}
				var viewPoints=obj.viewPoints;
				if(viewPoints.length>25){
					viewPoints=viewPoints.substr(0,25)+"……";
				}
				var mediaName=obj.mediaName;
				tb += '<tr><td class="td_border"><a href="${ctx}/discuss/detail/'+obj.id+'?type=1">' + title + '</a></td>';
				tb += '<td class="td_border">' + obj.affection + '</td>';
				tb += '<td class="td_border">'+ viewPoints + '</td>';
				tb += '<td class="td_border">' + (mediaName?mediaName:'') + '</td>';
				tb += '<td class="td_border">' + obj.ptime + '</td></tr>';
			}
			
			if(data.list.length==0){
				tb = '<tr><td colspan="5" class="td_border">暂无数据</td></tr>';
				tbody.append(tb);
				$("#_articles").empty();
				return ;
			}
			
			tbody.append(tb);
			$("#_articles").paginate({
				totalPage 	: data.totalPage,
				currentPage : data.currentPage,
				display     : 22,
				onLoad      : function(page){
								$('#offset').val(page);
								loadData();
							  }
			});
		});
	}
	
</script>

</head>
<body>
	<center>
		<jsp:include page="common/head.jsp" />

		<div class="all">
			<div class="detail_left">
				<div class="detail_left_index">
					<a href="${ctx}/discuss/index">产品口碑</a> 
					<a href="javascript:void();" style="color: #ffffff; background-image: url('../media/images/detail_left_as.png');">车友口碑明细</a>
				</div>
			</div>
			<div class="detail_right">	
				<div class="detail_right_search">			
					<table id="search_table">
						<tr>
							<td class="td_border" colspan="2" ><div class="search_tag">筛选</div></td>
						</tr>
						<tr>
							<td class="td_no_border_right" valign="top"  style="width: 100px;"><div class="search_tag">时间：</div></td>
							<td class="td_border_left" id="a_time">
								<span>今天<input type="hidden" value="0" /></span> 
								<span>昨天<input type="hidden" value="-1" /></span> 
								<span class="search_a">最近7天<input 	type="hidden" value="7" /></span>
								<span>最近15天<input type="hidden" value="15" /></span> 
								<span>最近30天<input type="hidden" value="30" /></span> 
								<span class="custom">自定义时间<input type="hidden" value="1" /></span>
								<div class="clear"></div>
								<div id="search_time" style="display: none;">
									开始时间：<input type="text" readonly="readonly" name="startTime" id="startTime" />&nbsp;&nbsp;&nbsp;结束时间：<input type="text" readonly="readonly"
										name="endTime" id="endTime" />
								</div> 
								<input type="hidden" id="time" value="7" />
								<input type="hidden" id="offset" value="1" />
								<input type="hidden" id="provinceId" />
								<input type="hidden" id="area" />
							</td>
						</tr>
						<tr>
							<td class="td_no_border_right" valign="top"  ><div class="search_tag">品牌：</div></td>
							<td class="td_border_left" id="a_brand">
								<c:forEach items="${brands }" var="dto" varStatus="sn">
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
							<td class="td_border_left" id="a_serials"></td>
						</tr>
						<tr>
							<td class="td_no_border_right"  valign="top" ><div class="search_tag">选中产品：</div></td>
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
										<%-- <td valign="top"><c:if test="${serials.size()>6}">
												<div id="search_brand_more">+更多</div>
											</c:if></td> --%>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="td_no_border_right" ><div class="search_tag">观点属性：</div></td>
							<td class="td_border_left"  id="a_affection">
								<input id="affectionId" type="hidden" value="0" />
								<span class="search_a">全部<input type="hidden" value="0" /></span>
								<span >好评<input type="hidden" value="3" /></span>
								<span >中评<input type="hidden" value="2" /></span>
								<span >差评<input type="hidden" value="1" /></span>
								<div class="clear"></div></td>
						</tr>
						<tr>
							<td class="td_no_border_right" valign="top"><div class="search_tag">区域：</div></td>
							<td class="td_border_left" >
								<table width="100%" id="clean_border">
									<tr>
										<td id="a_sear"><span class="search_a">大区</span> <span class="custom">省份地区</span>
											<div class="clear"></div>
											<div id="search_province" style="display: none;">
												<c:forEach items="${provinces }" var="dto" varStatus="sn">
													<span>${dto.name}<input type="hidden" value="${dto.id}" /></span>
												</c:forEach>
											</div>
											<div class="clear"></div> <input type="hidden" id="provinceId" value="0" /></td>
									</tr>
									<tr>
										<td id="a_qu"><span class="search_a">全国<input type="hidden" value="0" /></span> <c:forEach items="${areas }" var="dto" varStatus="sn">
												<span>${dto.name}<input type="hidden" value="${dto.id}" /></span>
											</c:forEach>
											<div class="clear"></div> <input type="hidden" id="area" value="0" /></td>
									</tr>
								</table>
	
							</td>
						</tr>
						<tr>
							<td class="td_border" colspan="2" ><div id="search_btn">立即筛选</div></td>
						</tr>
					</table>
				</div>
				
				<span class="search_tag">车友评论列表 </span>
				<div class="clear"></div>
				<div class="detail_right_content_table">
					<table cellpadding="0" cellspacing="0" >
						<tr>
							<th class="th_border" >评价内容</th>
							<th class="th_border" >属性</th>
							<th class="th_border" >观点	</th>
							<th class="th_border" >来源媒体</th>
							<th class="th_border" >时间</th>
						</tr>
						<tbody id="dataTable">
						</tbody>
					</table>
				</div>
				<div class="clear"></div>
				<div>
					<div class="demo">
		                <div id="_articles"></div>
		            </div>
				</div>
			</div>
			<div class="clear"></div>
						
		</div>
		<jsp:include page="common/footer.jsp" />

	</center>
</body>
</html>
