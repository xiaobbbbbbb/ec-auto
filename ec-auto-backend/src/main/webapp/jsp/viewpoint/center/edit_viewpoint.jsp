<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="edit_dlg" class="easyui-dialog" title="编辑" style="padding:10px;width:500px;"
			data-options="modal:true, closed:true, iconCls: 'icon-save',buttons:edit_buttons">
		
	    <form id="edit_form" method="post" style="display:block;">
	    	<table>
	    		<tr>
	    			<td>名字:</td>
	    			<td><input class="easyui-validatebox" style="width:350px;" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>情感说明:</td>
	    			<td>
	    				<select class="easyui-combobox" name="affection" style="width:150px;">
	    					<option value="3">正面</option>
	    					<option value="2">中性</option>
	    					<option value="1">负面</option>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>是否自动:</td>
	    			<td>
	    				<select class="easyui-combobox" name="isManual" style="width:150px;">
	    					<option value="1">手动</option>
	    					<option value="0">自动</option>	    					
	    				</select>
	    			</td>
	    		</tr>
	    	</table>
	    	<input type="hidden" name="id"/>
	    </form>
	</div>
</div>
<script type="text/javascript">
		var edit_buttons = [{
			text:'Ok',
			iconCls:'icon-ok',
			handler:function(){
				doEdit();
			}
		},{
			text:'Cancel',
			handler:function(){
				$("#edit_dlg").dialog('close');
			}
		}];
		
		function doEdit () {			
			 var $form = $("#edit_form");
			 if ($form.attr("submiting") == "true") {
				return; 
			 }
			 
			 $form.attr("submiting", "true");		
			 AjaxUtils.ajaxFormSubmit($form, "/viewpoint/doedit", function(json) {
					if (json.succ=='Y') {
						$("#edit_dlg").dialog('close');
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
	
