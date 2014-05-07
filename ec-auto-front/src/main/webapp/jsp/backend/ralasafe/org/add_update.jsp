<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/backend_add_updte.css" rel="stylesheet" type="text/css"  media="screen"/>
<script type="text/javascript">
	
</script>
</head>

<body>
	<div>
		<form id="form">
			<center>
			    <input type="hidden" id="orgId" name="orgId" value="${dto.orgId }" />
				<table class="add_update_table"  >
					<tr>
						<td align="right">上级部门：</td>
						<td class="td_left"><select name="parentId" >
								<c:forEach items="${orgs}" var="dto" varStatus="sn">
									<option value="${dto.orgId}" <c:if test="${dto.orgId==dto.parentId}">selected="selected"</c:if>>${dto.name}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<td align="right">部门名称：</td>
						<td class="td_left"><input id="name" name="name" value="${dto.name }" /></td>
					</tr>
					
				</table>
			</center>
		</form>
	</div>
</body>
</html>
