<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>企业战略情报决策分析系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/common/base.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />
<link
	href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/media/js/paginate/css/style.css" media="screen" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_negative.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/paginate/jquery.paginate.js"></script>

<script type="text/javascript">
	$(function() {
		$("#defaultTime_").click();
		$("#startTime,#endTime").datepickerStyle();
		loadData();
		$("#search_btn").bind("click", function() {
			$("#page").val(1);
			loadData();
		});
	}); 


	function loadData() {
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		if (startTime.length > 0 && endTime.length > 0) {
			if(validateDate(startTime,endTime)){
			return ;
			}
		}
		var param = getSearchUrl();
		anchart(1, param);
		var url = getWebPath() + "/downside/query?1=1";
		if (param != null) {
			url += param;
		}
		$.getJSON(
			url,
			function(voData) {
				var data = voData.page;
				$("#tableList").empty();
				
					var html = "";
				if (voData.page.list.length > 0) {	
					html += "<table cellspacing='0' cellpadding='0' >"
							+ "<tr><th class='th_border' >时间</th>";
				
					$.each(voData.data.split(","), function(index,
							value) {
						if (index > 0) {
							html += "<th class='th_border' name='"+value.split("_")[0]+"' >" + value.split("_")[1]
									+ "</th>";
						}
					});
					html + "</tr>";
				
					$.each(voData.page.list,
							function(index, value) {
								html += "<tr>";
								$.each(value.data.split(","),
										function(indexs, va) {
											if(indexs!=0){
												html += "<td class='td_border td_border_a' onclick='tdClick(this)' >" + va
														+ "</td>";
											}else{
												html += "<td class='td_border'>" + va
												+ "</td>";
											}
										});
								html += "</tr>";
							});
					html += "</table>";
					$("#tableList").empty().append(html);
					$("#_articles").paginate({
						totalPage : data.totalPage,
						currentPage : data.currentPage,
						display:22,
						onLoad : function(page) {
							$('#cpage').val(page);
							loadData();
						}
					});
				}
				else{
					html += '<table><tr><td colspan="1" class="td_border">暂无数据</td></tr></table>';
					$("#tableList").empty().append(html);
					$("#_articles").empty();
				}

			});
	}
	
	function tdClick(note){
		var grade=$("#grade_hidden").val();
		var cell =note.cellIndex;
		var rowValue = $(note).parent().find("td");
		var queryDate=$(rowValue[0]).html();
		var brandId=$(".th_border:eq("+cell+")").attr("name");
		window.location.href="${ctx}/downside/list?brandId="+brandId+"&queryDate="+queryDate+"&grade="+grade;
	}
</script>
</head>
<body>
	<center>
		<jsp:include page="common/head.jsp" />

		<div class="all">
			<div class="detail_left">
				<div class="detail_left_index">
					<a href="javascript:void();"
						style="color: #ffffff; background-image: url('../media/images/detail_left_as.png');">负面情报分析</a>
					<a href="${ctx}/downside/list">负面情报列表</a>

				</div>
			</div>
			<div class="detail_right">
				<table id="search_table">
					<tr>
						<td class="td_border" colspan="2" ><div class="search_tag">筛选</div></td>
					</tr>
					<tr>
						<td  class="td_no_border_right" valign="top"  style="width: 100px;"><div class="search_tag">时间：</div></td>
						<td  class="td_border_left" id="a_time"><span class="search_a">今天<input
								type="hidden" value="0" /></span> <span>昨天<input type="hidden"
								value="-1" /></span> <span>最近7天<input type="hidden"
								id="defaultTime_" value="-6" /></span> <span>最近15天<input
								type="hidden" value="-14" /></span> <span>最近30天<input
								type="hidden" value="-29" /></span> <span class="custom">自定义时间<input
								type="hidden" value="1" /></span>
							<div class="clear"></div>
							<div id="search_time" style="display: none;">
								开始时间：<input type="text" readonly="readonly" name="startTime"
									id="startTime" />&nbsp;&nbsp;&nbsp;结束时间：<input type="text"
									readonly="readonly" name="endTime" id="endTime" />
							</div> <input type="hidden" id="time" value="0" /></td>
					</tr>
					<tr>
						<td  class="td_no_border_right" valign="top" ><div class="search_tag">品牌：</div></td>
						<td  class="td_border_left"  id="a_brand"><c:forEach items="${brands }" var="dto"
								varStatus="sn">
								<c:choose>
									<c:when test="${sn.index==0}">
										<span class="search_a" title='${dto.name}'>${dto.name}<input type="hidden"
											value="${dto.id}" /></span>
									</c:when>
									<c:otherwise>
										<span title="${dto.name}"  >${dto.name}<input type="hidden" value="${dto.id}" /></span>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<div class="clear"></div></td>
					</tr>
					<tr>
						<td  class="td_no_border_right"  valign="top"  ><div class="search_tag">选中品牌：</div></td>
						<td  class="td_border_left"  >
							<table width="100%" id="clean_border1">
								<tr>
									<td width="595px" style="padding-bottom: 5px;" id="select_ser">
										<div class="search_brand_item serial_${brand.id}">
											<table class="select_serial">
												<tr>
													<td><div class="brandNames" title="${brand.name}" style="padding-top: 5px; padding-bottom: 5px;">${brand.name}<input type="hidden" name="product"
																value="${brand.id}" />
														</div></td>
													<td><div class="select_d"
															onclick="removeBrand('serial_${brand.id}');">X</div></td>
												</tr>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td  class="td_no_border_right" valign="top"><div class="search_tag">区域：</div></td>
						<td  class="td_border_left"  >
							<table width="100%" id="clean_border">
								<tr>
									<td id="a_sear"><span class="search_a">大区</span> <span
										class="custom">省份地区</span>
										<div class="clear"></div>
										<div id="search_province" style="display: none;">
											<c:forEach items="${provinces }" var="dto" varStatus="sn">
												<span>${dto.name}<input type="hidden"
													value="${dto.id}" /></span>
											</c:forEach>
										</div>
										<div class="clear"></div> <input type="hidden" id="provinceId"
										value="0" /></td>
								</tr>
								<tr>
									<td id="a_qu"><span class="search_a">全国<input
											type="hidden" value="0" /></span> <c:forEach items="${areas }"
											var="dto" varStatus="sn">
											<span>${dto.name}<input type="hidden"
												value="${dto.id}" /></span>
										</c:forEach>
										<div class="clear"></div> <input type="hidden" id="area"
										value="0" /></td>
								</tr>
							</table>

						</td>
					</tr>
					<tr>
						<td  class="td_no_border_right" ><div class="search_tag">负面等级：</div></td>
						<td  class="td_border_left"  >
							<table width="100%" id="clean_border1">
								<tr>
									<td width="595px" style="padding-bottom: 5px;" id="select_ser">
										<input type="radio" id="grade" name="grade" value="0"
										checked="checked" />全部 &nbsp;&nbsp; <input type="radio"
										name="grade" value="1" />一般 &nbsp;&nbsp; <input type="radio"
										name="grade" value="2" />中等&nbsp;&nbsp; <input type="radio"
										name="grade" value="3" />严重&nbsp;&nbsp;
										<input type="hidden" id="grade_hidden" value='0'/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_border" colspan="2" ><div id="search_btn">立即筛选</div></td>
					</tr>
				</table>

				<div class="clear"></div>
				<div class="detail_right_content_boder">
					<div class="detail_right_content_no_boder_img" id="fmqb"
						style="height: 350px;"></div>
					<div class="detail_right_content_table">
					<div id="fmqb_table" >
					</div>
					</div>
				</div>
				<span class="search_tag"> 负面情报详细报道统计 </span>
				<div class="clear"></div>
				<div class="detail_right_content_table">
					<div id="tableList" >
					</div>

					<div class="demo">
						<div id="_articles"></div>
					</div>
					<input type="hidden" id="cpage" value=1 />
				</div>



			</div>
		</div>
		<div class="clear"></div>
		<jsp:include page="common/footer.jsp" />

	</center>
</body>
</html>