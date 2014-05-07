<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
<title>系统设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/media/css/backend_add_updte.css" rel="stylesheet" type="text/css"  media="screen"/>
<script type="text/javascript">
</script>
</head>
	<body>
      <div class="iframeH">
          <form id="form">
	          <input id="status" type="hidden" value="${status }"/>
	          <input id="id" type="hidden" value="${dto.id }"/>
	          <table class="add_update_table" >
		      	<tr>
		         	<td align="right">分类(必填)</td>
		        	<td class="td_left">
	                    <select name="type" id="type">
	                    <c:choose>
					  		<c:when test="${myBrand>0}" >    
					  			<option value="2" ${dto.type==2 ?'selected="selected"':''}>竞品</option>
					   		</c:when>
					   
					   		<c:otherwise>  
					   			<option value="1" ${dto.type==1 ?'selected="selected"':''}>我的</option>			
							 	<option value="2" ${dto.type==2 ?'selected="selected"':''}>竞品</option>	
					   		</c:otherwise>
					  
						</c:choose>
			    		</select>
	                </td>
	            </tr>                    
	            <tr>
		         	<td align="right">厂牌:</td>
		         	<td class="td_left">
		                <input name="name" type="text" id="name" value="${dto.name }"  />
		            </td>
		    	</tr>
		 	 </table>
	  	</form>
      </div>
	</body>
</html>
