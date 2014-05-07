<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="add_dlg" class="easyui-dialog" title="添加" style="padding:10px;width:320px;"
			data-options="modal:true, closed:true, iconCls: 'icon-save',buttons:add_buttons">
		
	    <form id="add_form" method="post" style="display:block;">
	    	<table>
	    		<tr>
	    			<td>名字:</td>
	    			<td><input class="easyui-validatebox" style="width:200px;" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>字母:</td>
	    			<td>
	    				<select class="easyui-combobox" name="letter" style="width:150px;">
	    					<option value="A">A</option>
	    					<option value="B">B</option>
	    					<option value="C">C</option>
	    					<option value="D">D</option>
	    					<option value="E">E</option>
	    					<option value="F">F</option>
	    					<option value="G">G</option>
	    					<option value="H">H</option>
	    					<option value="I">I</option>
	    					<option value="J">J</option>
	    					<option value="K">K</option>	    					
	    					<option value="L">L</option>
	    					<option value="M">M</option>
	    					<option value="N">N</option>
	    					<option value="O">O</option>
	    					<option value="P">P</option>	    					
	    					<option value="Q">Q</option>
	    					<option value="R">R</option>
	    					<option value="S">S</option>
	    					<option value="T">T</option>
	    					<option value="U">U</option>
	    					<option value="V">V</option>
	    					<option value="W">W</option>
	    					<option value="X">X</option>
	    					<option value="Y">Y</option>
	    					<option value="Z">Z</option>
	    				</select>
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
			 AjaxUtils.ajaxFormSubmit($form, "/carbrand/doadd", function(json) {
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
	
