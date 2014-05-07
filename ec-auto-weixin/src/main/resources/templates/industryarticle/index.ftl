<!DOCTYPE html>
<html>
<head>
<title>行业新闻</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.0/jquery.mobile-1.4.0.min.js"></script>
<body>

	<div data-role="page" id="home">
		<div data-role="header" data-theme="c">
			<h1></h1>
		</div>

		<div data-role="content">
			<ul data-role="listview" data-inset="true">
				<#if page.list??> 
				<#list page.list as dto>
			    		    
			    <li>
			    <a href="/industry_article/detail?articleId=${dto.id?c}&userId=${userId?c}">
				    <h2>${dto.title}</h2>
				    <p>${dto.simpleContent}</p>
				    <p><strong>${dto.ctime?date}</strong></p>
			    </a>
			    </li>
			    </#list> 
				</#if>
			</ul>
			
			<#if page??> 
			<div data-role="controlgroup" data-type="horizontal" id="pager" pageSize="${page.pageSize}">
			    <a href="/industry_article/index?page=${page.prePage?c}&userId=${userId?c}" data-role="button" data-icon="arrow-l" data-iconpos="left">上一页</a>
			    <a href="/industry_article/index?page=${page.nextPage?c}&userId=${userId?c}" data-role="button" data-icon="arrow-r" data-iconpos="right">下一页</a>
			         页次${page.page?c}/${page.totalPage?c}
			</div>
			</#if> 
			
		</div>

		<#include "../common/footer.ftl">
	</div>
</body>
</html>