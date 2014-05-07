<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<div id="pager_params" url="/carbrand/list"></div>
	<table id="table" class="easyui-datagrid" style=""
			title="厂牌列表" iconCls="icon-save"
			data-options="pageSize:20,pagination:true,singleSelect:true, method:'get',toolbar:'#table_toolbar'">
		<thead>
			<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th field="name" width="200">名字</th>
				<th field="letter" width="100">字母</th>
				<th field="img" width="100">图片</th>
				<th field="isValid" width="100">状态</th>
				<th field="orderNo" width="100">排序</th>

			</tr>
		</thead>		
	</table>
	
 	<div id="table_toolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" title="新增" plain="true" onclick="add();return false;"></a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" title="编辑" plain="true" onclick="edit();return false;"></a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" title="作废" plain="true" onclick="discardRecord(true);return false;"></a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" title="启用" plain="true" onclick="discardRecord(false);return false;"></a>
		</div>
		<div>
			<form id="search_form">
	    	名字:
	    	<input class="search-field" style="width:200px;" type="text" name="name"></input>
	    	状态:
	    	<select class="easyui-combobox search-field" name="isValid" style="width:50px;">
	    					<option value="-1">全部</option>
	    					<option value="0">无效</option>
	    					<option value="1">有效</option>
	    				</select>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"  onclick="searchRecord();return false;">Search</a>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
		function add(){
			$('#add_dlg').dialog('open');
		}
	
		function edit(){
			var row = $('#table').datagrid('getSelected');
            if (row){            	
            	$('#edit_dlg').dialog('open');
                $('#edit_form').form('load',row); 
            } else {
					$.messager.alert("操作提示", "请选择一条记录", "info");
			}
		}
		
		function searchRecord() {
			 loadPage($('#table').datagrid('getPager'), 1);
		}
		
		function discardRecord(discard) {
			var row = $('#table').datagrid('getSelected');
            if (row){
				$.messager.confirm("操作提示", "您确定要执行该操作吗？", function (data) {
		            if (data) {
		            	doDiscard(row.id, discard);
		            }
		        });
				
			} else {
					$.messager.alert("操作提示", "请选择一条记录", "info");
			}
		}
		
		function doDiscard(id, discard) {
			AjaxUtils.ajaxPost("/carbrand/discard", {"brandId":id, "discard":discard}, function(json) {
   				if (json.succ=='Y') {
   					top.document.location.href ="/carbrand/index";
   				} else {
   					$.messager.alert("操作提示", json.desc, "error");
   				}
   		 	});
		}
		$(function() {
			$("#table").datagrid('getPager').pagination({
                layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh']
            });
		});
	</script>
	<jsp:include page="add_carbrand.jsp"></jsp:include>
	<jsp:include page="edit_carbrand.jsp"></jsp:include>
	<jsp:include page="../../common/pager.jsp"></jsp:include>