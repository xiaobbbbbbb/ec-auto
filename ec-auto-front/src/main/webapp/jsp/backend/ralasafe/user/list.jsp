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
<link rel="stylesheet" type="text/css" href="${ctx}/media/js/paginate/css/style.css" media="screen" />
<script type="text/javascript" src="${ctx}/media/js/ec/jquery.ec-business.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_dateformat.js"></script>
<script type="text/javascript">
function add() {
	var url="${ctx}/ruser/addUI";
	add_update(url,"添加");
}

function update(id){
	var	url="${ctx}/ruser/"+id+"/updateUI";
	add_update(url,"修改");
}
function del(id,disabled){
	var message="用户信息确认停用？";
	if(disabled==1){
		 message="用户信息确认启用？";
	}
	artDialog(
        {	
            content:message,
            lock:true,
            style:'succeed noClose'
        },
        function(){
        	var	url="${ctx}/ruser/delete?id="+id+"&disabled="+disabled;
    		$.post(url,function(json){
    			 if(json.success){
    				window.location.href="${ctx}/ruser/list";
    			 }else{
    				 art.dialog.alert(json.msg);
    			 }
    		});
        },
        function(){ }
	);
}

//添加和修改
function add_update(url,tit){
	if(art.dialog.list[2]){
		art.dialog.list[2].close();
	}
	art.dialog.open(url, {
		id:2,
	    width: 500,
	    height: 350,
	    title: tit+'用户信息',
        okVal:'保存',
	    ok: function () {
	    	var form=$(this.iframe.contentWindow.document);
	    	var $loginName = form.find("#loginName");
	    	if ($.trim($loginName.val())=="") {
	    		art.dialog.alert("登录名不能为空");
    			return false;
    		}
	    	
	    	var $password = form.find("#password");
	    	if ($.trim($password.val())=="") {
	    		art.dialog.alert("密码不能为空");
    			return false;
    		}
	    	var $reqPassword = form.find("#reqPassword");
	    	if ($.trim($reqPassword.val())=="") {
	    		art.dialog.alert("确认密码不能为空");
    			return false;
    		}
	    	
	    	var $name = form.find("#name");
	    	if ($.trim($name.val())=="") {
	    		art.dialog.alert("姓名不能为空");
    			return false;
    		}
	    	var $email = form.find("#email");
	    	if ($.trim($email.val())=="") {
	    		art.dialog.alert("邮箱不能为空");
    			return false;
    		}
	    	if (!$email.val().match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)) {
	    		art.dialog.alert("邮箱格式不正确");
       			return false; 
       		}

	    	var reqUrl="";
	    	if(tit=='修改'){
	    		reqUrl="${ctx}/ruser/update";
	    	}
	    	if(tit=='添加'){
	    		reqUrl="${ctx}/ruser/add";
	    	}
	    	$.ajax({
				url : reqUrl,
				type : "POST",
				contentType : "application/json;charset=utf-8",
				data : $.toJSON(form.find('#form').serializeObject()),
				dataType : "json",
				success : function(data,textStatus) {
					if (data.success) {
						window.location.href="${ctx}/ruser/list";
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
			width: 360,
			height: 200,
			urlUI : '${ctx}/ruser/searchUI',    //打开UI的界面的地址
			urlList : '${ctx}/ruser/list',  //搜索完成后查询加载的地址
			title : '用户资料检索'
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
     				<span>用户管理</span>
     			</div>
     			<div class="b_c_second_title">
     				<div class="second_title_l">
     					<span>用户列表</span>
     				</div>
     				<div class="second_title_r">
     					<a href="javascript:void(0);" onclick="add()">添加</a>
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
			                  <th>登录名</th>
			                  <th>用户姓名</th>
			                  <th>职位</th>
			                  <th>所属部门</th>
			                  <th>邮箱</th>
			                  <th>手机</th>
			                  <th>是否停用</th>
			                  <th>更新时间</th>
			                  <th width="90px">操作</th>
			                </tr>
			              </thead>
			              <tbody id="dataTable">
			              	  <c:forEach items="${ECPage.list}" var="dto" varStatus="sn" >
				              	<tr>
				                   <td>${sn.count}</td>
				                   <td>${dto.loginName }</td>
				                   <td>${dto.name }</td>
				                   <td>${dto.job}</td>
				                   <td>${dto.orgName}</td>
				                   <td>${dto.email }</td>
				                   <td>${dto.phone }</td>
				                   <td>
				                   	  <c:if test="${dto.disabled=='1'}">启用</c:if>
			   					      <c:if test="${dto.disabled=='0'}">停用</c:if>
				                   </td>
			                 	   <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.updateTime }" /></td>
				                   <td><a href="javascript:update(${dto.userId });" title="修改" class="update_a" ></a>&nbsp;&nbsp;&nbsp;
				                   	<%-- <a href="javascript:void(0);"  title="删除" class="delete_a"  onclick="del(${dto.userId })"></a> --%>
				                   	 <c:if test="${dto.disabled=='1'}">
			                   		 	<a href="javascript:void(0);" title="停用" class="unlock_a" onclick="del(${dto.userId },0)"></a>
			                   		 </c:if>
			                   		 <c:if test="${dto.disabled=='0'}">
			                   			<a href="javascript:void(0);" title="启用" class="lock_a"  onclick="del(${dto.userId },1)"></a>
			                   		</c:if>
				                   </td>  
				                </tr>
			              	</c:forEach> 
			              	<c:if test="${empty ECPage.list}">
			              		<tr>
				                   <td colspan="10">暂无数据</td>
				                </tr>
			              	</c:if>
			              </tbody>
     				</table>
     				<c:if test="${not empty ECPage.list}">
	     				<div class="demo" style="width: 900px" >
	    				 	<div id="page" class="gradient_bottomleft_bottomright">
							   <form id="form" action="${ctx}/ruser/list" method="get">
							       <input type="hidden" name="name" value="${param.name}" />
						           <input type="hidden" name="loginName" value="${param.loginName}" />
						           <input type="hidden" name="depId" value="${param.depId}" />
						           <input type="hidden" name="isAway" value="${param.isAway}" />
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