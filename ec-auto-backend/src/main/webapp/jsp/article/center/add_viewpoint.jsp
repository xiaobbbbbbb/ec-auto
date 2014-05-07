<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="add_dlg" class="easyui-dialog" title="添加" style="padding:10px;width:320px;"
			data-options="modal:true, closed:true, iconCls: 'icon-save',buttons:add_buttons">
		
	    <form id="add_form" method="post" style="display:block;">
	    	<table>
	    		<tr>
	    			<td>名字:</td>
	    			<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true"></input></td>
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
			 AjaxUtils.ajaxFormSubmit($form, "/viewpoint/doadd", function(json) {
					if (json.succ=='Y') {
						$("#add_dlg").dialog('close');
						edit();
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
	
