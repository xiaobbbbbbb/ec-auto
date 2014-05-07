<!DOCTYPE html>
<html>
<head>
<title>负面情报</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.js"></script>
<link href="/static/mobiscroll/css/mobiscroll.custom-2.6.2.min.css" rel="stylesheet" type="text/css" />
<script src="/static/mobiscroll/js/mobiscroll.custom-2.6.2.min.js" type="text/javascript"></script>
<#include "../common/header_script.ftl">
</head>
<body>

	<div data-role="page" id="index_page">
		<div data-role="header" data-theme="c">
			<a href="#search_page" data-role="button" class="ui-btn-right" data-theme="c">筛选</a>
			<h1></h1>
		</div>

		<div data-role="content">
			<select data-mini="true" name="brandId" id="brandId" brandId="${brandId?c}" onchange="window.location.href= '/article/bad_article_index?page=1&userId=${userId?c}&brandId=' + parseInt($('#brandId').val());">
					<#list brands as brand>
						<#if brandId=brand.id>
						  <option value="${brand.id?c}" selected="selected">${brand.name} </option>
						<#else>
						  <option value="${brand.id?c}">${brand.name} </option>
						</#if>  					
					</#list>
			</select> 
			
			<span>	
				<#if searchLevel=1>
					负面等级：一般
				<#elseif searchLevel=2>
					负面等级：中等
				<#elseif searchLevel=3> 
					负面等级：严重
				</#if>
			</span>&nbsp;&nbsp;&nbsp;&nbsp;
			<span>
				<#if searchDateType=1>
					日期：近7天
				<#elseif searchDateType=2>
					日期：近30天
				<#elseif searchDateType=3> 
					日期：${searchBdate}至${searchEdate}
				</#if>
			</span>
			
			<ul data-role="listview" data-inset="true">
				<#if page.list??> 
				<#list page.list as dto>
			    		    
			    <li>
			    <a href="/article/detail?articleId=${dto.id?c}&userId=${userId?c}">
				    <h2>${dto.title}</h2>
				    <p>${dto.simpleContent}</p>
				    <p><strong>${dto.ctime?date}</strong></p>
			    </a>
			    </li>
			    </#list> 
				</#if>
			</ul>
			
			<#if page??> 
			<div data-role="controlgroup" data-type="horizontal" id="pager" pageSize="${page.pageSize?c}">
			    <a href="/article/bad_article_index?page=${page.prePage?c}&userId=${userId?c}" onclick="appendBrandId(this); return true;" data-role="button" data-icon="arrow-l" data-iconpos="left">上一页</a>
			    <a href="/article/bad_article_index?page=${page.nextPage?c}&userId=${userId?c}" onclick="appendBrandId(this); return true;" data-role="button" data-icon="arrow-r" data-iconpos="right">下一页</a>
			         页次${page.page?c}/${page.totalPage?c}
			</div>
			</#if> 
			
			<script type="text/javascript">
				function appendBrandId(link) {
					var brandId = parseInt($("#brandId").val());
					$(link).attr("href", $(link).attr("href") + "&brandId=" + brandId);
				}
			</script>
		</div>

		<#include "../common/footer.ftl">
	</div>
	
	<#include "search.ftl">
	<#include "../common/message.ftl">
</body>
</html>