<!DOCTYPE html>
<html>
<head>
<title>产品观点</title>
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
			<h1></h1>
			<a href="#search_page" data-role="button" class="ui-btn-right" data-theme="c">筛选</a>
		</div>

		<div data-role="content">
			<select data-mini="true" name="brandId" id="brandId" brandId="${brandId?c}" onchange="window.location.href= '/viewpoint/index?page=1&userId=${userId?c}&brandId=' + parseInt($('#brandId').val());">
					<#list brands as brand>
						<#if brandId=brand.id>
						  <option value="${brand.id?c}" selected="selected">${brand.name} </option>
						<#else>
						  <option value="${brand.id?c}">${brand.name} </option>
						</#if>  					
					</#list>
			</select> 
			
			<span>	
				<#if searchLevel=3>
					评价程度：好评
				<#elseif searchLevel=2>
					评价程度：中评
				<#elseif searchLevel=1> 
					评价程度：差评
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
			
			<ul data-role="listview" data-inset="true" data-theme="c">
				<#if viewpoints??> 
				<#list viewpoints as dto>
				<#if dto.articlePercent gt 0>
			    <li>
			    <a href="/viewpoint/detail?viewpointId=${dto.id?c}&userId=${userId?c}&brandId=${brandId}&searchDateType=${searchDateType}&searchBdate=${searchBdate}&searchEdate=${searchEdate}&articlePercent=${dto.articlePercent}">
			    	<#if dto_index=0>
						<img src="/static/image/01.png" alt="1" class="ui-li-icon ui-corner-none">
					<#elseif dto_index=1>
						<img src="/static/image/02.png" alt="2" class="ui-li-icon ui-corner-none">
					<#elseif dto_index=2> 
						<img src="/static/image/03.png" alt="3" class="ui-li-icon ui-corner-none">
					</#if>
				    ${dto.name}
				    <p class="ui-li-aside"><strong>${(100*dto.articlePercent)?string("0.##")}%(${dto.articleCount?c})</strong></p>
			    </a>
			    </li>
			    </#if>
			    </#list> 
				</#if>
			</ul>
		</div>

		<#include "../common/footer.ftl">
	</div>
	
	<#include "search.ftl">
	<#include "../common/message.ftl">
</body>
</html>