<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/base.jsp"%>
<script type="text/javascript" src="${ctx}/media/js/left.js"></script>

<div class="b_left_line"></div>
<shiro:hasPermission name="/rgroup/list">
	<a class="left_dot left_a" href="${ctx}/rgroup/list">公司管理</a>
	<div class="b_left_line"></div>
</shiro:hasPermission>
<shiro:hasPermission name="/rorg/list">
	<a class="left_dot left_a" href="${ctx}/rorg/list">部门管理</a>
	<div class="b_left_line"></div>
</shiro:hasPermission>
<shiro:hasPermission name="/ruser/list">
	<a  id="user_role_a" class="left_a" >用户及权限设置</a>
	<div class="b_left_line"></div>
</shiro:hasPermission>

<div id="user_role_div"  style="display: none">
	<shiro:hasPermission name="/ruser/list">
		<a class="left_dot left_a second_a" href="${ctx}/ruser/list">操作用户</a>
		<div class="b_left_line"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/rrole/list">
		<a class="left_dot left_a second_a" href="${ctx}/rrole/list">用户权限</a>
		<div class="b_left_line"></div>
	</shiro:hasPermission>
</div>
<shiro:hasPermission name="/carbrand/list">
    <a id="carbrand_carserial_id" class="left_a" >品牌设置</a>
	<div class="b_left_line"></div>
</shiro:hasPermission>
<div  id="carbrand_carserial_div" style="display: none">
	<shiro:hasPermission name="/carbrand/list">
		<a class="left_dot left_a second_a" href="${ctx}/carbrand/list">厂牌设置</a>
		<div class="b_left_line"></div>
	</shiro:hasPermission>
	<shiro:hasPermission name="/carserial/list">
		<a class="left_dot left_a second_a" href="${ctx}/carserial/list">车系设置</a>
		<div class="b_left_line"></div>
	</shiro:hasPermission>
</div>





