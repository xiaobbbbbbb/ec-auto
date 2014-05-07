<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="edit_dlg" class="easyui-dialog" title="编辑" style="padding:10px;width:500px;"
			data-options="modal:true, closed:true, iconCls: 'icon-save',buttons:edit_buttons">
		
	    <form id="edit_form" method="post" style="display:block;">
	    	<table>
	    		<tr>
	    			<td>标题:</td>
	    			<td><input class="easyui-validatebox" style="width:350px;" type="text" name="title" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>内容简介:</td>
	    			<td><textarea class="easyui-validatebox" style="width:350px;height: 100px;" name="simpleContent" data-options="required:true"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td>地址:</td>
	    			<td><input class="easyui-validatebox"  style="width:350px;" type="text" name="url"  data-options="required:true"></input><a href="javascript:void(0);" onclick="var url=$(this).parent().find('input').val(); window.open(url, '_blank');">查看</a></td>
	    		</tr>
	    		<tr>
	    			<td>媒体:</td>
	    			<td>
	    				<select class="easyui-combobox" name="mediaId" style="width:150px;">
	    					<c:forEach var="media" items="${medias }">
	    						<option value="${media.id }">${media.name }</option>
	    					</c:forEach>
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
				doEditIndustryArticle();
			}
		},{
			text:'Cancel',
			handler:function(){
				$("#edit_dlg").dialog('close');
			}
		}];
		
		function doEditIndustryArticle () {			
			 var $form = $("#edit_form");
			 if ($form.attr("submiting") == "true") {
				return; 
			 }
			 
			 $form.attr("submiting", "true");		 				
			 AjaxUtils.ajaxFormSubmit($form, "/industry_article/doedit", function(json) {
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
	
