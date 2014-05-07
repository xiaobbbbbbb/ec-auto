<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="add_dlg" class="easyui-dialog" title="添加用户" style="padding:10px;width:320px;"
			data-options="modal:true, closed:true, iconCls: 'icon-save',buttons:add_buttons">
		
	    <form id="add_form" method="post" style="display:block;">
	    	<table>
	    		<tr>
	    			<td>登录名:</td>
	    			<td><input class="easyui-validatebox" type="text" name="loginName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>显示名:</td>
	    			<td><input class="easyui-validatebox" type="text" name="displayName" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td><input class="easyui-validatebox" type="password" name="passwd" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>重复密码:</td>
	    			<td><input class="easyui-validatebox" type="password" name="repasswd" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱:</td>
	    			<td><input class="easyui-validatebox" name="email" data-options="required:true,validType:'email'"/></td>
	    		</tr>
	    		<tr>
	    			<td>厂牌:</td>
	    			<td>
	    				<select class="search-field"   name="brandId" style="width:150px;" onchange="selectSerialIdByBrandIdAdd();">
		    				<c:forEach var="brands" items="${brandLists}">
		    					<option value="${brands.id}">${brands.name}</option>
		    				</c:forEach>	
		    			</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>车系:</td>
	    			<td>
	    				<select class="search-field"  id="add_seria_id" name="serialId" style="width:150px;">
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
				doAddUser();
			}
		},{
			text:'Cancel',
			handler:function(){
				$("#add_dlg").dialog('close');
			}
		}];
		
		function doAddUser () {			
			 var $form = $("#add_form");
			 if ($form.attr("submiting") == "true") {
				return; 
			 }
			 
			 $form.attr("submiting", "true");		 				
			 AjaxUtils.ajaxFormSubmit($form, "/user/doadd", function(json) {
					if (json.succ=='Y') {
						window.location.href = "/user/index";
					} else {
						alert("提交失败");
					}
					$form.attr("submiting", "false");
			},function () {
				alert("提交失败");
				$form.attr("submiting", "false");
			});
		}
		
		
		function selectSerialIdByBrandIdAdd() {
			var brandId = FormUtils.formParams2Json($("#add_form")).brandId; 
			if(brandId<=0){
				return;
			}
			$.ajax({type: "post", 
                url: "/user/serialIds?brandId=" + brandId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '">' + this.name + '</option>';
                	}); 
                    $("#add_seria_id").html(html);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
	</script>	
	
