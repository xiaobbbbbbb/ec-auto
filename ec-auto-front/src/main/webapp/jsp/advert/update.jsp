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
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_question.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_search.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>

<script type="text/javascript">
	$(function() {
		$("#planStime,#planEtime").datepickerStyle();
		$(".q_search_a").click(function(){
			dosubmit();
		});
	});
	
	function dosubmit(){
		var data = $.toJSON($('#form').serializeObject());
		art.dialog.alert(data);
		var url=getWebPath()+"/advert/update";
		$.ajax({
			url : url,
			type : "POST",
			contentType :'application/json;charset=utf-8',
			data : data,
			dataType :  'json',
			beforeSend:function(){
				return validate();
			},
			success : function(data) {
				if(data.success){
					$("#pid").val(data.obj);
					art.dialog.alert(data.msg);
					window.location.href="${ctx}/advert/index";
				}
			}
		});
	}
	function validate(){
		var stime = $("#planStime").val();
		var etime = $("#planEtime").val();
		var title = $("#title").val();
		var content = $("#content").val();
		var url = $("#url").val();
		var pageId= $("#pageId").val();
		var mediaId = $("#mediaId").val();
		if(!stime){
			art.dialog.alert("投放开始时间不能为空！");
			return false;
		}if(!etime){
			art.dialog.alert("投放结束时间不能为空！");
			return false;
		}if(!title){
			art.dialog.alert("广告标题不能为空!");
			return false;
		}if(!content){
			art.dialog.alert("广告内容不能为空!");
			return false;
		}if(!url){
			art.dialog.alert("投放地址不能为空！");
			return false;
		}if(!pageId){
			art.dialog.alert("投放版面不能为空！");
			return false;
		}if(!mediaId){
			art.dialog.alert("投放媒体不能为空");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<center>
		<jsp:include page="../common/head.jsp" />
		<div class="all">
				<div class="q_title">
				<div class="q_title_node" >广宣配置</div>
		        <div class="clear" ></div>
				<div class="q_line" ></div>
			</div>
			<div class="q_content no_q_content">
			<form id="form">
	          <div class="q_content_border bottom_border">
	          		 <div class="q_content_search">
					 	 <div class="search_node">活动说明：</div>
					 	 <div class="search_content type">
					 	 	${ad.content}
					 	 	<input type="hidden" name= "id" id="id" value="${detail.id}"/>
					 	 	<input type="hidden" name= "pid" id="pid" value="${ad.id}"/>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	 <div class="search_node">投放时间：</div>
					 	 <div class="search_content">
					 	 	<input type="text" readonly="readonly" name="planStime" id="planStime" value="<fmt:formatDate value="${detail.planStime}"
											pattern="yyyy-MM-dd" />"/>
					 	 	&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
					 	 	<input type="text" readonly="readonly" name="planEtime" id="planEtime"  value="<fmt:formatDate value="${detail.planEtime}"
											pattern="yyyy-MM-dd" />"/>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	 <div class="search_node">广告标题：</div>
					 	 <div class="search_content type">
					 	 	<input type="text" name="title" id="title" value="${detail.title }"></input>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	 <div class="search_node">内容：</div>
					 	 <div class="search_content type">
					 	 	<textarea name="content" rows="3" id="content" >${detail.content }</textarea>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	 <div class="search_node">广告媒体：</div>
					 	 <div class="search_content type">
					 	 	<select	name="mediaId" id="mediaId">
					 	 		<c:forEach items="${mediaInfo}" var="dto" varStatus="sn">
					 	 		<option value="${dto.id }" <c:if test="${detail.mediaId== dto.id}"> selected="selected" </c:if> >${dto.name }</option>
							</c:forEach>
					 	 	</select>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	 <div class="search_node">投放地址：</div>
					 	 <div class="search_content type">
					 	 	<input type="text" name="url" id="url" id="url" value="${ detail.url}"></input>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	 <div class="search_node">投放版面：</div>
					 	 <div class="search_content type">
					 	 	<select	name="pageId" id="pageId" >
					 	 		
					 	 		<option value="1" <c:if test="${detail.pageId==1}"> selected="selected" </c:if>>首页</option>
					 	 		<option value="2" <c:if test="${detail.pageId==2}"> selected="selected" </c:if>>首页新闻第二级页面焦点图</option>
					 	 	</select>
					 	 </div>
					 	 <div class="clear"></div>
					 </div>
					 <div class="q_content_search">
					 	<a class="q_search_a" href="javascript:void(0);" >修改</a>
					 </div>
				</div>
		    	
	  	</form>
			</div>

			<jsp:include page="../common/footer.jsp" />
		</div>
	</center>
</body>
</html>