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
<link href="${ctx}/media/js/ec/uploadify3.1/uploadify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/highcharts.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_chart.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_question.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_search.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/jquery.ec-event.js"></script>
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/uploadify3.1/jquery.uploadify-3.1.js"></script>
<script type="text/javascript">
	$(function() {
		$("#startTime,#endTime").datepickerStyle();
		$("#a_submit").click(function(){
			event_add_submit(this);			
		});
		
		$("input[name=radio_type]").click(function(){
			$(this).attr("checked",true).parent().addClass("orage").siblings().removeClass("orage");
		});
		
		
		var eid = $("#a_submit").attr("eid");
		if (eid!=null && eid!="") {
			var eventType = $("input[name=eventBrief_eventType]").val();
			$("input[name=radio_type]").each(function(){
				if($(this).val() == eventType){
					$(this).attr("checked",true).parent().addClass("orage").siblings().removeClass("orage").children().attr("checked",false);
				};
			});
		}
		
	});
	//提交事件配置
	function event_add_submit(btn){
		var eid = $(btn).attr("eid");
		if (eid==null || eid=="") {
			eid = 0;
		}
		var urlStr = getWebPath()+"/event/addEventBrief";
		var event_name = $("#event_name").val().trim();
		var keywords = $("#keywords").val().trim();
		var startTime = $("#startTime").val().trim();
		var picPath = $("#img_url").val();
		/* var endTime = $("#endTime").val().trim(); */
		var event_type = $('input[name="radio_type"]:checked').val();
		var event_description = $("#event_description").val().trim();
		//前台数据验证
		if (event_name.length<=0) {
			art.dialog.alert("对不起，事件主题不能为空。");
			return;
		}
		if (event_name.length<4 || event_name.length>100) {
			art.dialog.alert("对不起，事件主题长度为4-100个字符。");
			return;
		}
		if (((keywords.split(";")).length)-1>5) {
			art.dialog.alert("对不起，关键词超过5组。");
			return;
		}
		 
	    if (keywords.length<=0) {
			art.dialog.alert("对不起，关键词不能为空。");
			return;
		}
		if (startTime.length<=0) {
			art.dialog.alert("对不起，开始时间不能为空。");
			return;
		}
		
		/* if (endTime.length<=0) {
			art.dialog.alert("对不起，结束时间不能为空。");
			return;
		} */
		if (event_description.length>0 && (event_description.length<4 || event_description.length>180)) {
			art.dialog.alert("对不起，事件概述长度为4-180个字符。");
			return;
		}
		var param={"eventName":event_name, "eventKeywords":keywords, "stime":startTime,"eventType":event_type, "eventDescription":event_description, "picPath":picPath, "eid": eid};
		$.post(urlStr,param,function(json){
			 if(json.success){
				 art.dialog.alert("配置成功。");
				window.location.href= getWebPath() + "/event/index?version="+new Date().getTime();
				//window.location.href="${ctx}/carbrand/list";
			 }else{
				 art.dialog.alert(json.msg);
			 }
		});
	}
	
	
	function delete_event(eid){
		artDialog(
	        {	
	            content:'是否删除该事件？',
	            lock:true,
	            style:'succeed noClose'
	        },
	        function(){
	        	var urlStr = getWebPath()+"/event/delEventBrief";
				$.post(urlStr,{"eid":eid},function(json){
					if(json.success){
						art.dialog.alert(json.msg);
						window.location.href= getWebPath() + "/event/index?version="+new Date().getTime();
					 }else{
						 art.dialog.alert(json.msg);
					 }
				});
	        },
	        function(){return; }
		);
	}
	
	
</script>
<script>

 $(function(){
        	$("#file_upload").uploadFile({
        		 auto          : true,
        		 fileTypeDesc  : '请选择上传图片(*.gif,*.png,*.jpg,*.jpeg)',
                 fileTypeExts  : '*.gif;*.png;*.jpg;*.jpeg;', 
			     uploader      : '${ctx}/event/upimg',
			     onDialogClose : dialogClose,
			     onDialogOpen  : dialogOpen,
			     onUploadSuccess : uploadSuccess,
			     sizeLimit :2097152,//2M限制
			     swf:'${ctx}/media/js/ec/uploadify3.1/uploadify.swf'
        	});
	    });
        
        function dialogClose(data)
        {
        	$("#fileSize").val(data.queueLength);
        }
        
        function dialogOpen(data)
        {
        	$("#file_upload").uploadify('cancel');
        }
        
        function uploadSuccess(data)
        {
        	$.loadClose();
        	art.dialog.close();
     	    var dataObj=eval(data);
 	    	if(dataObj.success){
 	    		$("#img_url").val(dataObj.obj);
 	    		$("#show_img").attr("src",getWebPath()+dataObj.obj);
 	    		$("#show_img").show();
 	    		art.dialog.alert("图片上传成功!");
 	    		
 	    	}
     	    else
     	    {
            	//window.location.href='${ctx}/opeDeviceVersionTemp/list';
     	    	art.dialog.alert(dataObj.msg);
 	    	}
        }

        function upload()
		{
        	var $fileSize=$("#fileSize").val();
		    if($fileSize>0)
		    {
		    	$("#file_upload").uploadify('upload');
		    	return true;
		    }
		    else
		    {
		    	art.dialog.alert('请选择要上传的文件!');
		    	return false;
		    }
		}
</script>
</head>
<body>
	  <center>
	  <jsp:include page="../common/head.jsp" />
		<div class="all">
				<div class="q_title">
				<div class="q_title_node" >事件配置</div>
		        <div class="clear" ></div>
				<div class="q_line" ></div>
			</div>
			<div class="q_content no_q_content">
				<form id="form">
			          <div class="q_content_all_border">
			          		 <div class="q_content_add margin-top">
							 	 <div class="add_node"><span class="red" >*&nbsp;</span>事件主题：</div>
							 	 <div class="add_content">
							 	 	<input class="input" type="text" name="" id="event_name" placeholder="4~100个字符 "
							 	 		value="<c:if test="${not empty title}">${title}</c:if><c:if test="${empty title}">${eventBrief.name }</c:if>"
							 	 	/>
							 	 </div>
							 	 <div class="clear"></div>
							 </div>
							 <div class="q_content_add">
							 	 <div class="add_node"><span class="red" >*&nbsp;</span>关键词：</div>
							 	 <div class="add_content">
							 	 	<input placeholder="可添加5组键词。组内关键词以空格隔开。一组关键词结束请写英文符号下的分号。"  class="input" type="text" name="" id="keywords" value="${eventKeyWords.keywords }"/>
							 	 </div>
							 	 <div class="clear"></div>
							 </div>
							 <div class="q_content_add">
							 	 <div class="add_node"><span class="red" >*&nbsp;</span>开始时间：</div>
							 	 <div class="add_content">
							 	 	<input class="inputDate" type="text" style="margin-top: 10px;" readonly="readonly" name="startTime" id="startTime" value="<fmt:formatDate value="${eventBrief.startTime }" type="date"/>" />
							 	 	<%-- &nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
							 	 	<input class="inputDate" type="text" readonly="readonly" name="endTime" id="endTime" value="<fmt:formatDate value="${eventBrief.endTime }" type="date"/>"/> --%>
							 	 </div>
							 	 <div class="clear"></div>
							 </div>
							 <div class="q_content_add">
							 	 <div class="add_node"><span class="red" >*&nbsp;</span>事件类型：</div>
							 	 <div class="add_content">
							 	 	<span class="orage" ><input  class="inputRadio" type="radio" name="radio_type" id="" value="1" checked="checked"/>我的事件</span>&nbsp;&nbsp;&nbsp;
							 	 	<span class="" ><input  class="inputRadio" type="radio" name="radio_type" id="" value="2" />行业政策</span>&nbsp;&nbsp;&nbsp;
							 	 	<span class="" ><input  class="inputRadio" type="radio" name="radio_type" id="" value="3" />其他品牌</span>&nbsp;&nbsp;&nbsp;
							 	 	<span class="" ><input  class="inputRadio" type="radio" name="radio_type" id="" value="4" />其他事件</span>
							 	 </div>
							 	 <div class="clear"></div>
							 </div>
							 <div class="q_content_add">
							 	 <div class="add_node">事件描述：</div>
							 	 <div class="add_content">
							 	 	<textarea class="textarea"  placeholder="4~180个字符 " name="" id="event_description">${eventBrief.eventDescription }</textarea>
							 	 </div>
							 	 <div class="clear"></div>
							 </div>
							 <input type="hidden" name="img_url" id="img_url" value=""/>
			  			</form>
							 <div class="q_content_add">
							 	 <div class="add_node">图片：</div>
							 	 <div class="add_content">
								 <form id="imgform" method="post" enctype="multipart/form-data">
					        		<input type="hidden" id="fileSize"/>
						        		<table width="250px" style="width: 250px;">
							            <tr>
						                <td height="30px"></td>
						                </tr>
						                <tr>
						                   <td valign="bottom" align="left"><input type="file" id="file_upload" name="file_upload" /></td>
						                </tr>
						                <tr>
						                   <td><div id="fileQueue"></div></td>
						                </tr>
									</table>
									</form>
									<img id="show_img" width="100px" height="75px" style="display:none;"/>
							 	 </div>
							 	 <div class="clear"></div>
							 </div>
							  
							 <div class="q_content_add height">
								
							 	<a class="q_search_a ftleft margin-tl margin50" href="javascript:void(0);" id="a_submit" eid="${eventBrief.eid }">确定</a>
							 	<c:if test="${!empty eventBrief.eid && eventBrief.eid gt 0}">
							 	<a class="q_search_a ftleft margin-tl gray-bg" href="javascript:delete_event(${eventBrief.eid });" >删除</a>
							 	</c:if>
							 	<!-- <a class="q_search_a ftright margin-tr orange-bg" href="javascript:void(0);" >继续添加</a> -->
							 	
							 </div>
						</div>
				  
			</div>

			<jsp:include page="../common/footer.jsp" />
		</div>
	</center>
	<input type="hidden" value="${eventBrief.eventType }" name="eventBrief_eventType"/>
	       
</body>
</html>
