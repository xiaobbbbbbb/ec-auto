<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div id="pager_params" url="/industry_article/list"></div>
	<table id="table" class="easyui-datagrid" style=""
			title="动态新闻列表" iconCls="icon-save"
			data-options="pageSize:20,pagination:true,singleSelect:false,method:'get',toolbar:'#table_toolbar'">
		<thead>
			<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th field="sqId"></th>
				<th field="title" width="280">标题</th>
				<th field="showUrl" width="250">地址</th>
				<th field="showMediaId" width="100">站点</th>
				<th field="showPubTime" width="150">发布时间</th>
				<th field="showStatus" width="100">状态</th>
				<th field="showCtime" width="150">导入时间</th>
				
				<th field="mediaId" hidden="true"></th>
				<th field="status" hidden="true"></th>
				<th field="url" hidden="true"></th>
			</tr>
		</thead>		
	</table>
	<div id="table_toolbar" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" title="编辑" plain="true" onclick="edit();return false;"></a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" title="作废" plain="true" onclick="discardRecord(true);return false;"></a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" title="启用" plain="true" onclick="discardRecord(false);return false;"></a>
		</div>
		<div>
			<form id="search_form">
			站点:
			<select class="easyui-combobox" name="mediaId" style="width:150px;">
						<option value="-1">全部</option>
	    					<c:forEach var="media" items="${medias }">
	    						<option value="${media.id }">${media.name }</option>
	    					</c:forEach>
	    				</select>
	    	标题:
	    	<input style="width:200px;" type="text" name="title"></input>
	    	状态:
	    	<select class="easyui-combobox" name="status" style="width:50px;">
	    					<option value="-1">全部</option>
	    					<option value="0" selected="selected">初始</option>
	    					<option value="1">有效</option>
	    					<option value="2">无效</option>
	    				</select>
	    	发布时间段:
	    	<input id="search_pub_time_begin" class="easyui-datebox" name="search_pub_time_begin" value="${todayDate }"> — 
	    	<input id="search_pub_time_end" class="easyui-datebox" name="search_pub_time_end" value="${todayDate }">  
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search"  onclick="searchRecord();return false;">Search</a>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		function edit(){
	        var rows = $("#table").datagrid("getSelections");    // 获取所有选中的行
			if(rows.length>1){
				$.messager.alert("操作提示", "请选择一条记录", "info");
			}else{
				var row = $('#table').datagrid('getSelected');
				if(row){
					$('#edit_dlg').dialog('open');
		            $('#edit_form').form('load',row); 
				} else {
					$.messager.alert("操作提示", "请选择一条记录", "info");
				}
			}
		}
		
		function searchRecord() {
			 loadPage($('#table').datagrid('getPager'), 1);
		}
		
		function discardRecord(discard) {
	        var rows = $("#table").datagrid("getSelections");    // 获取所有选中的行
	        if(rows.length<=0){
	        	$.messager.alert("操作提示", "请选择至少一条记录", "info");
	        }else{
	        	var ids = [];
				for (var i = 0; i < rows.length; i++) {
				    var row = rows[i];
					ids.push(row.id);
				}
				$.messager.confirm("操作提示", "您确定要执行该操作吗？", function (data) {
		            if (data) {
		            	batchDiscardArticle(ids, discard);
		            }
		        });
	        }
		}
		
		function discardArticle(id, discard) {
			AjaxUtils.ajaxPost("/industry_article/discard", {"articleId":id, "discard":discard}, function(json) {
   				if (json.succ=='Y') {
   					loadPage($('#table').datagrid('getPager'));	
   				} else {
   					$.messager.alert("操作提示", json.desc, "error");
   				}
   		 	});
		}
		function batchDiscardArticle(ids, discard) {
			AjaxUtils.ajaxPost("/industry_article/batch_discard", {"articleIds":ids, "discard":discard}, function(json) {
   				if (json.succ=='Y') {
   					loadPage($('#table').datagrid('getPager'));	
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
	<jsp:include page="edit_industryarticle.jsp"></jsp:include>
	<jsp:include page="../../common/pager.jsp"></jsp:include>