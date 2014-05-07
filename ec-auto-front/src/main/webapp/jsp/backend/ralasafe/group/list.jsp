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
<link rel="stylesheet" type="text/css" href="${ctx}/media/js/paginate/css/style.css" media="screen" />
<script type="text/javascript" src="${ctx}/media/js/paginate/jquery.paginate.js"></script>

<script type="text/javascript">
 
function update(id){
	var	url="${ctx}/rgroup/"+id+"/edit";
	add_update(url,"修改");
}

function add_update(url,tit){
	if(art.dialog.list[1]){
		art.dialog.list[1].close();
	}
	art.dialog.open(url, {
		id: 1,
	    width: 330,
	    height: 200,
	    title: tit+'公司信息',
        okVal:'保存',
	    ok: function () {
	    	var form=$(this.iframe.contentWindow.document);
	    	$.ajax({
				url : "${ctx}/rgroup/update",
				type : "POST",
				contentType : "application/json;charset=utf-8",
				data : $.toJSON(form.find('#form').serializeObject()),
				dataType : "json",
				success : function(data,textStatus) {
					if (data.success) {
						window.location.href="${ctx}/rgroup/list";
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
					<span>公司管理</span>
				</div>
				<div class="b_c_second_title">
					<div class="second_title_l">
						<span>公司列表</span>
					</div>
					<div class="second_title_r">
					</div>
					<div class="clear"></div>
				</div>
				<div class="b_c_line"></div>
				<div class="b_c_content">
					<table style="width: 900px;" class="b_c_content_table" cellpadding="0px" cellspacing="0px">
						<thead>
							<tr>
								<th width="40px;">序号</th>
								<th>名称</th>
								<th>编辑人</th>
								<th>编辑时间</th>
								<th width="90px" >操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${groups}" var="dto" varStatus="sn">
								<tr>
									<td>${sn.count}</td>
									<td>${dto.name }</td>
									<td>${dto.updateUserName }</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${dto.updateTime }" /></td>
									<td><a style="margin-left: 30px" href="javascript:void(0);" title="编辑" class="update_a" onclick="update(${dto.groupId })"></a>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty groups}">
			              		<tr>
				                   <td colspan="5">暂无数据</td>
				                </tr>
			              	</c:if>
						</tbody>
					</table>
				</div>
			</td>
		</tr>
	</table>

	<div class="clear"></div>
</body>
</html>