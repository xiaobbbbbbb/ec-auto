<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<link href="${ctx}/media/js/ztree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/css/backend_add_updte.css" rel="stylesheet" type="text/css"  media="screen"/>
<script src="${ctx}/media/js/ztree3.5/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
<script src="${ctx}/media/js/ztree3.5/jquery.ztree.excheck-3.5.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function zTreeOnClick(event, treeId, treeNode, clickFlag) {
		//空实现，目的是在别的页面可以共用这棵树
	}
	function zTreeOnDblClick(event, treeId, treeNode) {
		//空实现，目的是在别的页面可以共用这棵树
	};

	//异步成功后
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		if (treeId == "treeDate") {
			expandAll();
		}
	}

	//加载选中的资源
	function loadIds() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDate"), nodes = treeObj
				.getCheckedNodes(true), array = new Array(); //用于保存 选中的那一条数据的ID
		for ( var i = 0; i < nodes.length; i++) {
			array.push(nodes[i].id); //将选中的值 添加到 array中 
		}
		$("#ids").val(array);
	}
</script>
<script type="text/javascript">
	$(function() {
		var $showUrl = $("#showUrl").val();
		var $roleId = $("#roleId").val();
		var setting = {
			check : {
				enable : true
			},
			async : {
				enable : true,
				type : "get",
				url : "${ctx}/rresource/tree?showUrl=" + $showUrl + "&&roleId="
						+ $roleId + "&&time=" + Math.random(),
				autoParam : [ "id" ],
				dataFilter : filter
			},
			callback : {
				onClick : zTreeOnClick,
				onDblClick : zTreeOnDblClick,
				onAsyncSuccess : onAsyncSuccess
			}
		};
		$.fn.zTree.init($("#treeDate"), setting);
	});

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for ( var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}

	//判断是否是父节点
	function getIsParent() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDate");
		var isParent = false;
		var sNodes = treeObj.getSelectedNodes();
		if (sNodes.length > 0) {
			isParent = sNodes[0].isParent;
		}
		return isParent;
	}

	//默认打开所有
	function expandAll() {
		var zTree = $.fn.zTree.getZTreeObj("treeDate");
		expandNodes(zTree.getNodes());
	}

	function expandNodes(nodes) {
		if (!nodes)
			return;
		var zTree = $.fn.zTree.getZTreeObj("treeDate");
		for ( var i = 0, l = nodes.length; i < l; i++) {
			zTree.expandNode(nodes[i], true, false, false);
			if (nodes[i].isParent && nodes[i].zAsync) {
				expandNodes(nodes[i].children);
			}
		}
	}
</script>
</head>

<body>
	<table style="height: 100%;">
		<tr>
			<td valign="top">
				<div style="overflow: auto; margin-top: 15px;">
					<form id="form">
						<center>
						<input id="ids" type="hidden" name="ids"/>
	        <input type="hidden" id="oldName" value="${dto.name}" />
	        <input type="hidden" id="roleId" value="${dto.roleId}" name="roleId"/>
							<table class="add_update_table" style="width: 580px;">
								<tr>
									<td align="right">角色名称：</td>
									<td class="td_left"><input id="name" name="name" value="${dto.name}" class="formText" type="text" style="width: 252px;" /></td>
								</tr>
								<tr>
									<td align="right">拥有权限：</td>
									<td class="td_left"><input type="hidden" id="selectId" /> <input type="hidden" id="selectName" /> <input type="hidden" id="isParent" />
										<input type="hidden" id="showUrl" value="false" /> <input type="hidden" id="parentId" />
										<ul id="treeDate" class="ztree" style="padding-top: 0px;"></ul></td>
								</tr>
								<tr>
									<td align="right">备注：</td>
									<td class="td_left"><textarea style="height: 70px;min-height:70px;max-height:70px;width: 252px;min-width: 252px;max-width: 252px;" name="message">${dto.message}</textarea></td>
								</tr>
							</table>
						</center>
					</form>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>