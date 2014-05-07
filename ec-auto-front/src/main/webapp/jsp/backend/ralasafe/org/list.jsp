<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
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
function add() {
	var url="${ctx}/rorg/addUI";
	add_update(url,"添加");
}

function update(id){
	var	url="${ctx}/rorg/"+id+"/edit";
	add_update(url,"修改");
}

function del(id,disabled){
	var message="部门信息确认停用？";
	if(disabled==1){
		 message="部门信息确认启用？";
	}
	artDialog({	
          content:message,
          lock:true,
          style:'succeed noClose'
       },
       function(){
    	    var	url="${ctx}/rorg/"+id+"/delete?disabled="+disabled;
	   		$.post(url,function(json){
	   			 if(json.success){
	   				window.location.href="${ctx}/rorg/list";
	   			 }else{
	   				 art.dialog.alert(json.msg);
	   			 }
	   		});
       },
       function(){}
	);
	 
}

//添加和修改
function add_update(url,tit){
	if(art.dialog.list[1]){
		art.dialog.list[1].close();
	}
	art.dialog.open(url, {
		id: 1,
	    width: 400,
	    height: 150,
	    title: tit+'部门信息',
        okVal:'保存',
	    ok: function () {
	    	if(tit=='修改'){
	    		reqUrl="${ctx}/rorg/update";
	    	}
	    	if(tit=='添加'){
	    		reqUrl="${ctx}/rorg/add";
	    	}
	    	var form=$(this.iframe.contentWindow.document);
	    	var $name = form.find("#name");
	    	if ($.trim($name.val())=="") {
	    		art.dialog.alert("名称不能为空");
    			return false;
    		}
	    	$.ajax({
				url : reqUrl,
				type : "POST",
				contentType : "application/json;charset=utf-8",
				data : $.toJSON(form.find('#form').serializeObject()),
				dataType : "json",
				success : function(data,textStatus) {
					if (data.success) {
						window.location.href="${ctx}/rorg/list";
					}
					else
					{
						art.dialog.alert(data.msg);
					}
				}
			});
    		 
    		return false;
	    	 	    	
	    },
	    cancel: true
       });
   }
	
//搜索
function search()
{
	if(art.dialog.list[10000]){
		art.dialog.list[10000].close();
	}
	$.ec.searchUI({
		width: 350,
		height: 200,
		urlUI : '${ctx}/rorg/searchUI',    //打开UI的界面的地址
		urlList : '${ctx}/rorg/list',  //搜索完成后查询加载的地址
		title : '部门资料检索'
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
     			<%@ include file="../../left.jsp"%>
     		</td>
     		<td class="b_right" valign="top"  >
     			<div class="b_c_title">
     				<span>部门管理</span>
     			</div>
     			<div class="b_c_second_title">
     				<div class="second_title_l">
     					<span>部门列表</span>
     				</div>
     				<div class="second_title_r">
     					<a href="javascript:add();" >添加</a>
     					<a href="javascript:search();" >检索</a>
     				</div>
     				<div class="clear" ></div>
     			</div>
     			<div class="b_c_line" ></div>
     			<div class="b_c_content" >
     				<table style="width:900px;" class="b_c_content_table" cellpadding="0px" cellspacing="0px" >
     					 <thead>
			                <tr>
			                  <th  width="40px;">序号</th>
			                  <th>部门名称</th>
			                  <th>上级部门</th>
			                  <th>是否停用</th>
			                  <th>更新人</th>
			                  <th>更新时间</th>
			                  <th width="90px">操作</th>
			                </tr>
			              </thead>
			              <tbody>
			              	<c:forEach items="${ECPage.list}" var="dto" varStatus="sn" >
				              	<tr>
				                   <td>${sn.count}</td>
				                   <td>${dto.name }</td>
				                   <td>${dto.parentName }</td>
				                   <td>
				                      <c:if test="${dto.disabled=='1'}">启用</c:if>
			   					      <c:if test="${dto.disabled=='0'}">停用</c:if>
			   					   </td>
				                   <td>${dto.updateUserName }</td>
			                 	   <td><fmt:formatDate pattern="yyyy-MM-dd HH:ss:mm" value="${dto.updateTime }"/></td>
				                   <td>
				                   		 <a href="javascript:void(0);" title="编辑" class="update_a" onclick="update(${dto.orgId })"></a>
				                   		 &nbsp;&nbsp;&nbsp;
				                   		 <c:if test="${dto.disabled=='1'}">
				                   		 	<a href="javascript:void(0);" title="停用" class="unlock_a" onclick="del(${dto.orgId },0)"></a>
				                   		 </c:if>
				                   		 <c:if test="${dto.disabled=='0'}">
				                   			<a href="javascript:void(0);" title="启用" class="lock_a"  onclick="del(${dto.orgId },1)"></a>
				                   		</c:if>
				                   </td>  
				                </tr>
			              	</c:forEach>
			              	<c:if test="${empty ECPage.list}">
			              		<tr>
				                   <td colspan="7">暂无数据</td>
				                </tr>
			              	</c:if>
			              </tbody>
     				</table>
     				<c:if test="${not empty ECPage.list}">
	     				<div class="demo" style="width: 900px" >
	    				 	<div id="page" class="gradient_bottomleft_bottomright">
							   <form id="form" action="${ctx}/rorg/list" method="get">
							       <input type="hidden" name="name" value="${param.name}" />
						           <input type="hidden" name="disabled" value="${param.disabled}" />
							       <jsp:include page="../../../common/pager_new.jsp" />
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