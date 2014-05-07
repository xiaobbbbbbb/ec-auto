<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>用户管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link href="${ctx}/media/css/backend_add_updte.css" rel="stylesheet" type="text/css"  media="screen"/>
        <script type="text/javascript">
        </script>
	</head>

	<body>
	    <table style="height:100%;">
		<tr>
		    <td valign="top">
		       <div class="iframeH" style="overflow: auto; margin-top: 15px;">
		           <form id="form">
		           <center>
		           <table class="add_update_table" style="width: 340px;">
				      <tr>
				         <td align="right">用户名：</td>
				         <td class="td_left"><input name="name"   /></td>
				      </tr>
				      <tr>
				         <td align="right">登录名：</td>
				         <td class="td_left"><input name="loginName"  /></td>
				      </tr>
				      <tr>
				      	<td align="right">职位：</td>
				         <td class="td_left"><input name="job"  /></td>
				      </tr>
				      <tr>
				         <td align="right">所属部门：</td>
				         <td class="td_left">
				             <select name="depId"  >
				             	<option value="">全部</option>
					             	<c:forEach items="${orgs }" var="odto">
					             		<option value="${odto.orgId }">${odto.name }</option>
					             	</c:forEach>
				             </select>
				         </td>
				      </tr>
				      
				      <tr>
				         <td align="right">是否停用：</td>
				         <td class="td_left searchPage">
				            <select id="selectKey" name="isAway">
				                    <option value="-1">全部</option>
									<option value="0">已停用</option>
									<option value="1">正常使用</option>
							</select>
				         </td>
				      </tr>
				  </table>
				  </center>
				  </form>
		       </div>
		    </td>
		</tr>
        </table>
	</body>
</html>
