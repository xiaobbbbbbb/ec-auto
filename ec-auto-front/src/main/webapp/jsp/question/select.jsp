<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
<title>系统设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/ec/ec_base.js"></script>
<script type="text/javascript">

	function getSerials(e){
		$("#serials option").remove();
		var url="${ctx}/question/serials?brandId="+e;
		$.getJSON(url,function(voData) {
			
			if (voData.length > 0) {	
				$.each(voData,function(index, value) {
					if('${otherSerialId}'!=value.id){
						$("#serials").append("<option value='"+value.id+"'>"+value.name+"</option>");
					}
				});
			}
		});
	}
	
</script>
</head>
	<body>
	    <table style="height:100%;">
		<tr>
		    <td valign="top">
		       <div class="iframeH" style="overflow: auto; ">
		           <form id="form">
		           <input id="id" type="hidden" value="${dto.id }"/>
		           <center>
		           <div style="width:400px;">
		           <div style="float:left;">
		           <select  multiple= "multiple" style="width:198px;height:180px;" id="brands">
				     <c:forEach items="${brandList }" var="dto" varStatus="sn">
				     	<option  value="${dto.id }" <c:if test="${dto.id ==brandId}"> selected="selected" </c:if> onclick="getSerials('${dto.id }')">${dto.name }</option>
				     </c:forEach>
					</select>
				  </div>
				  <div style="float:left">
		           <select  multiple= "multiple" style="width:198px;height:180px;" id="serials">
		           	 <c:forEach items="${serailList }" var="dto" varStatus="sn">
		           	 	<c:if test="${otherSerialId!=dto.id}">
				     	<option  value="${dto.id }" <c:if test="${dto.id==serialId}"> selected="selected" </c:if> >${dto.name }</option>
				     	</c:if>
				     </c:forEach>
					</select>
				  </div>	
				  </div>
				  </center>
				  </form>
		       </div>
		    </td>
		</tr>
        </table>
	</body>
</html>
