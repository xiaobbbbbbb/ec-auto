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

<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>

<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/jquery.ec-business.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/media/js/paginate/css/style.css" media="screen" />
<script type="text/javascript" src="${ctx}/media/js/ec/jquery.ec-business.js"></script>

<script type="text/javascript">
function add() {
	var url="${ctx}/rrole/addUI";
	add_update(url,"添加");
}

function update(id){
	var	url="${ctx}/rrole/"+id+"/updateUI";
	add_update(url,"修改");
}

function del(id){
	var	url="${ctx}/rrole/delete?ids="+id;
	artDialog(
        {	
            content:'角色信息删除后将不可使用，确认删除？',
            lock:true,
            style:'succeed noClose'
        },
        function(){
        	$.post(url,function(json){
	   			 if(json.success){
	   				window.location.href="${ctx}/rrole/list";
	   			 }else{
	   				 art.dialog.alert(json.msg);
	   			 }
	   		});
        },
        function(){ }
	);
}


function check(form)
{
		var $name=$.trim(form.find("#name").val()),
		$ids=$.trim(form.find("#ids").val());
       if($name.length==0)
       {
       	art.dialog.alert('请输入权限名称!');
       	return false; 
       }
       else if($ids.length==0)
       {
       	art.dialog.alert('请选择授权菜单!');
       	return false; 
       }
       return true; 
}

//添加和修改
function add_update(url,tit)
{	
	if(art.dialog.list[1]){
		art.dialog.list[1].close();
	}
	art.dialog.open(url, {
		id:1,
	    width: 620,
	    height: 350,
        title: tit+'角色',
	    ok: function () {
	    	var contentWindow=this.iframe.contentWindow;
	    	//加载弹出层中选中的资源
	    	contentWindow.loadIds();
	    	var form=$(contentWindow.document);
	    	if(check(form))
			{
	    		var urlType="add",
	    		roleId=$.trim(form.find("#roleId").val()),
	    		flag = false,
	    		oldName=$.trim(form.find("#oldName").val()),
	    		$newName=$.trim(form.find("#name").val());
	    		if(roleId.length>0)
				{
					urlType="update";
				}
				var url="${ctx}/rrole/"+urlType;
				$.ajax({
					url : url,
					type : "POST",
					contentType : "application/json;charset=utf-8",
					data : $.toJSON(form.find('#form').serializeObject()),
					dataType : "json",
					success : function(data,textStatus) {
						if (data.success) {
							window.location.href="${ctx}/rrole/list";
						}else{
							art.dialog.alert(data.msg);
						}
					}
				});
				
			}
	    	else
	    	{
	    		return false;
	    	}
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
		width: 330,
		height: 200,
		urlUI : '${ctx}/rrole/searchUI',    //打开UI的界面的地址
		urlList : '${ctx}/rrole/list',  //搜索完成后查询加载的地址
		title : '角色资料检索'
	});
}
</script>
</head>
<body>
	<div class="b_top">
		<img src="${ctx}/media/images/backend_logo.png" alt="" />
	</div>

	<table class="b_table" cellpadding="0px" cellspacing="0px">
		<tr>
			<td class="b_left" valign="top"><%@ include file="../../left.jsp"%></td>
			<td class="b_right" valign="top">
				<div class="b_c_title">
					<span>角色管理</span>
				</div>
				<div class="b_c_second_title">
					<div class="second_title_l">
						<span>角色列表</span>
					</div>
					<div class="second_title_r">
						<a href="javascript:add();" >添加</a>
						<a href="javascript:search();" >检索</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="b_c_line"></div>
				<div class="b_c_content">
					<table style="width: 900px;" class="b_c_content_table" cellpadding="0px" cellspacing="0px">
						<thead>
							<tr>
								<th  width="40px;">序号</th>
								<th>角色</th>
								<th>更新人</th>
								<th>更新时间</th>
								<th>备注</th>
								<th width="90px;">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list}" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.name }</td>
									<td>${dto.updateUserName }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.updateTime }" /></td>
									<td>${dto.message }</td>
									<td><a href="javascript:update(${dto.roleId });" title="修改" class="update_a"  ></a>&nbsp;&nbsp;&nbsp;<a href="javascript:del(${dto.roleId });" title="删除"  class="delete_a"></a></td>
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
						<div class="demo" style="width: 900px" >
	    				 	<div id="page" class="gradient_bottomleft_bottomright">
							   <form id="form" action="${ctx}/rrole/list" method="get">
							       <input type="hidden" name="name" value="${param.name}" />
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