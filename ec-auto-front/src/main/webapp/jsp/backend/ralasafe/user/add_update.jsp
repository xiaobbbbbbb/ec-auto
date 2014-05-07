<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<link href="${ctx}/media/css/backend_add_updte.css" rel="stylesheet" type="text/css"  media="screen"/>
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript" src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<script type="text/javascript">
	var oldPass = "";
	var oldPass2 = "";
	$(function() {
		oldPass = $("#password").val();
		oldPass2 = $("#reqPassword").val();
	});

	function enter(which) {
		if (which == 1) {
			$("#password").val("");
		} else {
			$("#reqPassword").val("");
		}
	}

	function leave(which) {
		if (which == 1) {
			var newPass = $("#password").val();
			if (newPass == "") {
				$("#password").val(oldPass);
			} else {
				oldPass = newPass;
			}
		} else {
			var newPass2 = $("#reqPassword").val();
			if (newPass2 == "") {
				$("#reqPassword").val(oldPass2);
			} else {
				oldPass2 = newPass2;
			}
			var password=$("#password").val();
			var reqPassword=$("#reqPassword").val();
			
			if(password!=reqPassword){
				art.dialog.alert("密码不一致，请从新输入");
				$("#reqPassword").val('');
				return ;
			}
		}
	}
</script>
</head>

<body>
	<div class="iframeH" >
		<form id="form">
				<input type="hidden" id="userId" name="userId" value="${dto.userId }" /> <input type="hidden" id="disabled" name="disabled"
					value="${dto.disabled }" /> <input type="hidden" id="oldName" value="${dto.loginName}" /> <input type="hidden" name="oldPassword"
					value="${dto.password}" />
				<table class="add_update_table" >
					<tr>
						<td align="right">登录名：</td>
						<td class="td_left">
							<input name="loginName" type="text" id="loginName" value="${dto.loginName }" />[必填]
						</td>
					</tr>
					<tr>
						<td align="right">密码：</td>
						<td class="td_left"><input name="password" onfocus="enter(1)" onblur="leave(1)" type="password" id="password" value="${dto.password}" />[必填]</td>
					</tr>
					<tr>
						<td align="right">确认密码：</td>
						<td class="td_left"><input onfocus="enter(2)" onblur="leave(2)" type="password" id="reqPassword" value="${dto.password}" />[必填]</td>
					</tr>
					<tr>
						<td align="right">所属部门：</td>
						<td class="td_left"><select name="orgId">
								<c:forEach items="${orgs}" var="org" varStatus="sn">
									<option value="${org.orgId }" <c:if test="${dto.orgId==org.orgId}">selected="selected"</c:if>>${org.name }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td align="right">用户角色：</td>
						<td class="td_left"><select name="roleIds">
								<c:forEach items="${roles}" var="role" varStatus="sn">
									<option value="${role.roleId }" <c:if test="${role.roleId==roleId}">selected="selected"</c:if>>${role.name }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td align="right">姓名：</td>
						<td class="td_left"><input id="name" name="name" value="${dto.name }" />[必填]</td>
					</tr>
					<tr>
						<td align="right">职位：</td>
						<td class="td_left">
						  <input id="job" name="job" value="${dto.job }" />
						</td>
					</tr>
					<tr>
						<td align="right">邮箱：</td>
						<td class="td_left"><input id="email" name="email" value="${dto.email }" />[必填]</td>
					</tr>
					<tr>
						<td align="right">手机：</td>
						<td class="td_left"><input id="phone" name="phone" value="${dto.phone }" />[选填]</td>
					</tr>
					
				</table>
		</form>
	</div>
</body>
</html>
