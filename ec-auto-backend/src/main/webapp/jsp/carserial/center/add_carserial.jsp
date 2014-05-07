<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="add_dlg" class="easyui-dialog" title="添加" style="padding:10px;width:320px;"
			data-options="modal:true, closed:true, iconCls: 'icon-save',buttons:add_buttons">
		
	    <form id="add_form" method="post" style="display:block;">
	    	<table>
	    		<tr>
	    			<td>名字:</td>
	    			<td><input class="easyui-validatebox" style="width:200px;" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>厂牌:</td>
	    			<td>
	    				<select name="brandId" style="width:100px;">
	    					<option value="-1">全部</option>
	    					<c:forEach var="brand" items="${brands }">
	    						<option value="${brand.id }">${brand.name }</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>是否关注:</td>
	    			<td>
	    				<input type="radio" checked="checked" name="isFollow" value="1">关注</input>
	    				<input type="radio" name="isFollow" value="0">不关注</input>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>排序:</td>
	    			<td><input class="easyui-numberbox" precision="0" min="0" style="width:200px;" type="text" name="orderNo" data-options="required:true" value="0"></input></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
</div>
<script type="text/javascript">
		var add_buttons = [{
			text:'Ok',
			iconCls:'icon-ok',
			handler:function(){
				doAdd();
			}
		},{
			text:'Cancel',
			handler:function(){
				$("#add_dlg").dialog('close');
			}
		}];
		
		function doAdd () {			
			 var $form = $("#add_form");
			 if ($form.attr("submiting") == "true") {
				return; 
			 }
			 
			 $form.attr("submiting", "true");		 				
			 AjaxUtils.ajaxFormSubmit($form, "/carserial/doadd", function(json) {
					if (json.succ=='Y') {
						$("#add_dlg").dialog('close');
						loadPage($('#table').datagrid('getPager'));	
					} else {
						alert("提交失败");
					}
					$form.attr("submiting", "false");
			},function () {
				alert("提交失败");
				$form.attr("submiting", "false");
			});
		}
		
	</script>	
	
