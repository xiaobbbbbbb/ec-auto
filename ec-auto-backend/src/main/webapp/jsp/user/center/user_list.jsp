<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<table id="table" class="easyui-datagrid" style="100%;"
			title="前台用户列表" iconCls="icon-save"
			data-options="pageSize:20,rownumbers:true,pagination:true,singleSelect:true,url:'/user/list',method:'get',toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th field="showLoginName" width="80">登录名</th>
				<th field="showDisplayName" width="100">显示名</th>
				<th field="showEmail" width="150">邮箱</th>
				<th field="showBrandId"  width="150">厂牌</th>
				<th field="showSerialId"  width="150">车系</th>
				<th field="showCtime" width="150" align="right">创建时间</th>
			</tr>
		</thead>		
	</table>
	<script type="text/javascript">
		var toolbar = [{
			text:'新增',
			iconCls:'icon-add',
			handler:function(){$("#add_dlg").dialog('open');}
		},{
			text:'编辑',
			iconCls:'icon-edit',
			handler:function(){
				edit();
			}
		},'-',{
			text:'删除',
			iconCls:'icon-cancel',
			handler:function(){
				var row = $('#table').datagrid('getSelected');
	            if (row){
					$.messager.confirm("操作提示", "您确定要执行该操作吗？", function (data) {
			            if (data) {
			            	deleteUser(row.id);
			            }
			        });
					
				} else {
   					$.messager.alert("操作提示", "请选择一条记录", "info");
				}
			}
		}];
		
		function edit(){
			var row = $('#table').datagrid('getSelected');
            if (row){
            	$('#edit_dlg').dialog('open');
            	renderSerialIdByBrandId(row.brandId);
                $('#edit_form').form('load',row);
            } else {
				$.messager.alert("操作提示", "请选择一条记录", "info");
			}
		}
		
		function deleteUser(id) {
			AjaxUtils.ajaxPost("/user/dodelete", {"userId":id}, function(json) {
   				if (json.succ=='Y') {
					window.location.href = "/user/index";
   				} else {
   					$.messager.alert("操作提示", json.desc, "error");
   				}
   		 	});
		}
		$(function() {
			$("#table").datagrid('getPager').pagination({
                layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh']
            });
			selectSerialIdByBrandIdAdd();
		});
		
		
		function renderSerialIdByBrandId(brandId) {
			$.ajax({type: "post", 
                url: "/user/serialIds?brandId=" + brandId, 
                dataType: "json", 
                success: function (json) { 
                	var html = "";
                	$(json).each(function(){ 
                		html += '<option value="' + this.id + '"' + (this.id==brandId?' selected="selected"':'') +'>' + this.name + '</option>';
                	}); 
                    $("#edit_seria_id").html(html);
                }, 
                error: function (XMLHttpRequest, textStatus, errorThrown) { 
                	$.messager.alert("操作提示", textStatus, "error");
                } 
        	});
		}
	</script>
	<jsp:include page="add_user.jsp"></jsp:include>
	<jsp:include page="edit_user.jsp"></jsp:include>