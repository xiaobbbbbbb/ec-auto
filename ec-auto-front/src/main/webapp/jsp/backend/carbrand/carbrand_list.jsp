<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
<title>系统设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="${ctx}/media/css/index.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/backend.css" type="text/css" rel="stylesheet" />

<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/media/js/paginate/css/style.css" media="screen"/>
<script type="text/javascript" src="${ctx}/media/js/ec/jquery.ec-business.js"></script>
<script type="text/javascript">
/* $(function() {
	loadData();
}); */
function add() {
	var url="${ctx}/carbrand/goAdd";
	add_update(url,"添加");
}

function update(id){
	var	url="${ctx}/carbrand/goEdit?carBrandId="+id;
	add_update(url,"修改");
}

//添加和修改
function add_update(url,tit){	
	if(art.dialog.list[1]){
		art.dialog.list[1].close();
	}
	
	art.dialog.open(url, {
		id: 1,
	    width: 440,
	    height: 80,
        title: tit+'厂牌信息',
	    ok: function () {
	    	var $form=$(this.iframe.contentWindow.document);
	    	var $name = $form.find("#name");
	    	var $type = $form.find("#type");
	    	var $status = $form.find("#status");
	    	if ($.trim($name.val())=="") {
	    		art.dialog.alert("名称不能为空");
    			return false;
    		}
	    	var urlType=$status.val();
	    	if(urlType=='doAdd'){
	    		var param={"type":$type.val(),"name":$name.val()};
	    		var url="${ctx}/carbrand/"+urlType;
	    		$.post(url,param,function(json){
					 if(json.success){
						window.location.href="${ctx}/carbrand/list";
					 }else{
						 art.dialog.alert(json.msg);
					 }
				});
	    	}else if(urlType=='doUpdate'){
	    		var $id = $form.find("#id");
	    		var param={"type":$type.val(),"name":$name.val(),'id':$id.val()};
	    		var url="${ctx}/carbrand/"+urlType;
	    		$.post(url,param,function(json){
					 if(json.success){
						window.location.href="${ctx}/carbrand/list";
					 }else{
						 art.dialog.alert(json.msg);
					 }
				});
	    	}
	        
    		return false;
	    },
	    cancel: true
    });
}

function discardRecord(id,discardRecord){
	artDialog({	
        content:'厂牌删除后将不可使用，确认删除？',
        lock:true,
        style:'succeed noClose'
     },
     function(){ //确定
    	 var url="${ctx}/carbrand/discard?carBrandId="+id+"&discard="+discardRecord;
 		 $.getJSON(url, function(json) {
 			 if(json.success){
 				window.location.href="${ctx}/carbrand/list";
 			 }else{
 				art.dialog.alert(json.msg);
 			 }
 		 });
     },
     function(){}
	);
}
//搜索
function search()
{
	if(art.dialog.list[10000]){
		art.dialog.list[10000].close();
	}
		
 	$.ec.searchUI({
		width: 400,
		height: 150,
		urlUI : '${ctx}/carbrand/searchUI',    //打开UI的界面的地址
		urlList : '${ctx}/carbrand/list',  //搜索完成后查询加载的地址
		title : '厂牌资料检索'
	});
	
	
}

</script>
</head>
  <body>
     <div class="b_top">
     	<img src="${ctx}/media/images/backend_logo.png" alt="" />
     </div>
     
     <table class="b_table" cellpadding="0px" cellspacing="0px" >
     	<tr>
     		<td class="b_left" valign="top" >
     			<%@ include file="../left.jsp"%>
     		</td>
     		<td class="b_right" valign="top"  >
     			<div class="b_c_title">
     				<span>厂牌设置</span>
     			</div>
     			<div class="b_c_second_title">
     				<div class="second_title_l">
     					<span>厂牌列表</span>
     				</div>
     				<div class="second_title_r">
     					<a href="javascript:add();">添加</a>
     					<a href="javascript:search();">检索</a>
     				</div>
     				<div class="clear" ></div>
     			</div>
     			<div class="b_c_line" ></div>
     			<div class="b_c_content" >
    				<table class="b_c_content_table" cellpadding="0px" cellspacing="0px" >
    					 <thead>
			                <tr>
			                  <th width="40px;">序号</th>
			                  <th>厂牌</th>
			                  <th>厂牌归属</th>
			                  <th>编辑人</th>
			                  <th>编辑日期</th>
			                  <th width="90px" >操作</th>
			                </tr>
		              </thead>
		              <tbody id="dataTable">
		              		<c:forEach items="${ECPage.list}" var="dto" varStatus="sn" >
				              	<tr>
				                   <td>${sn.count}</td>
				                   <td>${dto.name }</td>
				                   <td>
				                      <c:if test="${dto.type=='1'}">我的</c:if>
			   					      <c:if test="${dto.type=='2'}">竞品</c:if>
			   					   </td>
				                   <td>${dto.editName }</td>
			                 	   <td><fmt:formatDate pattern="yyyy-MM-dd HH:ss:mm" value="${dto.editTime }"/></td>
				                   <td>
				                   		<a href="javascript:void(0);" title="修改" class="update_a" onclick="update(${dto.id })"></a>
				                   		<a href="javascript:void(0);" title="删除"  class="delete_a" onclick="discardRecord('${dto.id }',true)"></a>
				                   </td>  
				                </tr>
			              	</c:forEach>
			              	<c:if test="${empty ECPage.list}">
			              		<tr>
				                   <td colspan="6">暂无数据</td>
				                </tr>
			              	</c:if>
		              </tbody>
    				</table>
    				<c:if test="${not empty ECPage.list}">
	    				<div class="demo" >
	    				 	<div id="page" class="gradient_bottomleft_bottomright">
							   <form id="form" action="${ctx}/carbrand/list" method="get">
							       <input type="hidden" name="name" value="${param.name}" />
						           <input type="hidden" name="type" value="${param.type}" />
							       <jsp:include page="../../common/pager_new.jsp" />
							   </form>
			        		</div>
			        	</div>
			        </c:if>
     			</div>
     		</td>
     	</tr>
     </table>
     
     <div class="clear"></div>
  </body>
</html>
