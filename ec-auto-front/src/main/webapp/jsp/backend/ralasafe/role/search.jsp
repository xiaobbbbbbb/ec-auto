<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>角色管理</title>
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
		           <table class="add_update_table" style="width: 300px;">
				      <tr>
				         <td align="right">角色名称：</td>
				         <td class="td_left"><input name="name" /></td>
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
